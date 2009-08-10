package com.j6.project.common.jsf;
//package com.privasia.amms.common.jsf;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.faces.model.SelectItem;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import com.privasia.amms.catalog.dao.ItemDao;
//import com.privasia.amms.catalog.dao.ItemTypeDao;
//import com.privasia.amms.catalog.vo.Item;
//import com.privasia.amms.catalog.vo.ItemType;
//import com.privasia.amms.inventory.master.dao.StoreDao;
//import com.privasia.amms.inventory.master.vo.Store;
//import com.privasia.amms.inventory.mrn.vo.MrnIssue;
//import com.privasia.amms.inventory.mrn.vo.MrnReturn;
//import com.privasia.amms.inventory.operation.dao.StkAdjustRemarkDao;
//import com.privasia.amms.inventory.operation.dao.StkAdjustTypeDao;
//import com.privasia.amms.inventory.operation.vo.StkAdjust;
//import com.privasia.amms.inventory.operation.vo.StkAdjustRemark;
//import com.privasia.amms.inventory.operation.vo.StkAdjustType;
//import com.privasia.amms.inventory.operation.vo.StkTake;
//import com.privasia.amms.inventory.operation.vo.StkTransfer;
//import com.privasia.amms.maintenance.pm.controller.managedbean.PmListBean;
//import com.privasia.amms.maintenance.pm.vo.FrequencyType;
//import com.privasia.amms.maintenance.pm.vo.TimeFreqUnit;
//import com.privasia.amms.maintenance.wo.vo.WoStatus;
//import com.privasia.amms.master.dao.CurrencyDao;
//import com.privasia.amms.master.dao.DeptDao;
//import com.privasia.amms.master.dao.EmpGroupDao;
//import com.privasia.amms.master.dao.EquipBrandDao;
//import com.privasia.amms.master.dao.EquipTypeDao;
//import com.privasia.amms.master.dao.FailureDao;
//import com.privasia.amms.master.dao.GlAccountDao;
//import com.privasia.amms.master.dao.LocationDao;
//import com.privasia.amms.master.dao.MeterTypeDao;
//import com.privasia.amms.master.dao.MeterUnitDao;
//import com.privasia.amms.master.dao.ProblemDao;
//import com.privasia.amms.master.dao.SectionDao;
//import com.privasia.amms.master.dao.WorkorderTypeDao;
//import com.privasia.amms.master.vo.Currency;
//import com.privasia.amms.master.vo.Dept;
//import com.privasia.amms.master.vo.EmpGroup;
//import com.privasia.amms.master.vo.Equip;
//import com.privasia.amms.master.vo.EquipBrand;
//import com.privasia.amms.master.vo.EquipType;
//import com.privasia.amms.master.vo.Failure;
//import com.privasia.amms.master.vo.GlAccount;
//import com.privasia.amms.master.vo.Location;
//import com.privasia.amms.master.vo.MeterType;
//import com.privasia.amms.master.vo.MeterUnit;
//import com.privasia.amms.master.vo.Problem;
//import com.privasia.amms.master.vo.Section;
//import com.privasia.amms.master.vo.ServiceContract;
//import com.privasia.amms.master.vo.WorkorderType;
//import com.privasia.framework.application.Application;
//import com.privasia.framework.dao.GenericDao;
//import com.privasia.framework.jsf.managedbean.AbstractPreValueBean;
//
//public class ApplicationMBean extends AbstractPreValueBean {
//	private static final Log log = LogFactory.getLog(ApplicationMBean.class);
//
//	private CurrencyDao currencyDao;
//	private EquipTypeDao equipTypeDao;
//	private EquipBrandDao equipBrandDao;
//	private DeptDao deptDao;
//	private FailureDao failureDao;
//	private GenericDao genericDao;
//	private ItemTypeDao itemTypeDao;
//	private GlAccountDao glAccountDao;
//	private LocationDao locationDao;
//	private MeterTypeDao meterTypeDao;
//	private MeterUnitDao meterUnitDao;
//	private ProblemDao problemDao;
//	private SectionDao sectionDao;
//	private StkAdjustTypeDao stkAdjustTypeDao;
//	private StkAdjustRemarkDao stkAdjustRemarkDao;
//	private StoreDao storeDao;
//	private WorkorderTypeDao workorderTypeDao;
//	private EmpGroupDao empGroupDao;
//	private ItemDao itemDao;
//
//	public ApplicationMBean() {
//		currencyDao = (CurrencyDao) Application.lookupBean("currencyDao");
//		deptDao = (DeptDao) Application.lookupBean("deptDao");
//		empGroupDao = (EmpGroupDao) Application.lookupBean("empGroupDao");
//		equipTypeDao = (EquipTypeDao) Application.lookupBean("equipTypeDao");
//		equipBrandDao = (EquipBrandDao) Application.lookupBean("equipBrandDao");
//		failureDao = (FailureDao) Application.lookupBean("failureDao");
//		genericDao = (GenericDao) Application.lookupBean("genericDao");
//		glAccountDao = (GlAccountDao) Application.lookupBean("glAccountDao");
//		itemTypeDao = (ItemTypeDao) Application.lookupBean("itemTypeDao");
//		locationDao = (LocationDao) Application.lookupBean("locationDao");
//		meterTypeDao = (MeterTypeDao) Application.lookupBean("meterTypeDao");
//		meterUnitDao = (MeterUnitDao) Application.lookupBean("meterUnitDao");
//		problemDao = (ProblemDao) Application.lookupBean("problemDao");
//		sectionDao = (SectionDao) Application.lookupBean("sectionDao");
//		stkAdjustRemarkDao = (StkAdjustRemarkDao) Application.lookupBean("stkAdjustRemarkDao");
//		stkAdjustTypeDao = (StkAdjustTypeDao) Application.lookupBean("stkAdjustTypeDao");
//		storeDao = (StoreDao) Application.lookupBean("storeDao");
//		workorderTypeDao = (WorkorderTypeDao) Application.lookupBean("workorderTypeDao");
//		itemDao = (ItemDao) Application.lookupBean("itemDao");
//	}
//
//	public List<SelectItem> getWoStatus() {
//		return constructListOfSelectItem(getPredefinedValue(WoStatus.class, new ArrayList<SelectItem>()), false);
//	}
//
//	public List<SelectItem> getCurrencies() {
//		List<Currency> list = currencyDao.findAvailableCurrencies();
//		List<SelectItem> selectList = new ArrayList<SelectItem>();
//
//		for (Currency cr : list) {
//			selectList.add(new SelectItem(cr.getCurrencyCode(), cr.getCurrencyCode() + " - " + cr.getCurrencyName()));
//		}
//		return selectList;
//	}
//
//	public List<SelectItem> getDepts() {
//		Dept exampleDept = new Dept();
//		exampleDept.setDeptStatus("A");
//		List<Dept> deptList = deptDao.findByExample(exampleDept);
//		List<SelectItem> selectList = new ArrayList<SelectItem>();
//
//		for (Dept dept : deptList) {
//			selectList.add(new SelectItem(dept.getDeptId(), dept.getDeptName()));
//		}
//
//		return selectList;
//	}
//
//	public List<SelectItem> getEquipTypes() {
//		EquipType exampleEquipType = new EquipType();
//		exampleEquipType.setEquipTypeStatus("A");
//		List<EquipType> equipTypes = equipTypeDao.findByExample(exampleEquipType);
//		List<SelectItem> retEquipTypes = new ArrayList<SelectItem>();
//
//		for (EquipType equipType : equipTypes) {
//			SelectItem selectItem = new SelectItem();
//			selectItem.setValue(equipType.getEquipTypeId());
//			selectItem.setLabel(equipType.getEquipTypeName());
//			retEquipTypes.add(selectItem);
//		}
//
//		return retEquipTypes;
//	}
//
//	public List<SelectItem> getEquipTypesWithAll() {
//		List<SelectItem> list = new ArrayList<SelectItem>();
//		list.add(new SelectItem("", "All"));
//		list.addAll(getEquipTypes());
//		return list;
//	}
//
//	public List<SelectItem> getEquipStatus() {
//		return MBeanUtil.buildEnumSelectItems(Equip.EquipStatus.values());
//	}
//
//	public List<SelectItem> getServiceContractStatus() {
//		return MBeanUtil.buildEnumSelectItems(ServiceContract.Status2.values());
//	}
//
//	public List<SelectItem> getItemStatusWithAll() {
//		List<SelectItem> list = new ArrayList<SelectItem>();
//		list.add(new SelectItem("", "All"));
//		list.addAll(getItemStatus());
//		return list;
//	}
//
//	public List<SelectItem> getItemStatus() {
//		return MBeanUtil.buildEnumSelectItems(Item.ItemStatus.values());
//	}
//
//	public List<SelectItem> getEquipStatusWithAll() {
//		List<SelectItem> list = new ArrayList<SelectItem>();
//		list.add(new SelectItem("", "All"));
//		list.addAll(getEquipStatus());
//		return list;
//	}
//
//	public List<SelectItem> getEquipBrands() {
//		EquipBrand exampleEquipBrand = new EquipBrand();
//		exampleEquipBrand.setEquipBrandStatus("A");
//		List<EquipBrand> equipBrands = equipBrandDao.findByExample(exampleEquipBrand);
//		List<SelectItem> retEquipBrands = new ArrayList<SelectItem>();
//
//		for (EquipBrand equipBrand : equipBrands) {
//			SelectItem selectItem = new SelectItem();
//			selectItem.setValue(equipBrand.getEquipBrandId());
//			selectItem.setLabel(equipBrand.getEquipBrandName());
//			retEquipBrands.add(selectItem);
//		}
//		return retEquipBrands;
//	}
//
//	public List<SelectItem> getEquipBrandsWithAll() {
//		List<SelectItem> list = new ArrayList<SelectItem>();
//		list.add(new SelectItem("", "All"));
//		list.addAll(getEquipBrands());
//		return list;
//	}
//
//	public List<SelectItem> getMeters() {
//
//		List<Item> meters = itemDao.getAvailableMeters();
//		List<SelectItem> retMeter = new ArrayList<SelectItem>();
//
//		for (Item meter : meters) {
//			SelectItem selectItem = new SelectItem(meter.getItemId(), meter.getItemDesc());
//			retMeter.add(selectItem);
//		}
//		return retMeter;
//	}
//
//	public List<SelectItem> getEquipMeterTypes() {
//		MeterType exampleMeterType = new MeterType();
//		exampleMeterType.setMeterTypeStatus("A");
//		List<MeterType> meterTypes = meterTypeDao.findByExample(exampleMeterType);
//		List<SelectItem> retMeterTypes = new ArrayList<SelectItem>();
//
//		for (MeterType meterType : meterTypes) {
//			SelectItem selectItem = new SelectItem(meterType.getMeterTypeId(), meterType.getMeterTypeName());
//			retMeterTypes.add(selectItem);
//		}
//		return retMeterTypes;
//	}
//
//	public List<SelectItem> getItemTypes() {
//		// TODO hardcode itemType..because table not exist in DB
//		// List<ItemType> itemTypeList = itemTypeDao.getAvailableItemType();
//		List<ItemType> itemTypeList = new ArrayList();
//		ItemType itemType1 = new ItemType(true);
//		itemType1.setItemTypeId(ItemType.ITEM_TYPE_ID_STK);
//		itemType1.setItemTypeName("STK");
//		itemType1.setItemTypeDesc("STK");
//		itemTypeList.add(itemType1);
//
//		ItemType itemType2 = new ItemType(true);
//		itemType2.setItemTypeId(ItemType.ITEM_TYPE_ID_NS);
//		itemType2.setItemTypeName("NS");
//		itemType2.setItemTypeDesc("NS");
//		itemTypeList.add(itemType2);
//
//		ItemType itemType3 = new ItemType(true);
//		itemType3.setItemTypeId(ItemType.ITEM_TYPE_ID_SP);
//		itemType3.setItemTypeName("SP");
//		itemType3.setItemTypeDesc("SP");
//		itemTypeList.add(itemType3);
//		//
//
//		List<SelectItem> list = new ArrayList<SelectItem>();
//		for (ItemType itemType : itemTypeList) {
//			list.add(new SelectItem(itemType.getItemTypeId(), itemType.getItemTypeName()));
//		}
//		return list;
//	}
//
//	public List<SelectItem> getItemTypesWithAll() {
//		List<SelectItem> list = new ArrayList<SelectItem>();
//		list.add(new SelectItem("", "All"));
//		list.addAll(getItemTypes());
//		return list;
//	}
//
//	public List<SelectItem> getEquipMeterUnits() {
//		MeterUnit exampleMeterUnit = new MeterUnit();
//		exampleMeterUnit.setMeterUnitStatus("A");
//		List<MeterUnit> meterUnits = meterUnitDao.findByExample(exampleMeterUnit);
//		List<SelectItem> meterTypes = new ArrayList<SelectItem>();
//
//		for (MeterUnit meterUnit : meterUnits) {
//			SelectItem selectItem = new SelectItem(meterUnit.getMeterUnitCode(), meterUnit.getMeterUnitName());
//			meterTypes.add(selectItem);
//		}
//		return meterTypes;
//	}
//
//	public List<SelectItem> getActive() {
//		List<SelectItem> retActives = new ArrayList<SelectItem>();
//
//		SelectItem selectItemActive = new SelectItem("A", "Yes");
//		SelectItem selectItemInactive = new SelectItem("I", "No");
//
//		retActives.add(selectItemActive);
//		retActives.add(selectItemInactive);
//
//		return retActives;
//	}
//
//	public List<SelectItem> getActivePleaseSelect() {
//		List<SelectItem> retActives = new ArrayList<SelectItem>();
//
//		SelectItem selectItemActive = new SelectItem("A", "Yes");
//		SelectItem selectItemInactive = new SelectItem("I", "No");
//
//		retActives.add(new SelectItem("", getPleaseSelect()));
//		retActives.add(selectItemActive);
//		retActives.add(selectItemInactive);
//
//		return retActives;
//	}
//
//	public List<SelectItem> getYesNo() {
//		List<SelectItem> retSelectItems = new ArrayList<SelectItem>();
//
//		retSelectItems.add(new SelectItem(Boolean.TRUE, "Yes"));
//		retSelectItems.add(new SelectItem(Boolean.FALSE, "No"));
//
//		return retSelectItems;
//	}
//
//	public List<SelectItem> getYesNoStr() {
//		List<SelectItem> retSelectItems = new ArrayList<SelectItem>();
//
//		retSelectItems.add(new SelectItem("Yes", "Yes"));
//		retSelectItems.add(new SelectItem("No", "No"));
//
//		return retSelectItems;
//	}
//
//	public List<SelectItem> getYesNoAllStr() {
//		List<SelectItem> retSelectItems = new ArrayList<SelectItem>();
//
//		retSelectItems.add(new SelectItem("Yes", "Yes"));
//		retSelectItems.add(new SelectItem("No", "No"));
//		retSelectItems.add(new SelectItem("All", "All"));
//
//		return retSelectItems;
//	}
//
//	public List<SelectItem> getWorkOrderType() {
//		List<SelectItem> retWorkorderType = new ArrayList<SelectItem>();
//		WorkorderType exampleWorkorderType = new WorkorderType(false);
//		exampleWorkorderType.setWoTypeStatus(WorkorderType.WoTypeStatus.A.name());
//
//		List<WorkorderType> workorderTypes = workorderTypeDao.findByExample(exampleWorkorderType);
//
//		for (WorkorderType workorderType : workorderTypes) {
//			retWorkorderType.add(new SelectItem(workorderType.getWoTypeId(), workorderType.getWoTypeName()
//					+ (workorderType.getWoPlan() ? " (Plan)" : "")));
//		}
//
//		return retWorkorderType;
//	}
//
//	public List<SelectItem> getWorkOrderType4PM() {
//		List<SelectItem> retWorkorderType = new ArrayList<SelectItem>();
//		WorkorderType exampleWorkorderType = new WorkorderType(false);
//		exampleWorkorderType.setWoPlan(true);
//		exampleWorkorderType.setWoTypeStatus(WorkorderType.WoTypeStatus.A.name());
//
//		List<WorkorderType> workorderTypes = workorderTypeDao.findByExample(exampleWorkorderType);
//
//		for (WorkorderType workorderType : workorderTypes) {
//			retWorkorderType.add(new SelectItem(workorderType.getWoTypeId(), workorderType.getWoTypeName()));
//		}
//
//		return retWorkorderType;
//	}
//
//	public List<SelectItem> getWorkOrderStatus() {
//		List<SelectItem> retActives = new ArrayList<SelectItem>();
//
//		retActives.add(new SelectItem("APPR", "APPR"));
//		retActives.add(new SelectItem("WAPPR", "WAPPR"));
//		retActives.add(new SelectItem("WSCH", "WSCH"));
//
//		return retActives;
//	}
//
//	public List<SelectItem> getStoreStatus() {
//		return MBeanUtil.buildEnumSelectItems(Store.StoreStatus.values());
//	}
//
//	public List<SelectItem> getStoreStatusWithAll() {
//		List<SelectItem> list = new ArrayList<SelectItem>();
//		list.add(new SelectItem("", "All"));
//		list.addAll(getStoreStatus());
//		return list;
//	}
//
//	public List<SelectItem> getSections() {
//		Section section = new Section();
//		section.setSectionStatus("A");
//
//		List<SelectItem> sectionList = new ArrayList<SelectItem>();
//		List<Section> list = sectionDao.findByExample(section);
//		for (Section sec : list) {
//			sectionList.add(new SelectItem(sec.getSectionId(), sec.getSectionName()));
//		}
//		return sectionList;
//	}
//
//	public List<SelectItem> getStores() {
//		List<SelectItem> retStores = new ArrayList<SelectItem>();
//		List<Store> stores = storeDao.getAvailable();
//		for (Store store : stores) {
//			retStores.add(new SelectItem(store.getStoreId(), store.getStoreName()));
//		}
//		return retStores;
//	}
//
//	public List<SelectItem> getStoreroomWithStoreroomType() {
//		List<SelectItem> retStores = new ArrayList<SelectItem>();
//		Store storeExample = new Store(false);
//		storeExample.setStoreStatus(Store.StoreStatus.A.name());
//		storeExample.setStoreType(Store.StoreType.STR.name());
//		List<Store> stores = storeDao.findByExample(storeExample);
//
//		for (Store store : stores) {
//			retStores.add(new SelectItem(store.getStoreId(), store.getStoreName()));
//		}
//		return retStores;
//	}
//
//	public List<SelectItem> getStoresWithAll() {
//		List<SelectItem> list = new ArrayList<SelectItem>();
//		list.add(new SelectItem("", "All"));
//		list.addAll(getStores());
//		return list;
//	}
//
//	public List<SelectItem> getGlAccount() {
//		List<SelectItem> retGlAccount = new ArrayList<SelectItem>();
//		List<GlAccount> glAccounts = glAccountDao.getAvailable();
//		for (GlAccount glAccount : glAccounts) {
//			retGlAccount.add(new SelectItem(glAccount.getGlAccountId(), glAccount.getGlAccountName()));
//		}
//		return retGlAccount;
//	}
//
//	public List<SelectItem> getLocations() {
//		Location location = new Location();
//		location.setLocationStatus("A");
//
//		List<SelectItem> locationList = new ArrayList<SelectItem>();
//		List<Location> list = locationDao.findByExample(location);
//		for (Location loc : list) {
//			locationList.add(new SelectItem(loc.getLocationId(), loc.getLocationName()));
//		}
//		return locationList;
//	}
//
//	public List<SelectItem> getLocationsWithAll() {
//		List<SelectItem> list = new ArrayList<SelectItem>();
//		list.add(new SelectItem("", "All"));
//		list.addAll(getLocations());
//		return list;
//	}
//
//	public List<SelectItem> getFrequencyUnits() {
//		return constructListOfSelectItem(getPredefinedValue(TimeFreqUnit.class, new ArrayList<SelectItem>()), false);
//	}
//
//	public List<SelectItem> getFrequencyType() {
//		return constructListOfSelectItem(getPredefinedValue(FrequencyType.class, new ArrayList<SelectItem>()), false);
//	}
//
//	public List<SelectItem> getYesNoNone() {
//		List<SelectItem> retActives = new ArrayList<SelectItem>();
//		retActives.add(new SelectItem("None", "None"));
//		retActives.add(new SelectItem("Yes", "Yes"));
//		retActives.add(new SelectItem("No", "No"));
//
//		return retActives;
//	}
//
//	public List<SelectItem> getMrnIssueStatus() {
//		return MBeanUtil.buildEnumSelectItems(MrnIssue.MrnIssueStatus.values());
//	}
//
//	public List<SelectItem> getMrnIssueStatusWithAll() {
//		List<SelectItem> list = new ArrayList<SelectItem>();
//		list.add(new SelectItem("", "All"));
//		list.addAll(getMrnIssueStatus());
//		return list;
//	}
//
//	public List<SelectItem> getMrnReturnStatus() {
//		return MBeanUtil.buildEnumSelectItems(MrnReturn.MrnReturnStatus.values());
//	}
//
//	public List<SelectItem> getMrnReturnStatusWithAll() {
//		List<SelectItem> list = new ArrayList<SelectItem>();
//		list.add(new SelectItem("", "All"));
//		list.addAll(getMrnReturnStatus());
//		return list;
//	}
//
//	public List<SelectItem> getStkAdjustType() {
//		List<SelectItem> list = new ArrayList<SelectItem>();
//
//		List<StkAdjustType> typeList = stkAdjustTypeDao.getAvailable();
//		for (StkAdjustType adjustType : typeList) {
//			list.add(new SelectItem(adjustType.getAdjustTypeCode(), adjustType.getAdjustTypeDesc()));
//		}
//		return list;
//	}
//
//	public List<SelectItem> getStkAdjustStatus() {
//		return MBeanUtil.buildEnumSelectItems(StkAdjust.StkAdjustStatus.values());
//	}
//
//	public List<SelectItem> getStkAdjustStatusWithAll() {
//		List<SelectItem> list = new ArrayList<SelectItem>();
//		list.add(new SelectItem("", "All"));
//		list.addAll(getStkAdjustStatus());
//		return list;
//	}
//
//	public List<SelectItem> getStkAdjustRemark() {
//		List<SelectItem> list = new ArrayList<SelectItem>();
//
//		List<StkAdjustRemark> remarkList = stkAdjustRemarkDao.getAvailable();
//		for (StkAdjustRemark adjustRemark : remarkList) {
//			list.add(new SelectItem(adjustRemark.getStkAdjustRemarkCode(), adjustRemark.getStkAdjustRemarkDesc()));
//		}
//		return list;
//	}
//
//	public List<SelectItem> getStkTakeStatus() {
//		return MBeanUtil.buildEnumSelectItems(StkTake.StkTakeStatus.values());
//	}
//
//	public List<SelectItem> getStkTakeStatusWithAll() {
//		List<SelectItem> list = new ArrayList<SelectItem>();
//		list.add(new SelectItem("", "All"));
//		list.addAll(getStkTakeStatus());
//		return list;
//	}
//
//	public List<SelectItem> getStkTakeStatus2() {
//		return MBeanUtil.buildEnumSelectItems(StkTake.StkTakeStatus2.values());
//	}
//
//	public List<SelectItem> getStkTakeStatus2WithAll() {
//		List<SelectItem> list = new ArrayList<SelectItem>();
//		list.add(new SelectItem("", "All"));
//		list.addAll(getStkTakeStatus2());
//		return list;
//	}
//
//	public List<SelectItem> getStkTransferStatus() {
//		return MBeanUtil.buildEnumSelectItems(StkTransfer.StkTransferStatus.values());
//	}
//
//	public List<SelectItem> getStkTransferStatusWithAll() {
//		List<SelectItem> list = new ArrayList<SelectItem>();
//		list.add(new SelectItem("", "All"));
//		list.addAll(getStkTransferStatus());
//		return list;
//	}
//
//	public List<SelectItem> getPmListSearchBy() {
//		return MBeanUtil.buildEnumSelectItems(PmListBean.SearchBy.values());
//	}
//
//	public List<SelectItem> getProblems() {
//		Problem exampleProblem = new Problem(false);
//		exampleProblem.setProblemStatus(Problem.ProblemStatus.A.name());
//
//		List<SelectItem> problemList = new ArrayList<SelectItem>();
//		List<Problem> list = problemDao.findByExample(exampleProblem);
//		for (Problem problem : list) {
//			problemList.add(new SelectItem(problem.getProblemId(), problem.getProblemName()));
//		}
//		return problemList;
//	}
//
//	public List<SelectItem> getFailures() {
//		Failure exampleFailure = new Failure(false);
//		exampleFailure.setFailureStatus(Failure.FailureStatus.A.name());
//
//		List<SelectItem> failureList = new ArrayList<SelectItem>();
//		List<Failure> list = failureDao.findByExample(exampleFailure);
//		for (Failure failure : list) {
//			failureList.add(new SelectItem(failure.getFailureId(), failure.getFailureName()));
//		}
//		return failureList;
//	}
//
//	public List<SelectItem> getEmpGroups() {
//
//		List<SelectItem> empGroupList = new ArrayList<SelectItem>();
//		// TODO empGROUP
//		// List<EmpGroup> list = empGroupDao.loadAll();
//		List<EmpGroup> list = new ArrayList<EmpGroup>();
//		EmpGroup empGroupHC = new EmpGroup();
//		empGroupHC.setEmpGroupId(1l);
//		empGroupHC.setGroupName("ABCD");
//		list.add(empGroupHC);
//		// end
//
//		for (EmpGroup empGroup : list) {
//			empGroupList.add(new SelectItem(empGroup.getEmpGroupId(), empGroup.getGroupName()));
//		}
//		return empGroupList;
//	}
//
//}
