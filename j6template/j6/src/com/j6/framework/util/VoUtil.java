package com.j6.framework.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.j6.framework.vo.SelectionFlag;

public class VoUtil {

	private VoUtil() {
	}

	/**
	 * only return those UserRole which property of selected = true.
	 * 
	 * @param userRoles
	 * @return
	 */
	public static Set filterSelected(Collection<? extends SelectionFlag> selectionFlags) {
		Set<SelectionFlag> retList = new HashSet<SelectionFlag>();

		if (selectionFlags != null)
			for (SelectionFlag selectionFlag : selectionFlags) {
				if (selectionFlag.isSelected()) {
					retList.add(selectionFlag);
				}
			}

		return retList;
	}

	public static void check(Collection<? extends SelectionFlag> selectionFlags, boolean check) {
		if (selectionFlags != null)
			for (SelectionFlag selectionFlag : selectionFlags) {
				selectionFlag.setSelected(check);
			}
	}

}
