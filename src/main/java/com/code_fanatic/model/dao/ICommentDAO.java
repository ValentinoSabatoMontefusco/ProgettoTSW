package com.code_fanatic.model.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;

import com.code_fanatic.model.bean.CommentBean;

public interface ICommentDAO extends IGenericDAO<CommentBean, Integer> {

	
	public Collection<CommentBean> doRetrieveAllByUser(String user) throws SQLException;
	
	public Collection<CommentBean> doRetrieveAllByProduct(int id) throws SQLException; 
	
	public Collection<CommentBean> doRetrieveAll(Timestamp fromDate, Timestamp toDate, String order) throws SQLException;

}
