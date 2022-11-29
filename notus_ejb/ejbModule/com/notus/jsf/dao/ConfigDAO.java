package com.notus.jsf.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.notus.jsf.e.Config;

@Stateless
public class ConfigDAO {
	@PersistenceContext
	EntityManager em;
	
	public void add(Config config) {
		em.persist(config);
	}
	
	public Config update(Config config) {
		return em.merge(config);
	}
	
	public void del(Config config) {
		em.remove(em.merge(config));
	}
	
	public Config get(Object id) {
		return em.find(Config.class, id);
	}
	
	
}