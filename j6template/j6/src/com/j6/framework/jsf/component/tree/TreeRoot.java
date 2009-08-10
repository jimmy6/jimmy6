package com.j6.framework.jsf.component.tree;

import java.text.DecimalFormat;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <pre>
 * Created by IntelliJ IDEA.
 * User: ENG
 * Date: Jul 4, 2007
 * Time: 4:16:54 PM
 * </pre>
 */
public abstract class TreeRoot extends TreeBranch {
	private static Log log = LogFactory.getLog(TreeRoot.class);

	protected long nextId = 0;

	public abstract void initData();

	protected TreeRoot() {
		super();
	}

	protected TreeRoot(long id, String text) {
		super(id, text);
	}

	protected long getNextId() {
		return ++nextId;
	}

	public String getType() {
		return "menu-root";
	}

	protected Map getChildMap() {
		if (childMap.isEmpty())
			initData();

		return super.getChildMap();
	}

	public void add(TreeLeaf child) {
		DecimalFormat df = new DecimalFormat("000000");
		child.setParent(this);
		this.childMap.put(df.format(child.getId()), child);
	}

	public boolean isLeaf() {
		return getChildMap().isEmpty();
	}
}
