package com.notus.jsf.post;

import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import com.notus.jsf.etc.Client;
import com.notus.jsf.etc.Mess;
import com.notus.jsf.e.Post;
import com.notus.jsf.e.User;
import com.notus.jsf.e.Category;
import com.notus.jsf.dao.PostDAO;
import com.notus.jsf.dao.UserDAO;
import com.notus.jsf.dao.CategoryDAO;
import com.notus.jsf.etc.Pin;
import com.notus.jsf.etc.Mess;


@Named
@RequestScoped
public class PostBB {
	private String note;
	@Inject
	Client c;
	
	@Inject
	PostDAO postDAO;
	
	@Inject
	UserDAO userDAO;
	
	@Inject
	CategoryDAO categoryDAO;
	
	@Inject
	Pin pin;
	
	
	
	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public int test() {
		Post post = new Post();
		
		//post.setShare(true);
		post.setContent(note);
		
		return c.getId();
	}
	
	
	public String save_guest() {
		if(!note.isEmpty()) {
			Post post = new Post();
			post.setContent(note);
			post.setShare((byte)1);
			String post_pin = pin.get_unique_for_post(6);
			post.setPin(post_pin);
			Date date = new Date();
			post.setDate(date);
			postDAO.add(post);
			return "/public/post.xhtml?faces-redirect=true&pin="+post_pin;
		} else Mess.add(FacesMessage.SEVERITY_INFO, "Błąd", "Nie można zapisać pustej notatki.");
		return null;
	}
	

	
}
