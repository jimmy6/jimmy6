package com.j6.framework.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.j6.framework.exception.DataException;

public interface BasicDao<T> {

	public void save(T obj);

	public void saveAll(Set<T> set);

	public void update(T obj);

	public void updateInsert(T obj);

	public void delete(T obj);

	public void deleteAll(Set<T> set);

	public void refresh(T obj);

	public void flush();

	/**
	 * find object by id which .
	 * 
	 * @param id
	 * @return
	 * @throws DataException
	 */
	public T get(Serializable serializablekey);

	public List<T> loadAll();

	public List<T> findByExample(T obj);

	public List<T> findByExExample(T obj, String... orderBy);

}
