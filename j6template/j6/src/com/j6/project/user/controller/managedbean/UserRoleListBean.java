package com.j6.project.user.controller.managedbean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.el.ELException;
import javax.el.Expression;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeListener;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.richfaces.component.html.HtmlDataTable;
import org.richfaces.component.html.HtmlExtendedDataTable;
import org.richfaces.model.ExtendedFilterField;
import org.richfaces.model.FilterField;
import org.richfaces.model.Modifiable;
import org.richfaces.model.Ordering;
import org.richfaces.model.SortField2;
 
import com.j6.framework.application.Application;
import com.j6.framework.dao.TableModel;
import com.j6.framework.jsf.application.FacesUtil;
import com.j6.framework.jsf.application.PhaseListener;
import com.j6.framework.user.manager.UserManager;
import com.j6.framework.user.vo.UserRole;
import com.j6.framework.util.CollectionUtil;
import com.j6.framework.util.J;
import com.j6.project.common.to.UserRoleReport;
import com.j6.project.user.manager.UserExManager;

/**
 * <!--  
			<q:dataTable id="userRolesTable2" var="userRole" align="center"
				value="#{userRoleListBean.userRoles}" pageSize="20"
				binding="#{userRoleListBean.userRoleDModel}"
				headerSectionClass="standardTableHeader" 
				horizontalGridLines="1px solid #999999"
				styleClass="checkboxColumn, tableColumn, tableColumn "
				filterRowStyle="background: #efefef"
				rolloverRowStyle="background: #ededed;" rowKey="#{userRole.roleName}"
				width="100%">
				<q:multipleRowSelection rowDatas="#{userRoleListBean.userRoleSelected}"/>
				<f:facet name="below">
					<h:panelGroup>
						<q:dataTablePaginator id="paginator" />
					</h:panelGroup>
				</f:facet> 
				<q:selectionColumn >
					<f:facet name="header">
						<q:selectAllCheckbox/>
					</f:facet>
				</q:selectionColumn>
				
				<q:column id="userIdColumn" sortingExpression="#{userRole.roleName}"
					filterExpression="#{userRole.roleName}" filterKind="searchField">
					<f:facet name="header">
						<h:outputText value="User Role" />
					</f:facet>
					<h:commandLink action="#{userRoleListBean.editUserRoleAction}">
						<h:outputText value="#{userRole.roleName}" styleClass="text"/>						
					</h:commandLink>
					
				</q:column>
				<q:column id="userIdColumn" filterExpression="#{userRole.status}"
					filterKind="comboBox">
					<f:facet name="header">
						<h:outputText value="Status" />
					</f:facet>
					<h:outputText value="#{userRole.status}" styleClass="text"/>
					
				</q:column>
			</q:dataTable>
-->
 * @author jimmy6
 *
 */
public class UserRoleListBean implements PhaseListener{
	public static final String BEAN_NAME = "userRoleListBean";

	private UserManager userManager;
	private UserExManager userExManager;
	private TableModelRichFaces<UserRole>  userRoleDModel = new TableModelRichFaces<UserRole> () ;
	private List<UserRole> userRoleSelected = new ArrayList<UserRole>();
	private List<UserRole> userRoles = new ArrayList<UserRole>();
	private String roleNameEdit;
	
	public UserRoleListBean() {
		userManager = (UserManager) Application.lookupBean(UserManager.BEAN_NAME);
		userExManager = (UserExManager) Application.lookupBean(UserExManager.BEAN_NAME);
//		userRoles = userManager.findAllUserRoles();
	}
	
	public void editUserRoleActListener( ActionEvent ae){
		UIParameter param = (UIParameter)ae.getComponent().findComponent("roleName") ;
		roleNameEdit = (String)param.getValue();
		
	}
	
	public String editUserRoleAct (  ){
		J.printNegetif("..");
		UserRoleEditBean userRoleModificationBean = (UserRoleEditBean) FacesUtil
				.getManagedBean(UserRoleEditBean.BEAN_NAME);
	 
		userRoleModificationBean.setUserRole(userManager.getUserRole(roleNameEdit));
		return UserRoleEditBean.BEAN_NAME;
	 
	}
	
