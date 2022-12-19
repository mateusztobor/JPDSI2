package com.notus.jsf.etc;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class StrongPassword {
	public static Boolean check(String password) {
		int
			len = password.length(),
			passwordLength=6,
			upChars=0,
			lowChars=0,
			special=0,
			digits=0;
		char ch;
		if(len<passwordLength) return false;
		else {
			for(int i=0; i<len; i++) {
				ch = password.charAt(i);
				if(Character.isUpperCase(ch)) upChars = 1;
				else if(Character.isLowerCase(ch)) lowChars = 1;
				else if(Character.isDigit(ch)) digits = 1;
				else special = 1;
			}
		}
	  
		if(
			//upChars==1 &&
			//lowChars==1 &&
			digits==1 &&
			special==1
		) return true;
		
		return false;
	}
}