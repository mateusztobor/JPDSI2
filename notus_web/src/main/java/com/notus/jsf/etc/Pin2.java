package com.notus.jsf.etc;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;


@Named
@RequestScoped
public final class Pin2 {
	
	public static Boolean valid(String pin) {
		if(pin == null) return false;
		if(pin.length() != 6) return false;
		for(int i=0; i < pin.length(); i++) {
			if(pin.charAt(i) != '0' &
			pin.charAt(i) != '1' &
			pin.charAt(i) != '2' &
			pin.charAt(i) != '3' &
			pin.charAt(i) != '4' &
			pin.charAt(i) != '5' &
			pin.charAt(i) != '6' &
			pin.charAt(i) != '7' &
			pin.charAt(i) != '8' &
			pin.charAt(i) != '9')
				return false;
		}
		return true;
	}
	
	
}
