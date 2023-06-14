package com.code_fanatic.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import javax.sql.DataSource;

import com.code_fanatic.model.bean.Cartesio;
import com.code_fanatic.model.bean.OrderBean;
import com.code_fanatic.model.bean.ProductBean;


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
		
		prepStmt.setString(1, bean.getUsername());
		prepStmt.setTimestamp(2,  bean.getOrder_date());
		
		
		
		if (prepStmt.executeUpdate() > 0) {
			
			ResultSet rs = prepStmt.getGeneratedKeys();
			if (rs.next()) {
				
				int orderID = rs.getInt(1);
				int count = 0;
				System.out.println(String.format("orderID = %d", orderID));
				prepStmt = connection.prepareStatement("INSERT INTO " + TABLE_NAME2 + " (order_id, product_id, product_name, product_price, quantity)"
						+ " VALUES (?, ?, ?, ?, ?)");
				
				ProductDAO productDAO = new ProductDAO(dataSource);
				
				
				for (Entry<Integer, Integer> entry : bean.getCart().getProducts()) {
					

					ProductBean currentProduct = productDAO.doRetrieveByKey(entry.getKey());
					
					if (currentProduct != null)  {
						
						prepStmt.setInt(1, orderID);
						prepStmt.setInt(2, entry.getKey());
						prepStmt.setString(3, currentProduct.getName());
						prepStmt.setFloat(4, currentProduct.getPrice());
						prepStmt.setInt(5, entry.getValue());
						prepStmt.addBatch();
						count++;
					} else {
						System.err.println("Nessuna corrispondenza trovata in Products per un articolo nell'ordine");
					}
					
				
				}
				
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
			
		
			connection = dataSource.getConnection();
			prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " ORDER BY ?;");
			prepStmt.setString(1, order);
			
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
			currentProduct.setPrice(rs.getFloat("product_price"));
			products.add(new SimpleEntry<ProductBean, Integer>(currentProduct, rs.getInt("quantity")));
		}

		order.setProducts(products);
		
		return order;
	}

	

}