	// public String deleteUserRoles(){
	//		
	// }

//	public DataTable getUserRoleDModel() {
//		// userRoleDModel.setWrappedData(userRoles);
//		return userRoleDModel;
//	}
//
//	public String editUserRoleAction() {
//		UserRoleEditBean userRoleModificationBean = (UserRoleEditBean) FacesUtil
//				.getManagedBean(UserRoleEditBean.BEAN_NAME);
//		userRoleModificationBean.setUserRole((UserRole) userRoleDModel.getRowData());
//		return UserRoleEditBean.BEAN_NAME;
//	}
//
//	public void setUserRoleDModel(DataTable userRoleDModel) {
//		this.userRoleDModel = userRoleDModel;
//	}

	public String deleteUserRolesAction() { 

		if (CollectionUtil.isNotEmpty(userRoles)) {
			userManager.deleteUserRoles(new HashSet<UserRole>(userRoleSelected));
		} else {
			FacesUtil.addInfoMessage("No user role to be deleted.");
		}
		return BEAN_NAME;

	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public List<UserRole> getUserRoleSelected() {
		return userRoleSelected;
	}

	public void setUserRoleSelected(List<UserRole> userRoleSelected) {
		this.userRoleSelected = userRoleSelected;
	}

	public TableModelRichFaces<UserRole> getUserRoleDModel() {
		return userRoleDModel;
	}

	public void setUserRoleDModel(TableModelRichFaces<UserRole> userRoleDModel) {
		this.userRoleDModel = userRoleDModel;
	}
	private boolean ok;
	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		J.printPositif("ok = "+ ok);
		
		this.ok = ok;
	}

	public void change(ValueChangeListener v){
		J.printPositif("i am here");
		
	}
	
	public class TableModelRichFaces<T> extends ExtendedDataModel implements Modifiable {
  
	    private Long rowKey;
	    
	    private T dataItem;
	    
	    private SequenceRange cachedRange;
	    
	    private List  cachedItems;
	    private Map  wrappedData = new HashMap ();

	    public List getCachedItems() {
			return cachedItems;
		}

		public void setCachedItems(List cachedItems) {
			this.cachedItems = cachedItems;
		}

		private List<FilterField> filterFields;

	    private List<SortField2> sortFields;

//	    private static boolean areEqualRanges(SequenceRange range1, SequenceRange range2) {
//	        if (range1 == null || range2 == null) {
//	            return range1 == null && range2 == null;
//	        } else {
//	            return range1.getFirstRow() == range2.getFirstRow() && range1.getRows() == range2.getRows();
//	        }
//	    }
     
	    private void appendFilters( FacesContext context ) {
	        if (filterFields != null) {
	            for (FilterField filterField : filterFields) {
	                String propertyName = getPropertyName(context, filterField.getExpression());

	                String filterValue = ((ExtendedFilterField) filterField).getFilterValue();
	                if (filterValue != null && filterValue.length() != 0) {
	                   J.printNegetif(filterValue+". filterValue   ."+propertyName);
	                }
	            }
	        }
	    }
	    
	    private String getPropertyName(FacesContext facesContext, Expression expression) { 
	        try {
	        	
	            return (String) ((ValueExpression) expression).getValue(facesContext.getELContext());
	            
	        } catch (ELException e) {
	        	throw new FacesException(e.getMessage(), e);
	        }
	    }
	    
	    private void appendSorts(FacesContext context) {
	        if (sortFields != null) {
	            for (SortField2 sortField : sortFields) {
	                Ordering ordering = sortField.getOrdering();
	                
	                if (Ordering.ASCENDING.equals(ordering) || Ordering.DESCENDING.equals(ordering)) {
	                    String propertyName = getPropertyName(context, sortField.getExpression());
	                    
	                    String order = Ordering.ASCENDING.equals(ordering) ? 
	                            "asc"+ propertyName :"desc"+(propertyName);
	                            
	                    J.printPositif("asc = "+order);
	                    J.printPositif("sortField.getExpression() = "+sortField.getExpression().getExpressionString());
	                }
	            }
	        }
	    }
	   TableModel tableModel =new TableModel();
	    @Override
	    public Object getRowKey() {
	        return rowKey;
	    }

	    @Override
	    public void setRowKey(Object key) { 
	        this.rowKey = (Long) key;
	        J.printNegetif("key = " + key);
	        
//	        this.dataItem = null;
//	        
//	        if (this.rowKey != null) {
//	            this.dataItem = (DataItem) session.load(DataItem.class, this.rowKey);
//	        }
	    }

	    public TableModel getTableModel() {
			return tableModel;
		}

