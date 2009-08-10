package com.j6.project.user.controller.managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.j6.framework.jsf.managedbean.AbstractPreValueBean;
import com.j6.framework.user.vo.RoleActivity;

public class PredefinedValueBean extends AbstractPreValueBean {

	/** List handle predefined value Vo's value* */
	List<RoleActivity> roleActivities = new ArrayList<RoleActivity>();

	/** ***************************************** */

	public PredefinedValueBean() {
	}

	// /**
	// * <pre>
	// * clear all the value in predefined value. Needed when you want to retrieve data again from DB.
	// * </pre>
	// */
	// public void clearAllPredefinedValue(){
	// races.clear();
	// }

	public List<SelectItem> getRoleActivities4SelectItems() {

		roleActivities = getPredefinedValue(RoleActivity.class, roleActivities);
		return constructListOfSelectItem(roleActivities, false);
	}

}
