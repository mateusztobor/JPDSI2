package com.notus.jsf.register;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.simplesecurity.RemoteClient;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import com.notus.jsf.dao.UserDAO;
import com.notus.jsf.e.User;
import com.notus.jsf.etc.Md5;
import com.notus.jsf.etc.Mess;
import com.notus.jsf.etc.StrongPassword;

@Named
@RequestScoped
public class RegisterBB {
	private static final String PAGE_MAIN = "/public/index.xhtml?faces-redirect=true&r=1";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String email;
	private String password;
	private String nick;
	private int type = 1;
	
	@Inject
	UserDAO userDAO;
	
	@Inject
	Md5 hash;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
	
	private Boolean valid() {
		Boolean valid=true;
		if(!StrongPassword.check(password)) {
			Mess.add(FacesMessage.SEVERITY_ERROR, "Słabe hasło", "Podane hasło nie spełnia wymagań!");
			valid=false;
		}
		
		if(nick.length() > 15) {
			Mess.add(FacesMessage.SEVERITY_ERROR, "Zbyt długi nick", "Wprowadzony nick jest zbyt długi.");
			valid=false;
		} else {
			if(!userDAO.checkNickUnique(nick)) {
				Mess.add(FacesMessage.SEVERITY_ERROR, "Nick zajęty", "Wprowadzony nick jest już zajęty.");
				valid=false;
			}
		}
		
		if(!userDAO.checkEmailUnique(email)) {
			Mess.add(FacesMessage.SEVERITY_ERROR, "Email zajęty", "Na podany adres e-mail zostało już utworzone konto.");
			valid=false;
		}
		
		
		
		return valid;
	}
	
	public String doRegister() throws NoSuchAlgorithmException {
		if(this.valid()) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			User u = new User();
			u.setNick(nick);
			u.setEmail(email);
			u.setPassword(hash.getPassword(password));
			u.setType(type);
			
			userDAO.create(u);
			
			RemoteClient<User> client = new RemoteClient<User>(); //create new RemoteClient
			client.setDetails(u);
			
			List<String> roles = userDAO.getUserRolesFromDatabase(u); //get User roles 
			
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
		return PAGE_STAY_AT_THE_SAME;
	}

	
}
