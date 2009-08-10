package com.j6.project.user.dao.oracle;

import java.util.ArrayList;
import java.util.List;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.j6.framework.dao.AbstractJdbcDao;
import com.j6.project.user.dao.UserExDao;
import com.j6.project.user.vo.User;

public class UserDaoImpl extends AbstractJdbcDao implements UserExDao, UserDetailsService {
	private static final Log log = LogFactory.getLog(UserDaoImpl.class);

	public UserDaoImpl() {
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		String sql = "SELECT user_id,first_name||' '|| last_name as name , user_name, user_password,"
				+ " email1,role_function.function_id, profile.user_status as user_status, dept_id"
				+ " from USER_PROFILE profile,APP_ROLE_FUNCTION role_function "
				+ " where profile.role_id= role_function.role_id and profile.user_name=?";

		final SqlRowSet rowSet = queryForRowSet(sql, username);

		final List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		int i = 0;

		User user = new User() {
			@Override
			public GrantedAuthority[] getAuthorities() {
				return grantedAuthorities.toArray(new GrantedAuthority[] {});
			}
		};

		while (rowSet.next()) {
			if (i == 0) {
				user.setUserId(rowSet.getInt("user_id"));
				user.setEmail(rowSet.getString("email1"));
				user.setEnabled(rowSet.getString("user_status").equalsIgnoreCase("A"));
				user.setName(rowSet.getString("name"));
				user.setPassword(rowSet.getString("user_password"));
				user.setUsername(rowSet.getString("user_name"));
				user.setDeptId(rowSet.getLong("dept_id"));
			}
			i++;

			grantedAuthorities.add(new GrantedAuthority() {
				public String getAuthority() {
					return rowSet.getInt("function_id") + "";
				}
			});
		}

		return user;
	}

