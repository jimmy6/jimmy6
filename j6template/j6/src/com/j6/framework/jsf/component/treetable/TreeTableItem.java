package com.j6.framework.jsf.component.treetable;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * Created by IntelliJ IDEA.
 * User: ENG
 * Date: Jul 10, 2007
 * Time: 2:16:24 PM
 * </pre>
 */
public interface TreeTableItem extends Serializable {
	public List<TreeTableItem> getChild();

	public void addChild(TreeTableItem item);
}
