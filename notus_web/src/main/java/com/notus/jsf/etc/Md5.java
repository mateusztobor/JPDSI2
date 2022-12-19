package com.notus.jsf.etc;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;


@Named
@ApplicationScoped
public class Md5 {
	
	@Inject
	Conf config;
	
	public String getHash(String value) throws NoSuchAlgorithmException {
		return this._md5(value);
	}
	
	public String getPassword(String password) throws NoSuchAlgorithmException {
		return this._md5(config.get("pass_hash_sec") + password + config.get("pass_hash_sec2"));
	}
	
	private String _md5(String value) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.reset();
		md5.update(value.getBytes());
		
		byte[] digest = md5.digest();
		BigInteger bigInt = new BigInteger(1,digest);
		
		String hash = bigInt.toString(16);
		while(hash.length() < 32 ){
		  hash = "0"+hash;
		}
		
		return hash;
	}
	
}
