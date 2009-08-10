package com.j6.framework.jsf.managedbean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.model.SelectItem;

import com.j6.framework.application.Application;
import com.j6.framework.dao.GenericDao;
import com.j6.framework.vo.PredefinedValueVo;

public class AbstractPreValueBean {
	private GenericDao genericDao = (GenericDao) Application.lookupBean("genericDao");

	/** please select bean name * */
	// private static String PLEASE_SELECT="pleaseSelect";
	/**
	 * Default Please Select.
	 */
	private String pleaseSelect = "[Please Select]";

	/**
	 * <pre>
	 * alwaysAccessDB = false ,mean whenever you get the Predefined Value, it will not access DB again if it is not empty.
	 * 
	 * </pre>
	 */
	boolean alwaysAccessDB = false;

	public AbstractPreValueBean() {
	}

	public boolean isAlwaysAccessDB() {
		return alwaysAccessDB;
	}

	/**
	 * 
	 * @param alwaysAccessDB
	 */
	public void setAlwaysAccessDB(boolean alwaysAccessDB) {

		this.alwaysAccessDB = alwaysAccessDB;
	}

	/**
	 * 
	 * @param predefinedValueList -
	 *            object in PredefinedValueList must be instance of PredefinedValueVo.
	 * @param needPleaseSelect -
	 *            indicates whether need to add "[Please Select]" SelectItem. Will be the first appear value.
	 * @return
	 */
	protected List<SelectItem> constructListOfSelectItem(List predefinedValueList, boolean needPleaseSelect) {

		SelectItem selectItem;
		List<SelectItem> retList = new ArrayList<SelectItem>();

		if (needPleaseSelect) {
			selectItem = new SelectItem();
			selectItem.setValue("");
			selectItem.setLabel(pleaseSelect);
			retList.add(selectItem);
			// retList.add((SelectItem)FacesUtils.getManagedBean(PLEASE_SELECT));
		}
		for (Object object : predefinedValueList) {
			selectItem = new SelectItem();
			selectItem.setValue(((PredefinedValueVo) object).getCode());
			selectItem.setLabel(((PredefinedValueVo) object).getDescription());
			retList.add(selectItem);
		}
		return retList;
	}

	/**
	 * <pre>
	 * get the Predefined Value.
	 * Have to consider alwaysAccessDB variable.
	 * </pre>
	 */
	protected List getPredefinedValue(Class clazz, List list) {

		if (alwaysAccessDB == true || (list == null || list.size() == 0)) {

			try {
				list = genericDao.loadAll(clazz);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Collections.sort(list);
		}

		return list;
	}

	public String getPleaseSelect() {
		return pleaseSelect;
	}

	public void setPleaseSelect(String pleaseSelect) {
		this.pleaseSelect = pleaseSelect;
	}

}
