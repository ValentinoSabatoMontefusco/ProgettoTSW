package com.code_fanatic.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import com.code_fanatic.model.bean.ProductBean;


public class ProductDAO implements IGenericDAO<ProductBean, Integer> {

	DataSource dataSource = null;
	private static final String TABLE_NAME = "products";
	
	public ProductDAO(DataSource dataSource) {
		
		this.dataSource = dataSource;
		
	}

	public synchronized void  doSave(ProductBean bean) throws SQLException {
			

		
	}


	public synchronized boolean doDelete(int key) throws SQLException {

		return false;
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
		
		return products;
		
		
		
	}

}
