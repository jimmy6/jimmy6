package com.j6.project.common.jsf;

import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

public class MBeanUtil {

	private MBeanUtil() {
	}

	public static List<SelectItem> buildActionSelectItems(String... actions) {
		List<SelectItem> retList = new ArrayList<SelectItem>();

		SelectItem selectItem = new SelectItem();

		for (String action : actions) {
			selectItem = new SelectItem(action, action);

			retList.add(selectItem);
		}
		return retList;
	}

	public static List<SelectItem> buildEnumSelectItems(Enum<?>[] enums) {
		List<SelectItem> retList = new ArrayList<SelectItem>();
		for (Enum enum1 : enums) {
			retList.add(new SelectItem(enum1.name(), enum1.toString()));
		}
		return retList;
	}

}
