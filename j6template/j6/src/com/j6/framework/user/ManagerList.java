package com.j6.framework.user;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.j6.framework.util.ReflectionUtil;

public class ManagerList extends java.util.ArrayList<String> {
	private static Log log = LogFactory.getLog(ManagerList.class);

	public ManagerList() {
		try {
			Set<String> clazzStr = ReflectionUtil.findFileNames("com", true, "com.privasia.+?\\.manager\\..+?");
			for (String str : clazzStr) {
				Class clazz = Class.forName(str);
				if (clazz.isInterface()) {
					add((str.substring(str.lastIndexOf(".") + 1).charAt(0) + "").toLowerCase()
							+ str.substring(str.lastIndexOf(".") + 2));
					log.info(str.substring(str.lastIndexOf(".") + 1));
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String a[]) {
		try {
			Set<String> clazzStr = ReflectionUtil.findFileNames("com", true, "com.privasia.+?\\.manager\\..+?");
			for (String str : clazzStr) {
				// if (clazz.) {
				// add(clazz);
				// }
				// add(str.getClass().getName());
				log.info((str.substring(str.lastIndexOf(".") + 1).charAt(0) + "").toLowerCase()
						+ str.substring(str.lastIndexOf(".") + 2));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
