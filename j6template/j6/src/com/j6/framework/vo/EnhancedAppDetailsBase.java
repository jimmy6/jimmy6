package com.j6.framework.vo;

public class EnhancedAppDetailsBase extends VoBase implements SelectionFlag {

	/**
	 * not for hibernate persistance purpose. can set anything you want.
	 */
	private boolean selected = false;

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	/** *************************************************************** */

}
