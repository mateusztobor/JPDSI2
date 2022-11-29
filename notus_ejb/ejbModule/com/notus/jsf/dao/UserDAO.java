package com.notus.jsf.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.notus.jsf.e.User;

@Stateless
public class UserDAO {
	@PersistenceContext
	EntityManager em;
	
	public User findUser(String mail, String password) {
		
		return null;
	}
	
	public void add(User user) {
		em.persist(user);
	}
	
	public User update(User user) {
		return em.merge(user);
	}
	
	public void del(User user) {
		em.remove(em.merge(user));
	}
	
	public User get(Object id) {
		return em.find(User.class, id);
	}
	
	
}