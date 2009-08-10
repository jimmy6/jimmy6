package com.j6.project.user.controller.managedbean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.component.html.ext.HtmlDataTable;
import org.apache.myfaces.custom.datascroller.HtmlDataScroller;
import org.apache.myfaces.custom.datascroller.ScrollerActionEvent;

import com.j6.framework.application.Application;
import com.j6.framework.dao.GenericDao;
import com.j6.framework.dao.TableModel;
import com.j6.framework.jsf.application.FacesUtil;
import com.j6.framework.jsf.application.PhaseListener;
import com.j6.framework.user.manager.UserManager;
import com.j6.framework.user.vo.User;
import com.j6.framework.util.J;
import com.j6.project.common.to.UserReport;
import com.j6.project.user.manager.UserExManager;

public class UserListBeanTest implements PhaseListener {
	public static final String BEAN_NAME = "userListBeanTest";

	private User user = new User(false);
	private UserManager userManager;
	private DataModel userDModel = new javax.faces.model.ListDataModel();
	private UserExManager userExManager;
	private List<User> users = new ArrayList<User>();
	private HtmlDataTable htmlDataTable = new HtmlDataTable();
	private List<UserReport> UserReports = new ArrayList<UserReport>();
	private GenericDao genericDao;

	public HtmlDataTable getHtmlDataTable() {

		return htmlDataTable;
	}

	public List<UserReport> getUserReports() {
		return UserReports;
	}

	public void setUserReports(List<UserReport> userReports) {
		UserReports = userReports;
	}

	public void setHtmlDataTable(HtmlDataTable htmlDataTable) {
		this.htmlDataTable = htmlDataTable;
	}

	public UserListBeanTest() {

		userManager = (UserManager) Application.lookupBean(UserManager.BEAN_NAME);
		userExManager = (UserExManager) Application.lookupBean(UserExManager.BEAN_NAME);
		genericDao = (GenericDao) Application.lookupBean("genericDao");
	}

	public void searchUser() {
		tableModel.setPageNo(1);// reset paging to page 1. solve empty table bug
	}

	public DataModel getUserDModel() {
		return userDModel;
	}

