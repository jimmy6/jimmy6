package com.j6.framework.spring.dao;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JdbcDao extends JdbcDaoSupport {

	public List<?> query(String sql, Object[] params, RowMapper rowMapper) {
		return getJdbcTemplate().query(sql, params, rowMapper);
	}

	public List<?> query(String sql, RowMapper rowMapper) {
		return getJdbcTemplate().query(sql, rowMapper);
	}

	public List<?> queryForList(String sql) {
		return getJdbcTemplate().queryForList(sql);
	}

	public List<?> queryForList(String sql, Object... params) {
		return getJdbcTemplate().queryForList(sql, params);
	}

	public List<?> queryForList(String sql, Object[] params, int[] argTypes) {
		return getJdbcTemplate().queryForList(sql, params, argTypes);
	}

	public int queryForInt(String sql) {
		return getJdbcTemplate().queryForInt(sql);
	}

	public int queryForInt(String sql, Object... params) {
		return getJdbcTemplate().queryForInt(sql, params);
	}

	public int queryForInt(String sql, Object[] params, int[] argTypes) {
		return getJdbcTemplate().queryForInt(sql, params, argTypes);
	}

	public long queryForLong(String sql) {
		return getJdbcTemplate().queryForLong(sql);
	}

	public long queryForLong(String sql, Object... params) {
		return getJdbcTemplate().queryForLong(sql, params);
	}

	public long queryForLong(String sql, Object[] params, int[] argTypes) {
		return getJdbcTemplate().queryForLong(sql, params, argTypes);
	}

	public Object queryForObject(String sql, Class requiredType) {
		return getJdbcTemplate().queryForObject(sql, requiredType);
	}

	public Object queryForObject(String sql, Object[] params, Class requiredType) {
		return getJdbcTemplate().queryForObject(sql, params, requiredType);
	}

	public SqlRowSet queryForRowSet(String sql) {
		return getJdbcTemplate().queryForRowSet(sql);
	}

	public SqlRowSet queryForRowSet(String sql, Object... params) {
		return getJdbcTemplate().queryForRowSet(sql, params);
	}

	public SqlRowSet queryForRowSet(String sql, Object[] params, int[] argTypes) {
		return getJdbcTemplate().queryForRowSet(sql, params, argTypes);
	}

	public int update(String sql) {
		return getJdbcTemplate().update(sql);
	}

	public int update(String sql, Object... params) {
		return getJdbcTemplate().update(sql, params);
	}

	public int update(String sql, Object[] params, int[] argTypes) {
		return getJdbcTemplate().update(sql, params, argTypes);
	}

	public int save(String sql) {
		return getJdbcTemplate().update(sql);
	}

	public int save(String sql, Object... params) {
		return getJdbcTemplate().update(sql, params);
	}

	public int save(String sql, Object[] params, int[] argTypes) {
		return getJdbcTemplate().update(sql, params, argTypes);
	}
}
