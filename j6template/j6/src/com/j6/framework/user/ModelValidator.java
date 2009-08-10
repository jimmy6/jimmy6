package com.j6.framework.user;

import java.lang.reflect.Field;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.myfaces.config.RuntimeConfig;
import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

import com.j6.framework.util.CollectionUtil;
import com.j6.framework.util.J;

/**
 * 
 * 
 * @author Jimmy
 * 
 */
public class ModelValidator implements Validator {

	public ModelValidator() {
	}

	/**
	 * <pre>
	 * Hibernate validation.
	 * Other validation:-
	 * 1) (code was commented)unique - only work for Id/identifier of string datattype annotation.
	 * 			 		-  for column(unique = true).
	 * </pre>
	 */
	public void validate(FacesContext facesContext, UIComponent component, Object value) throws ValidatorException {
		Class clazz;
		String propertyName;
		InvalidValue[] ivs;

		ValueBinding valueBinding = component.getValueBinding("value");
		if (valueBinding == null) {
			throw new RuntimeException("component has no value attribute: " + component.getId());
		}

		try {
			if (component.getAttributes().get("labelInput") != null) {
				// cater for facelet compositon value
				String backingBeanStr = (String) component.getAttributes().get("labelInput");
				if (backingBeanStr.split("\\.").length > 2) {

					clazz = getClass(Class.forName(RuntimeConfig.getCurrentInstance(facesContext.getExternalContext())
							.getManagedBean(backingBeanStr.substring(0, backingBeanStr.indexOf(".")))
							.getManagedBeanClassName()), backingBeanStr.substring(backingBeanStr.indexOf(".") + 1));
				} else {
					clazz = Class.forName(RuntimeConfig.getCurrentInstance(facesContext.getExternalContext())
							.getManagedBean(backingBeanStr.substring(0, backingBeanStr.indexOf(".")))
							.getManagedBeanClassName());
				}
				propertyName = backingBeanStr.substring(backingBeanStr.lastIndexOf(".") + 1);

			} else {
				// not facelet composition value
				if (valueBinding.getExpressionString().split("\\.").length > 2) {
					clazz = getClass(Class
							.forName(RuntimeConfig.getCurrentInstance(facesContext.getExternalContext())
									.getManagedBean(
											valueBinding.getExpressionString().substring(2,
													valueBinding.getExpressionString().indexOf(".")))
									.getManagedBeanClassName()), valueBinding.getExpressionString().substring(
							valueBinding.getExpressionString().indexOf(".") + 1));
				} else {
					clazz = Class.forName(RuntimeConfig.getCurrentInstance(facesContext.getExternalContext())
							.getManagedBean(
									valueBinding.getExpressionString().substring(2,
											valueBinding.getExpressionString().lastIndexOf(".")))
							.getManagedBeanClassName());
				}
				propertyName = valueBinding.getExpressionString().substring(
						valueBinding.getExpressionString().lastIndexOf(".") + 1,
						valueBinding.getExpressionString().lastIndexOf("}"));

			}

			ivs = validate(clazz, propertyName, value);
		} catch (Exception e) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Annotation validation failed:"
					+ e.getMessage(), null));
		}
		if (ivs.length > 0) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ivs[0].getMessage()));
		} else {
			// validate those can't validated by hibernate.
			// unique = id. Only validate vo persistence entity.
			List<?> list = null;

			try {
				// if (isAnnotatedUnique(getDeclaredField(clazz, propertyName))) {
				// GenericDao genericDao = (GenericDao) Application.lookupBean("genericDao");
				// list = genericDao.findQueryAsList("from " + clazz.getName() + " where " + propertyName + " =?",
				// value);
				//
				// }
			} catch (Exception e) {
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Annotation validation failed.:" + e.getMessage(), null));
			}
			if (CollectionUtil.isNotEmpty(list)) {
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", value + " is in used."));
			}
		}

	}

	private Field getDeclaredField(Class clazz, String propertyName) {
		Field retField = null;

		try {
			retField = clazz.getDeclaredField(propertyName);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			if (clazz.getSuperclass() != null)
				retField = getDeclaredField(clazz.getSuperclass(), propertyName);
			else
				throw new RuntimeException(clazz + "." + propertyName + " not found.");
		}

		return retField;
	}

	/**
	 * only check String else false.
	 * 
	 * @param field
	 * @return
	 */
	public static boolean isAnnotatedUnique(Field field) {
		if (field.getType() == java.lang.String.class) {
			if (field.getAnnotation(javax.persistence.Id.class) != null) {
				return true;
			} else if (field.getAnnotation(javax.persistence.Column.class) != null
					&& field.getAnnotation(javax.persistence.Column.class).unique() == true) {
				return true;// TODO get primary key for query to determine unique.

			}
		}
		return false;
	}

	/**
	 * return the second last class of the propertiesStr.
	 * getClass(Class.forName("com.amms.user.facesbean.LoginBean"),"user.email") return User class object.
	 * 
	 * @param propertiesStr -
	 *            property of the fromClass parameter.
	 * @param fromClass
	 */
	public static Class getClass(Class fromClass, String propertiesStr) {

		String properties[] = propertiesStr.split("\\.");

		// for (int i = 0; i < properties.length - 1; i++) {
		try {
			// J.printNegetif("get" + properties[i].substring(0, 1).toUpperCase() + properties[i].substring(1));
			// clazz = fromClass.getMethod(
			// "get" + properties[i].substring(0, 1).toUpperCase() + properties[i].substring(1))
			// .getReturnType();
			String property = properties[0];
			if (properties.length > 1) {
				fromClass = getClass(fromClass.getMethod(
						"get" + property.substring(0, 1).toUpperCase() + property.substring(1)).getReturnType(),
						propertiesStr.substring(property.length() + 1));

			} else {
				return fromClass;
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		return fromClass;
		// }
	}

	public static void main(String a[]) {
		try {
			validate(Class.forName("com.privasia.amms.maintenance.pm.controller.managedbean.PmAddBean"),
					"selectedScheduleWorkOn", null);
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		// try {
		// J.printLine("..."+getClass(Class.forName("com.privasia.amms.maintenance.jobplan.controller.managedbean.JobPlanEditBean"),
		// "jobPlan.duration"));
		// } catch (Exception e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// try {
		//
		// validate(Class.forName("com.privasia.amms.maintenance.jobplan.vo.JobPlan"),
		// "jobPlanName", null);
		// } catch (ClassNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	/**
	 * validate(getClass(Class.forName("com.j6.framework.user.controller.managedbean.LoginBean"),
	 * "user.userRoles"), "userRoles", new UserRole());
	 * 
	 * @param clazz
	 * @param propertyName
	 * @param value
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static InvalidValue[] validate(Class clazz, String propertyName, Object value) throws ClassNotFoundException {

		ClassValidator validator = new ClassValidator(clazz);

		InvalidValue[] validationMessages = validator.getPotentialInvalidValues(propertyName, value);
		J.printNegetif("annotation has rule " + validator.hasValidationRules());

		for (InvalidValue invalidValue : validationMessages)
			J.printNegetif("error msg = " + invalidValue.getMessage());

		return validationMessages;
	}

}
