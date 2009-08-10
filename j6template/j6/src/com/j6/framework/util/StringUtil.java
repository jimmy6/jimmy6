package com.j6.framework.util;

import org.apache.commons.lang.StringUtils;

public class StringUtil {

	private StringUtil() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] a) {
		J.printNegetif(constructMoney("35059").equalsIgnoreCase("350.59"));
		J.printNegetif(constructMoney("0.59").equalsIgnoreCase("0.59"));
		J.printNegetif(constructMoney("3505.9").equalsIgnoreCase("3505.90"));
		J.printNegetif(constructMoney("3").equalsIgnoreCase("0.03"));
		J.printNegetif(constructMoney("50").equalsIgnoreCase("0.50"));
		J.printNegetif(constructMoney("2.00").equalsIgnoreCase("2.00"));
		J.printNegetif(constructMoney("200").equalsIgnoreCase("2.00"));

	}

	/**
	 * change string to money format. 0.0 -> 0.00 null/empty/not numeric -> 0.00 0 -> 0.00 12 -> 0.12 123 -> 1.23 1234->
	 * 12.34
	 * 
	 * @param str
	 * @return
	 */
	public static String constructMoney(String str) {
		if (!StringUtils.isNumeric(str))
			return "0.00";
		if (StringUtils.isEmpty(str))
			return "0.00";

		if (str.matches("[0-9]*\\.[0-9][0-9]"))
			return str;

		if (str.matches("[0-9]*\\.[0-9]")) {
			str = str + "0";
			return str;
		}

		String newStr = StringUtils.leftPad(str, 3, "0");
		return newStr.substring(0, newStr.length() - 2) + "." + newStr.substring(newStr.length() - 2, newStr.length());
	}
	
	public boolean containStr(String[] strArray , String strCompare ){
		for(String str : strArray){
			if(str.equals(strCompare))
				return true;
		}
		return false;
	}
}
