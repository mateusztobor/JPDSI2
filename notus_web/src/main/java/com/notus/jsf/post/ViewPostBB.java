package com.notus.jsf.post;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import com.notus.jsf.e.Post;

import com.notus.jsf.dao.PostDAO;
import com.notus.jsf.etc.Pin2;
import com.notus.jsf.etc.Redirect;
import com.notus.jsf.etc.Client;

@Named
@RequestScoped
public class ViewPostBB {	
	FacesContext context = FacesContext.getCurrentInstance();
	Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
	
	private String post_pin=null,
				   post_content=null,
				   post_title=null,
				   post_category=null,
				   post_author=null,
				   page_title=null;
	
	private boolean post_share, post_exists = false, perm_edit = false;
	
	Date post_date = null;
	

	@Inject
	PostDAO postDAO;
	@Inject
	Client client;
	
	public ViewPostBB() throws IOException {
		post_pin = paramMap.get("pin");
		if(!Pin2.valid(post_pin)) Redirect.home();
    }
	
	public Boolean PostExists() {
		post_pin = paramMap.get("pin");
		Post p = new Post();
		p = postDAO.getPostByPin(post_pin);
		
		if(p == null) return false;
		if(p.getUser() == null & client.getType() == 2) perm_edit = true;
		if(p.getUser() != null) {
			if(p.getShare() != (byte)1 &
				p.getUser().getId() != client.getId() &
				client.getType() != 2)
			return false;
			
			if(p.getUser().getId() == client.getId() || client.getType() == 2) perm_edit = true;
		}
		
		
		if(p.getTitle() == null) {
			page_title = p.getPin();
			post_title = "Bez tytułu";
		}
		else {
			page_title = p.getTitle();
			post_title = p.getTitle();
		}
		
		post_content = p.getContent();
		
		post_date = p.getDate();
		
		post_category = "Bez kategorii";
		if(p.getCategoryBean() != null) post_category = p.getCategoryBean().getTitle();
		
		post_author = "Gość";
		if(p.getUser() != null) post_author = p.getUser().getNick();
		
		post_share = p.getShare()!=0;
		
		post_exists = true;
		return true;
	}

	public String getPage_title() {
		return page_title;
	}

	public String getPost_content() {
		return post_content;
	}

	public String getPost_title() {
		return post_title;
	}

	public String getPost_category() {
		return post_category;
	}

	public String getPost_author() {
		return post_author;
	}

	public boolean isPost_share() {
		return post_share;
	}

	public boolean isPost_exists() {
		return post_exists;
	}
	
	public boolean isPerm_edit() {
		return perm_edit;
	}

	public Date getPost_date() {
		return post_date;
	}

}
