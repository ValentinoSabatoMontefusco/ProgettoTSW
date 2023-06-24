package com.code_fanatic.model.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;

import com.code_fanatic.model.bean.OrderBean;

public interface IOrderDAO<T, K> extends IGenericDAO<T, K> {

	public Collection<T> doRetrieveAllByUsername(String value) throws SQLException;

	public Collection<OrderBean> doRetrieveAll(Timestamp fromDate, Timestamp toDate, String sort) throws SQLException;
}
