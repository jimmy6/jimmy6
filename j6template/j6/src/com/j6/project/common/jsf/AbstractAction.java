package com.j6.project.common.jsf;

import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;

import com.j6.framework.jsf.application.FacesUtil;
import com.j6.framework.util.J;

public abstract class AbstractAction {

	@org.hibernate.validator.Pattern(regex = "\\w.+?", message = "Action is required.")
	private String action;

	public abstract String submit(String action);

	private String[] actions;

	public void setActions(String... actions) {
		this.actions = actions;
	}

	public AbstractAction(String... actions) {
		this.actions = actions;
	}

	// public static void main(String a[]){
	// Pattern p = Pattern.compile("\\w.+?");
	// Matcher m = p.matcher("sssss");
	// boolean b = m.matches();
	// J.printPositif(b);
	// }

	public String submit() {
		J.printPositif("action=" + action);
		if (StringUtils.isEmpty(action)) {
			FacesUtil.addInfoMessage("Please select one action.");
			return "";
		}
		return submit(action);
	}

	public List<SelectItem> getSelectItemActions() {

		return MBeanUtil.buildActionSelectItems(actions);
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}

}
