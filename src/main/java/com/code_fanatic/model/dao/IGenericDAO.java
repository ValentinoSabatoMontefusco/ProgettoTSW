package com.code_fanatic.model.dao;

import java.sql.SQLException;
import java.util.Collection;


public interface IGenericDAO<T, K> {
	
	
	public void doSave(T bean) throws SQLException;
	
	public boolean doDelete(int key) throws SQLException;
	
	public T doRetrieveByKey (K key) throws SQLException;
	
	public Collection<T> doRetrieveAll(String order) throws SQLException;
	

}
