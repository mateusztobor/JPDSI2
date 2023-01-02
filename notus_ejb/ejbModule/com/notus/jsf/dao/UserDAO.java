package com.notus.jsf.dao;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.notus.jsf.e.Category;
import com.notus.jsf.e.Post;
import com.notus.jsf.e.User;

@Named
@RequestScoped
@Transactional(rollbackOn = Exception.class)
public class UserDAO {
	@PersistenceContext
	protected EntityManager em;
	
	public User update(User user) {
		return em.merge(user);
	}
	
	public void del(User user) {
		em.remove(em.merge(user));
	}
	
	public User get(User id) {
		return em.find(User.class, id);
	}
	
	public User getUserFromDatabase(String email, String password) {
		User u = null;

		Query query = em.createQuery("select u FROM User u where u.email=:email and u.password=:password");
		query.setParameter("email", email);
		query.setParameter("password", password);
		
		try {
			User user = (User) query.getSingleResult();
			u = new User();
			u.setId(user.getId());
			u.setEmail(email);
			u.setPassword(password);
			u.setNick(user.getNick());
			u.setType(user.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}
	
	public User getByNick(String nick) {
		User u = null;

		Query query = em.createQuery("select u FROM User u where u.nick=:nick");
		query.setParameter("nick", nick);
		
		try {
			User user = (User) query.getSingleResult();
			u = new User();
			u.setId(user.getId());
			u.setEmail(user.getEmail());
			u.setPassword(user.getPassword());
			u.setNick(user.getNick());
			u.setType(user.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}
	
	public User getByEmail(String email) {
		User u = null;

		Query query = em.createQuery("select u FROM User u where u.email=:email");
		query.setParameter("email", email);
		
		try {
			User user = (User) query.getSingleResult();
			u = new User();
			u.setId(user.getId());
			u.setEmail(user.getEmail());
			u.setPassword(user.getPassword());
			u.setNick(user.getNick());
			u.setType(user.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return u;
	}
	
	public void create(User u) {
	    em.persist(u); //em.merge(u); for updates
	}
	
	public Boolean checkNickUnique(String nick) {

		Query query = em.createQuery("select u FROM User u where u.nick=:nick");
		query.setParameter("nick", nick);
		
		try {
			User user = (User) query.getSingleResult();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public Boolean checkEmailUnique(String email) {
		Query query = em.createQuery("select u FROM User u where u.email=:email");
		query.setParameter("email", email);
		
		try {
			User user = (User) query.getSingleResult();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public List<String> getUserRolesFromDatabase(User user) {
		ArrayList<String> roles = new ArrayList<String>();
		if(user.getType() == 1) roles.add("user");
		else if(user.getType() == 2) roles.add("admin");
		
		return roles;
	}
	
	public List<User> usersInRole(int r) {
		List<User> c = null;

		Query query = em.createQuery("select u FROM User u where u.type=:r");
		query.setParameter("r", r);
		
		try {
			c = (List<User>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
		
	}
}
