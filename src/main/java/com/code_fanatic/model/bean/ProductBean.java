package com.code_fanatic.model.bean;

public class ProductBean {
	
	

	private int id;
	private String name;
	private String description;
	private int quantity;
	private String type;
	private float price;
	
	public ProductBean() {
	
	}
	
//	public ProductBean (int id, String name, String description, float price) {
//		
//		this.id = id;
//		this.name = name;
//		this.description = description;
//		this.price = price;
//		
//	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	

}
