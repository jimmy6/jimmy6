package com.j6.framework.dao;

import java.util.List;

public abstract class AbstractReportDao {

	private GenericDao genericDao;

	public void setGenericDao(GenericDao genericDao) {
		this.genericDao = genericDao;
	}

	protected List<?> findQueryAsList(String query, Object... objectArray) {
		return (List<?>) genericDao.findQueryAsList(query, objectArray);
	}

	protected List<?> findQueryNameAsList(String queryName, Object... objectArray) {
		return (List<?>) genericDao.findQueryNameAsList(queryName, objectArray);
	}

	protected Object findUnique(final String queryString, final Object... objectArray) {
		return genericDao.findUnique(queryString, objectArray);
	}

	protected Object findFirst(final String queryString, final Object... objectArray) {
		return genericDao.findFirst(queryString, objectArray);
	}

	protected List<?> findBlock(final String queryString, final int offset, final int recordCount,
			final Object... objectArray) {
		return (List<?>) genericDao.findBlock(queryString, offset, recordCount, objectArray);
	}

}
