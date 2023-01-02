package com.notus.jsf.dao;

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
		}
		catch (Exception nre) {}
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
	
	public Boolean delPost(int post_id, int user_id) {
		Post post = new Post();
		post = get(post_id);
		
		if(post != null) {
			if(post.getUser().getId() == user_id) {
				del(post);
				return true;
			}
		}
		return false;
	}
	
	public List<Post> loadCategoryPosts(Category cat) {
		List<Post> posts = null;

		Query query = em.createQuery("select p FROM Post p where categoryBean=:cat ORDER BY p.date DESC");
		//Query query = em.createQuery("select u FROM Category u where u.User=:user_id");
		//SELECT w FROM WorkEntry w WHERE w.customerId.customerId = 1
		query.setParameter("cat", cat);
		
		try {
			posts = (List<Post>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return posts;
	}
	
	public List<Post> loadPostsWithoutCategory(User us) {
		List<Post> posts = null;

		Query query = em.createQuery("select p FROM Post p where categoryBean is NULL and user=:us ORDER BY p.date DESC");
		query.setParameter("us", us);
		
		try {
			posts = (List<Post>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return posts;
	}
	

}
