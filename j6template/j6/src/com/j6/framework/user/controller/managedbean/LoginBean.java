package com.j6.framework.user.controller.managedbean;

import com.j6.framework.application.Application;
import com.j6.framework.jsf.application.FacesUtil;
import com.j6.framework.spring.aop.UserInfo;
import com.j6.framework.user.manager.SecurityManagerImpl;
import com.j6.framework.user.vo.User;

public class LoginBean implements UserInfo {

	public static final String BEAN_NAME = "loginBean";

	@org.hibernate.validator.Valid
	private User user;

	@org.hibernate.validator.NotNull
	@org.hibernate.validator.Length(min = 4, max = 15)
	private String password;
	@org.hibernate.validator.NotNull
	@org.hibernate.validator.Length(min = 4, max = 15)
	private String username;
	private com.j6.framework.user.manager.SecurityManager securityManager;

	public LoginBean() {
		// it is not a good idea to code anything in constructor because annotation validation will init.
		// this bean before validation phase.
		user = new User(true);
		securityManager = (com.j6.framework.user.manager.SecurityManager) Application
				.lookupBean(SecurityManagerImpl.BEAN_NAME);
	}

	/**
	 * maintain the current login user info. after login.
	 * 
	 * @return
	 */
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String login() {
		// /////////////jasper report Demo/////////////////
		// User user = new User(true);
		// user.setName("1");
		// user.setUsername("name");
		//			
		// List results = new ArrayList();
		// results.add(user);
		// results.add(user);
		//			
		// Map parameters = new HashMap();
		// String ctxPath = FacesUtil.getServletContext().getRealPath("/");
		//	  
		// try{
		// FacesContext ctx=FacesContext.getCurrentInstance();
		// HttpServletResponse response= (HttpServletResponse)ctx.getExternalContext().getResponse();
		// InputStream is = FacesUtil.getServletContext().getResourceAsStream("/WEB-INF/reports/parkTransReport.jrxml");
		// JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(results);
		// response.setContentType("application/pdf");
		// response.addHeader("Content-Disposition", "attachment; filename=parkTransReport.pdf");
		// JasperDesign jasperDesign = JRXmlLoader.load(is);
		// JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		// JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
		// JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
		// ctx.getApplication().getStateManager().saveSerializedView(ctx);
		// ctx.responseComplete();
		// }
		// catch(Exception e){
		// e.printStackTrace();
		// }
		// //////////////////////////
		// try {
		// Thread.sleep(3000);
		// } catch (InterruptedException e) {
		//			
		// e.printStackTrace();
		// }
		user = securityManager.verifyLogin(username, password);
		// once back end manager layer throw ValidatorException, the following will not be executed.
		FacesUtil.setManagedBeanInSession(BEAN_NAME, this);
		// FacesUtil.addInfoMessage("Welcome " + user.getName()+" to AMMS.");

		return "main";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setSecurityManager(SecurityManagerImpl securityManager) {
		this.securityManager = securityManager;
	}

	public String logout() {
		FacesUtil.logout();
		return "logout";
	}

	public int getUserUniqueId() {
		return user.getUserId();
	}

}