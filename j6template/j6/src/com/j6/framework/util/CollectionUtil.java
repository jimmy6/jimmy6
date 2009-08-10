package com.j6.framework.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionUtil {

	private CollectionUtil() {
	}

	/**
	 * true if collection is null or empty.
	 * 
	 * @param collection
	 */
	public static boolean isEmpty(Collection collection) {
		return collection == null || collection.isEmpty();
	}

	/**
	 * true if collection is not null and not empty.
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean isNotEmpty(Collection collection) {
		return collection != null && !collection.isEmpty();
	}

	/**
	 * Remove duplicate object base on hashcode
	 * 
	 * @param retList
	 */
	public static List removeDuplicateInList(List retList) {
		Set set = new HashSet(retList);
		retList = new ArrayList(set);
		return retList;
	}

}
