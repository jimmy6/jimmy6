package com.j6.framework.application;

import java.util.List; 

public class PackagePatternInjection {
	private List<String> packagePatterns;
	private List<String> packagePatternExceptions;
	
	public List<String> getPackagePatternExceptions() {
		return packagePatternExceptions;
	}

	public void setPackagePatternExceptions(List<String> packagePatternExceptions) {
		this.packagePatternExceptions = packagePatternExceptions;
	}

	public PackagePatternInjection() {
	}

	public void setPackagePatterns(List<String> packagePatterns) {
		this.packagePatterns = packagePatterns;
	}

	public List<String> getPackagePatterns() {
		return packagePatterns;
	}
	
}
