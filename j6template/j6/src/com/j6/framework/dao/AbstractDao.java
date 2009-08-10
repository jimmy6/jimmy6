package com.j6.framework.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public abstract class AbstractDao<T> {

	private T t;

	private GenericDao genericDao;

	public AbstractDao(T t) {
		this.t = t;
	}

	public void setGenericDao(GenericDao genericDao) {
		this.genericDao = genericDao;
	}

	//
	// private T getT() {
	// return t;
	// }
	//
	// private void setT(T t) {
	// this.t = t;
	// }
	//	
	public void delete(T object) {
		genericDao.delete(object);
	}

	public void deleteAll(Set<T> set) {
		genericDao.deleteAll(set);
	}

	public void save(T object) {
		genericDao.save(object);
	}

	public void saveAll(Set<T> set) {
		genericDao.saveAll(set);
	}

	public void update(T object) {
		genericDao.update(object);
	}

	public void updateInsert(T object) {
		genericDao.updateInsert(object);
	}

	public T get(Serializable serializablekey) {
		return (T) genericDao.get(t.getClass(), serializablekey);
	}

	public List<T> loadAll() {
		return (List<T>) genericDao.loadAll(t.getClass());
	}

	public List<T> findByExample(T example) {
		return (List<T>) genericDao.findByExample(example);
	}

	public List<T> findByExExample(T example, String... orderByArray) {
		return (List<T>) genericDao.findByExExample(example, orderByArray);
	}

	protected List<T> findQueryAsList(String query, Object... objectArray) {
		return (List<T>) genericDao.findQueryAsList(query, objectArray);
	}

	protected List<T> findQueryNameAsList(String queryName, Object... objectArray) {
		return (List<T>) genericDao.findQueryNameAsList(queryName, objectArray);
	}

	protected T findFirst(String query, Object... objectArray) {
		return (T) genericDao.findFirst(query, objectArray);
	}

	protected T findUnique(final String queryString, final Object... objectArray) {
		return (T) genericDao.findUnique(queryString, objectArray);
	}

	protected List<T> findBlock(final String queryString, final int offset, final int recordCount,
			final Object... objectArray) {
		return (List<T>) genericDao.findBlock(queryString, offset, recordCount, objectArray);
	}

	public void refresh(T example) {
		genericDao.refresh(example);
	}

	public void flush() {
		genericDao.flush();
	}
}