		public void setTableModel(TableModel tableModel) {
			this.tableModel = tableModel;
		}

		@SuppressWarnings("unchecked")
	    @Override
	    public void walk(FacesContext facesContext, DataVisitor visitor, Range range,
	            Object argument) throws IOException {

	        SequenceRange sequenceRange = (SequenceRange) range;
	        
	     //   if (this.cachedItems == null  ) {
	           
	            appendFilters(facesContext );
	            appendSorts(facesContext );

	            if (sequenceRange != null) {
	                int first = sequenceRange.getFirstRow();
	                int rows = sequenceRange.getRows();
	                
	               
	            } 
	            
	            this.cachedRange = sequenceRange;
	            tableModel = new TableModel(sequenceRange.getRows(), ( sequenceRange.getFirstRow() /sequenceRange.getRows()) +1);
	          //  if ( FacesContext.getCurrentInstance().getRenderResponse()) {
	            this.cachedItems =  userExManager.findAllUserRoles(tableModel) ; 
	       //     }
//	            this.cachedItems =  userManager.findAllUserRoles() ;
	           
	    //     }
	        J.printNegetif(sequenceRange.getFirstRow() + ".sequenceRange." + sequenceRange.getRows());
	        J.printPositif(cachedItems.size());
	     
	        //System.out.println(getRowCount());
	       
	        for (Object item: cachedItems) {
	        	 
	        	wrappedData.put(new Long(((UserRoleReport)item).hashCode()), (UserRoleReport)item);
	            visitor.process(facesContext, new Long(((UserRoleReport)item).hashCode()), argument); 
	            
//	            wrappedData.put(new Long(((UserRole)item).hashCode()), (UserRole)item);
//	            visitor.process(facesContext, new Long(((UserRole)item).hashCode()), argument); 
	         
	        }
	    }
	  
	    /**
	     * this run b4 walk(...)
	     */
	    @Override
	    public int getRowCount() {
	    	 
	    	J.printPositif("getRowCount()"+tableModel.getTotalNoOfRecord());
//	    	 Criteria criteria = createCriteria();
	         appendFilters(FacesContext.getCurrentInstance());
//	         return (Integer) criteria.list().size();

	        //return wrappedData.size();
	         return new Integer(tableModel.getTotalNoOfRecord()+"");
	    }
 
	    @Override
	    public Object getRowData() {
	    	 if (rowKey==null) {
	             return null;
	         } else {
	             Object ret = wrappedData.get(rowKey);
//	             if (ret==null) {
//	                 ret = getDataProvider().getAuctionItemByPk(currentPk);
//	                 wrappedData.put(currentPk, ret);
//	                 return ret;
//	             } else {
	                 return ret;
//	             }
	         }

	    }

	    /**
	     * Unused rudiment from old JSF staff.
	     */
	    @Override
	    public int getRowIndex() {
	    	J.printPositif("getRowIndex()");
	        return -1;
	    }
 
	    /**
	     * Unused rudiment from old JSF staff.
	     */
	    @Override
	    public Object getWrappedData() {
	    	J.printPositif("getWrappedData()");
	        throw new UnsupportedOperationException();
	    }


	    @Override
	    public boolean isRowAvailable() {
	    	J.printPositif("isRowAvailable()="+(wrappedData.size()!=0));
	        return tableModel.getTotalNoOfRecord()!=0;
	    }
	    
	    /**
	     * Unused rudiment from old JSF staff.
	     */
	    @Override
	    public void setRowIndex(int rowIndex) {
	    	J.printPositif("setRowIndex( "+rowIndex+" )");
//	        throw new UnsupportedOperationException(); 
	    }
	    
	    /**
	     * Unused rudiment from old JSF staff.
	     */
	    @Override
	    public void setWrappedData(Object data) {
	        throw new UnsupportedOperationException();
	    }
	    
	    public void modify(List<FilterField> filterFields, List<SortField2> sortFields) {
	    	J.printPositif("modify(");
	        this.filterFields = filterFields;
	        this.sortFields = sortFields;
 
	    }

	}

	public void afterBuildView() {
		// TODO Auto-generated method stub
		
	}
	HtmlExtendedDataTable htmlDataTable = new HtmlExtendedDataTable();
	public void beforeResponsePhase() {
		 
//		htmlDataTable.getse
//		userExManager.findAllUserRoles(tableModel) ; 
		 
	}
}
