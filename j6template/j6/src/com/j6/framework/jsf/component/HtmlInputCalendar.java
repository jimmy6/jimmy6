package com.j6.framework.jsf.component;

import java.lang.reflect.Field;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.config.RuntimeConfig;

import com.j6.framework.user.ModelValidator;

public class HtmlInputCalendar extends org.apache.myfaces.custom.calendar.HtmlInputCalendar {
	private static final Log log = LogFactory.getLog(HtmlInputCalendar.class);
	public final static String LABEL_INPUT = "labelInput";
	private boolean annotation = false;

	public void initAnnotation() {
		Class clazz;
		String propertyName;
		String backingBeanStr = (String) getAttributes().get(LABEL_INPUT);
		try {
			log.debug("==============================");
			log.debug("backingBeanStr = " + backingBeanStr);
			log.debug("==============================");
			if (backingBeanStr.split("\\.").length > 2) {
				clazz = ModelValidator.getClass(Class.forName(RuntimeConfig.getCurrentInstance(
						FacesContext.getCurrentInstance().getExternalContext()).getManagedBean(
						backingBeanStr.substring(0, backingBeanStr.indexOf("."))).getManagedBeanClassName()),
						backingBeanStr.substring(backingBeanStr.indexOf(".") + 1));

			} else {
				clazz = Class.forName(RuntimeConfig.getCurrentInstance(
						FacesContext.getCurrentInstance().getExternalContext()).getManagedBean(
						backingBeanStr.substring(0, backingBeanStr.indexOf("."))).getManagedBeanClassName());
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("backingBeanStr( " + backingBeanStr + " ) not found.");
		}
		propertyName = backingBeanStr.substring(backingBeanStr.lastIndexOf(".") + 1);

		try {

			Field field = clazz.getDeclaredField(propertyName);

			// required
			if (!isRequired() && field.isAnnotationPresent(org.hibernate.validator.NotNull.class))
				setRequired(true);
			// TODO correct the .+?.id..+?
			if (!isRequired() && backingBeanStr.matches(".+?.id..+?"))
				setRequired(true);

			if (!isRequired() && field.isAnnotationPresent(javax.persistence.Id.class))
				setRequired(true);

			// size
			// if (getSize() <0) {
			// if (field.isAnnotationPresent(org.hibernate.validator.Max.class))
			// setSize(field.getAnnotation(org.hibernate.validator.Max.class).value() >= 100 ? 100 : 50);
			// else if (field.isAnnotationPresent(org.hibernate.validator.Length.class))
			// setSize(field.getAnnotation(org.hibernate.validator.Length.class).max() >= 100 ? 100 : 50);
			// else if (field.getType() == Integer.class || field.getType() == Long.class
			// || field.getType() == Boolean.class ||field.getType().isPrimitive())
			// setSize(15);
			// else
			// setSize(25);
			// }

		} catch (Exception e) {
			throw new RuntimeException("backingBeanStr( " + backingBeanStr + " )'s " + propertyName + " not found.", e);
		}
		annotation = true;
	}

	public String getClientId(FacesContext context) {

		if (!annotation && getAttributes().get("labelInput") != null)
			initAnnotation();

		return super.getClientId(context);
	}

}
