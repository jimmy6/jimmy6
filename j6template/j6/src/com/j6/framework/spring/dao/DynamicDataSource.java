package com.j6.framework.spring.dao;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

public class DynamicDataSource implements DataSource, InitializingBean {
	private static final Log log = LogFactory.getLog(DynamicDataSource.class);

	private DataSource dataSource;
	private String jndiName;
	private String driverClassName;
	private String url;
	private String username;
	private String password;

	public DynamicDataSource() {
	}

	public void init() {

	}

	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public Connection getConnection(String arg0, String arg1) throws SQLException {
		return dataSource.getConnection(arg0, arg1);
	}

	public PrintWriter getLogWriter() throws SQLException {
		return dataSource.getLogWriter();
	}

	public int getLoginTimeout() throws SQLException {
		return dataSource.getLoginTimeout();
	}

	public void setLogWriter(PrintWriter arg0) throws SQLException {
		dataSource.setLogWriter(arg0);
	}

	public void setLoginTimeout(int arg0) throws SQLException {
		dataSource.setLoginTimeout(arg0);
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void afterPropertiesSet() throws Exception {
		if (StringUtils.isNotBlank(jndiName)) {

			Context initContext = null;
			try {
				initContext = new InitialContext();
				dataSource = (DataSource) initContext.lookup(jndiName);
				log.info("Using DataSource " + jndiName);
			} catch (Exception e1) {
				try {
					dataSource = (DataSource) initContext.lookup("java:/comp/env/" + jndiName);
					log.info("Using DataSource " + "java:/comp/env/" + jndiName);
				} catch (Exception e2) {
					try {
						if (jndiName.startsWith("comp/env/")) {
							jndiName = jndiName.substring("comp/env/".length());
							System.out.println("\n\n" + jndiName + "\n\n");
							dataSource = (DataSource) initContext.lookup(jndiName);

							log.info("Using DataSource " + jndiName);
						}
					} catch (Exception e3) {
						initBasicDataSource();
					}
				}
			}
		}

		if (dataSource == null) {
			initBasicDataSource();
		}
	}

	private void initBasicDataSource() {

		BasicDataSource basicDataSource = new org.apache.commons.dbcp.BasicDataSource();
		basicDataSource.setDriverClassName(driverClassName);
		basicDataSource.setUrl(url);
		basicDataSource.setUsername(username);
		basicDataSource.setPassword(password);
		dataSource = basicDataSource;
		log.debug("DataSource - " + url);

	}
}
