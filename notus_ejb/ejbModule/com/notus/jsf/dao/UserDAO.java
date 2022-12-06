package com.notus.jsf.dao;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.notus.jsf.e.User;

@Named
@RequestScoped
public class UserDAO {
	@PersistenceContext
	protected EntityManager em;
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
	
	public List<String> getUserRolesFromDatabase(User user) {
		ArrayList<String> roles = new ArrayList<String>();
		if(user.getType() == 1) roles.add("user");
		else if(user.getType() == 2) roles.add("admin");
		
		return roles;
	}
}
