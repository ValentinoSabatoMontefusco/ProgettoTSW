package com.code_fanatic.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

import com.code_fanatic.model.bean.Cartesio;

public class CartDAO implements ICartDAO {
	
	DataSource dataSource = null;
	private static final String TABLE_NAME = "cart"; //carts?
	
	private Connection connection = null;
	private PreparedStatement prepStmt = null;
	
	public CartDAO(DataSource dataSource) {
		
		this.dataSource = dataSource;
	}


	public synchronized void doSave(Cartesio cart, String username) throws SQLException {

		try {
			
			connection = dataSource.getConnection();
			prepStmt = connection.prepareStatement("INSERT INTO " + TABLE_NAME + " (user_username, product_id, quantity) VALUES (?, ?, ?);");
			
			Collection<Entry<Integer, Integer>> products = cart.getProducts();
			for (Entry<Integer, Integer> entry : products) {
				
				prepStmt.setString(1,  "username");
				prepStmt.setInt(2,  entry.getKey());				// ProductID
				prepStmt.setInt(3, entry.getValue());				// Quantity
				
				prepStmt.executeUpdate();
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			try {
				if (prepStmt != null)
					prepStmt.close();
			} finally {
				if (connection != null) 
					connection.close();
			}
		}
	}


	public synchronized Cartesio doRetrieveByKey(String username) throws SQLException {

		Cartesio newCart = null;
		
		try {
			
			connection = dataSource.getConnection();
			
			prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE user_username = ?;");
			
			prepStmt.setString(1, username);
			
			ResultSet rs = prepStmt.executeQuery();
			
			newCart = new Cartesio();
			
			while (rs.next()) {
				
				newCart.setQuantity(rs.getInt("product_id"), rs.getInt("quantity"));
				
			}
				
		} catch (SQLException e) {
				
				e.printStackTrace();
			} finally {
				
				try {
					if (prepStmt != null)
						prepStmt.close();
				} finally {
					if (connection != null) 
						connection.close();
				}
			}
			
		return newCart;
	}


	public synchronized void doUpdateAddProduct(String username, int productID) throws SQLException {
		
		try {
			
			connection = dataSource.getConnection();
			
			prepStmt = connection.prepareStatement("INSERT INTO " + TABLE_NAME + 
					" (user_username, product_id, quantity) VALUES (?, ?, 1) ON DUPLICATE KEY UPDATE quantity = quantity + 1;");
			
			prepStmt.setString(1, username);
			prepStmt.setInt(2, productID);
			
			
			prepStmt.executeUpdate();
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			try {
				if (prepStmt != null)
					prepStmt.close();
			} finally {
				if (connection != null) 
					connection.close();
			}
		}

		
	}
	
	public synchronized void doUpdateSubtractProduct(String username, int productID) throws SQLException {
		
		try {
			
			connection = dataSource.getConnection();
			
			prepStmt = connection.prepareStatement("UPDATE " + TABLE_NAME + 
					" SET quantity = (quantity - 1) WHERE user_username = ? AND product_id = ?;");
			
			prepStmt.setString(1, username);
			prepStmt.setInt(2, productID);
			
			
			prepStmt.executeUpdate();
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			try {
				if (prepStmt != null)
					prepStmt.close();
			} finally {
				if (connection != null) 
					connection.close();
			}
		}

		
	}



	public synchronized  void doUpdateSetProduct(String username, int productID, int quantity) throws SQLException {

		try {
			
			connection = dataSource.getConnection();
			
			prepStmt = connection.prepareStatement("INSERT INTO " + TABLE_NAME + 
					" (user_username, product_id, quantity) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE quantity = ?;");
			
			prepStmt.setString(1, username);
			prepStmt.setInt(2, productID);
			prepStmt.setInt(3, quantity);
			
			prepStmt.setInt(4, quantity);
			
			prepStmt.executeUpdate();
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			try {
				if (prepStmt != null)
					prepStmt.close();
			} finally {
				if (connection != null) 
					connection.close();
			}
		}

	}

	
	public synchronized void doUpdateRemoveProduct(String username, int productID) throws SQLException {

		
		try {
			
			connection = dataSource.getConnection();
			prepStmt = connection.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE user_username = ? AND product_id = ?;");
			
			prepStmt.setString(1, username);
			prepStmt.setInt(2, productID);
			
			prepStmt.executeUpdate();
			
		}	catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			try {
				if (prepStmt != null)
					prepStmt.close();
			} finally {
				if (connection != null) 
					connection.close();
			}
		}

		
	}


	public synchronized void doDelete(String username) throws SQLException {

try {
			
			connection = dataSource.getConnection();
			prepStmt = connection.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE user_username = ?;");
			
			prepStmt.setString(1, username);
			
			prepStmt.executeUpdate();
			
		}	catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			try {
				if (prepStmt != null)
					prepStmt.close();
			} finally {
				if (connection != null) 
					connection.close();
			}
		}

	}
	
	

}
