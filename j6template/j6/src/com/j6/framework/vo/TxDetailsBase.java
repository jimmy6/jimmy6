package com.j6.framework.vo;

import java.io.Serializable;
import java.util.Date;

public abstract class TxDetailsBase implements Serializable {

	private String txId;
	private Date txDate;

	public TxDetailsBase() {
	}

	/**
	 * @hibernate.timestamp column = "tx_date" unsaved-value = "undefined" access = "property"
	 */
	public Date getTxDate() {
		return txDate;
	}

	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}

	/**
	 * @hibernate.id column = "tx_id" generator-class = "uuid.hex" type = "string" length = "32"
	 */
	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

}
