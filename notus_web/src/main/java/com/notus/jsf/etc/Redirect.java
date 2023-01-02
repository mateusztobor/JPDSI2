package com.notus.jsf.etc;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public final class Redirect {
    public static String here() {
		UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
	    return view.getViewId() + "?faces-redirect=true";
    }
    
    public static String home() {
    	 return "/public/index.xhtml?faces-redirect=true";
    }
}