package com.j6.framework.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.AccessType;

@MappedSuperclass
@AccessType(value = "field")
public abstract class VoBase implements Serializable {
	// @Id
	// @Column (name="app_id")
	// private String appId;

	public static final String[] properties = new String[] { "addBY", "datetimeAdd", "updateBy", "datetimeUpdate" };
	public static final String[] propertiesVer = new String[] { "addBY", "datetimeAdd", "updateBy", "datetimeUpdate",
			"version" };
	@Column(name = "ADD_BY", length = 50)
	private Integer addBY;
	@Column(name = "DATETIME_ADD")
	private Date datetimeAdd;
	@Column(name = "UPDATE_BY")
	private Integer updateBy;
	@Column(name = "DATETIME_UPDATE")
	private Date datetimeUpdate;
	@Version
	private int version;

	public VoBase() {
		// //hibernate will not use id as findbyexample so it is fine to set it in initialization.
		// appId=java.util.UUID.randomUUID().toString().replaceAll("-","");
	}

	//	
	// public String getAppId() {
	// return appId;
	// }
	//
	// public void setAppId(String appId) {
	// this.appId = appId;
	// }

	public Integer getAddBY() {
		return addBY;
	}

	public void setAddBY(Integer addBY) {
		this.addBY = addBY;
	}

	public Date getDatetimeAdd() {
		return datetimeAdd;
	}

	public void setDatetimeAdd(Date datetimeAdd) {
		this.datetimeAdd = datetimeAdd;
	}

	public Integer getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}

	public Date getDatetimeUpdate() {
		return datetimeUpdate;
	}

	public void setDatetimeUpdate(Date datetimeUpdate) {
		this.datetimeUpdate = datetimeUpdate;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	// public boolean equals(Object o) {
	// if (this == o) return true;
	// if (o == null ||
	// !(o instanceof VoBase)) {
	//
	// return false;
	// }
	//
	// VoBase other
	// = (VoBase)o;
	//
	// // if the id is missing, return false
	// if (appId == null) return false;
	//
	// // equivalence by id
	// return appId.equals(other.getAppId());
	// }
	//
	// public int hashCode() {
	// if (appId != null) {
	// return appId.hashCode();
	// } else {
	// return super.hashCode();
	// }
	// }

	// public String toString() {
	// return this.getClass().getName()
	// + "[appId=" + appId + "]";
	// }

}
