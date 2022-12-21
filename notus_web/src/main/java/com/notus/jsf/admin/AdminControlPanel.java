package com.notus.jsf.admin;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("AdminControlPanel")
@ViewScoped
public class AdminControlPanel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String handleCommand(String command, String[] params) {
        if ("greet".equals(command)) {
            if (params.length > 0) {
                return "Hello " + params[0];
            }
            else {
                return "Hello Stranger";
            }
        }
        else if ("xd".equals(command)) {
            return "Fajnie";
        }

        return command + " not found";
    }
}