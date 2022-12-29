package com.notus.jsf.post;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.notus.jsf.e.Post;
import com.notus.jsf.e.User;
import com.notus.jsf.e.Category;

import com.notus.jsf.dao.PostDAO;
import com.notus.jsf.dao.UserDAO;

@Named
@RequestScoped
public class ViewPostBB {	
	FacesContext context = FacesContext.getCurrentInstance();
	Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
	
	
	private int id=0;
	private byte share=(byte)0,
				 true_share=(byte)1;
	private String pin=null,
				   content=null,
				   title=null,
				   shared=null,
				   category_name=null,
				   author_name=null,
				   title_show=null;
	Date date = null;
	Category category = null;
	User author = null;
	

	@Inject
	PostDAO postDAO;
	public ViewPostBB() throws IOException {
		pin = paramMap.get("pin");
		if(validPin()) {
			
			
			
		} else redirectHome();
    }
	
	
	
	
	public Boolean PostExists() {
		Post p = new Post();
		p = postDAO.getPostByPin(pin);
		
		if(p == null) return false;
		
		id = p.getId();
		title = p.getTitle();
		if(title == null) {
			title_show = pin;
		} else {
			title_show = title;
		}
		if(title == null) title = "Bez tytułu";
		content = p.getContent();
		
		date = p.getDate();
		
		category = p.getCategoryBean();
		category_name = "Bez kategorii";
		if(category != null) category_name = category.getTitle();
		
		
		author = p.getUser();
		author_name = "Gość";
		if(author != null) author_name = author.getNick();
		
		
		share = p.getShare();
		if(share == true_share) shared = "Publiczna";
		else shared = "Prywatna";
		
		return true;
	}
	
	
	
	
	public String getContent() {
		Post p = postDAO.getPostByPin(pin);
		if(p != null)
		return p.getContent();
		return null;
	}
	
	private Boolean validPin() {
		if(pin == null) return false;
		if(pin.length() != 6) return false;
		for(int i=0; i < pin.length(); i++) {
			if(pin.charAt(i) != '0' &&
			pin.charAt(i) != '1' &&
			pin.charAt(i) != '2' &&
			pin.charAt(i) != '3' &&
			pin.charAt(i) != '4' &&
			pin.charAt(i) != '5' &&
			pin.charAt(i) != '6' &&
			pin.charAt(i) != '7' &&
			pin.charAt(i) != '8' &&
			pin.charAt(i) != '9')
				return false;
		}
		return true;
	}
	
	private void redirectHome() throws IOException {
		context.getExternalContext().redirect("/public/index.xhtml");
		
	}
	
	
    
    public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getShare() {
		return share;
	}

	public void setShare(byte share) {
		this.share = share;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public byte getTrue_share() {
		return true_share;
	}
	public void setTrue_share(byte true_share) {
		this.true_share = true_share;
	}

	public String getShared() {
		return shared;
	}
	public void setShared(String shared) {
		this.shared = shared;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getAuthor_name() {
		return author_name;
	}
	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}
	public String getTitle_show() {
		return title_show;
	}
	public void setTitle_show(String title_show) {
		this.title_show = title_show;
	}
}
