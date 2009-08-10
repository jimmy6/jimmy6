package com.j6.framework.user;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.log4j.Logger;

import com.j6.framework.jsf.application.FacesUtil;
import com.j6.framework.util.J;

public class PhaseTracker implements PhaseListener {
	public static final String PHASE_PARAMETER = "com.j6.framework.user.phase";
	private static final Logger logger = Logger.getLogger("com.corejsf.phases");
	private static String phase = null;

	public void setPhase(String newValue) {
		phase = newValue;
	}

	public static PhaseId getCurrentPhase() {
		if (phase == null) {
			FacesContext context = FacesContext.getCurrentInstance();
			phase = (String) context.getExternalContext().getInitParameter(PHASE_PARAMETER);
		}
		PhaseId phaseId = PhaseId.ANY_PHASE;

		if (phase != null) {
			if ("RESTORE_VIEW".equals(phase))
				phaseId = PhaseId.RESTORE_VIEW;
			else if ("APPLY_REQUEST_VALUES".equals(phase))
				phaseId = PhaseId.APPLY_REQUEST_VALUES;
			else if ("PROCESS_VALIDATIONS".equals(phase))
				phaseId = PhaseId.PROCESS_VALIDATIONS;
			else if ("UPDATE_MODEL_VALUES".equals(phase))
				phaseId = PhaseId.UPDATE_MODEL_VALUES;
			else if ("INVOKE_APPLICATION".equals(phase))
				phaseId = PhaseId.INVOKE_APPLICATION;
			else if ("RENDER_RESPONSE".equals(phase)) {

				phaseId = PhaseId.RENDER_RESPONSE;
			} else if ("ANY_PHASE".equals(phase))
				phaseId = PhaseId.ANY_PHASE;
		}
		return phaseId;
	}

	public PhaseId getPhaseId() {
		if (phase == null) {
			FacesContext context = FacesContext.getCurrentInstance();
			phase = (String) context.getExternalContext().getInitParameter(PHASE_PARAMETER);
		}
		PhaseId phaseId = PhaseId.ANY_PHASE;

		if (phase != null) {

			if ("RESTORE_VIEW".equals(phase))
				phaseId = PhaseId.RESTORE_VIEW;
			else if ("APPLY_REQUEST_VALUES".equals(phase))
				phaseId = PhaseId.APPLY_REQUEST_VALUES;
			else if ("PROCESS_VALIDATIONS".equals(phase))
				phaseId = PhaseId.PROCESS_VALIDATIONS;
			else if ("UPDATE_MODEL_VALUES".equals(phase))
				phaseId = PhaseId.UPDATE_MODEL_VALUES;
			else if ("INVOKE_APPLICATION".equals(phase))
				phaseId = PhaseId.INVOKE_APPLICATION;
			else if ("RENDER_RESPONSE".equals(phase)) {
				phaseId = PhaseId.RENDER_RESPONSE;
			} else if ("ANY_PHASE".equals(phase))
				phaseId = PhaseId.ANY_PHASE;
		}
		return phaseId;
	}

	public void beforePhase(PhaseEvent e) {
		logger.info("BEFORE " + e.getPhaseId());

		// call phaseListener in backing bean
		if (e.getPhaseId() == PhaseId.RENDER_RESPONSE) {
			FacesContext facesContext = e.getFacesContext();
			Map<String, Object> map = facesContext.getCurrentInstance().getExternalContext().getRequestMap();
			// J.printNegetif("map="+map.values().size());
			for (String key : map.keySet()) {
			//	J.printNegetif("map=key.." + key);
				Object obj = map.get(key);
				if (obj instanceof com.j6.framework.jsf.application.PhaseListener) {
					((com.j6.framework.jsf.application.PhaseListener) obj).beforeResponsePhase();
				}
			}
			// for (Object key :
			// FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().keySet()) {
			// J.printPositif(",,,nnnn,,,"+key);
			// }

			if ("true".equalsIgnoreCase(FacesUtil.getRequestParameter("PVS_CLEAR_JSF_SUBMITTED_VALUE"))) {
				FacesUtil.resetValues(FacesContext.getCurrentInstance().getViewRoot());
			}
		}

	}

	public void afterPhase(PhaseEvent e) {
		logger.info("AFTER " + e.getPhaseId());
	}
}