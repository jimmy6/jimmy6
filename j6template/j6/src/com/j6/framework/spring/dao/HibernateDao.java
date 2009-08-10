package com.j6.framework.spring.dao;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.j6.framework.dao.GenericDao;
import com.j6.framework.exception.DataException;
import com.j6.framework.util.J;
import com.j6.framework.util.ReflectionUtil;
import com.j6.framework.vo.VoBase;

public class HibernateDao extends HibernateDaoSupport implements GenericDao {
	public void delete(Object object) {
		try {
			getHibernateTemplate().delete(object);
		} catch (Exception e) {
			throw new DataException(e);
		}
	}

	public void deleteAll(Set set) {
		try {
			getHibernateTemplate().deleteAll(set);
		} catch (Exception e) {
			throw new DataException(e);
		}
	}

	public void save(Object object) {

		try {
			getHibernateTemplate().save(object);

		} catch (Exception e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

	// public void save(Object object, boolean bResult) {
	//
	// try {
	// if (bResult)
	// getHibernateTemplate().save(object);
	// else
	// getHibernateTemplate().update(object);
	// } catch (Exception e) {
	// throw new DataException(e);
	// }
	// }

	public void saveAll(Set set) {
		try {
			for (Object object : set)
				getHibernateTemplate().save(object);
		} catch (Exception e) {
			throw new DataException(e);
		}
	}

	public void update(Object object) {
		try {
			getHibernateTemplate().update(object);
		} catch (Exception e) {
			throw new DataException(e);
		}
	}

	public void updateInsert(Object object) {
		try {
			getHibernateTemplate().saveOrUpdate(object);
		} catch (Exception e) {
			throw new DataException(e);
		}
	}

	public Object get(Class clazz, Serializable serializablekey) throws DataException {
		try {
			return getHibernateTemplate().get(clazz, serializablekey);
			// getHibernateTemplate().get() will not use proxy. so it is not lazy compare with
			// getHibernateTemplate().load()
		} catch (Exception e) {
			throw new DataException(e);
		}
	}

	public List loadAll(Class clazz) {
		try {
			return getHibernateTemplate().loadAll(clazz);
		} catch (Exception e) {
			throw new DataException(e);
		}
	}

	public List findQueryAsList(String query, Object... objectArray) {
		try {
			if (objectArray == null || objectArray.length == 0)
				return getHibernateTemplate().find(query);
			else
				return getHibernateTemplate().find(query, objectArray);
		} catch (Exception e) {
			throw new DataException(e);
		}
	}

	public List findQueryNameAsList(String queryName, Object... objectArray) {
		try {
			if (objectArray == null || objectArray.length == 0)
				return getHibernateTemplate().findByNamedQuery(queryName);
			else
				return getHibernateTemplate().findByNamedQuery(queryName, objectArray);
		} catch (Exception e) {
			throw new DataException(e);
		}
	}

	public Object findFirst(String query, Object... objectArray) throws DataException {

		Object returnObject = null;
		List list = findBlock(query, 0, 1, objectArray);
		if (list.size() > 0) {
			returnObject = list.get(0);
		}
		return returnObject;
	}

	public Object findUnique(final String queryString, final Object... objectArray) {
		try {
			return getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException {
					Query query = session.createQuery(queryString);
					if ((objectArray != null) && objectArray.length > 0) {
						int paramCount = objectArray.length;
						for (int i = 0; i < paramCount; i++) {
							query.setParameter(i, objectArray[i]);
						}
					}
					return query.uniqueResult();
				}
			});
		} catch (Exception e) {
			throw new DataException(e);
		}
	}

