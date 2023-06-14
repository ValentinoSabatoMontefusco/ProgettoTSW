package com.code_fanatic.model.dao;

import java.sql.SQLException;
import java.util.Collection;

public interface IOrderDAO<T, K> extends IGenericDAO<T, K> {

	public Collection<T> doRetrieveAllByUsername(String value) throws SQLException;
}
