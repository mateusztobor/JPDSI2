package com.notus.jsf.post;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.notus.jsf.e.Post;
import com.notus.jsf.e.Category;

import com.notus.jsf.dao.PostDAO;
import com.notus.jsf.dao.CategoryDAO;
import com.notus.jsf.etc.Pin2;
import com.notus.jsf.etc.Pin;
import com.notus.jsf.etc.Redirect;
import com.notus.jsf.etc.Client;

@Named
@ViewScoped
public class EditPostBB implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7401152322334442413L;
	FacesContext context = FacesContext.getCurrentInstance();
	Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
	
	private String post_pin,
				   post_content,
				   post_title,
				   post_share;
	private boolean post_exists=false;
	private int post_id,
				post_category;
	private Date last_save;
	

	@Inject
	PostDAO postDAO;
	@Inject
	CategoryDAO categoryDAO;
	@Inject
	Client client;
	
	@Inject
	Pin pin;
	
	public EditPostBB() throws IOException {
		post_pin = paramMap.get("pin");
		if(!Pin2.valid(post_pin)) Redirect.home();
    }
	
	@PostConstruct
	public void init() {
		post_pin = paramMap.get("pin");
		Post p = new Post();
		p = postDAO.getPostByPin(post_pin);
		
		if(p != null) {
			if(p.getUser() != null) {
				if(p.getUser().getId() == client.getId()) {
					post_id = p.getId();
					post_title = p.getTitle();
					post_content = p.getContent();
					last_save = p.getDate();
					
					if(p.getCategoryBean() != null) post_category = p.getCategoryBean().getId();
					
					if(p.getShare() == (byte)0) post_share="0";
					else post_share="1";
					
					post_exists=true;
				}
			}
			else if(client.getType() == 2) {
				post_id = p.getId();
				post_title = p.getTitle();
				post_content = p.getContent();
				last_save = p.getDate();
				
				if(p.getCategoryBean() != null) post_category = p.getCategoryBean().getId();
				
				if(p.getShare() == (byte)0) post_share="0";
				else post_share="1";
				
				post_exists=true;
			}
		}
	}
	
	public void saveTitle() {
    	Post p = new Post();
    	p = postDAO.get(post_id);
    	p.setTitle(post_title);
    	p.setDate(new Date());
    	postDAO.update(p);
    	last_save = p.getDate();
	}
	
    public void saveContent() {
    	Post p = new Post();
    	p = postDAO.get(post_id);
    	p.setContent(post_content);
    	p.setDate(new Date());
    	postDAO.update(p);
    	last_save = p.getDate();
    }
    
    public void saveCategory() {
    	Post p = new Post();
    	p = postDAO.get(post_id);
    	
    	if(post_category == 0) {
    		p.setCategoryBean(null);
        	postDAO.update(p);
    	} else {
	    	Category c = categoryDAO.get(post_category);
	    	
	    	if(c != null) {
		    	if(c.getUser().getId() == client.getId() || client.getType() == 2) {
		    		p.setCategoryBean(c);
		        	postDAO.update(p);
		    	}
	    	}
    	}
    }
    
    public void saveShare() {
    	Post p = new Post();
    	p = postDAO.get(post_id);
    	if(post_share.equals("1")) p.setShare((byte) 1);
    	else p.setShare((byte) 0);
    	postDAO.update(p);
    }

	public String getPost_content() {
		return post_content;
	}

	public void setPost_content(String post_content) {
		this.post_content = post_content;
	}

	public String getPost_title() {
		return post_title;
	}

	public void setPost_title(String post_title) {
		this.post_title = post_title;
	}

	public String getPost_share() {
		return post_share;
	}

	public void setPost_share(String post_share) {
		this.post_share = post_share;
	}

	public int getPost_category() {
		return post_category;
	}

	public void setPost_category(int post_category) {
		this.post_category = post_category;
	}

	public Date getLast_save() {
		return last_save;
	}

	public void setLast_save(Date last_save) {
		this.last_save = last_save;
	}

	public boolean isPost_exists() {
		return post_exists;
	}




	
	
	
	



}
