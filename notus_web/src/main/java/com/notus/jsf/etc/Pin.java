package com.notus.jsf.etc;

import java.util.Random;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.notus.jsf.dao.PostDAO;


@Named
@ApplicationScoped
public class Pin {
	
	@Inject
	PostDAO postDAO;
	
	public String get(int len) {
		return _get_pin(len);
	}
	
	public String get_unique_for_post(int len) {
		String pin=null;
		Boolean ok = false;
		while(!ok) {
			pin = _get_pin(len);
			if(postDAO.checkPinUnique(pin)) ok=true;
		}
		return pin;
	}
	
	private String _get_pin(int len) {
		String chars = "0123456789";
		String output = "";
		Random rand = new Random();
		
		for(int i=0; i < len; i++) {
			

			int n = rand.nextInt(10);
			output += chars.substring(n,n+1);
			
		}
		
		//for ($i=0; $i < $length; $i++){
			//$output .= substr($chars, rand(0, strlen($chars)-1), 1);
		//}
		
		return output;
		
	}
	
	public Boolean valid(String pin) {
		if(pin == null) return false;
		if(pin.length() != 6) return false;
		for(int i=0; i < pin.length(); i++) {
			if(pin.charAt(i) != '0' &&
			pin.charAt(i) != '1' &&
			pin.charAt(i) != '2' &&
			pin.charAt(i) != '3' &&
			pin.charAt(i) != '4' &&
			pin.charAt(i) != '5' &&
			pin.charAt(i) != '6' &&
			pin.charAt(i) != '7' &&
			pin.charAt(i) != '8' &&
			pin.charAt(i) != '9')
				return false;
		}
		return true;
	}
	
	
}
