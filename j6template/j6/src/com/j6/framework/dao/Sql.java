package com.j6.framework.dao;

public class Sql {
	public String id;
	public String sql;

	public Sql() {
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public String toString(){
		return sql;
	}
}