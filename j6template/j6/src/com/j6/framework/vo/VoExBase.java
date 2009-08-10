package com.j6.framework.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.AccessType;

@MappedSuperclass
@AccessType(value = "field")
public abstract class VoExBase implements Serializable {
	// @Id
	// @Column (name="app_id")
	// private String appId;
	public static final String[] properties = new String[] { "modifyDateTime", "modifyIp", "modifyWho", "createDateTime","createIp","createWho" };
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MOD_DT")
	private Date modifyDateTime;

	@Column(name = "MOD_IP", length = 15)
	private String modifyIp;

	@Column(name = "MOD_WHO", length = 15)
	private String modifyWho;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CRT_DT")
	private Date createDateTime;

	@Column(name = "CRT_IP", length = 15)
	private String createIp;

	@Column(name = "CRT_WHO", length = 15)
	private String createWho;

	public VoExBase() {
		// //hibernate will not use id as findbyexample so it is fine to set it in initialization.
		// appId=java.util.UUID.randomUUID().toString().replaceAll("-","");
	}

	public Date getModifyDateTime() {
		return modifyDateTime;
	}

	public void setModifyDateTime(Date modifyDateTime) {
		this.modifyDateTime = modifyDateTime;
	}

	public String getModifyIp() {
		return modifyIp;
	}

	public void setModifyIp(String modifyIp) {
		this.modifyIp = modifyIp;
	}

	public String getModifyWho() {
		return modifyWho;
	}

	public void setModifyWho(String modifyWho) {
		this.modifyWho = modifyWho;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getCreateIp() {
		return createIp;
	}

	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}

	public String getCreateWho() {
		return createWho;
	}

	public void setCreateWho(String createWho) {
		this.createWho = createWho;
	}

}
