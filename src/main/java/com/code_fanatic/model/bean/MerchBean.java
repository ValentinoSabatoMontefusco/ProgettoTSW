package com.code_fanatic.model.bean;

public class MerchBean extends ProductBean {

	
	private int amount;
	
	public MerchBean() {
		
		super();
	}
	
	public MerchBean(ProductBean pb) {
		
	
		if (pb.getId() != 0)
			setId(pb.getId());
		setName(pb.getName());
		setDescription(pb.getDescription());
		setPrice(pb.getPrice());
		setType(pb.getType());
		
	
	}
	
	public int getAmount() {
		
		return amount;
	}
	
	public void setAmount(int amount) {
		
		this.amount = amount;
	}
}
