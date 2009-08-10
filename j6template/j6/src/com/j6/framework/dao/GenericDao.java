package com.j6.framework.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public interface GenericDao {

	/**
	 * Delete object
	 * 
	 * @param obj
	 */
	public void delete(Object object);

	/**
	 * Delete all from the set
	 * 
	 * @param set
	 */
	public void deleteAll(Set set);

	/**
	 * Save object
	 * 
	 * @param object
	 */
	public void save(Object object);

	/**
	 * Save all into the set
	 * 
	 * @param set
	 */
	public void saveAll(Set set);

	/**
	 * Update object
	 * 
	 * @param object
	 */
	public void update(Object object);

	/**
	 * Update or Insert. base on version will know whether it is new or old object. With Passing the object , hibernate
	 * will determine the object for update or insert.
	 * 
	 * @param object
	 */
	public void updateInsert(Object object);

	/**
	 * Loading data with serializableKey. Can pass in composite object to search
	 * 
	 * @param clazz
	 * @param serializablekey
	 * @return object of type class @
	 */
	public Object get(Class clazz, Serializable serializablekey);

	/**
	 * Load all the data
	 * 
	 * @param clazz
	 * @return return all object into list @
	 */
	public List loadAll(Class clazz);

	/**
	 * Find object using the query into list
	 * 
	 * @param query
	 * @param objectArray
	 * @return List of objects @
	 */
	public List findQueryAsList(String query, Object... objectArray);

	/**
	 * Find object using queryName QueryName to be stored in hbm file.
	 * 
	 * @param queryName
	 * @param objectArray
	 * @return List of objects @
	 */
	public List findQueryNameAsList(String queryName, Object... objectArray);

	/**
	 * Retrieve the first record object
	 * 
	 * @param query
	 * @param objectArray
	 * @return object @
	 */
	public Object findFirst(String query, Object... objectArray);

	/**
	 * Find the unique object
	 * 
	 * @param query
	 * @param objectArray
	 * @return object @
	 */
	public Object findUnique(String query, Object... objectArray);

	/**
	 * Find a block by query
	 * 
	 * @param query
	 * @param objectArray
	 * @param offset
	 * @param recordCount
	 * @return list @
	 */
	public List findBlock(String query, int offset, int recordCount, Object... objectArray);

	/**
	 * Empty String will not be included in search criteria. identifier(primary/id key) and version not consider in
	 * findByExample.
	 */
	public List findByExample(Object example);

	public List findByExExample(Object example, String... orderBy);

	public void refresh(Object example);

	public void flush();
}
