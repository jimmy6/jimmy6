package com.j6.framework.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import com.j6.framework.util.ReflectionUtil;

public class Sqls {
	private List<Sql> sqls = new ArrayList<Sql>();

	public Sqls() {
	}

	public void addSqls(Sqls sqls) {
		this.sqls.addAll(sqls.getSqls());
	}

	public Sql findSql(String id) {
		for (Sql sql : sqls) {
			if (sql.getId().equals(id))
				return sql;
		}
		return null;
	}

	public static void main(String a[]) throws IOException, SAXException {
		Digester digester = new Digester();
		digester.setValidating(false);

		digester.addObjectCreate("sqls", Sqls.class);
		digester.addObjectCreate("sqls/sql", Sql.class);
		digester.addSetProperties("sqls/sql");
		digester.addSetNext("sqls/sql", "addSql");
		digester.addBeanPropertySetter("sqls/sql", "sql");
		digester.addSetProperties("sqls/sql", "id", "id");

		File inputFile = new File("D:\\workspace\\amms\\src\\com\\privasia\\amms\\resources\\amms.oracle.sql.xml");
		Sqls sqls2 = (Sqls) digester.parse(inputFile);
		System.out.println(sqls2.sqls.get(0).getId());
		System.out.println(sqls2.sqls.get(0).getSql());

		System.out.println(ReflectionUtil.findFileNames("com\\.privasia\\.amms\\..+?\\.sql\\.xml".substring(0,
				"com\\.privasia\\.amms\\..+?\\.sql\\.xml".indexOf("\\")), true,
				"com\\.privasia\\.amms\\..+?\\.sql\\.xml"));

	}

	public List<Sql> getSqls() {
		return sqls;
	}

	public void addSql(Sql sql) {
		this.sqls.add(sql);
	}

}
