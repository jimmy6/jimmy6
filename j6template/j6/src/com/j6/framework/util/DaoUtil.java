package com.j6.framework.util;

import java.lang.reflect.InvocationTargetException;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

public class DaoUtil {
	public static String ClobToString(Clob cl) {
		String str = "";

		try {
			if (cl != null) {
				long len = cl.length();

				str = cl.getSubString(1, (int) len);

				/*
				 * StringBuffer strOut = new StringBuffer(); String aux;
				 * 
				 * BufferedReader br = new
				 * BufferedReader(cl.getCharacterStream());
				 * 
				 * while ((aux=br.readLine())!=null) strOut.append(aux+"\r\n");
				 * 
				 * str = strOut.toString();
				 */

			}
		}
		/*
		 * catch (IOException ioe) { System.err.println("Syslib.DAO :
		 * ClobToString : IOException : Error Msg="+ioe.getMessage()); }
		 */
		catch (SQLException sqle) {
			System.err
					.println("Syslib.DAO : ClobToString : IOException : Error Msg="
							+ sqle.getMessage());
		} catch (Exception e) {
			System.err
					.println("Syslib.DAO : ClobToString : IOException : Error Msg="
							+ e.getMessage());
		}

		return str;
	}

	protected static String sql2javaName(String name) {
		String column = "";
		name = name.toLowerCase();
		for (int i = 0; i < name.length(); i++) {

			if (name.charAt(i) == '_') {
				column += ++i < name.length() ? String.valueOf(name.charAt(i))
						.toUpperCase() : "";
			} else {
				column += name.charAt(i);
			}
		}
		return column;
	}

	public static void populate(Object bean, ResultSet rs) throws SQLException {
		ResultSetMetaData metaData = rs.getMetaData();
		int ncolumns = metaData.getColumnCount();
		Object sdata;
		BeanWrapper beanO1 = new BeanWrapperImpl(bean);
		for (int i = 1; i <= ncolumns; i++) {
			try {
				// TODO have not tested in double/decimal field
				if (metaData.getColumnTypeName(i).toUpperCase().indexOf("DATE") != -1) {
					sdata = rs.getDate(i);
				} else if (metaData.getColumnTypeName(i).equalsIgnoreCase(
						"CLOB"))
					sdata = ClobToString(rs.getClob(i));
				else
					sdata = rs.getString(i);
				beanO1.setPropertyValue(
						sql2javaName(metaData.getColumnName(i)), sdata);

			} catch (Exception e) {
				// e.printStackTrace();
			}

		}

	}

	public static <T> List<T> populate(SqlRowSet rs, Class<T> returnType) {
		List<T> retList = new ArrayList<T>();

		while (rs.next()) {
			T t = null;
			try {
				t = returnType.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			SqlRowSetMetaData metaData = rs.getMetaData();
			int ncolumns = metaData.getColumnCount();
			Object sdata;
			BeanWrapper beanO1 = new BeanWrapperImpl(t);
			for (int i = 1; i <= ncolumns; i++) {
				try {
					// TODO have not tested in double/decimal field
					if (metaData.getColumnTypeName(i).toUpperCase().indexOf(
							"DATE") != -1)
						sdata = rs.getDate(i);
					// TODO solve clob type
					// } else if
					// (metaData.getColumnTypeName(i).equalsIgnoreCase(
					// "CLOB"))
					// sdata = ClobToString(rs.get);
					else
						sdata = rs.getString(i);
					beanO1.setPropertyValue(sql2javaName(metaData
							.getColumnName(i)), sdata);

				} catch (Exception e) {
					//e.printStackTrace();
				}

			}

			retList.add(t);
		}
		return retList;
	}

}
