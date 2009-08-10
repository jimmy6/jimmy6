package com.j6.framework.spring.aop;

import java.lang.reflect.Method;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.MethodBeforeAdvice;

public class SchemaChangeB4Advice implements MethodBeforeAdvice {

	private String fromSchema;
	private String toSchema;

	public void before(Method m, Object[] args, Object target) throws Throwable {
		if (StringUtils.isNotBlank(fromSchema) && StringUtils.isNotBlank(toSchema))
			if (args.length > 0) {
				if ((args[0] instanceof String) && isSql((String) args[0])) {
					String sql = (String) args[0];
					sql = StringUtils.replace(sql, fromSchema, toSchema);
				}
			}

	}

	/**
	 * check whether it is sql.
	 * 
	 * @param method
	 * @return
	 */
	private boolean isSql(String text) {
		if (org.apache.commons.lang.StringUtils.isBlank(text) || text.length() < 7)
			return false;

		if (text.trim().substring(0, 6).equalsIgnoreCase("update")
				|| text.trim().substring(0, 6).equalsIgnoreCase("select")
				|| text.trim().substring(0, 6).equalsIgnoreCase("delete")) {
			return true;
		} else {
			return false;
		}
	}

	public void setFromSchema(String fromSchema) {
		this.fromSchema = fromSchema;
	}

	public void setToSchema(String toSchema) {
		this.toSchema = toSchema;
	}

}