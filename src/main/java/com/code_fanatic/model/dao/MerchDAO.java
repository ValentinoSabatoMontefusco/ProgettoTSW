package com.code_fanatic.model.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import com.code_fanatic.model.bean.MerchBean;

public class MerchDAO implements IGenericDAO<MerchBean, Integer> {
	
	DataSource dataSource;
	private static final String TABLE_NAME = "merchandise INNER JOIN products ON products.id = merchandise.product_id";
	
	public MerchDAO(DataSource dataSource) {
		
		this.dataSource = dataSource;
		System.out.print("DataSource connesso al DAO");
	}
	
	
	@Override
	public void doSave(MerchBean bean) throws SQLException {
		
		PreparedStatement prepStmt = null;
		Connection connection = null;
		
		try {
			
			

			connection = dataSource.getConnection();
		
		

			if (bean.getId() != 0) {
				
				prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id = ?;");
				
				prepStmt.setInt(1, bean.getId());
				ResultSet rs = prepStmt.executeQuery();
				
				if (rs.next()) {
//					
//					MerchBean newBean = new MerchBean();
//					newBean.setId(bean.getId());
//					newBean.setName(bean.getName());
//					newBean.setDescription(bean.getDescription());
//					newBean.setPrice(bean.getPrice());
//					newBean.setType("Merchandise");
//					newBean.setAmount(bean.getAmount());
					
					prepStmt.close();
					prepStmt = connection.prepareStatement("UPDATE products SET name = ?, description = ?, "
							+ "type = ?, price = ? WHERE id = ?;");
					
					prepStmt.setString(1, bean.getName());
					prepStmt.setString(2, bean.getDescription());
					prepStmt.setString(3, bean.getType());
					prepStmt.setFloat(4, bean.getPrice());
					prepStmt.setInt(5, bean.getId());
					
					prepStmt.executeUpdate();
					prepStmt.close();
					prepStmt = connection.prepareStatement("UPDATE merchandise SET amount = ? WHERE product_id = ?;");
					
					prepStmt.setInt(1,  bean.getAmount());
					prepStmt.setInt(2, bean.getId());
					prepStmt.executeUpdate();
					System.err.println("Merciandisio allegedly modificato");
				}} else {
				
				prepStmt = connection.prepareStatement("INSERT INTO products (name, description, type, price) "
						+ " VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
				
				prepStmt.setString(1, bean.getName());
				prepStmt.setString(2, bean.getDescription());
				prepStmt.setString(3, bean.getType());
				prepStmt.setFloat(4,  bean.getPrice());
						
				prepStmt.executeUpdate();
				ResultSet tempRs = prepStmt.getGeneratedKeys();
				if (tempRs.next()) {
					
					bean.setId(tempRs.getInt(1));
					prepStmt.close();
					prepStmt = connection.prepareStatement("INSERT INTO merchandise (product_id, amount) VALUES(?, ?);");
					
					prepStmt.setInt(1, bean.getId());
					prepStmt.setInt(2,  bean.getAmount());
					prepStmt.executeUpdate();
					
					System.err.println("Merciandisio allegedly inserito");
				} else {
					System.err.println("Qualcosa andò male");
				}
				
					
				
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

	@Override
	public boolean doDelete(int key) throws SQLException {

		return false;
	}

	@Override
	public MerchBean doRetrieveByKey(Integer key) throws SQLException {

		MerchBean merch = null;
		Connection connection = null;
		PreparedStatement prepStmt = null;
		
		try {
			
		connection = dataSource.getConnection();
		prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id = ?;");

		
		prepStmt.setInt(1, key);
		
		ResultSet rs = prepStmt.executeQuery();
		
		if (rs.next()) {
			merch = new MerchBean();
			merch.setName(rs.getString("name"));
			merch.setDescription(rs.getString("description"));
			merch.setType(rs.getString("type"));
			merch.setPrice(rs.getFloat("price"));
			merch.setAmount(rs.getInt("amount"));
			merch.setId(rs.getInt("id"));
			
		}
		}finally {
			
			try {
				if (prepStmt != null)
					prepStmt.close();
			} finally {
				if (connection != null)
					connection.close();
			}
			
		}

		return merch;
	}

	@Override
	public Collection<MerchBean> doRetrieveAll(String order) throws SQLException {
		
		Connection connection = null;
		PreparedStatement prepStmt = null;
		Collection<MerchBean> merchs = null; 
		
		try {
			

		connection = dataSource.getConnection();
		prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME);
		
		ResultSet rs = prepStmt.executeQuery();
		merchs = new ArrayList<MerchBean>();
		MerchBean currentMerch;
		
		
		while(rs.next()) {
			
			currentMerch = new MerchBean();
			currentMerch.setName(rs.getString("name"));
			currentMerch.setDescription(rs.getString("description"));
			currentMerch.setType(rs.getString("type"));
			currentMerch.setPrice(rs.getFloat("price"));
			currentMerch.setAmount(rs.getInt("amount"));
			currentMerch.setId(rs.getInt("id"));
			
			merchs.add(currentMerch);
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
		
		return merchs;
		
	}

}
