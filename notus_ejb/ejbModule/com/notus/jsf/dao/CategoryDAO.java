package com.notus.jsf.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.notus.jsf.e.Category;
import com.notus.jsf.e.User;

@Stateless
public class CategoryDAO {
	@PersistenceContext
	EntityManager em;
	
	public void add(Category category) {
		em.persist(category);
	}
	
	public Category update(Category category) {
		return em.merge(category);
	}
	
	public void del(Category category) {
		em.remove(em.merge(category));
	}
	
	public Category get(Object id) {
		return em.find(Category.class, id);
	}
	
	public List<Category> loadUserCategories(User u) {
		List<Category> c = null;

		Query query = em.createQuery("select u FROM Category u where user=:u ORDER BY u.orderNum");
		//Query query = em.createQuery("select u FROM Category u where u.User=:user_id");
		//SELECT w FROM WorkEntry w WHERE w.customerId.customerId = 1
		query.setParameter("u", u);
		
		try {
			c = (List<Category>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
		
	}
	
	public Boolean checkUserCategory(int category_id, int user_id) {
		Category c = new Category();
		//c.setId(category_id);
		c = get(category_id);
		
		if(c != null) {
			if(c.getUser().getId() == user_id)
				return true;
		}
		return false;
	}
	
	public Boolean delCategory(int category_id, int user_id) {
		Category c = new Category();
		c = get(category_id);
		
		if(c != null) {
			if(c.getUser().getId() == user_id) {
				del(c);
				return true;
				
			}
		}
		return false;
	}
	
	
}