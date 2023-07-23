package com.code_fanatic.model.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;

import com.code_fanatic.model.bean.CommentBean;

public interface IExtendedDAO<T, K> extends IGenericDAO<T, K> {

	
	public Collection<T> doRetrieveAllByUser(String user) throws SQLException;
	
	public Collection<T> doRetrieveAllByProduct(int id) throws SQLException; 
	
	public Collection<T> doRetrieveAll(Timestamp fromDate, Timestamp toDate, String order) throws SQLException;

}
