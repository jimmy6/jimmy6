package com.j6.framework.jsf.component.tree;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.richfaces.model.TreeNode;

/**
 * <pre>
 * Created by IntelliJ IDEA.
 * User: ENG
 * Date: Jul 4, 2007
 * Time: 4:05:33 PM
 * </pre>
 */
public class TreeBranch extends TreeLeaf {
	private static Log log = LogFactory.getLog(TreeBranch.class);

	protected Map childMap = new TreeMap();

	public TreeBranch() {
		super();
	}

	public TreeBranch(long id, String text) {
		super(id, text);
	}

	protected Map getChildMap() {
		return childMap;
	}

	public String getType() {
		return "menu-branch";
	}

	public void setData(Object o) {
	}

	public boolean isLeaf() {
		return getChildMap().isEmpty();
	}

	public Iterator getChildren() {
		return getChildMap().entrySet().iterator();
	}

	public TreeNode getChild(Object id) {
		return (TreeNode) getChildMap().get(id);
	}

	public void addChild(Object identifier, TreeNode child) {
		getChildMap().put(identifier, child);
	}

	public void removeChild(Object id) {
		getChildMap().remove(id);
	}

	public void add(TreeLeaf child) {
		DecimalFormat df = new DecimalFormat("000000");
		child.setParent(this);
		addChild(df.format(child.getId()), child);
	}

	public int getChildrenSize() {
		return getChildMap().size();
	}
}
