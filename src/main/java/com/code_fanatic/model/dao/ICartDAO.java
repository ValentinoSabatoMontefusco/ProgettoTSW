package com.code_fanatic.model.dao;

import java.sql.SQLException;

import com.code_fanatic.model.bean.Cartesio;

public interface ICartDAO {

	
	public void doSave(Cartesio cart, String username) throws SQLException;
	
	public Cartesio doRetrieveByKey(String username) throws SQLException;
	
	public void doUpdateAddProduct(String username, int productID) throws SQLException;
	
	public void doUpdateSubtractProduct(String username, int productID) throws SQLException;
	
	public void doUpdateSetProduct(String username, int productID, int quantity) throws SQLException;
	
	public void doUpdateRemoveProduct(String username, int productID) throws SQLException;
	
	public void doDelete(String username) throws SQLException; // Delete user's Cart
	
}
