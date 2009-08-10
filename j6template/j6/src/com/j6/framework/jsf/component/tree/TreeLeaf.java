package com.j6.framework.jsf.component.tree;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang.StringUtils;
import org.richfaces.model.TreeNode;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * <pre>
 * Created by IntelliJ IDEA.
 * User: ENG
 * Date: Jul 4, 2007
 * Time: 3:51:42 PM
 * </pre>
 */
public class TreeLeaf implements TreeNode {
	private static final long serialVersionUID = -3530085227471752526L;

	private static Log log = LogFactory.getLog(TreeLeaf.class);

	protected TreeNode parent;

	protected long id;

	protected String text;

	protected String action;

	/*
	 * public String toString() { return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE); }
	 */

	public TreeLeaf() {
	}

	public TreeLeaf(long id, String text) {
		this.id = id;
		this.text = text;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return "menu-leaf";
	}

	public Object getData() {
		return this;
	}

	public void setData(Object o) {
		// throw new UnsupportedOperationException("TreeLeaf do not have children");
	}

	public boolean isLeaf() {
		return true;
	}

	public Iterator getChildren() {
		// TODO: Fix me!
		return new ArrayList().iterator(); // work around limitation for TreeNode
	}

	public TreeNode getChild(Object o) {
		return null;
	}

	public void addChild(Object o, TreeNode treeNode) {
		// throw new UnsupportedOperationException("TreeLeaf do not have children");
	}

	public void removeChild(Object o) {
		// throw new UnsupportedOperationException("TreeLeaf do not have children");
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode treeNode) {
		this.parent = treeNode;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String action() {
		return action;
	}

	public boolean isHasAction() {
		return StringUtils.isNotBlank(action);
	}
}
