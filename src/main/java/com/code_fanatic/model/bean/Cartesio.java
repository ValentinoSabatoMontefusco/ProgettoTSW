package com.code_fanatic.model.bean;

import java.util.*;
import java.util.Map.Entry;

public class Cartesio {
	
	private HashMap<Integer, Integer> contenutoCarrello; // Prodotti x Quantità
	
	public Cartesio() {
		
		contenutoCarrello = new HashMap<>();
				
	}
	
	public void addProduct(int productID, int quantity) {
		
		if (contenutoCarrello.containsKey(productID))
			quantity += contenutoCarrello.get(productID);
			
		contenutoCarrello.put(productID, quantity);
	}
	
	public void setQuantity(int product, int quantity) {
		

			
			if (quantity <= 0) 
				contenutoCarrello.remove(product);
			else {
				contenutoCarrello.put(product, quantity);
			}

	}
	
	public void subtractProduct(int product, int quantity) {
		
		if (contenutoCarrello.containsKey(product))
			quantity = contenutoCarrello.get(product) - quantity;
		
		if (contenutoCarrello.get(product) <= 1)
			contenutoCarrello.remove(product);
		else {
			contenutoCarrello.put(product, quantity);
		}
			
	}
	
	public void removeProduct(int product) {
		
		contenutoCarrello.remove(product);
	}
	
	public Collection<Entry<Integer, Integer>> getProducts() {
		
		return contenutoCarrello.entrySet();
	}
	
	
	public int getTotalQuantity() {
		
		int totalQuantity = 0;
		
		for (Entry<Integer, Integer> entry : contenutoCarrello.entrySet()) {
			
			totalQuantity += entry.getValue();
			
		}
		
		return totalQuantity;
	}
	
	public int getProductQuantity(int product) {
		
		int quantity = 0;
		if (contenutoCarrello.containsKey(product))
			quantity = contenutoCarrello.get(product);
			
		return quantity;
	}
	
	public void clear() {
		
		contenutoCarrello.clear();
	}
}
