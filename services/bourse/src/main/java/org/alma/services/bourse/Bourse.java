package org.alma.services.bourse;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

/*
	Convert EUR to other currencies
*/
public class Bourse {

	private Map<String, Double> rate;

	public Bourse(){

		this.rate = new HashMap<>();

		this.rate.put("USD", 1.1);
		this.rate.put("EUR", 1.0);		
		this.rate.put("INR", 66.0);		
		this.rate.put("KRW", 1170.0);		
	}

	public ArrayList<String> GetCurrencies(){
		return new ArrayList<>(this.rate.keySet());
	}

	public Double Convert(String currency, Double value){
		Double ratio = (Double) this.rate.get(currency);

		if(ratio == null){
			return -1.0;
		}

		return ratio * value;
	}
}