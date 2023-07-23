package com.code_fanatic.model.bean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;

public class OrderBean implements IUserSpecific {

	private int id;
	private String user_username;
	private Cartesio cart;
	private Collection<Entry<ProductBean, Integer>> products;
	private Timestamp order_date;
	
	public OrderBean() {
		
		cart = new Cartesio();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser_username() {
		return user_username;
	}

	public void setUser_username(String username) {
		this.user_username = username;
	}

	public Collection<Entry<ProductBean, Integer>> getProducts() {
		return products;
	}

	public void setProducts(Collection<Entry<ProductBean, Integer>> products) {
		this.products = products;
	}

	public Timestamp getOrder_date() {
		return order_date;
	}

	public void setOrder_date(Timestamp order_date) {
		this.order_date = order_date;
	}

	public Cartesio getCart() {
		return cart;
	}

	public void setCart(Cartesio cart) {
		this.cart = cart;
	}
	
	
	
}
