package com.code_fanatic.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.code_fanatic.control.admin.OrdersRecapServlet;
import com.code_fanatic.model.bean.ProductBean;


public class ProductDAO implements IGenericDAO<ProductBean, Integer> {
	
	private static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());

	DataSource dataSource = null;
	private static final String TABLE_NAME = "products";
	
	public ProductDAO(DataSource dataSource) {
		
		this.dataSource = dataSource;
		
	}

	public synchronized void  doSave(ProductBean bean) throws SQLException {
		
		Connection connection = null;
		PreparedStatement prepStmt = null;
		
		try {
		
			
		connection = dataSource.getConnection();
	
		if ( bean.getId() != 0) {
			prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE"
					+ " id = ?;");
			
			prepStmt.setInt(1, bean.getId());
			
			ResultSet rs = prepStmt.executeQuery();
			
			if (rs.next()) {
				
				prepStmt.close();
				prepStmt = connection.prepareStatement("UPDATE " + TABLE_NAME + " SET name = ?, description = ?"
						+ ", price = ? WHERE id = ?;");
						
				prepStmt.setString(1, bean.getName());
				prepStmt.setString(2, bean.getDescription());
				prepStmt.setFloat(3, bean.getPrice());
				prepStmt.setInt(4, bean.getId());
				
				prepStmt.executeUpdate();
				LOGGER.log(Level.INFO, "Prodotto allegedly modificato");
				
			}
		} else { 
			
			prepStmt = connection.prepareStatement("INSERT INTO "+ TABLE_NAME + " (name, description, price) "
					+ " VALUE (?, ?, ?);");
			
			prepStmt.setString(1, bean.getName());
			prepStmt.setString(2, bean.getDescription());
			prepStmt.setFloat(3, bean.getPrice());
					
			prepStmt.executeUpdate();
			LOGGER.log(Level.INFO, "Prodotto allegedly inserito");
				
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


	public synchronized boolean doDelete(int key) throws SQLException {

		Connection connection = null;
		PreparedStatement prepStmt = null;
		int del;
		try {
			
		connection = dataSource.getConnection();
		prepStmt = connection.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE id = ?;");
		
		prepStmt.setInt(1, key);
		del = prepStmt.executeUpdate();
		
		} finally {
				
			try {
				if (prepStmt != null)
					prepStmt.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return del > 0;
	}


	public synchronized ProductBean doRetrieveByKey(Integer key) throws SQLException {

		Connection connection = null;
		PreparedStatement prepStmt = null;
		ProductBean product = null;
		
		try {
			
			connection = dataSource.getConnection();
			prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id = ?;");
			prepStmt.setInt(1, key);
			
			ResultSet rs = prepStmt.executeQuery();
			
			
			
			
			
			if (rs.next()) {
				
				product = new ProductBean();
				
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setDescription(rs.getString("description"));
				product.setType(rs.getString("type"));
				product.setPrice(rs.getFloat("price"));
				
				
			} 
		} catch (SQLException e) {
				
				LOGGER.log(Level.SEVERE, e.getMessage());
			} finally {
				try {
					if (prepStmt != null)
						prepStmt.close();
				} finally {
					if (connection != null)
						connection.close();
				}
			}
			
			return product;
			
	}


	public synchronized Collection<ProductBean> doRetrieveAll(String order) throws SQLException {
		
		Connection connection = null;
		PreparedStatement prepStmt = null;
		Collection<ProductBean> products = new ArrayList<ProductBean>(); 
		
		try {
			
			connection = dataSource.getConnection();
			prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " ORDER BY ?;");
			
			prepStmt.setString(1, order);
			
			ResultSet rs = prepStmt.executeQuery();
			
			
			ProductBean currentProduct;
			while (rs.next()) {
				
				currentProduct = new ProductBean();
				
				currentProduct.setId(rs.getInt("id"));
				currentProduct.setName(rs.getString("name"));
				currentProduct.setDescription(rs.getString("description"));
				currentProduct.setType(rs.getString("type"));
				currentProduct.setPrice(rs.getFloat("price"));
				
				products.add(currentProduct);
				
			}
			
		} catch (SQLException e) {
			
			LOGGER.log(Level.SEVERE, e.getMessage());
		} finally {
			try {
				if (prepStmt != null)
					prepStmt.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		
		return products;
		
		
		
	}
	
public synchronized Collection<ProductBean> doRetrieveAllSubclasses(String order) throws SQLException {
		
		Connection connection = null;
		PreparedStatement prepStmt = null;
		Collection<ProductBean> products = new ArrayList<ProductBean>(); 
		
		try {
			
			connection = dataSource.getConnection();
			products.addAll(new MerchDAO(dataSource).doRetrieveAll(order));
			products.addAll(new CourseDAO(dataSource).doRetrieveAll(order));
			
			
		} catch (SQLException e) {
			
			LOGGER.log(Level.SEVERE, e.getMessage());
		} finally {
			try {
				if (prepStmt != null)
					prepStmt.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		
		return products;
		
		
		
	}

}
