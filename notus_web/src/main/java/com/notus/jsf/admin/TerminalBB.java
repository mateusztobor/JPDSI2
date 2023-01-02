package com.notus.jsf.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.lang.*;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.notus.jsf.dao.UserDAO;
import com.notus.jsf.dao.PostDAO;
import com.notus.jsf.e.User;
import com.notus.jsf.e.Post;
import com.notus.jsf.etc.Client;


@Named("AdminTerminal")
@ViewScoped
public class TerminalBB implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 821750316314425586L;
	private static final String badparameters = "Użyto nieprawidłowych parametrów polecenia.";

	@Inject
	UserDAO userDAO;
	@Inject
	Client client;
	@Inject
	PostDAO postDAO;
	
	public String handleCommand(String command, String[] params) {
	
		if ("about".equals(command)) {
			return "Notuś was created by Mateusz Tobor.";
					
		}
		else if ("setperm".equals(command)) {
			if (params.length == 2) {
				if(params[1].equals("banned") | params[1].equals("user") || params[1].equals("admin")) {
					
					User u = userDAO.getByNick(params[0]);
					if(u != null) {
						if(u.getId() != client.getId()) {
							if(params[1].equals("banned")) u.setType(0);
							else if(params[1].equals("user")) u.setType(1);
							else u.setType(2);
							userDAO.update(u);
						} else return "Ze względów bezpieczeństwa nie możesz zmienić swoich uprawnień.";
					} else return "Użytkownik o nicku " + params[0] + " nie istnieje.";
					return "Pomyślnie zmieniono upraweniania użytkownika " + params[0] + " na " + params[1]+".";
				}
			}
			return badparameters;
		}
		
		else if ("getperm".equals(command)) {
			if (params.length == 1) {
				User u = userDAO.getByNick(params[0]);
				if(u != null) {
					String ret = "Użytkownik "+params[0]+" ma uprawnienia ";
					if(u.getType() == 0) ret+="banned.";
					else if(u.getType() == 1) ret+="user.";
					else ret+="admin.";
					return ret;
				} else return "Użytkownik o nicku " + params[0] + " nie istnieje.";
			}
			return badparameters;
		}
		else if ("listperm".equals(command)) {
			if (params.length == 1) {
				if(params[0].equals("banned") || params[0].equals("user") || params[0].equals("admin")) {
					List<User> users = null;
					int r;
					if(params[0].equals("banned")) r=0;
					else if(params[0].equals("user")) r=1;
					else r=2;
					users = userDAO.usersInRole(r);
					
					if(users.size() > 0) {
						String ret = "Lista użytkowników z uprawnieniami "+params[0]+": ";
						for(User user : users)
							ret += user.getNick() + ", ";
						return ret;
					} else return "Brak użytkowników z takimi uprawnieniami.";
				}
			}
			return badparameters;
		}
		else if ("deluser".equals(command)) {
			if (params.length == 1) {
				User u = userDAO.getByNick(params[0]);
				if(u != null) {
					if(u.getId() != client.getId()) {
						userDAO.del(u);
						return "Użytkownik o nicku " + params[0] + " został usunięty.";
					} else return "Nie możesz usunąć swojego konta.";
				} else return "Użytkownik o nicku " + params[0] + " nie istnieje.";
			}
			return badparameters;
		}
		else if ("delpost".equals(command)) {
			if (params.length == 1) {
				Post p = postDAO.getPostByPin(params[0]);
				if(p != null) {
					postDAO.del(p);
					return "Wpis " + params[0] + " został usunięty.";
				} else return "Taki wpis nie istnieje.";
			}
			return badparameters;
		}

        return "Polecenie " + command + " nie jest obsługiwane.";
    }
}