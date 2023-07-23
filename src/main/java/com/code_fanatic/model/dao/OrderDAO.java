package com.code_fanatic.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;



import com.code_fanatic.control.utils.SecurityUtils;

import com.code_fanatic.model.bean.MerchBean;
import com.code_fanatic.model.bean.OrderBean;
import com.code_fanatic.model.bean.ProductBean;



public class OrderDAO implements IExtendedDAO<OrderBean, Integer> {

	DataSource dataSource = null;
	private static final String TABLE_NAME = "orders";
	private static final String TABLE_NAME2 = "order_products";
	private static final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());
	
	public OrderDAO(DataSource dataSource) {
		
		this.dataSource = dataSource;
		
	}

	public synchronized void doSave(OrderBean bean) throws SQLException {
		
		Connection connection = null;
		PreparedStatement prepStmt = null;
		
		try {
		connection = dataSource.getConnection();
		prepStmt = connection.prepareStatement("INSERT INTO " + TABLE_NAME + " (user_username, order_date)"
													+ " VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);
		
		PreparedStatement consumeMerch = connection.prepareStatement("UPDATE merchandise SET amount = amount - ? WHERE product_id = ?;");
		
		prepStmt.setString(1, bean.getUser_username());
		prepStmt.setTimestamp(2,  bean.getOrder_date());
		
		
		if (prepStmt.executeUpdate() == 0) {
			LOGGER.log(Level.SEVERE, "Qualcosa è andato storto in OrderDAO");
			return;
		}
		
			
		ResultSet rs = prepStmt.getGeneratedKeys();
		if (rs.next()) {
			
			int orderID = rs.getInt(1);
			prepStmt.close();
			prepStmt = connection.prepareStatement("INSERT INTO " + TABLE_NAME2 + " (order_id, product_id, product_name, product_type, "
					+ "product_price, quantity)"
					+ " VALUES (?, ?, ?, ?, ?, ?)");
			
			ProductDAO productDAO = new ProductDAO(dataSource);
			
			
			for (Entry<Integer, Integer> entry : bean.getCart().getProducts()) {
				

				ProductBean currentProduct = productDAO.doRetrieveByKey(entry.getKey());
				
				if (currentProduct == null) {
					LOGGER.log(Level.WARNING, "Nessuna corrispondenza trovata in Products per un articolo dell\'Ordine");
					break;
				}
				
					
				prepStmt.setInt(1, orderID);
				prepStmt.setInt(2, entry.getKey());
				prepStmt.setString(3, currentProduct.getName());
				prepStmt.setString(4, currentProduct.getType());
				prepStmt.setFloat(5, currentProduct.getPrice());
				prepStmt.setInt(6, entry.getValue());
				prepStmt.addBatch();
				
				if (currentProduct.getType().equals("Merchandise")) {
					
					MerchBean tempMerch = new MerchDAO(dataSource).doRetrieveByKey(entry.getKey());
					consumeMerch.setInt(1, entry.getValue());
					consumeMerch.setInt(2, tempMerch.getId());
					consumeMerch.addBatch();
				}
			
				
			
			}
			consumeMerch.executeBatch();
	
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


	public OrderBean doRetrieveByKey(Integer key) throws SQLException {

		Connection connection = null;
		PreparedStatement prepStmt = null;
		OrderBean order = null;
		
		
		try {
			
			
			connection = dataSource.getConnection();
			prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id = ?;");
			prepStmt.setInt(1, key);
			
			ResultSet rs = prepStmt.executeQuery();
			
			
			order = buildOrder(rs, connection);
			
			
			
				
	
		} finally {
			try {
				if (prepStmt != null)
					prepStmt.close();
			} finally {
				if (connection != null)
					connection.close();
			}
			
			
		}
		
		return order;
	}


	public synchronized Collection<OrderBean> doRetrieveAll(String order) throws SQLException {

			Collection<OrderBean> orders = new ArrayList<>();
			
			Connection connection = null;
			PreparedStatement prepStmt = null;
			
			try {
			
			String sanitizedOrder = SecurityUtils.sanitizeForOrder(order);
		
			connection = dataSource.getConnection();
			prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " ORDER BY " + sanitizedOrder+ " ;");
			
			ResultSet rs = prepStmt.executeQuery();
			
			OrderBean currentOrder = null;
			
			while(rs.next()) {
				
				ResultSet proxyRS = rs;
				currentOrder = buildOrder(proxyRS, connection);
				orders.add(currentOrder);
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
			
			
			
			return orders;
			
	}
	
	public synchronized Collection<OrderBean> doRetrieveAll(Timestamp fromDate, Timestamp toDate, String order) throws SQLException {
		
		Collection<OrderBean> orders = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement prepStmt = null;
		
		try {
			
		
		
		order = SecurityUtils.sanitizeForOrder(order);
		
		connection = dataSource.getConnection();
		prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE order_date >= ? "
				+ " AND order_date <= ? ORDER BY " + order + ";");
		prepStmt.setTimestamp(1, fromDate);
		prepStmt.setTimestamp(2, toDate);

		
		ResultSet rs = prepStmt.executeQuery();
		
		OrderBean currentOrder = null;
		
		while(rs.next()) {
			
			ResultSet proxyRS = rs;
			currentOrder = buildOrder(proxyRS, connection);
			orders.add(currentOrder);
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
		
		
		
		return orders;
		
	
	}
	
	public Collection<OrderBean> doRetrieveAllByUser(String value) throws SQLException {

		Collection<OrderBean> orders = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement prepStmt = null;
		
		try {
			
		connection = dataSource.getConnection();
		prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE user_username = ?;");
		
		prepStmt.setString(1,  value);
		
		ResultSet rs = prepStmt.executeQuery();
		
		OrderBean currentOrder = null;
		
		while(rs.next()) {
			
			ResultSet proxyRS = rs;
			currentOrder = buildOrder(proxyRS, connection);
			orders.add(currentOrder);
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
	
		
		
		return orders;
	}
	
	public OrderBean buildOrder(ResultSet rs, Connection connection) throws SQLException {
		
		OrderBean order = null;
		
		PreparedStatement prepStmt = null;
		
		order = new OrderBean();
		
		order.setId(rs.getInt("id"));
		order.setUser_username(rs.getString("user_username"));
		order.setOrder_date(rs.getTimestamp("order_date"));
		try {
			
		prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME2 +
				 " WHERE order_id = ?;");
		
		prepStmt.setInt(1, order.getId());
		
		rs = prepStmt.executeQuery();
		Collection<Entry<ProductBean, Integer>> products = new HashSet<>();
		ProductBean currentProduct;
		
		while (rs.next()) {
			
			currentProduct = new ProductBean();
			currentProduct.setId(rs.getInt("product_id"));
			currentProduct.setName(rs.getString("product_name"));
			currentProduct.setType(rs.getString("product_type"));
			currentProduct.setPrice(rs.getFloat("product_price"));
			products.add(new SimpleEntry<ProductBean, Integer>(currentProduct, rs.getInt("quantity")));
		}

		order.setProducts(products);
		} finally {
			
		
			if (prepStmt != null)
				prepStmt.close();
			
		}
		return order;
	}


	@Override
	public Collection<OrderBean> doRetrieveAllByProduct(int id) throws SQLException {

		// TEMP STUFF
		return new ArrayList<>();

	}

	

}
