package com.code_fanatic.model.bean;

import java.sql.Timestamp;

public class UserBean {

	private int id;
	private String username;
	private String password;
	private String role;

	private Cartesio carrello;
	
	public UserBean() {

	}
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public Timestamp getDataCreazione() {
		return dataCreazione;
	}
	
	public void setDataCreazione(Timestamp dataCreazione) {
		this.dataCreazione = dataCreazione;
	}


	private Timestamp dataCreazione;


	public Cartesio getCarrello() {
		return carrello;
	}
	
	public void setCarrello(Cartesio carrello) {
		
		this.carrello = carrello;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}
	
	
	
}
