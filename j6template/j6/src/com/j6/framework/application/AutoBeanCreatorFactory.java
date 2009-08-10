package com.j6.framework.application;

import java.util.List; 

public class AutoBeanCreatorFactory {
	private List<String> packagePatterns;

	public AutoBeanCreatorFactory() {
	}

	public void setPackagePatterns(List<String> packagePatterns) {
		this.packagePatterns = packagePatterns;
	}

	public List<String> getPackagePatterns() {
		return packagePatterns;
	}
	
}
