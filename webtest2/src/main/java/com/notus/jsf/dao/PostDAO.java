//package com.notus.jsf.dao;
//
//import javax.ejb.Stateless;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import com.notus.jsf.e.Post;
//
//@Stateless
//public class PostDAO {
//	@PersistenceContext
//	EntityManager em;
//	
//	public void add(Post post) {
//		em.persist(post);
//	}
//	
//	public Post update(Post post) {
//		return em.merge(post);
//	}
//	
//	public void del(Post post) {
//		em.remove(em.merge(post));
//	}
//	
//	public Post get(Object id) {
//		return em.find(Post.class, id);
//	}
//	
//	
//}