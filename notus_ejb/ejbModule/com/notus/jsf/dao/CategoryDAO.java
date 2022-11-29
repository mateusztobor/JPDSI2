package com.notus.jsf.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.notus.jsf.e.Category;

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
	
	
}