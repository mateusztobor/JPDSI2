package com.notus.jsf.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
	
	public Config get2(Object name) {
		return em.find(Config.class, name);
	}
	
	public Config get(String name) {
		Config c = null;

		Query q = em.createQuery("select u FROM Config u where u.name =:name");
		q.setParameter("name", name);
		
		try {
			Config conf = (Config) q.getSingleResult();
			c = new Config();
			c.setId(conf.getId());
			c.setName(name);
			c.setValue(conf.getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}
	
	
}