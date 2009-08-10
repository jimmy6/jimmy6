package com.j6.framework.util;

import java.util.LinkedList;

public class RecycleCircleList {

	private LinkedList<Object> list = new LinkedList<Object>();
	private int MAX = 1000;

	public RecycleCircleList(int max) {

	}

	public synchronized void add(Object obj) {
		if (list.size() > MAX)
			list.removeLast();
		list.addFirst(obj);
	}

	public synchronized boolean isContain(Object obj) {
		return list.contains(obj);
	}

	/**
	 * Add unique object only.
	 * 
	 * @param obj
	 * @return false if existed else return true and add it to list.
	 */
	public synchronized boolean addUnique(Object obj) {
		if (list.contains(obj))
			return false;

		add(obj);
		return true;
	}

}
