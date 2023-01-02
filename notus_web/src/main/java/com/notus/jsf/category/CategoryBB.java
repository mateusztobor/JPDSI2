package com.notus.jsf.category;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import com.notus.jsf.etc.Client;
import com.notus.jsf.etc.Mess;
import com.notus.jsf.dao.PostDAO;
import com.notus.jsf.e.Category;
import com.notus.jsf.e.Post;
import com.notus.jsf.dao.CategoryDAO;


@Named
@RequestScoped
public class CategoryBB {
	@Inject
	CategoryDAO categoryDAO;
	
	@Inject
	PostDAO postDAO;
	
	@Inject
	Client client;
	
	private List<Category> categories = null;
	private String cat_title,cat_orderNum;
	
	public Boolean loadCategories() {
		categories = categoryDAO.loadUserCategories(client.getClient());
		if(categories != null && categories.size() != 0)
			return true;
		return false;
	}
	
	public List<Post> getPostsWithoutCategory() {
		return postDAO.loadPostsWithoutCategory(client.getClient());
	}
	
	public String save_all() {
		for(Category cat : categories) {
			if(categoryDAO.checkUserCategory(cat.getId(), client.getId())) {
				categoryDAO.update(cat);
			}
		}
		Mess.add2(FacesMessage.SEVERITY_INFO, "", "Zmiany zostały zapisany.");
	    return Mess.here();
	}
	
	public String del(int id) {
		if(categoryDAO.delCategory(id, client.getId())) {
			Mess.add2(FacesMessage.SEVERITY_INFO, "", "Kategoria została usunięta.");
			return Mess.here();
		}
		Mess.add(FacesMessage.SEVERITY_ERROR, "", "Nie można było usunąć kategorii.");
		return null;
	}
	
	public String add() {
		Category c = new Category();
		if(!cat_title.isEmpty()) {
		    try {
		    	int cat_orderNum2 = Integer.parseInt(cat_orderNum);
				c.setOrderNum(cat_orderNum2);
				c.setTitle(cat_title);
				c.setUser(client.getClient());
				categoryDAO.add(c);
				Mess.add2(FacesMessage.SEVERITY_INFO, "", "Kategoria została dodana.");
				return Mess.here();
		    } catch (NumberFormatException nfe) {}
		}
		Mess.add(FacesMessage.SEVERITY_ERROR, "", "Nie można było dodać kategorii.");
		return null;
	}
	
	public Boolean loadCategoriesWithPosts() {
		if(loadCategories()) {
			for(Category cat : categories) {
				cat.setPosts(postDAO.loadCategoryPosts(cat));
			}
			return true;
		}
		return false;
	}

	
	
	
	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public String getCat_title() {
		return cat_title;
	}

	public void setCat_title(String cat_title) {
		this.cat_title = cat_title;
	}

	public String getCat_orderNum() {
		return cat_orderNum;
	}

	public void setCat_orderNum(String cat_orderNum) {
		this.cat_orderNum = cat_orderNum;
	}
	

	
	

	
}
