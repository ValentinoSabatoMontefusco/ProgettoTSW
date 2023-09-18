package com.code_fanatic.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.code_fanatic.control.utils.SecurityUtils;
import com.code_fanatic.model.bean.CommentBean;


public class CommentDAO implements IExtendedDAO<CommentBean, Integer> {
	
	DataSource ds = null;
	private static final String TABLE_NAME = "comments";
	private static final Logger LOGGER = Logger.getLogger(CommentDAO.class.getName());
	
	public CommentDAO(DataSource ds) {
		
		this.ds = ds;
	}

	@Override
	public synchronized void doSave(CommentBean bean) throws SQLException {
		
		
		Connection connection = null;
		PreparedStatement prepStmt = null;
		try {
			
		
		connection = ds.getConnection();

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
				LOGGER.log(Level.INFO, "Commento inserito con successo");
			} else {
				LOGGER.log(Level.SEVERE, "Inserimento commento fallita");
			}
		
		// MODIFICA COMMENTO
		} else {
		
			 prepStmt = connection.prepareStatement("UPDATE " + TABLE_NAME + " SET "
					+ "content = ? WHERE id = ?;");
			
			
			prepStmt.setString(1,  bean.getContent());
			prepStmt.setInt(2,  bean.getId());
			
			int outcome = prepStmt.executeUpdate();
			
			if (outcome > 0) {
				LOGGER.log(Level.INFO, "Commento modificato con successo");
			} else {
				LOGGER.log(Level.SEVERE, "Modifica commento fallita");
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

		Connection connection = null;
		PreparedStatement prepStmt = null;
		int del;
		
		try {
					
		connection = ds.getConnection();
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

	@Override
	public CommentBean doRetrieveByKey(Integer key) throws SQLException {
		
		Connection connection = null;
		PreparedStatement prepStmt = null;
		CommentBean comment = null;
		

		try {
			

		connection = ds.getConnection();
		prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id = ?;");
		prepStmt.setInt(1, key);
		
		ResultSet rs = prepStmt.executeQuery();
		
		if (rs.next()) {
				
			comment = buildComment(rs);
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
	
			
		return comment;
	}

	@Override
	public Collection<CommentBean> doRetrieveAll(String order) throws SQLException {
		
		Connection connection = null;
		PreparedStatement prepStmt = null;
		ArrayList<CommentBean> comments = null;
		
		try {
			
			
		connection = ds.getConnection();
		
		order = SecurityUtils.sanitizeForComment(order);
		prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " ORDER BY "+ order + ";");
		
		ResultSet rs = prepStmt.executeQuery();
		
		comments = new ArrayList<>();
		CommentBean currentComment;
		
		while(rs.next()) {
			
			currentComment = buildComment(rs);
			
			comments.add(currentComment);
			
			
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
	
		if ( comments.isEmpty())
			LOGGER.log(Level.WARNING, "Nessun commento trovato");
		
		return comments;
	}
	
public Collection<CommentBean> doRetrieveAllByUser(String username) throws SQLException {
		
		Connection connection = null;
		PreparedStatement prepStmt = null;
		ArrayList<CommentBean> comments = null; 
		
		try  {
			
			
			connection = ds.getConnection();
		
			prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE user_username = ?;");
		
		prepStmt.setString(1, username);
		
		ResultSet rs = prepStmt.executeQuery();
		
		comments = new ArrayList<>();
		CommentBean currentComment;
		
		while(rs.next()) {
			
			currentComment = buildComment(rs);
			
			comments.add(currentComment);
			
			
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
		if (comments.isEmpty())
			LOGGER.log(Level.WARNING, "Nessun commento trovato per questo utente");
		
		return comments;
	}

	public Collection<CommentBean> doRetrieveAllByProduct(int id) throws SQLException {
		
		Connection connection = null;
		PreparedStatement prepStmt = null;
		ArrayList<CommentBean> comments = null;
		
		try {
			
			connection = ds.getConnection();
			
			prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE product_id = ?;");
			
			prepStmt.setInt(1, id);
			
			ResultSet rs = prepStmt.executeQuery();
			comments = new ArrayList<>();
			comments = new ArrayList<>();
			CommentBean currentComment;
			
			while(rs.next()) {
				
				currentComment = buildComment(rs);
				comments.add(currentComment);
				
				
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
		if ( comments.isEmpty())
			LOGGER.log(Level.WARNING, "Nessun commento trovato per questo utente");

			
		return comments;
	}
	
public synchronized Collection<CommentBean> doRetrieveAll(Timestamp fromDate, Timestamp toDate, String order) throws SQLException {
		
		Collection<CommentBean> comments = null;
		
		Connection connection = null;
		PreparedStatement prepStmt = null;
		
		try {
			
			
		
		order = SecurityUtils.sanitizeForComment(order);
	
		connection = ds.getConnection();
		prepStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE create_time >= ? "
				+ " AND create_time <= ? ORDER BY " + order + ";");
		prepStmt.setTimestamp(1, fromDate);
		prepStmt.setTimestamp(2, toDate);


		
		ResultSet rs = prepStmt.executeQuery();
		
		CommentBean currentComment = null;
		comments = new ArrayList<>();
		while(rs.next()) {
			
			currentComment = buildComment(rs);
			comments.add(currentComment);
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
	
		if (comments == null || comments.isEmpty())
			LOGGER.log(Level.WARNING, "Nessun commento trovato");
			
		
		
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
