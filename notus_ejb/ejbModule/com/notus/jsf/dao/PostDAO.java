package com.notus.jsf.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.notus.jsf.e.Post;

@Named
@RequestScoped
@Transactional(rollbackOn = Exception.class)
public class PostDAO {
	@PersistenceContext
	protected EntityManager em;
	
	public void add(Post post) {
		em.persist(post);
	}
	
	public Post update(Post post) {
		return em.merge(post);
	}
	
	public void del(Post post) {
		em.remove(em.merge(post));
	}
	
	public Post get(Object id) {
		return em.find(Post.class, id);
	}
	
	public Boolean checkPinUnique(String pin) {
		Query query = em.createQuery("select u FROM Post u where u.pin=:pin");
		query.setParameter("pin", pin);
		
		try {
			Post post = (Post) query.getSingleResult();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public Post getPostByPin(String pin) {
		Post p = null;

		Query query = em.createQuery("select u FROM Post u where u.pin=:pin");
		query.setParameter("pin", pin);
		
		try {
			Post post = (Post) query.getSingleResult();
			p = new Post();
			p.setId(post.getId());
			p.setPin(pin);
			
			p.setTitle(post.getTitle());
			p.setContent(post.getContent());
			p.setDate(post.getDate());
			
			p.setUser(post.getUser());
			p.setCategoryBean(post.getCategoryBean());
			p.setShare(post.getShare());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}

}
