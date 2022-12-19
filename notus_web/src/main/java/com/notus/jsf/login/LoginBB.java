package com.notus.jsf.login;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.simplesecurity.RemoteClient;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.notus.jsf.dao.UserDAO;
import com.notus.jsf.e.User;
import com.notus.jsf.etc.Md5;
import com.notus.jsf.etc.Mess;

@Named
@RequestScoped
public class LoginBB {
	private static final String PAGE_MAIN = "/public/index.xhtml?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String email;
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String pass) {
		this.password = pass;
	}

	@Inject
	UserDAO userDAO;
	
	@Inject
	Md5 hash;
	
	public String doLogin() throws NoSuchAlgorithmException {
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		
		// 1. verify login and password - get User from "database"
		User user = userDAO.getUserFromDatabase(email, hash.getPassword(password));

		// 2. if bad login or password - stay with error info
		if (user == null) {
			Mess.add(FacesMessage.SEVERITY_WARN, "Błąd logowania", "Podana kombinacja adresu email i hasła jest nieprawidłowa.");
			return PAGE_STAY_AT_THE_SAME;
		}

		// 3. if logged in: get User roles, save in RemoteClient and store it in session
		
		RemoteClient<User> client = new RemoteClient<User>(); //create new RemoteClient
		client.setDetails(user);
		
		List<String> roles = userDAO.getUserRolesFromDatabase(user); //get User roles 
		
		if (roles != null) { //save roles in RemoteClient
			for (String role: roles) {
				client.getRoles().add(role);
			}
		}
	
		//store RemoteClient with request info in session (needed for SecurityFilter)
		HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
		client.store(request);
		
		// and enter the system (now SecurityFilter will pass the request)
		return PAGE_MAIN;
	}
	
	public String doLogout(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		//Invalidate session
		// - all objects within session will be destroyed
		// - new session will be created (with new ID)
		session.invalidate();
		return PAGE_MAIN;
	}
	
}
