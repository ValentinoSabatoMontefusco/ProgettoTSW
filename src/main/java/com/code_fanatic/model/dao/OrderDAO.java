package com.code_fanatic.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import javax.sql.DataSource;

import com.code_fanatic.control.utils.SecurityUtils;
import com.code_fanatic.model.bean.Cartesio;
import com.code_fanatic.model.bean.MerchBean;
import com.code_fanatic.model.bean.OrderBean;
import com.code_fanatic.model.bean.ProductBean;
import com.mysql.cj.util.StringUtils;


public class OrderDAO implements IOrderDAO<OrderBean, Integer> {

	DataSource dataSource = null;
	private static final String TABLE_NAME = "orders";
	private static final String TABLE_NAME2 = "order_products";
	
	public OrderDAO(DataSource dataSource) {
		
		this.dataSource = dataSource;
		
	}

	public synchronized void doSave(OrderBean bean) throws SQLException {
		
		Connection connection = dataSource.getConnection();
		PreparedStatement prepStmt = connection.prepareStatement("INSERT INTO " + TABLE_NAME + " (user_username, order_date)"
													+ " VALUES (?, ?);", PreparedStatement.RETURN_GENERATED_KEYS);
		
		PreparedStatement consumeMerch = connection.prepareStatement("UPDATE merchandise SET amount = amount - ? WHERE product_id = ?;");
		
		prepStmt.setString(1, bean.getUsername());
		prepStmt.setTimestamp(2,  bean.getOrder_date());
		
		
		
		if (prepStmt.executeUpdate() > 0) {
			
			ResultSet rs = prepStmt.getGeneratedKeys();
			if (rs.next()) {
				
				int orderID = rs.getInt(1);
				int count = 0;
				System.out.println(String.format("orderID = %d", orderID));
				prepStmt = connection.prepareStatement("INSERT INTO " + TABLE_NAME2 + " (order_id, product_id, product_name, product_type, "
						+ "product_price, quantity)"
						+ " VALUES (?, ?, ?, ?, ?, ?)");
				
				ProductDAO productDAO = new ProductDAO(dataSource);
				
				
				for (Entry<Integer, Integer> entry : bean.getCart().getProducts()) {
					

					ProductBean currentProduct = productDAO.doRetrieveByKey(entry.getKey());
					
					if (currentProduct != null)  {
						
						prepStmt.setInt(1, orderID);
						prepStmt.setInt(2, entry.getKey());
						prepStmt.setString(3, currentProduct.getName());
						prepStmt.setString(4, currentProduct.getType());
						prepStmt.setFloat(5, currentProduct.getPrice());
						prepStmt.setInt(6, entry.getValue());
						prepStmt.addBatch();
						count++;
						
						if (currentProduct.getType().equals("Merchandise")) {
							
							MerchBean tempMerch = new MerchDAO(dataSource).doRetrieveByKey(entry.getKey());
							consumeMerch.setInt(1, entry.getValue());
							consumeMerch.setInt(2, tempMerch.getId());
							consumeMerch.addBatch();
						}
					} else {
						System.err.println("Nessuna corrispondenza trovata in Products per un articolo nell'ordine");
					}
					
				
				}
				consumeMerch.executeBatch();
				if (count == prepStmt.executeBatch().length) {
					
					System.err.println("Salvataggio ordine apparentemente a buon fine");
				} else {
					
					System.err.println("Potenziali problemi col salvataggio ordine");
				}
			}
			
			if (prepStmt != null)
				prepStmt.close();
			if (connection != null)
				connection.close();
			
			
		}
	
	}


	public boolean doDelete(int key) throws SQLException {

		return false;
	}


	public OrderBean doRetrieveByKey(Integer key) throws SQLException {

		Connection connection = null;
		PreparedStatement prepStmt = null;
		OrderBean order = null;
		
		
			
			connection = dataSource.getConnection();
			prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id = ?;");
			prepStmt.setInt(1, key);
			
			ResultSet rs = prepStmt.executeQuery();
			
			
			order = buildOrder(rs, prepStmt, connection);
			
			
			
				
	

		if (prepStmt != null)
			prepStmt.close();

		if (connection != null)
			connection.close();

			
		return order;
	}


	public synchronized Collection<OrderBean> doRetrieveAll(String order) throws SQLException {

			Collection<OrderBean> orders = new ArrayList<OrderBean>();
			
			Connection connection = null;
			PreparedStatement prepStmt = null;
			
			String sanitizedOrder = SecurityUtils.sanitizeForOrder(order);
		
			connection = dataSource.getConnection();
			System.err.println("sanitizedOrder = " + sanitizedOrder);
			prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " ORDER BY " + sanitizedOrder+ " ;");
			
			ResultSet rs = prepStmt.executeQuery();
			
			OrderBean currentOrder = null;
			
			while(rs.next()) {
				
				ResultSet proxyRS = rs;
				currentOrder = buildOrder(proxyRS, prepStmt, connection);
				orders.add(currentOrder);
			}
			

			if (prepStmt != null)
				prepStmt.close();

			if (connection != null)
				connection.close();
			
			
			return orders;
			
	}
	
	public synchronized Collection<OrderBean> doRetrieveAll(Timestamp fromDate, Timestamp toDate, String order) throws SQLException {
		
		Collection<OrderBean> orders = new ArrayList<OrderBean>();
		
		Connection connection = null;
		PreparedStatement prepStmt = null;
		
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
			currentOrder = buildOrder(proxyRS, prepStmt, connection);
			orders.add(currentOrder);
		}
		

		if (prepStmt != null)
			prepStmt.close();

		if (connection != null)
			connection.close();
		
		
		return orders;
		
	
	}
	
	public Collection<OrderBean> doRetrieveAllByUsername(String value) throws SQLException {

		Collection<OrderBean> orders = new ArrayList<OrderBean>();
		
		Connection connection = null;
		PreparedStatement prepStmt = null;
		
	
		connection = dataSource.getConnection();
		prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE user_username = ?;");
		
		prepStmt.setString(1,  value);
		
		ResultSet rs = prepStmt.executeQuery();
		
		OrderBean currentOrder = null;
		
		while(rs.next()) {
			
			ResultSet proxyRS = rs;
			currentOrder = buildOrder(proxyRS, prepStmt, connection);
			orders.add(currentOrder);
		}
		

		if (prepStmt != null)
			prepStmt.close();

		if (connection != null)
			connection.close();
		
		
		return orders;
	}
	
	public OrderBean buildOrder(ResultSet rs, PreparedStatement prepStmt, Connection connection) throws SQLException {
		
		OrderBean order = null;
		

		
		order = new OrderBean();
		
		order.setId(rs.getInt("id"));
		order.setUsername(rs.getString("user_username"));
		order.setOrder_date(rs.getTimestamp("order_date"));
		
		prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME2 + /*" INNER JOIN "
				+ "products ON product_id = id " +*/  " WHERE order_id = ?;");
		
		prepStmt.setInt(1, order.getId());
		
		rs = prepStmt.executeQuery();
		Collection<Entry<ProductBean, Integer>> products = new HashSet<Entry<ProductBean,Integer>>();
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
		
		return order;
	}

	

}
