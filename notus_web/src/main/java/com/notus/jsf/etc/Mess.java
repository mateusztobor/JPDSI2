package com.notus.jsf.etc;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class Mess {
    public static void add(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
    
    public static void add2(FacesMessage.Severity severity, String summary, String detail) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		Mess.add(severity, summary, detail);
    }
    
    public static String here() {
		UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
	    return view.getViewId() + "?faces-redirect=true";
    }
}