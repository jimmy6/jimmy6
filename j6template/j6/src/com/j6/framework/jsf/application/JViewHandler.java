package com.j6.framework.jsf.application;

import java.io.IOException;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <pre>
 * Intercepter for Phaselistener.afterBuildView()
 * Create here instead of PhaseListener.beforeRenderResponsePhase is because of managedBean not exist in requestMap if it is 
 * in restore view initial state
 * </pre>
 * 
 * @author ADMIN
 * 
 */
public class JViewHandler extends com.sun.facelets.FaceletViewHandler {
	private static Log log = LogFactory.getLog(JViewHandler.class);

	public JViewHandler(ViewHandler parent) {
		super(parent);
	}

	@Override
	protected void buildView(FacesContext e, UIViewRoot viewToRender) throws IOException, FacesException {
		log.debug("buildView");
		super.buildView(e, viewToRender);

		// call phaseListener in backing bean
		Map<String, Object> map = e.getCurrentInstance().getExternalContext().getRequestMap();
		// J.printNegetif("map="+map.values().size());
		for (String key : map.keySet()) {
			// J.printNegetif("map=key.."+key);
			Object obj = map.get(key);
			if (obj instanceof PhaseListener) {
				((PhaseListener) obj).afterBuildView();
			}
		}

	}

}
