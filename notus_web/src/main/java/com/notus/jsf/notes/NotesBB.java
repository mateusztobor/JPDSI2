package com.notus.jsf.notes;

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
public class NotesBB {
	private static final String PAGE_MAIN = "/public/index.xhtml?faces-redirect=true&r=1";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	

	
}
