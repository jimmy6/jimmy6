package com.j6.framework.dao;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.j6.framework.spring.dao.JdbcDao;
import com.j6.framework.util.DaoUtil;
import com.j6.framework.util.J;

public class AbstractJdbcDao {

	private JdbcDao jdbcDao;
	private SchemaReplaceTo schemaReplaceTo;
	private String[] fromSchemas;
	private String[] toSchemas;

	public String replace(String sql) {
		if (fromSchemas != null)
			for (int i = 0; i < fromSchemas.length; i++)
				sql = StringUtils.replace(sql, fromSchemas[i], toSchemas[i]);

		return sql;
	}

	public void setJdbcDao(JdbcDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}

	// TODO this work for oracle. comment for fast mysql implementation first
	// private String buildOffsetSql(String sql, TableModel<?> tableModel) {
	// sql = "select * from ( select rownum as rownumber,q.* from ( " + sql +
	// " )q ) where rownumber between " //
	// + (((tableModel.getPageNo() - 1) * tableModel.getPageSize()) + 1) //
	// + " and " + tableModel.getPageNo() * tableModel.getPageSize(); //
	// return sql;
	// }

	private String buildOffsetSql(String sql, TableModel<?> tableModel) {
		sql = sql + " LIMIT "
				+ tableModel.getPageSize() //
				+ " OFFSET "
				+ (((tableModel.getPageNo() - 1) * tableModel.getPageSize())); //
		return sql;
	}

	private String buildCountSql(String sql) {
		sql = "select count(*) from ( " + sql + " ) c ";
		return sql;
	}

	/**
	 * 
	 * TODO will test performance of
	 * conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE
	 * ,ResultSet.readonly )
	 * 
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * @param tableModel
	 * @param params
	 * @return
	 */
	public <T> List<T> query(String sql, Class<T> returnType,
			TableModel<T> tableModel, Object... params) {
		String sqlReplaced = replace(sql);
		if (tableModel != null) {

			String sqlOffset = buildOffsetSql(sqlReplaced, tableModel);
			String sqlCount = buildCountSql(sqlReplaced);

			tableModel
					.setTotalNoOfRecord(jdbcDao.queryForInt(sqlCount, params));

			if (tableModel.getTotalNoOfRecord() != 0) {
				SqlRowSet set = jdbcDao.queryForRowSet(replace(sqlOffset),
						params);
				tableModel.setResults(DaoUtil.populate(set, returnType));

			} else
				tableModel.setResults(new ArrayList<T>());
			return tableModel.getResults();
		} else {
			SqlRowSet set = jdbcDao.queryForRowSet(sqlReplaced, params);
			return DaoUtil.populate(set, returnType);
		}

	}

	public <T> List<?> query(String sql, RowMapper rowMapper,
			TableModel<T> tableModel, Object... params) {
		String sqlReplaced = replace(sql);
		if (tableModel != null) {

			String sqlOffset = buildOffsetSql(sqlReplaced, tableModel);
			String sqlCount = buildCountSql(sqlReplaced);

			tableModel
					.setTotalNoOfRecord(jdbcDao.queryForInt(sqlCount, params));
			// J.printNegetif(".."+jdbcDao.queryForLong(sqlCount));
			if (tableModel.getTotalNoOfRecord() != 0)
				tableModel.setResults((List<T>) jdbcDao.query(
						replace(sqlOffset), params, rowMapper));
			else
				tableModel.setResults(new ArrayList<T>());
			return tableModel.getResults();

		} else {
			return jdbcDao.query(sqlReplaced, params, rowMapper);
		}
	}

	public List<?> query(String sql, RowMapper rowMapper, Object... params) {
		return jdbcDao.query(replace(sql), params, rowMapper);
	}

	public List<?> query(String sql, RowMapper rowMapper) {
		return jdbcDao.query(replace(sql), rowMapper);
	}

	public int queryForInt(String sql) {
		return jdbcDao.queryForInt(replace(sql));
	}

	public int queryForInt(String sql, Object... params) {
		return jdbcDao.queryForInt(replace(sql), params);
	}

	public int queryForInt(String sql, Object[] params, int[] argTypes) {
		return jdbcDao.queryForInt(replace(sql), params, argTypes);
	}

	public List<?> queryForList(String sql) {
		return jdbcDao.queryForList(replace(sql));
	}

	public List<?> queryForList(String sql, Object... params) {
		return jdbcDao.queryForList(replace(sql), params);
	}

	public List<?> queryForList(String sql, Object[] params, int[] argTypes) {
		return jdbcDao.queryForList(replace(sql), params, argTypes);
	}

	public long queryForLong(String sql) {
		return jdbcDao.queryForLong(replace(sql));
	}

	public long queryForLong(String sql, Object... params) {
		return jdbcDao.queryForLong(replace(sql), params);
	}

	public long queryForLong(String sql, Object[] params, int[] argTypes) {
		return jdbcDao.queryForLong(replace(sql), params, argTypes);
	}

	public Object queryForObject(String sql, Class requiredType) {
		return jdbcDao.queryForObject(replace(sql), requiredType);
	}

	public Object queryForObject(String sql, Object[] params, Class requiredType) {
		return jdbcDao.queryForObject(replace(sql), params, requiredType);
	}

	public SqlRowSet queryForRowSet(String sql) {
		return jdbcDao.queryForRowSet(replace(sql));
	}

	public SqlRowSet queryForRowSet(String sql, Object... params) {
		return jdbcDao.queryForRowSet(replace(sql), params);
	}

	public SqlRowSet queryForRowSet(String sql, Object[] params, int[] argTypes) {
		return jdbcDao.queryForRowSet(replace(sql), params, argTypes);
	}

	public int update(String sql) {
		return jdbcDao.update(replace(sql));
	}

	public int update(String sql, Object... params) {
		return jdbcDao.update(replace(sql), params);
	}

	public int update(String sql, Object[] params, int[] argTypes) {
		return jdbcDao.update(replace(sql), params, argTypes);
	}

	public int save(String sql) {
		return jdbcDao.save(replace(sql));
	}

	public int save(String sql, Object... params) {
		return jdbcDao.save(replace(sql), params);
	}

	public int save(String sql, Object[] params, int[] argTypes) {
		return jdbcDao.save(replace(sql), params, argTypes);
	}

	public void setSchemaReplaceTo(SchemaReplaceTo schemaReplaceTo) {
		fromSchemas = StringUtils.split(schemaReplaceTo.getFromSchema(), ";");
		toSchemas = StringUtils.split(schemaReplaceTo.getToSchema(), ";");
		this.schemaReplaceTo = schemaReplaceTo;
	}

}
