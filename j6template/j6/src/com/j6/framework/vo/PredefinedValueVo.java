package com.j6.framework.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.AccessType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "APP_PREDEFINED_VALUE")
@AccessType(value = "field")
public abstract class PredefinedValueVo implements Comparable, Serializable {
	private static final long serialVersionUID = -9082356106505693557L;

	@Column(nullable = true, length = 20, name = "flag", updatable = false, insertable = false)
	private String order;

	@Column(nullable = false, length = 50, name = "description")
	private String description;

	@Id
	@Column(nullable = false, length = 20, name = "code")
	private String code;
	 
	public PredefinedValueVo() {
	}

	public String getCode() {

		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private String getOrder() {
		return order;
	}

	private void setOrder(String order) {
		this.order = order;
	}

	public String getFlag() {
		return order;
	}

	// public void getFlag(String flag){
	// order = flag;
	// }
	//	
	public int hashCode() {
		return new HashCodeBuilder().append(getCode()).toHashCode();
	}

	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		}
		if ((other == null) || (other.getClass() != this.getClass()))
			return false;
		PredefinedValueVo castOther = (PredefinedValueVo) other;
		return new EqualsBuilder().append(getCode(), castOther.getCode()).isEquals();
	}

	/**
	 * if Order is empty, sort by Description else sort by Order.
	 * 
	 * @param arg0
	 * @return
	 */
	public int compareTo(Object arg0) {
		PredefinedValueVo predefinedValueVo = ((PredefinedValueVo) arg0);
		if (predefinedValueVo.getOrder() == null || predefinedValueVo.getOrder().equals("")) {
			return getDescription().compareTo(((PredefinedValueVo) arg0).getDescription());
		}
		return getOrder().compareTo(((PredefinedValueVo) arg0).getOrder());
	}
}