	public List findBlock(final String queryString, final int offset, final int recordCount,
			final Object... objectArray) {
		try {
			return getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException {
					Query query = session.createQuery(queryString);
					int paramCount = 0;
					if (objectArray != null && (paramCount = objectArray.length) > 0) {
						for (int i = 0; i < paramCount; i++) {
							query.setParameter(i, objectArray[i]);
						}
					}
					query.setMaxResults(recordCount);
					query.setFirstResult(offset);
					return query.list();
				}
			});
		} catch (Exception e) {
			throw new DataException(e);
		}
	}

	/**
	 * Empty String will not be included in search criteria. primary/id key and version not consider in findByExample.
	 */
	public List findByExample(Object example) {
		try {
			Object newObj = ReflectionUtil.changeEmptyStrToNull(example);

			return getHibernateTemplate().findByExample(newObj);
		} catch (Exception e) {
			throw new DataException(e);
		}
	}

	/**
	 * consider
	 * 
	 * @id/primary not consider properties in voBase
	 */
	public List findByExExample(Object example, String... orderBy) {

		try {
			Object newObj = example;
			String hql = " FROM ";
			List<Object> params = new ArrayList<Object>();

			BeanWrapper beanO1 = new BeanWrapperImpl(newObj);
			PropertyDescriptor[] proDescriptorsO1 = beanO1.getPropertyDescriptors();
			int propertyLength = proDescriptorsO1.length;
			Entity className = ((Entity) newObj.getClass().getAnnotation(Entity.class));

			if (newObj != null) {
				hql += className.name();
				hql += " IN " + example.getClass();
			}

			hql += " WHERE 1=1";

			for (int i = 0; i < propertyLength; i++) {
				try {
					Object propertyValueO1 = beanO1.getPropertyValue(proDescriptorsO1[i].getName());

					if ((propertyValueO1 instanceof String && StringUtils.isNotBlank((String) propertyValueO1))
							|| propertyValueO1 instanceof Long || propertyValueO1 instanceof Double
							|| propertyValueO1 instanceof Integer || propertyValueO1 instanceof Boolean
							|| propertyValueO1 instanceof Date || propertyValueO1.getClass().isPrimitive()) {
						Field field = example.getClass().getDeclaredField(proDescriptorsO1[i].getName());

						if (proDescriptorsO1[i].getName() != null && field != null
								&& !field.isAnnotationPresent(Transient.class)) {
							if (!Arrays.asList(VoBase.propertiesVer).contains(proDescriptorsO1[i].getName())) {
								hql += " AND " + className.name() + "." + proDescriptorsO1[i].getName() + " = ?";
								params.add(propertyValueO1);
							}
						}
					} else if (propertyValueO1 != null
							&& example.getClass().getDeclaredField(proDescriptorsO1[i].getName()).isAnnotationPresent(
									javax.persistence.Id.class)) {

						BeanWrapper bean = new BeanWrapperImpl(propertyValueO1);
						PropertyDescriptor[] proDescriptors = bean.getPropertyDescriptors();

						for (PropertyDescriptor propertyDescriptor : proDescriptors) {
							Object propertyValueId = bean.getPropertyValue(propertyDescriptor.getName());

							if (propertyValueId != null && ReflectionUtil.isJavaDataType(propertyValueId)) {
								hql += " AND " + className.name() + "." + proDescriptorsO1[i].getName() + "."
										+ propertyDescriptor.getName() + " = ?";
								params.add(bean.getPropertyValue(propertyDescriptor.getName()));
							}
						}
					} else {

					}

				} catch (Exception e) {
					// [exec] java.lang.IllegalAccessException: Class org.apache.commons.beanutils.BeanUtilsBean can not
					// access a member of class com.foremobile.gateway.usermgmt.vo.User with modifiers "private"
					// [exec] at sun.reflect.Reflection.ensureMemberAccess(Unknown Source)
				}
			}
			if (orderBy != null && orderBy.length != 0) {
				hql += " ORDER BY ";
				long count = 1;
				for (String orderByStr : orderBy) {
					if (count != 1)
						hql += ",";
					hql += className.name() + "." + orderByStr;
					count += 1;
				}
			}
J.printPositif(hql);
			return findQueryAsList(hql, params.toArray());
		} catch (Exception e) {
			throw new DataException(e);
		}
	}

	public void refresh(Object example) {
		getHibernateTemplate().refresh(example);
	}

	public void flush() {
		getHibernateTemplate().flush();
	}
}
