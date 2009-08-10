package com.j6.framework.util;

import java.io.StringWriter;
import java.io.PrintWriter;

/**
 * @author kamhon
 * @since Jan 10, 2008
 */
public class ExceptionUtil {
	public static String getExceptionStacktrace(Throwable cause) {
		StringWriter stringWriter = new StringWriter();
		cause.printStackTrace(new PrintWriter(stringWriter, true));
		return stringWriter.toString();
	}
}
