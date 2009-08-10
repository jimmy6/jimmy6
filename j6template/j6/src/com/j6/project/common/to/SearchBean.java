package com.j6.project.common.to;

import com.j6.project.user.vo.User;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * Created by IntelliJ IDEA.
 * User: ENG
 * Date: Sep 27, 2007
 * Time: 10:37:01 AM
 * $Id
 * </pre>
 */
public class SearchBean implements Serializable {
	private User user;
	private String itemName;
	private String itemNameFrom;
	private String itemNameTo;
	private String category;
	private String categoryFrom;
	private String categoryTo;
	private String itemType;
	private Long storeId;
	private String storeName;
	private String storeNameFrom;
	private String storeNameTo;
	private String storeName2From;
	private String storeName2To;
	private String trxCodeFrom;
	private String trxCodeTo;
	private String mrnCode;
	private String mrnCodeFrom;
	private String mrnCodeTo;
	private String mrnIssueCode;
	private String mrnIssueCodeFrom;
	private String mrnIssueCodeTo;
	private String mrnReturnCode;
	private String mrnReturnCodeFrom;
	private String mrnReturnCodeTo;
	private String stkTakeCodeFrom;
	private String stkTakeCodeTo;
	private String woCode;
	private String woCodeFrom;
	private String woCodeTo;
	private Long deptId;
	private Long deptIdFrom;
	private Long deptIdTo;
	private Date dateFrom;
	private Date dateTo;
	private String type;
	private String equipName;
	private String status;
	private String status2;

	public SearchBean() {
	}

