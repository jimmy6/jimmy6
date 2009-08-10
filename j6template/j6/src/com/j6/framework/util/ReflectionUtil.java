package com.j6.framework.util;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class ReflectionUtil {

	public ReflectionUtil() {

	}

	/**
	 * 
	 * get all properties and it value which is public method which name get* if obj is collection->no browser in the
	 * collection. only go in object in objPath package. Other than that just get the value and no go in to it
	 * preperties.
	 * 
	 * @param obj =
	 *            object that needed to print all the properties.
	 * @param objIndicator =
	 *            other object indication when vo contain other vo.
	 * @param objPath =
	 *            only browse object which class path contain objPath string.
	 * @return
	 */
	private static StringBuilder getAllProperties(Object obj, String objIndicator, String objPath) {

		StringBuilder retAllPreperties = new StringBuilder();
		try {
			retAllPreperties.append("\n" + objIndicator + obj + "->");

			BeanWrapper beanO1 = new BeanWrapperImpl(obj);
			PropertyDescriptor[] proDescriptorsO1 = beanO1.getPropertyDescriptors();
			int propertyLength = proDescriptorsO1.length;
			for (int i = 0; i < propertyLength; i++) {
				String variable = proDescriptorsO1[i].getName();
				// String clazz = proDescriptorsO1[i].getPropertyType().toString();
				try {
					Object propertyValueO1 = beanO1.getPropertyValue(variable);
					// compare whether it is vo. if not just print prepertie else go in

					// if (propertyValueO1 == null){
					//				
					// continue;
					//			
					// // go in vo
					// }else if (propertyValueO1 instanceof Collection){
					// Collection collection = (Collection)propertyValueO1;
					// if (!collection.isEmpty()){
					// collection.toArray()[0].getClass();
					// }
					// }

					if (propertyValueO1 == null) {
						retAllPreperties.append(variable + "=" + propertyValueO1 + ";");

						// /** for Set/List **/
						// }else if (propertyValueO1 instanceof Collection){
						// Collection collection = (Collection)propertyValueO1;
						// if (CollectionUtil.isNotEmpty(collection))
						// for(Object obj2 : collection){
						// getAllProperties(obj2,
						// objIndicator + objIndicator,objPath);
						// }
					} else if (propertyValueO1.getClass().toString().contains(objPath)) {

						retAllPreperties
								.append(getAllProperties(propertyValueO1, objIndicator + objIndicator, objPath));
						retAllPreperties.append("\n" + objIndicator);
					} else {

						retAllPreperties.append(variable + "=" + propertyValueO1 + ";");
					}
				} catch (Exception e) {

				}
			}
		} catch (Exception e) {

		}
		return retAllPreperties;
	}

	/**
	 * if collection then iterate one by one to reflection else just pass to reflection.
	 * 
	 * @param obj
	 * @return
	 */
	public static StringBuffer getAllExProperties(Object obj, String objIndicator, String objPath) {
		StringBuffer stringBuffer = new StringBuffer();
		if (obj != null)
			if (obj instanceof Collection) {
				Collection collection = (Collection) obj;
				if (CollectionUtil.isNotEmpty(collection))
					for (Object obj2 : collection) {
						stringBuffer.append(ReflectionUtil.getAllProperties(obj2, objIndicator, objPath));
					}
			} else {
				stringBuffer.append(ReflectionUtil.getAllProperties(obj, objIndicator, objPath));
			}
		else {
			stringBuffer.append(obj);
		}
		return stringBuffer;
	}

	/**
	 * search String variavle inside the given object, if Empty then change it to Null.
	 * 
	 * @param obj =
	 *            this object will not be changed, it will be close.
	 * @return
	 */
	public static Object changeEmptyStrToNull(Object obj) {
		Object newObj = obj;
		// J.printLine(getAllExProperties(obj, " ", ".vo."));
		// try {
		// newObj = BeanUtils.cloneBean(obj);
		// } catch (Exception e1) {
		// e1.printStackTrace();
		// }
		//		
		BeanWrapper beanO1 = new BeanWrapperImpl(newObj);
		PropertyDescriptor[] proDescriptorsO1 = beanO1.getPropertyDescriptors();
		int propertyLength = proDescriptorsO1.length;

		for (int i = 0; i < propertyLength; i++) {
			try {
				Object propertyValueO1 = beanO1.getPropertyValue(proDescriptorsO1[i].getName());
				if (propertyValueO1 instanceof String && StringUtils.isEmpty((String) propertyValueO1)) {
					// password is null. it will not come in because it is not instance of String
					// J.printNegetif(proDescriptorsO1[i].getName()+"..."+(String)propertyValueO1);
					beanO1.setPropertyValue(proDescriptorsO1[i].getName(), null);

				}
			} catch (Exception e) {
				// [exec] java.lang.IllegalAccessException: Class org.apache.commons.beanutils.BeanUtilsBean can not
				// access a member of class com.foremobile.gateway.usermgmt.vo.User with modifiers "private"
				// [exec] at sun.reflect.Reflection.ensureMemberAccess(Unknown Source)
			}
		}

		return newObj;
	}

	public static void copyProperties(Object source, Object target, String... ignoreProperties) {
		if (ignoreProperties == null)
			BeanUtils.copyProperties(source, target);
		else
			BeanUtils.copyProperties(source, target, ignoreProperties);
	}

	/**
	 * different with class.getDeclaredField(does not include the field in super class). this one include the field in
	 * super class also.
	 * 
	 * @return
	 * @throws NoSuchFieldException
	 */
	public static Field getDeclaredField(Class clazz, String fieldName) throws NoSuchFieldException {

		Field field = null;
		while (clazz != null && field == null) {
			try {
				field = clazz.getDeclaredField(fieldName);
			} catch (Exception e) {
			}
			clazz = clazz.getSuperclass();
		}
		if (clazz == null && field == null)
			throw new NoSuchFieldException(clazz + ". Field name=" + fieldName);
		return field;
	}

	/**
	 * output:
	 * 
	 * <pre>
	 * //test vo -&gt;user object
	 * com.test.vo.User@275-&gt;accountNonExpired=false;accountNonLocked=false;authorities=null;class=class com.test.vo.User;credentialsNonExpired=false;dob=Sun Apr 16 16:44:45 SGT 2006;email=no globa variable and no setter;enabled=false;name=my name;password=null;
	 *     com.test.vo.User@275-&gt;accountNonExpired=false;accountNonLocked=false;authorities=null;class=class com.test.vo.User;credentialsNonExpired=false;dob=null;email=no globa variable and no setter;enabled=false;name=null;password=null;
	 *         com.test.vo.User@275-&gt;accountNonExpired=false;accountNonLocked=false;authorities=null;class=class com.test.vo.User;credentialsNonExpired=false;dob=null;email=no globa variable and no setter;enabled=false;name=null;password=null;user=null;userOid=null;userRoles=[];username=null;
	 *     userOid=null;userRoles=[];username=null;
	 *   userOid=null;userRoles=[com.test.vo.UserRole@15a8767];username=null;
	 *   
	 * //test collection - &gt; list of user
	 * [com.test.vo.User@275, com.test.vo.User@275, com.test.vo.User@275]
	 *   com.test.vo.User@275-&gt;accountNonExpired=false;accountNonLocked=false;authorities=null;class=class com.test.vo.User;credentialsNonExpired=false;dob=Sun Apr 16 16:44:45 SGT 2006;email=no globa variable and no setter;enabled=false;name=my name;password=null;
	 *     com.test.vo.User@275-&gt;accountNonExpired=false;accountNonLocked=false;authorities=null;class=class com.test.vo.User;credentialsNonExpired=false;dob=null;email=no globa variable and no setter;enabled=false;name=null;password=null;
	 *         com.test.vo.User@275-&gt;accountNonExpired=false;accountNonLocked=false;authorities=null;class=class com.test.vo.User;credentialsNonExpired=false;dob=null;email=no globa variable and no setter;enabled=false;name=null;password=null;user=null;userOid=null;userRoles=[];username=null;
	 *     userOid=null;userRoles=[];username=null;
	 *   userOid=null;userRoles=[com.test.vo.UserRole@15a8767];username=null;
	 *   com.test.vo.User@275-&gt;accountNonExpired=false;accountNonLocked=false;authorities=null;class=class com.test.vo.User;credentialsNonExpired=false;dob=null;email=no globa variable and no setter;enabled=false;name=null;password=null;
	 *     com.test.vo.User@275-&gt;accountNonExpired=false;accountNonLocked=false;authorities=null;class=class com.test.vo.User;credentialsNonExpired=false;dob=null;email=no globa variable and no setter;enabled=false;name=null;password=null;user=null;userOid=null;userRoles=[];username=null;
	 *   userOid=null;userRoles=[];username=null;
	 *   com.test.vo.User@275-&gt;accountNonExpired=false;accountNonLocked=false;authorities=null;class=class com.test.vo.User;credentialsNonExpired=false;dob=null;email=no globa variable and no setter;enabled=false;name=null;password=null;user=null;userOid=null;userRoles=[];username=null;
	 * 
	 * 
	 * </pre>
	 * 
	 * @param a
	 */
	public static void main(String[] a) {

		// J.printPositif("com.privasiaddddd.vo.ccc".matches("com\\.privasia.+?\\.vo\\..+?"));
		// try {
		// for (String Clazz : findFileNames("com", true, "com.+?", ".class")) {
		// J.printNegetif(Clazz);
		// }
		//
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// User user = new User();
		// user.setName("my name");
		// user.setDob(new Date());
		// UserRole userRole = new UserRole();
		// userRole.setDescription("userrole desc");
		// userRole.setName("userrole name");
		// user.getUserRoles().add(userRole);
		// User user2 = new User();
		// User user3 = new User();
		// user.setUser(user2);
		// user2.setUser(user3);
		//
		// List set = new ArrayList();
		// set.add(user);
		// set.add(user2);
		// set.add(user3);
		//
		// // test vo ->user object
		// System.out.println(ReflectionUtil.getAllExProperties(user, " ", ".vo."));
		//
		// // test collection - > list of user
		// System.out.println(ReflectionUtil.getAllExProperties(set, " ", ".vo."));
		//
		// /** testing * */
		// User user5 = new User();
		// user5.setUsername("name");
		// user5.setName("");
		// user5.setPassword(null);
		//
		// Object obj = user5;
		// Object retObj;
		// retObj = changeEmptyStrToNull(obj);
		//
		// J.printPositif(user5.getName() == "");
		// J.printPositif(user5.getUsername() == "name");
		// J.printPositif(((User) retObj).getName() == null);
		// J.printPositif(((User) retObj).getUsername() == "name");

	}

	/**
	 * This method finds the recursively scans the given <code>packageDirectory</code> for {@link Class} files and
	 * adds their according Java names to the given <code>classSet</code>.
	 * 
	 * @param packageDirectory
	 *            is the directory representing the {@link Package}.
	 * @param classSet
	 *            is where to add the Java {@link Class}-names to.
	 * @param qualifiedNameBuilder
	 *            is a {@link StringBuilder} containing the qualified prefix (the {@link Package} with a trailing dot).
	 * @param qualifiedNamePrefixLength
	 *            the length of the prefix used to rest the string-builder after reuse.
	 * @see http://m-m-m.svn.sourceforge.net/svnroot/m-m-m/trunk/mmm-util/mmm-util-reflect/src/main/java/net/sf/mmm/util/reflect/ReflectionUtil.java
	 */
	private static void findClassNamesRecursive(File packageDirectory, Set<String> classSet,
			StringBuilder qualifiedNameBuilder, int qualifiedNamePrefixLength, String packagePattern,
			String... endWiths) {

		for (File childFile : packageDirectory.listFiles()) {
			String fileName = childFile.getName();
			if (childFile.isDirectory()) {
				qualifiedNameBuilder.setLength(qualifiedNamePrefixLength);
				StringBuilder subBuilder = new StringBuilder(qualifiedNameBuilder);
				subBuilder.append(fileName);
				subBuilder.append('.');
				findClassNamesRecursive(childFile, classSet, subBuilder, subBuilder.length(), packagePattern ,endWiths);
			} else {
				String simpleClassName = fixClassName(fileName);
				if (simpleClassName != null) {
					qualifiedNameBuilder.setLength(qualifiedNamePrefixLength);
					qualifiedNameBuilder.append(simpleClassName);
					//J.printPositif(qualifiedNameBuilder.toString() + endWiths[0]);
					if (qualifiedNameBuilder.toString().matches(packagePattern)
							&& endWith(qualifiedNameBuilder.toString(), endWiths))
						classSet.add(qualifiedNameBuilder.toString());
				}
			}
		}
	}

	/**
	 * <pre>
	 * This method finds all classes that are located in the package identified by
	 * the given
	 * <code>
	 * packageName
	 * </code>
	 * .
	 * <br>
	 * &lt;b&gt;ATTENTION:&lt;/b&gt;
	 * <br>
	 * This is a relative expensive operation. Depending on your classpath
	 * multiple directories,JAR, and WAR files may need to scanned.
	 * 
	 *  @param packageName is the name of the {@link Package} to scan.
	 *  @param includeSubPackages - if
	 * <code>
	 * true
	 * </code>
	 *  all sub-packages of the
	 *        specified {@link Package} will be included in the search.
	 *  @return a {@link Set} will the fully qualified names of all requested
	 *         classes.
	 *  @throws IOException if the operation failed with an I/O error.
	 *  @see http://m-m-m.svn.sourceforge.net/svnroot/m-m-m/trunk/mmm-util/mmm-util-reflect/src/main/java/net/sf/mmm/util/reflect/ReflectionUtil.java
	 * </pre>
	 */
	public static Set<String> findFileNames(String packageName, boolean includeSubPackages, String packagePattern,
			String... endWiths) throws IOException {

		Set<String> classSet = new HashSet<String>();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String path = packageName.replace('.', '/');
		String pathWithPrefix = path + '/';
		Enumeration<URL> urls = classLoader.getResources(path);
		StringBuilder qualifiedNameBuilder = new StringBuilder(packageName);
		qualifiedNameBuilder.append('.');

		int qualifiedNamePrefixLength = qualifiedNameBuilder.length();

		while (urls.hasMoreElements()) {

			URL packageUrl = urls.nextElement();
			String urlString = URLDecoder.decode(packageUrl.getFile(), "UTF-8");
			String protocol = packageUrl.getProtocol().toLowerCase();

			if ("file".equals(protocol)) {

				File packageDirectory = new File(urlString);

				if (packageDirectory.isDirectory()) {
					if (includeSubPackages) {
						findClassNamesRecursive(packageDirectory, classSet, qualifiedNameBuilder,
								qualifiedNamePrefixLength, packagePattern ,endWiths);
					} else {
						for (String fileName : packageDirectory.list()) {

							String simpleClassName = fixClassName(fileName);
							if (simpleClassName != null) {
								qualifiedNameBuilder.setLength(qualifiedNamePrefixLength);
								qualifiedNameBuilder.append(simpleClassName);

								if (qualifiedNameBuilder.toString().matches(packagePattern)
										&& endWith(qualifiedNameBuilder.toString(), endWiths)) {
									classSet.add(qualifiedNameBuilder.toString());
									//J.printLine(qualifiedNameBuilder);
								}
							}
						}
					}
				}
			} else if ("jar".equals(protocol)) {
				// somehow the connection has no close method and can NOT be
				// disposed
				JarURLConnection connection = (JarURLConnection) packageUrl.openConnection();
				JarFile jarFile = connection.getJarFile();
				Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
				while (jarEntryEnumeration.hasMoreElements()) {

					JarEntry jarEntry = jarEntryEnumeration.nextElement();
					String absoluteFileName = jarEntry.getName();
					// if (absoluteFileName.endsWith(".class")) { //original
					if (endWith(absoluteFileName, endWiths)) { // modified
						if (absoluteFileName.startsWith("/")) {
							absoluteFileName.substring(1);
						}
						// special treatment for WAR files...
						// "WEB-INF/lib/" entries should be opened directly in
						// contained jar
						if (absoluteFileName.startsWith("WEB-INF/classes/")) {
							// "WEB-INF/classes/".length() == 16
							absoluteFileName = absoluteFileName.substring(16);
						}
						boolean accept = true;

						if (absoluteFileName.startsWith(pathWithPrefix)) {
							String qualifiedName = absoluteFileName.replace('/', '.');
							if (!includeSubPackages) {
								int index = absoluteFileName.indexOf('/', qualifiedNamePrefixLength + 1);
								if (index != -1) {
									accept = false;
								}
							}
							if (accept) {
								String className = fixClassName(qualifiedName);

								if (className != null) {

									if (qualifiedName.toString().matches(packagePattern)) {
										classSet.add(className);
										//J.printLine(qualifiedNameBuilder);
									}
								}
							}
						}
					}
				}
			} else {
				J.printNegetif("unknown protocol -> " + protocol);
				// TODO: unknown protocol - log this?
			}
		}
		return classSet;
	}

	private static boolean endWith(String str, String... endWiths) {
		if (endWiths == null || endWiths.length == 0) {
			return true;
		}
		for (String endWith : endWiths) {
			if (str.endsWith(endWith)) {
				// J.printPositif("............"+str);
				return true;
			}
		}
		return false;
	}

	/**
	 * <pre>
	 * This method checks and transforms the filename of a potential {@link Class}
	 * given by
	 * <code>
	 * fileName
	 * </code>
	 * .
	 * </pre>
	 * 
	 * @param fileName
	 *            is the filename.
	 * @return the according Java {@link Class#getName() class-name} for the given <code>fileName</code> if it is a
	 *         class-file that is no anonymous {@link Class}, else <code>null</code>.
	 * 
	 */
	private static String fixClassName(String fileName) {

		if (fileName.endsWith(".class")) {
			// remove extension (".class".length() == 6)
			// String nameWithoutExtension = fileName.substring(0,
			// fileName.length() - 6); //original
			String nameWithoutExtension = fileName; // modified for include
			// extention.

			// handle inner classes...
			int lastDollar = nameWithoutExtension.lastIndexOf('$');
			if (lastDollar > 0) {
				char innerClassStart = nameWithoutExtension.charAt(lastDollar + 1);
				if ((innerClassStart >= '0') && (innerClassStart <= '9')) {
					// ignore anonymous class
				} else {
					return nameWithoutExtension.replace('$', '.');
				}
			} else {
				return nameWithoutExtension;
			}
		}
		return fileName;
	}

	/**
	 * no collection
	 * 
	 * @return
	 */
	public static boolean isJavaDataType(Object object) {
		if (object instanceof String || object instanceof Long || object instanceof Double || object instanceof Integer
				|| object instanceof Boolean || object instanceof Date || object.getClass().isPrimitive()) {
			return true;
		}
		return false;
	}
}