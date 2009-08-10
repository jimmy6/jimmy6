package com.j6.framework.vo;

public abstract class AbstractKeyLabel {
	private String key;
	private String label;

	/**
	 * 
	 * @param key -
	 *            value in DB
	 * @param label -
	 *            for display
	 */
	public AbstractKeyLabel(String key, String label) {
		this.key = key;
		this.label = label;
	}

	public String getKey() {
		return key;
	}

	public String getLabel() {
		return label;
	}

}
