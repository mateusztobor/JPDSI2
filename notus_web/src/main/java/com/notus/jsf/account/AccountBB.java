package com.notus.jsf.account;

import java.security.NoSuchAlgorithmException;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.notus.jsf.etc.Md5;
import com.notus.jsf.etc.Client;
import com.notus.jsf.etc.Mess;
import com.notus.jsf.etc.StrongPassword;

import com.notus.jsf.dao.UserDAO;
import com.notus.jsf.e.User;



@Named
@RequestScoped
public class AccountBB {
	//private static final String PAGE_MAIN = "/public/index.xhtml?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private String o_password, n_password, n_password2;
	
	@Inject
	Md5 hash;
	
	@Inject
	Client client;
	
	@Inject
	UserDAO userDAO;
	
	private Boolean chpass_validate() throws NoSuchAlgorithmException {
		Boolean ret=true;
		if(!hash.getPassword(o_password).equals(client.getPassword())) {
			Mess.add(FacesMessage.SEVERITY_WARN, "Błąd", "Aktualne hasło jest inne.");
			ret=false;
		}
		
		if(o_password.equals(n_password)) {
			Mess.add(FacesMessage.SEVERITY_WARN, "Błąd", "Nowe hasło jest takie samo jak stare.");
			ret=false;
		}
				
		if(!StrongPassword.check(n_password)) {
			Mess.add(FacesMessage.SEVERITY_WARN, "Błąd", "Nowe hasło nie spełnia wymagań.");
			ret=false;
		}
		
		return ret;
	}
	
	public String chpass() throws NoSuchAlgorithmException {
		if(chpass_validate()) {
			Mess.add(FacesMessage.SEVERITY_INFO, "Ok", "Hasło zostało zmienione.");
			User user = new User();
			user.setId(client.getId());
			user.setEmail(client.getEmail());
			user.setType(client.getType());
			user.setNick(client.getNick());
			user.setPassword(hash.getPassword(n_password));
			
			userDAO.update(user);
			
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getFlash().setKeepMessages(true);
			
			UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
		    return view.getViewId() + "?faces-redirect=true";
		}
		return PAGE_STAY_AT_THE_SAME;
	}

	public String getO_password() {
		return o_password;
	}

	public void setO_password(String o_password) {
		this.o_password = o_password;
	}

	public String getN_password() {
		return n_password;
	}

	public void setN_password(String n_password) {
		this.n_password = n_password;
	}

	public String getN_password2() {
		return n_password2;
	}

	public void setN_password2(String n_password2) {
		this.n_password2 = n_password2;
	}
	

	
}