	public void setUserDModel(DataModel userDModel) {
		this.userDModel = userDModel;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private String editUsername = "";

	public String editUserAction() {
		UserAndUserRoleEditBean userAndUserRoleEditBean = (UserAndUserRoleEditBean) FacesUtil
				.getManagedBean(UserAndUserRoleEditBean.BEAN_NAME);
		User userExample = new User(false);
		userExample.setUsername(editUsername);
		userExample = (User) genericDao.findByExample(userExample).get(0);

		userAndUserRoleEditBean.setUser(userExample);
		FacesUtil.getRequestMap().remove(BEAN_NAME) ;
		return UserAndUserRoleEditBean.BEAN_NAME;
	}

	public void editUserActListener(ActionEvent ae) {
		UIParameter param = (UIParameter) ae.getComponent().findComponent("username");
		editUsername = (String) param.getValue();
	}

	public String resetPassword() {

		User user = ((User) userDModel.getRowData());
		userManager.resetLoginPassword(user.getUsername());
		FacesUtil.addInfoMessage("The new password of " + user.getName() + " is "
				+ Application.getMessage("usermgmt.resetPassword"));
		return "";
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	// LocalDataModel dataModel;
	// TableModel tableModel = new TableModel();
	//
	// private TableModel<UserReport> getDataPage(int startRow, int pageSize) {
	//
	// // tableModel = new TableModel(pageSize, (startRow / pageSize) + 1);
	// tableModel.setPageSize(pageSize);
	// tableModel.setPageNo((startRow / pageSize) + 1);
	// J.printPositif("find....PageNo=" + tableModel.getPageNo());
	// // if ( FacesContext.getCurrentInstance().getRenderResponse()) {
	//
	// userExManager.findAllUserReport(tableModel);
	// // }
	//
	// return tableModel;
	// }
	//
	// public DataModel getDataModel() {
	// if (dataModel == null) {
	// dataModel = new LocalDataModel(5);
	// }
	// return dataModel;
	//
	// }
	//
	// private class LocalDataModel extends PagedListDataModel {
	// public LocalDataModel(int pageSize) {
	// super(pageSize);
	// }
	//
	// public TableModel<UserReport> fetchPage(int startRow, int pageSize) {
	// // call enclosing managed bean method to fetch the data
	//
	// return getDataPage(startRow, pageSize);
	// }
	// }
	//
	// public abstract class PagedListDataModel<T> extends DataModel {
	//
	// int pageSize;
	// int rowIndex;
	// TableModel<T> page;
	//
	// /*
	// * Create a datamodel that pages through the data showing the specified
	// * number of rows on each page.
	// */
	// public PagedListDataModel(int pageSize) {
	// super();
	// this.pageSize = pageSize;
	// this.rowIndex = -1;
	// this.page = null;
	// }
	//
	// /**
	// * Not used in this class; data is fetched via a callback to the fetchData method rather than by explicitly
	// * assigning a list.
	// */
	// @Override
	// public void setWrappedData(Object o) {
	// throw new UnsupportedOperationException("setWrappedData");
	// }
	//
	// @Override
	// public int getRowIndex() {
	// return rowIndex;
	// }
	//
	// /**
	// * Specify what the "current row" within the dataset is. Note that the UIData component will repeatedly call
	// * this method followed by getRowData to obtain the objects to render in the table.
	// */
	// @Override
	// public void setRowIndex(int index) {
	// J.printNegetif("setRowIndexsetRowIndex = " + index);
	// rowIndex = index;
	// }
	//
	// /**
	// * Return the total number of rows of data available (not just the number of rows in the current page!).
	// */
	// @Override
	// public int getRowCount() {
	// J.printPositif("getRowCountgetRowCount");
	// return (int) getPage().getTotalNoOfRecord();
	// }
	//
	// /**
	// * Return a DataPage object; if one is not currently available then fetch one. Note that this doesn't ensure
	// * that the datapage returned includes the current rowIndex row; see getRowData.
	// */
	// private TableModel<T> getPage() {
	// if (page != null)
	// return page;
	//
	// int rowIndex = getRowIndex();
	// int startRow = rowIndex;
	// if (rowIndex == -1) {
	// // even when no row is selected, we still need a page
	// // object so that we know the amount of data available.
	// startRow = 0;
	// }
	//
	// // invoke method on enclosing class
	// page = fetchPage(startRow, pageSize);
	// return page;
	// }
	//
	// /**
	// * Return the object corresponding to the current rowIndex. If the DataPage object currently cached doesn't
	// * include that index then fetchPage is called to retrieve the appropriate page.
	// */
	// @Override
	// public Object getRowData() {
	// if (rowIndex < 0) {
	// throw new IllegalArgumentException("Invalid rowIndex for PagedListDataModel; not within page");
	// }
	//
	// // ensure page exists; if rowIndex is beyond dataset size, then
	// // we should still get back a DataPage object with the dataset size
	// // in it...
	// if (page == null) {
	// page = fetchPage(rowIndex, pageSize);
	// }
	//
	// // Check if rowIndex is equal to startRow,
	// // useful for dynamic sorting on pages
	// J.printLine(rowIndex + "-------" + page.getStartRow());
	// if (rowIndex == 0 || rowIndex % 5 == 0) {
	//
	// page = fetchPage(rowIndex, pageSize);
	// }
	//
	// int datasetSize = page.getTotalNoOfRecord();
	// int startRow = page.getStartRow();
	// int nRows = page.getResults().size();
	// int endRow = startRow + nRows;
	//
	// if (rowIndex >= datasetSize) {
	// throw new IllegalArgumentException("Invalid rowIndex");
	// }
	//
	// if (rowIndex < startRow) {
	// page = fetchPage(rowIndex, pageSize);
	// startRow = page.getStartRow();
	// } else if (rowIndex >= endRow) {
	// page = fetchPage(rowIndex, pageSize);
	// startRow = page.getStartRow();
	// }
	// J.printLine("rowIndex=" + rowIndex + ". page.getPageNo() =  " + page.getPageNo() + ". page.getPageSize()="
	// + page.getPageSize());
	// return page.getResults().get(rowIndex - (page.getPageNo() - 1) * page.getPageSize());
	// }
	//
	// @Override
	// public Object getWrappedData() {
	// return page.getResults();
	// }
	//
	// /**
	// * Return true if the rowIndex value is currently set to a value that matches some element in the dataset. Note
	// * that it may match a row that is not in the currently cached DataPage; if so then when getRowData is called
	// * the required DataPage will be fetched by calling fetchData.
	// */
	// @Override
	// public boolean isRowAvailable() {
	//
	// TableModel<T> page = getPage();
	// if (page == null) {
	// J.printPositif("isRowAvailableisRowAvailable = false");
	// return false;
	// }
	// int rowIndex = getRowIndex();
	// if (rowIndex < 0) {
	// J.printPositif("isRowAvailableisRowAvailable = false");
	// return false;
	// } else if (rowIndex >= page.getTotalNoOfRecord()) {
	// J.printPositif("isRowAvailableisRowAvailable = false");
	// return false;
	// } else {
	// J.printPositif("isRowAvailableisRowAvailable = true");
	// return true;
	// }
	// }
	//
	// /**
	// * Method which must be implemented in cooperation with the managed bean class to fetch data on demand.
	// */
	// public abstract TableModel<T> fetchPage(int startRow, int pageSize);
	// }

	public void afterBuildView() {

	}

	private HtmlDataScroller htmlDataScroller = new HtmlDataScroller();

	public HtmlDataScroller getHtmlDataScroller() {
		return htmlDataScroller;
	}

	public void setHtmlDataScroller(HtmlDataScroller htmlDataScroller) {
		this.htmlDataScroller = htmlDataScroller;
	}

	private int pageIndexVar = 0;

	public int getPageIndexVar() {
		return pageIndexVar;
	}

	public void setPageIndexVar(int pageIndexVar) {
		this.pageIndexVar = pageIndexVar;
	}

	TableModel<UserReport> tableModel = new TableModel<UserReport>();

	public TableModel<UserReport> getTableModel() {

		return tableModel;
	}

	public void setTableModel(TableModel<UserReport> tableModel) {

		this.tableModel = tableModel;
	}

	public class PagedTableModel extends DataModel {

		private int rowIndex = -1;

		private int totalNumRows;

		private int pageSize;

		private List list;

		public PagedTableModel() {
			super();
		}

		public PagedTableModel(TableModel tableModel) {
			super();
			setWrappedData(tableModel.getResults());
			this.totalNumRows = tableModel.getTotalNoOfRecord();
			this.pageSize = tableModel.getPageSize();
		}

		public boolean isRowAvailable() {
			if (list == null)
				return false;

			int rowIndex = getRowIndex();
			if (rowIndex >= 0 && rowIndex < list.size())
				return true;
			else
				return false;
		}

		public int getRowCount() {
			return totalNumRows;
		}

		public Object getRowData() {
			if (list == null)
				return null;
			else if (!isRowAvailable())
				throw new IllegalArgumentException();
			else {
				int dataIndex = getRowIndex();
				return list.get(dataIndex);
			}
		}

		public int getRowIndex() {

			return (rowIndex % pageSize);
		}

		public void setRowIndex(int rowIndex) {
			this.rowIndex = rowIndex;
		}

		public Object getWrappedData() {
			return list;
		}

		public void setWrappedData(Object list) {
			this.list = (List) list;
		}

	}

	public void beforeResponsePhase() {
		J.printPositif("beforeResponsePhasebeforeResponsePhasebeforeResponsePhase");
		if (tableModel.getPageNo() != 1)
			tableModel.setPageNo(htmlDataScroller.getPageIndex());

		tableModel.setPageSize(htmlDataTable.getRows());

		userExManager.findAllUserReport(tableModel, user.getUsername(), user.getName());
		htmlDataTable.setValue(new PagedTableModel(tableModel));
		if (tableModel.getPageNo() == 1)
			htmlDataTable.setFirst(0);
	}

}