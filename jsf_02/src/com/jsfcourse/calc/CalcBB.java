package com.jsfcourse.calc;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class CalcBB {
	private String poj, rok, prawko;
	private String x;
	private String y;
	private Double result;

	@Inject
	FacesContext ctx;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getPoj() {
		return poj;
	}

	public void setPoj(String poj) {
		this.poj = poj;
	}

	public String getRok() {
		return rok;
	}

	public void setRok(String rok) {
		this.rok = rok;
	}

	public String getPrawko() {
		return prawko;
	}

	public void setPrawko(String prawko) {
		this.prawko = prawko;
	}
	
	

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public Double getResult() {
		return result;
	}

	public String calc() {
		try {
			int rok = Integer.parseInt(this.rok);
			int prawko = Integer.parseInt(this.prawko);
			double poj = Double.parseDouble(this.poj);
			int nowYear = 2022;
			
			if((rok < 1700 || rok > nowYear) || (prawko < 1950 || prawko > nowYear)) {
				ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Błędnie wprowadzone dane", null));
				return null; 
			} else {
				double baza = 400 + (100*poj);
				double baza_2 = 10 * (nowYear - rok);
				double baza_3 = 4;
				
				if(nowYear - prawko > 6) {
					baza_3 *= -10;
				}
				else if(nowYear - prawko > 5) {
					baza_3 *= -5;
				}
				else if(nowYear - prawko > 4) {
					baza_3 *= 0;
				}
				else if(nowYear - prawko > 3) {
					baza_3 *= 10;
				}
				else if(nowYear - prawko > 2) {
					baza_3 *= 15;
				}
				else if(nowYear - prawko >= 0) {
					baza_3 *= 30;
				}
				 
				result = (double) ( baza + baza_2 + baza_3 );
				ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operacja wykonana poprawnie", null));
				return "showresult"; 
			}

		} catch (Exception e) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd podczas przetwarzania parametrów", null));
			return null; 
		}
				
	}

	public String info() {
		return "info"; 
	}
	
	public String buy() {
		return "buy"; 
	}
}
