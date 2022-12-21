package com.notus.jsf.etc;

import java.security.NoSuchAlgorithmException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.notus.jsf.dao.ConfigDAO;
import com.notus.jsf.e.Config;

@Named
@RequestScoped
public class Conf {
	@Inject
	ConfigDAO configDAO;
	public String get(String name) throws NoSuchAlgorithmException {
		Config config = configDAO.get(name);
		if(config != null)
			return config.getValue();
		return "";
	}
	
}