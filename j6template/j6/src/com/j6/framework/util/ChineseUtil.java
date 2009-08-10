package com.j6.framework.util;

import org.apache.commons.lang.StringUtils;

public class ChineseUtil {

	private ChineseUtil() {
	}

	// http://202.9.99.228/cms/routemgmt/broadcast.srv?mobileno=60126377988&broadcastid=MY3GZONE-SUBSCRIPTION&username=system&password=system&contentmsg=8c228c224f60768453c24e0e&contenturl=null&contentext=bst&shortcode=33318&encoding8&price=0000&messagetype=S
	// java code/unicode = \u4eca\u65e5\u306f\u4e16\u754c
	// html code = &#20170;&#26085;&#12399;&#19990;&#30028;
	// web show =?????
	public static void main(String str[]) {

		// test isContainHTMLChineseCode
		J.printPositif(isContainHTMLChineseCode("123&#45678;90;llll") == true);
		J.printPositif(isContainHTMLChineseCode("&#33333;llll") == true);
		J.printPositif(isContainHTMLChineseCode("&#3333;llll") == false);
		J.printPositif(isContainHTMLChineseCode("&#33332;") == true);
		J.printPositif(isContainHTMLChineseCode("&#333332;") == false);
		J.printPositif(isContainHTMLChineseCode("&#3d332;") == false);
		J.printPositif(isContainHTMLChineseCode("&33332;") == false);
		J.printPositif(isContainHTMLChineseCode("") == false);
		// ////////////////////////////

		J.printPositif(convertHexUnicodeToHtmlChinese("8a00").toString().equalsIgnoreCase("&#35328;"));
		J.printPositif(convertToHexUnicode(".!q&#20449;&#23432;&#35834;&#35328;").toString().equalsIgnoreCase(
				"002e002100714fe15b888bfa8a00"));
		J.printPositif(isContainHTMLChineseCode("!!&#20170;&#26085;&#12399;&#19990;&#30028;!!"));

		J
				.printPositif(convertToHexUnicode("&#22312;&#36825;&#20010;&#24773;&#20154;&#33410;&#65292;&#25105;&#36824;&#26377;&#19968;&#20010;&#24895;&#26395;&#65292;&#37027;&#23601;&#26159;&#24076;&#26395;&#20182;&#30495;&#30340;&#20250;&#25913;&#21644;&#24076;&#26395;&#20182;&#30495;&#30340;&#20250;&#23545;&#20320;&#22909;&#12290;&#32780;&#25105;&#23601;&#26159;&#20851;&#24515;&#20320;&#30340;&#26379;&#21451;:-)"));
	}

	/**
	 * HTML chinese code is formatted like &#12345;
	 * 
	 * Logic: - exist atleast one formatted = &#[5numeric]; -
	 * 
	 * @return
	 */
	public static boolean isContainHTMLChineseCode(String str) {

		int strLenght = str.length();

		for (int i = 0; i + 8 <= strLenght; i++) {

			if (isHTMLChineseCode(str.substring(i, i + 8))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * just check first word's symbol.
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isHTMLChineseCode(String str) {
		if (StringUtils.isNotEmpty(str) && str.length() == 8 && str.substring(0, 2).equalsIgnoreCase("&#")
				&& str.substring(7, 8).equalsIgnoreCase(";") && StringUtils.isNumeric(str.substring(2, 3))
				&& StringUtils.isNumeric(str.substring(3, 4)) && StringUtils.isNumeric(str.substring(4, 5))
				&& StringUtils.isNumeric(str.substring(5, 6)) && StringUtils.isNumeric(str.substring(6, 7))) {
			return true;
		}
		return false;
	}

	/**
	 * if it is html chinese code, change &#12345; -> hex in 4 char. if it is not(mean ".!q" in ".!q&#20449;" which is
	 * not html code), change it to ascii and then change to hex 4 char.
	 * 
	 * @return
	 */
	public static StringBuffer convertToHexUnicode(String str) {
		StringBuffer retStr = new StringBuffer();
		int strLength = str.length();
		for (int i = 0; i < strLength; i++)
			if (i + 8 <= strLength && isHTMLChineseCode(str.substring(i, i + 8))) {
				retStr.append(StringUtils.leftPad(Integer.toHexString(Integer.parseInt(str.substring(i + 2, i + 7))),
						4, "0"));
				// J.printLine(StringUtils.leftPad(Integer.toHexString(Integer.parseInt(str.substring(i+3,i+7))),4
				// ,"0"));
				i = i + 7;
			} else {
				// J.printPositif(StringUtils.leftPad(Integer.toHexString(str.charAt(i) & 0xFFFF),4,"0"));
				retStr.append(StringUtils.leftPad(Integer.toHexString(str.charAt(i) & 0xFFFF), 4, "0"));
			}

		return retStr;
	}

	/**
	 * 8a00(hp format) -> &#35328;(website viewable format)
	 * 
	 * @return
	 */
	public static String convertHexUnicodeToHtmlChinese(String str) {
		StringBuffer retStr = new StringBuffer();
		int strLength = str.length();
		for (int i = 0; i < strLength; i++) {
			retStr.append("&#" + (Integer.parseInt(str.substring(i, i + 4), 16) + ";"));
			i = i + 3;
		}
		return retStr.toString();
	}

}
