package com.notus.jsf.etc;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.simplesecurity.RemoteClient;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.notus.jsf.e.User;

@Named
@ViewScoped
public class Client implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FacesContext fCtx = FacesContext.getCurrentInstance();
	private HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(true);
	@SuppressWarnings("unchecked")
	private RemoteClient<User> r = RemoteClient.load(session);
	
	public User getClient() {
		return r.getDetails();
	}
	
	public int getId() {
		if(r != null)
			return r.getDetails().getId();
		return 0;
	}
	
	public String getNick() {
		if(r != null)
			return r.getDetails().getNick();
		return null;
	}
	
	public String getEmail() {
		if(r != null)
			return r.getDetails().getEmail();
		return null;
	}
	
	public String getPassword() {
		if(r != null)
			return r.getDetails().getPassword();
		return null;
	}
	
	public int getType() {
		if(r != null)
			return r.getDetails().getType();
		return 0;
	}
	
}