	public SearchBean(User user) {
		this.user = user;
		if (user != null) {
			deptId = user.getDeptId();
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMrnCode() {
		return mrnCode;
	}

	public void setMrnCode(String mrnCode) {
		this.mrnCode = StringUtils.upperCase(mrnCode);
	}

	public String getMrnCodeFrom() {
		return mrnCodeFrom;
	}

	public void setMrnCodeFrom(String mrnCodeFrom) {
		this.mrnCodeFrom = StringUtils.upperCase(mrnCodeFrom);
	}

	public String getMrnCodeTo() {
		return mrnCodeTo;
	}

	public void setMrnCodeTo(String mrnCodeTo) {
		this.mrnCodeTo = StringUtils.upperCase(mrnCodeTo);
	}

	public String getMrnIssueCode() {
		return mrnIssueCode;
	}

	public void setMrnIssueCode(String mrnIssueCode) {
		this.mrnIssueCode = StringUtils.upperCase(mrnIssueCode);
	}

	public String getMrnIssueCodeFrom() {
		return mrnIssueCodeFrom;
	}

	public void setMrnIssueCodeFrom(String mrnIssueCodeFrom) {
		this.mrnIssueCodeFrom = StringUtils.upperCase(mrnIssueCodeFrom);
	}

	public String getMrnIssueCodeTo() {
		return mrnIssueCodeTo;
	}

	public void setMrnIssueCodeTo(String mrnIssueCodeTo) {
		this.mrnIssueCodeTo = StringUtils.upperCase(mrnIssueCodeTo);
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getMrnReturnCode() {
		return mrnReturnCode;
	}

	public void setMrnReturnCode(String mrnReturnCode) {
		this.mrnReturnCode = StringUtils.upperCase(mrnReturnCode);
	}

	public String getMrnReturnCodeFrom() {
		return mrnReturnCodeFrom;
	}

	public void setMrnReturnCodeFrom(String mrnReturnCodeFrom) {
		this.mrnReturnCodeFrom = StringUtils.upperCase(mrnReturnCodeFrom);
	}

	public String getMrnReturnCodeTo() {
		return mrnReturnCodeTo;
	}

	public void setMrnReturnCodeTo(String mrnReturnCodeTo) {
		this.mrnReturnCodeTo = StringUtils.upperCase(mrnReturnCodeTo);
	}

	public Long getDeptIdFrom() {
		return deptIdFrom;
	}

	public void setDeptIdFrom(Long deptIdFrom) {
		this.deptIdFrom = deptIdFrom;
	}

	public Long getDeptIdTo() {
		return deptIdTo;
	}

	public void setDeptIdTo(Long deptIdTo) {
		this.deptIdTo = deptIdTo;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getEquipName() {
		return equipName;
	}

	public void setEquipName(String equipName) {
		this.equipName = StringUtils.upperCase(equipName);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = StringUtils.upperCase(status);
	}

	public String getWoCode() {
		return woCode;
	}

	public void setWoCode(String woCode) {
		this.woCode = StringUtils.upperCase(woCode);
	}

	public String getWoCodeFrom() {
		return woCodeFrom;
	}

	public void setWoCodeFrom(String woCodeFrom) {
		this.woCodeFrom = StringUtils.upperCase(woCodeFrom);
	}

	public String getWoCodeTo() {
		return woCodeTo;
	}

	public void setWoCodeTo(String woCodeTo) {
		this.woCodeTo = StringUtils.upperCase(woCodeTo);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = StringUtils.upperCase(type);
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = StringUtils.upperCase(storeName);
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = StringUtils.upperCase(itemName);
	}

	public String getItemNameFrom() {
		return itemNameFrom;
	}

	public void setItemNameFrom(String itemNameFrom) {
		this.itemNameFrom = StringUtils.upperCase(itemNameFrom);
	}

	public String getItemNameTo() {
		return itemNameTo;
	}

	public void setItemNameTo(String itemNameTo) {
		this.itemNameTo = StringUtils.upperCase(itemNameTo);
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = StringUtils.upperCase(category);
	}

	public String getCategoryFrom() {
		return categoryFrom;
	}

	public void setCategoryFrom(String categoryFrom) {
		this.categoryFrom = StringUtils.upperCase(categoryFrom);
	}

	public String getCategoryTo() {
		return categoryTo;
	}

	public void setCategoryTo(String categoryTo) {
		this.categoryTo = StringUtils.upperCase(categoryTo);
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = StringUtils.upperCase(itemType);
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getStoreNameFrom() {
		return storeNameFrom;
	}

	public void setStoreNameFrom(String storeNameFrom) {
		this.storeNameFrom = StringUtils.upperCase(storeNameFrom);
	}

	public String getStoreNameTo() {
		return storeNameTo;
	}

	public void setStoreNameTo(String storeNameTo) {
		this.storeNameTo = StringUtils.upperCase(storeNameTo);
	}

	public String getStkTakeCodeFrom() {
		return stkTakeCodeFrom;
	}

	public void setStkTakeCodeFrom(String stkTakeCodeFrom) {
		this.stkTakeCodeFrom = StringUtils.upperCase(stkTakeCodeFrom);
	}

	public String getStkTakeCodeTo() {
		return stkTakeCodeTo;
	}

	public void setStkTakeCodeTo(String stkTakeCodeTo) {
		this.stkTakeCodeTo = StringUtils.upperCase(stkTakeCodeTo);
	}

	public String getStatus2() {
		return status2;
	}

	public void setStatus2(String status2) {
		this.status2 = status2;
	}

	public String getTrxCodeFrom() {
		return trxCodeFrom;
	}

	public void setTrxCodeFrom(String trxCodeFrom) {
		this.trxCodeFrom = StringUtils.upperCase(trxCodeFrom);
	}

	public String getTrxCodeTo() {
		return trxCodeTo;
	}

	public void setTrxCodeTo(String trxCodeTo) {
		this.trxCodeTo = StringUtils.upperCase(trxCodeTo);
	}

	public String getStoreName2From() {
		return storeName2From;
	}

	public void setStoreName2From(String storeName2From) {
		this.storeName2From = StringUtils.upperCase(storeName2From);
	}

	public String getStoreName2To() {
		return storeName2To;
	}

	public void setStoreName2To(String storeName2To) {
		this.storeName2To = StringUtils.upperCase(storeName2To);
	}
}