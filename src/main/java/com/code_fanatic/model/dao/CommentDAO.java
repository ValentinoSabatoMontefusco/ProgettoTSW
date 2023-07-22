package com.code_fanatic.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import com.code_fanatic.control.utils.SecurityUtils;
import com.code_fanatic.model.bean.CommentBean;
import com.code_fanatic.model.bean.OrderBean;
import com.code_fanatic.model.bean.ProductBean;
import com.mysql.cj.exceptions.RSAException;

public class CommentDAO implements ICommentDAO {
	
	DataSource ds = null;
	private static final String TABLE_NAME = "comments";
	
	public CommentDAO(DataSource ds) {
		
		this.ds = ds;
	}

	@Override
	public synchronized void doSave(CommentBean bean) throws SQLException {
		
		Connection connection = ds.getConnection();
		PreparedStatement prepStmt = null;
		// INSERIMENTO NUOVO COMMENTO 
		
		if (bean.getId() == 0) {
			 prepStmt = connection.prepareStatement("INSERT INTO " + TABLE_NAME + " (user_username,"
					+ " product_id, create_time, content) "
					+ "VALUES (?, ?, ?, ?);");
			
			
			prepStmt.setString(1,  bean.getUser_username());
			prepStmt.setInt(2, bean.getProduct_id());
			prepStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			prepStmt.setString(4, bean.getContent());
			
			int outcome = prepStmt.executeUpdate();
			
			if (outcome > 0) {
				System.out.println("Commento inserito con successo");
			} else {
				System.out.println("Inserimento commento fallito");
			}
		
		// MODIFICA COMMENTO
		} else {
		
			 prepStmt = connection.prepareStatement("UPDATE " + TABLE_NAME + " SET "
					+ "content = ? WHERE id = ?;");
			
			
			prepStmt.setString(1,  bean.getContent());
			prepStmt.setInt(2,  bean.getId());
			
			int outcome = prepStmt.executeUpdate();
			
			if (outcome > 0) {
				System.out.println("Commento modificato con successo");
			} else {
				System.out.println("Modifca commento fallita");
			}
		}
	
		try {
			if (prepStmt != null)
				prepStmt.close();
		} finally {
			if (connection != null)
				connection.close();
		}
		
	}

	@Override
	public boolean doDelete(int key) throws SQLException {

		Connection connection = null;
		PreparedStatement prepStmt = null;
		
		connection = ds.getConnection();
		prepStmt = connection.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE id = ?;");
		
		prepStmt.setInt(1, key);
		int del = prepStmt.executeUpdate();
		try {
			if (prepStmt != null)
				prepStmt.close();
		} finally {
			if (connection != null)
				connection.close();
		}
		
		return del > 0;
	}

	@Override
	public CommentBean doRetrieveByKey(Integer key) throws SQLException {
		
		Connection connection = null;
		PreparedStatement prepStmt = null;
		CommentBean comment = null;
		

			
		connection = ds.getConnection();
		prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id = ?;");
		prepStmt.setInt(1, key);
		
		ResultSet rs = prepStmt.executeQuery();
		
		if (rs.next()) {
				
			comment = buildComment(rs);
		} 

		try {
			if (prepStmt != null)
				prepStmt.close();
		} finally {
			if (connection != null)
				connection.close();
		}
	
			
		return comment;
	}

	@Override
	public Collection<CommentBean> doRetrieveAll(String order) throws SQLException {
		
		
		Connection connection = ds.getConnection();
		
		order = SecurityUtils.sanitizeForComment(order);
		PreparedStatement prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " ORDER BY "+ order + ";");
		
		ResultSet rs = prepStmt.executeQuery();
		
		ArrayList<CommentBean> comments = new ArrayList<CommentBean>();
		CommentBean currentComment;
		
		while(rs.next()) {
			
			currentComment = buildComment(rs);
			
			if (currentComment != null)
				comments.add(currentComment);
			
			
		}
		
		try {
			if (prepStmt != null)
				prepStmt.close();
		} finally {
			if (connection != null)
				connection.close();
		}
	
		if (comments.size() == 0)
			System.err.println("Nessun commento trovato");
		
		return comments;
	}
	
public Collection<CommentBean> doRetrieveAllByUser(String username) throws SQLException {
		
		
		Connection connection = ds.getConnection();
		
		PreparedStatement prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE user_username = ?;");
		
		prepStmt.setString(1, username);
		
		ResultSet rs = prepStmt.executeQuery();
		
		ArrayList<CommentBean> comments = new ArrayList<CommentBean>();
		CommentBean currentComment;
		
		while(rs.next()) {
			
			currentComment = buildComment(rs);
			
			if (currentComment != null)
				comments.add(currentComment);
			
			
		}
		
		try {
			if (prepStmt != null)
				prepStmt.close();
		} finally {
			if (connection != null)
				connection.close();
		}
		
		if (comments.size() == 0)
			System.err.println("Nessun commento trovato per questo utente");
		
		return comments;
	}

	public Collection<CommentBean> doRetrieveAllByProduct(int id) throws SQLException {
		
		
		Connection connection = ds.getConnection();
		
		PreparedStatement prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE product_id = ?;");
		
		prepStmt.setInt(1, id);
		
		ResultSet rs = prepStmt.executeQuery();
		
		ArrayList<CommentBean> comments = new ArrayList<CommentBean>();
		CommentBean currentComment;
		
		while(rs.next()) {
			
			currentComment = buildComment(rs);
			
			if (currentComment != null)
				comments.add(currentComment);
			
			
		}
		
		try {
			if (prepStmt != null)
				prepStmt.close();
		} finally {
			if (connection != null)
				connection.close();
		}
	
		if (comments.size() == 0)
			System.err.println("Nessun commento trovato per questo utente");
			
		return comments;
	}
	
public synchronized Collection<CommentBean> doRetrieveAll(Timestamp fromDate, Timestamp toDate, String order) throws SQLException {
		
		Collection<CommentBean> comments = new ArrayList<CommentBean>();
		
		Connection connection = null;
		PreparedStatement prepStmt = null;
		
		order = SecurityUtils.sanitizeForComment(order);
	
		connection = ds.getConnection();
		prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE create_time >= ? "
				+ " AND create_time <= ? ORDER BY " + order + ";");
		prepStmt.setTimestamp(1, fromDate);
		prepStmt.setTimestamp(2, toDate);


		
		ResultSet rs = prepStmt.executeQuery();
		
		CommentBean currentComment = null;
		
		while(rs.next()) {
			
			currentComment = buildComment(rs);
			if (currentComment != null)
				comments.add(currentComment);
		}
		

		try {
			if (prepStmt != null)
				prepStmt.close();
		} finally {
			if (connection != null)
				connection.close();
		}
	
		if (comments.size() == 0)
			System.err.println("Nessun commento trovato");
			
		
		
		return comments;
		
	
	}

	
	private CommentBean buildComment (ResultSet rs) throws SQLException {
		
		CommentBean comment = new CommentBean();
		
		comment.setId(rs.getInt("id"));
		comment.setContent(rs.getString("content"));
		comment.setUser_username(rs.getString("user_username"));
		comment.setCreate_time(rs.getTimestamp("create_time"));
		comment.setProduct_id(rs.getInt("product_id"));
		
		return comment;
		
	}
}
