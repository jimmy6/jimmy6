package com.j6.framework.jsf.application;

public interface PhaseListener {

	public void beforeResponsePhase();

	/**
	 * interceptor of ViewHandler between: renderView[ buildView[ ] afterBuildView ]
	 */
	public void afterBuildView();

}
