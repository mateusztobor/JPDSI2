package com.notus.jsf.notes;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.notus.jsf.etc.Client;



@Named
@RequestScoped
public class NotesBB {

	@Inject
	Client c;
	
	public int test() {
		
		return c.getId();
	}
	

	
}
