package com.notus.jsf.post;

import java.util.Date;
import java.util.List;

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


@Named
@RequestScoped
public class PostBB {
	private String note, title, share, category;
	@Inject
	PostDAO postDAO;
	
	@Inject
	UserDAO userDAO;
	
	@Inject
	CategoryDAO categoryDAO;
	
	@Inject
	Pin pin;
	
	@Inject
	Client client;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShare() {
		return share;
	}

	public void setShare(String share) {
		this.share = share;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public List<Category> load_categories() {
		User u = new User();
		u.setId(client.getId());
		u.setCategories(categoryDAO.loadUserCategories(u));
		return u.getCategories();
	}
	
	public String save_guest() {
		if(!note.isEmpty()) {
			Post post = new Post();
			post.setContent(note);
			post.setShare((byte)1);
			String post_pin = pin.get_unique_for_post(6);
			post.setPin(post_pin);
			post.setDate(new Date());
			postDAO.add(post);
			Mess.add2(FacesMessage.SEVERITY_INFO, "Ok", "Notatka została utworzona.");
			return "/public/post.xhtml?faces-redirect=true&pin="+post_pin;
		} else Mess.add(FacesMessage.SEVERITY_WARN, "Błąd", "Nie można zapisać pustej notatki.");
		return null;
	}
	
	public String save_user() {
		if(!note.isEmpty()) {
			Post post = new Post();
			
			if(title.isEmpty()) title=null;
			post.setTitle(title);
			
			post.setContent(note);
			
			byte shared;
			if(share.equals("0")) shared=(byte)0;
			else shared=(byte)1;
			post.setShare(shared);
			
			User u = new User();
			u.setId(client.getId());
			post.setUser(u);
			
			Category c = null;
			if(category != null) {
			    try {
			    	int category_id = Integer.parseInt(category);
					Category cat = new Category();
					//cat.setId(category_id);
					//cat.setUser(u);
					cat = categoryDAO.get(category_id);
					if(cat.getUser().getId() == client.getId())
						c = cat;
			    } catch (NumberFormatException nfe) {}
			}
			post.setCategoryBean(c);
			
			String post_pin = pin.get_unique_for_post(6);
			post.setPin(post_pin);
			
			post.setDate(new Date());
			
			postDAO.add(post);
			
			Mess.add2(FacesMessage.SEVERITY_INFO, "", "Notatka została utworzona.");
			
			return "/public/post.xhtml?faces-redirect=true&pin="+post_pin;
		} else Mess.add(FacesMessage.SEVERITY_WARN, "Błąd", "Nie można zapisać pustej notatki.");
		return null;
	}
	
	
	public String delPost(int postId) {
		postDAO.delPost(postId, client.getId());
			//System.out.println(postId);
//			Post post = new Post();
//			post = postDAO.get(postId);
//			postDAO.del(post);

		
		return Mess.here();
	}
	

	
}