	// public List<WoSummary> findWoList(String pmName, String equipName, String equipType, String woType,
	// String jobPlanName, String actualVsPlan, List<String> status2Checked) {
	// List<Object> params = new ArrayList<Object>();
	//
	// String WO_LIST = "SELECT WO.WO_CODE, WOT.WO_TYPE_NAME, PRE.FLAG \n"
	// + " , EQ.EQUIP_NAME, EQT.EQUIP_TYPE_NAME, DEPT.DEPT_NAME \n"
	// + " , WO.DATETIME_SCHEDULE_START, WO.DATETIME_SCHEDULE_COMPLETE \n"
	// + " , WO.DATETIME_ACTUAL_START, WO.DATETIME_ACTUAL_COMPLETE \n"
	// + " , SUM(VMS.SUM_QTY) AS SUM_QTY, SUM(VMS.SUM_COST) AS SUM_COST \n"
	// + " , SUM(VMIS.SUM_QTY) AS SUM_ISSUE_QTY, SUM(VMIS.SUM_ISSUE_COST) AS SUM_ISSUE_COST \n "
	// + " FROM V_AMMS_MRN_SUMMARY VMS \n"
	// + " LEFT JOIN V_AMMS_MRNISSUE_SUMMARY VMIS ON VMS.MRN_CODE = VMIS.MRN_CODE \n"
	// + " RIGHT JOIN AMMS_WORKORDER WO ON VMS.WO_CODE = WO.WO_CODE \n"
	// + " LEFT JOIN AMMS_WORKORDER_TYPE WOT ON WO.WO_TYPE_ID = WOT.WO_TYPE_ID \n"
	// + " LEFT JOIN AMMS_EQUIP EQ ON WO.EQUIP_ID = EQ.EQUIP_ID \n"
	// + " LEFT JOIN AMMS_EQUIP_TYPE EQT ON EQ.EQUIP_TYPE_ID = EQT.EQUIP_TYPE_ID \n"
	// + " LEFT JOIN APP_DEPT DEPT ON WO.DEPT_ID = DEPT.DEPT_ID \n"
	// + " LEFT JOIN AMMS_PM PM ON WO.PM_ID = PM.PM_ID \n"
	// + " LEFT JOIN APP_PREDEFINED_VALUE PRE ON WO.WORKORDER_STATUS2 = PRE.CODE \n"
	// + " LEFT JOIN AMMS_JOB_PLAN JP ON WO.JOB_PLAN_ID = JP.JOB_PLAN_ID "
	// + " WHERE 1=1 AND WO.WORKORDER_STATUS =? ";
	// params.add(Workorder.WoStatus.A.name());
	//
	// if (StringUtils.isNotEmpty(pmName)) {
	// WO_LIST += " AND UPPER(PM.PM_NAME)=?";
	// params.add(pmName.toUpperCase());
	// }
	// if (StringUtils.isNotEmpty(equipName)) {
	// WO_LIST += " AND UPPER(WO.EQUIP_ID)=?";
	// params.add(equipName.toUpperCase());
	// }
	// if (StringUtils.isNotEmpty(equipType)) {
	// WO_LIST += " AND UPPER(EQT.EQUIP_TYPE_NAME)=?";
	// params.add(equipType.toUpperCase());
	// }
	// if (StringUtils.isNotEmpty(woType)) {
	// WO_LIST += " AND UPPER(WOT.WO_TYPE_NAME)=?";
	// params.add(woType.toUpperCase());
	// }
	// if (StringUtils.isNotEmpty(jobPlanName)) {
	// WO_LIST += " AND UPPER(JP.JOB_PLAN_NAME)=?";
	// params.add(jobPlanName.toUpperCase());
	// }
	//
	// if (status2Checked.size() > 0) {
	// int count = 1;
	// WO_LIST += " AND (";
	//
	// for (String s : status2Checked) {
	//
	// if (count == 1) {
	// WO_LIST += " PRE.CODE = ?";
	// params.add(s);
	// } else {
	// WO_LIST += " OR PRE.CODE = ?";
	// params.add(s);
	// }
	// count += 1;
	// }
	// WO_LIST += " )";
	// }
	//
	// // if (fromDate != null && toDate == null) {
	// // WO_LIST += " AND WO.DATETIME_SCHEDULE_START >=?";
	// // params.add(DateUtil.truncateTime(fromDate));
	// // } else if (fromDate != null && toDate != null) {
	// // WO_LIST += " AND (WO.DATETIME_SCHEDULE_START >=? AND WO.DATETIME_SCHEDULE_COMPLETE <=?)";
	// // params.add(DateUtil.truncateTime(fromDate));
	// // params.add(DateUtil.truncateTime(toDate));
	// // } else if (fromDate == null && toDate != null) {
	// // WO_LIST += " AND WO.DATETIME_SCHEDULE_COMPLETE <=?";
	// // params.add(DateUtil.truncateTime(toDate));
	// // }
	//
	// WO_LIST += " GROUP BY WO.WO_CODE, WOT.WO_TYPE_NAME \n"
	// + " , EQ.EQUIP_NAME, EQT.EQUIP_TYPE_NAME, DEPT.DEPT_NAME \n"
	// + " , WO.DATETIME_SCHEDULE_START, WO.DATETIME_SCHEDULE_COMPLETE \n"
	// + " , WO.DATETIME_ACTUAL_START, WO.DATETIME_ACTUAL_COMPLETE ,PRE.FLAG";
	//
	// if (StringUtils.isNotEmpty(actualVsPlan)) {
	// if ("qtyEqual".equalsIgnoreCase(actualVsPlan)) {
	// WO_LIST += " HAVING SUM(NVL(VMS.SUM_QTY,0)) = SUM(NVL(VMIS.SUM_QTY,0))";
	// } else if ("qtyNotEqual".equalsIgnoreCase(actualVsPlan)) {
	// WO_LIST += " HAVING SUM(NVL(VMS.SUM_QTY,0)) != SUM(NVL(VMIS.SUM_QTY,0))";
	// }
	// }
	//
	// WO_LIST += " ORDER BY WO.WO_CODE";
	// log.debug(WO_LIST);
	// List<WoSummary> woLists = (List<WoSummary>) query(WO_LIST, params.toArray(), new WoSummary());
	// return woLists;
	// }

}