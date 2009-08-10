package com.j6.framework.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

import com.j6.framework.util.J;
import com.j6.framework.util.ReflectionUtil;

public class JAnnotationSessionFactoryBean extends AnnotationSessionFactoryBean {
	private Log log = LogFactory.getLog(JAnnotationSessionFactoryBean.class);

	public JAnnotationSessionFactoryBean() {

	}

	public static void main(String[] a) throws IOException {
		// J.printPositif("ppp.hbm.xml".replaceAll("\\.hbm\\.xml", "\\\\hbm\\\\xml") );

		String filePath = "com.privasia.User.hbm.xml";
		filePath = filePath.replaceAll("\\.", "\\\\");
		J.printPositif(filePath);

		filePath = filePath.replaceAll("\\\\hbm\\\\xml", ".hbm.xml");
		J.printPositif(filePath);

		// Set<String> clazzStr = ReflectionUtil.findFileNames("com", true, "com.+?", ".class");
		// for (String str : clazzStr) {
		// try {
		// Class clazz = Class.forName(str);
		// // if (clazz.isAnnotationPresent(Entity.class)) {
		// J.printPositif(clazz.toString());
		// // }
		//
		// } catch (Exception e) {
		// J.printPositif("Class can't be loaded for hibernate mapping = " + str);
		// } catch (NoClassDefFoundError e) {
		//
		// }
		// }
	}

	public void setAnnotatationOrHbmXmlPackagePattern(String[] patterns) {

		List<Class> annotationClasses = new ArrayList<Class>();
		List<String> hbmXmlFiles = new ArrayList<String>();

		try {
			// annotationClasses.add(Class.forName("com.j6.framework.user.vo.RoleActivity"));
			// annotationClasses.add(Class.forName("com.j6.framework.user.vo.User"));
			// annotationClasses.add(Class.forName("com.j6.framework.user.vo.UserRole"));
			for (String pattern : patterns) {
				Set<String> filePaths = ReflectionUtil.findFileNames(pattern.substring(0, pattern.indexOf("\\")), true,
						pattern, ".class", ".hbm.xml");

				for (String filePath : filePaths) {

					if (filePath.endsWith(".class")) {
						try {
							log.debug("doing............. = " + filePath);
							Class clazz = Class.forName(filePath.substring(0, filePath.indexOf(".class")));
							if (clazz.isAnnotationPresent(Entity.class)) {

								annotationClasses.add(clazz);
								log.info("<mapping class=\"" + clazz.toString() + "\" />");

							}

						} catch (Exception e) {
							log.debug("Class can't be loaded for hibernate mapping = " + filePath);
						}

					} else if (filePath.endsWith(".hbm.xml")) {

						filePath = filePath.replaceAll("\\.", "\\\\");
						filePath = filePath.replaceAll("\\\\hbm\\\\xml", ".hbm.xml");
						hbmXmlFiles.add(filePath);
						log.info("Hibernate hbm.xml mapping file: " + filePath);
					}

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		log.info((annotationClasses.size() + hbmXmlFiles.size()) + " hibernate mapping file.");
		setAnnotatedClasses(annotationClasses.toArray(new Class[] {}));
		setMappingResources(hbmXmlFiles.toArray(new String[0]));
		// setMappingResources(new String[] { "com.j6.framework.user.vo.User.hbm.xml" });

	}

	/**
	 * 
	 * @param filesPath =
	 *            com.j6.framework.user.vo.User.hbm.xml -> com\\privasia\\framework\\user\\vo\\User.hbm.xml
	 * @return
	 */
	// private String[] filePathDot2Slash(String[] filesPath){
	// List<String> filePathDot2Slash = new ArrayList<String>();
	//		
	// for (String filePath : filesPath){
	// String slash = filePath.replaceAll(".", "\\");
	// slash = slash.replaceAll("\\hbm\\xml", ".hbm.xml");
	// filePathDot2Slash.add(slash);
	// }
	// filePathDot2Slash.
	// }
}
