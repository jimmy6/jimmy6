package com.j6.framework.user.vo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.j6.framework.vo.PredefinedValueVo;

@Entity
@DiscriminatorValue("Role_Activity")
public class RoleActivity extends PredefinedValueVo {

	public RoleActivity() {
	}

}
