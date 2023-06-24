package com.code_fanatic.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;

import javax.sql.DataSource;

import com.code_fanatic.model.bean.Cartesio;
import com.code_fanatic.model.bean.UserBean;

public class UserDAO implements IGenericDAO<UserBean, String> {

	DataSource dataSource = null;
	private static final String TABLE_NAME = "users";
	
	public UserDAO(DataSource dataSource) {
		
		this.dataSource = dataSource;
		
	}
	
	public void doSave(UserBean bean) throws SQLException {

		Connection connection = null;
		PreparedStatement prepStmt = null;
		
		try {
			connection = dataSource.getConnection();
			prepStmt = connection.prepareStatement("INSERT into " + TABLE_NAME + " (username, password, create_time) " +
													" VALUES (?, ?, ?);");
			
			prepStmt.setString(1, bean.getUsername());
			prepStmt.setString(2, bean.getPassword());
			prepStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			

			if (prepStmt.executeUpdate() == 1) {
				System.out.print("Boh, pare andata bene, controll");
			}
			
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


	public boolean doDelete(int key) throws SQLException {

		return false;
	}


	public UserBean doRetrieveByKey(String key) throws SQLException { // id o Username? :c

		Connection connection = null;
		PreparedStatement prepStmt = null;
		UserBean userCorrespondence = null;
		
		try {
			
			connection = dataSource.getConnection();
			prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE username = ?;");
			
			prepStmt.setString(1, key);
			
			ResultSet rs = prepStmt.executeQuery();
			
			if (rs.next()) {
				userCorrespondence = new UserBean();
				//userCorrespondence.setId(rs.getInt("Id"));
				userCorrespondence.setUsername(rs.getString("username"));
				userCorrespondence.setPassword(rs.getString("password"));
				userCorrespondence.setRole(rs.getString("role"));
				userCorrespondence.setDataCreazione(rs.getTimestamp("create_time"));
				
				prepStmt.close();
				prepStmt = connection.prepareStatement("SELECT * FROM cart WHERE user_username = ?;");
				
				prepStmt.setString(1, key);
				rs = prepStmt.executeQuery();
				Cartesio carrello = new Cartesio();
				while(rs.next()) {
					
					carrello.setQuantity(rs.getInt("product_id"), rs.getInt("quantity"));
				}
				
				userCorrespondence.setCarrello(carrello);
				
				prepStmt.close();
				prepStmt = connection.prepareStatement("SELECT product_id FROM user_products_owned WHERE user_username = ?;");
				
				prepStmt.setString(1, key);
				rs = prepStmt.executeQuery();
				HashSet<Integer> productsOwned = new HashSet<Integer>();
				
				while(rs.next()) {
					
					productsOwned.add(rs.getInt("product_id"));
					
				}
				
				userCorrespondence.setOwnedProducts(productsOwned);
				
			} else {
				
				System.out.print("Utente non trovato");
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
		
		return userCorrespondence;
	}


	public Collection<UserBean> doRetrieveAll(String order) throws SQLException {

		return null;
	}
	
	public boolean doAddToCart(String username, int productID) throws SQLException {
		
		Connection connection = null;
		PreparedStatement prepStmt = null;
		UserBean currentUser = null;
		Boolean success = false;
		
		try {
			
			connection = dataSource.getConnection();
			prepStmt = connection.prepareStatement("INSERT INTO cart (user_username, product_id, QUANTITY) VALUES (?, ?, 1);");
			
			prepStmt.setString(1, username);
			prepStmt.setInt(2, productID);
			
			if (prepStmt.executeUpdate() != 0) {
				success = true;
				System.out.print("Articolo inserito nel carrello");
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
		
		return success;
		
	}

	
	
}
