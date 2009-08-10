package com.j6.framework.dao;

import java.util.ArrayList;
import java.util.List;

public class TableModel<T> {
	private int totalPageNo;
	private int pageNo;
	private int pageSize;
	private List<T> results = new ArrayList<T>();
	private int totalNoOfRecord;

	public TableModel() {
	}

	public TableModel(int pageSize, int pageNo) {
		this.pageSize = pageSize;
		this.pageNo = pageNo;
	}

	public void setTotalNoOfRecord(int totalNoOfRecord) { // TODO not tested
		// yet.
		totalPageNo = (totalNoOfRecord / pageSize)
				+ (totalNoOfRecord % pageSize > 0 ? 1 : 0);
		this.totalNoOfRecord = totalNoOfRecord;
	}

	public int getStartRow() {
		return ((pageNo - 1) * pageSize) + 1;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	public int getTotalPageNo() {
		return totalPageNo;
	}

	public void setTotalPageNo(int totalPageNo) {
		this.totalPageNo = totalPageNo;
	}

	public int getTotalNoOfRecord() {
		return totalNoOfRecord;
	}

}
