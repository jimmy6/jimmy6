package com.j6.framework.user;

import java.lang.reflect.Field;

import javax.faces.context.FacesContext;
import javax.persistence.Embeddable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.config.RuntimeConfig;

public class HtmlInputTextArea extends org.apache.myfaces.component.html.ext.HtmlInputTextarea {
	private static final Log log = LogFactory.getLog(HtmlInputTextArea.class);
	public final static String LABEL_INPUT = "labelInput";
	private boolean annotation = false;
	private int maxlength = -12345;

	public void initAnnotation() {
		Class clazz;
		String propertyName;
		String backingBeanStr = (String) getAttributes().get("labelInput");
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

			// for embeddable object.
			if (clazz.isAnnotationPresent(Embeddable.class)) {

				String newBackingBeanStr = backingBeanStr.substring(backingBeanStr.indexOf(".") + 1, backingBeanStr
						.lastIndexOf("."));

				Class parentClazz = ModelValidator.getClass(Class.forName(RuntimeConfig.getCurrentInstance(
						FacesContext.getCurrentInstance().getExternalContext()).getManagedBean(
						backingBeanStr.substring(0, backingBeanStr.indexOf("."))).getManagedBeanClassName()),
						newBackingBeanStr);

				Field parentField = parentClazz.getDeclaredField(newBackingBeanStr.substring(newBackingBeanStr
						.lastIndexOf(".") + 1));

				if (!isRequired() && parentField.isAnnotationPresent(org.hibernate.validator.NotNull.class))
					setRequired(true);
				if (!isRequired() && parentField.isAnnotationPresent(javax.persistence.Id.class))
					setRequired(true);

			}

			Field field = clazz.getDeclaredField(propertyName);

			// required
			if (!isRequired() && field.isAnnotationPresent(org.hibernate.validator.NotNull.class))
				setRequired(true);
			// TODO correct the .+?.id..+?
			if (!isRequired() && backingBeanStr.matches(".+?.id..+?"))
				setRequired(true);

			if (!isRequired() && field.isAnnotationPresent(javax.persistence.Id.class))
				setRequired(true);

			// maxLenght
			if (getMaxlength() < 0) {
				if (field.isAnnotationPresent(org.hibernate.validator.Max.class))
					setMaxlength(field.getAnnotation(org.hibernate.validator.Max.class).value());
				else if (field.isAnnotationPresent(org.hibernate.validator.Length.class))
					setMaxlength(field.getAnnotation(org.hibernate.validator.Length.class).max());
			}

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

			setOnkeyup(" textCounter(this.form." + getId() + ",document.getElementById('" + getId() + "MaxValue'),"
					+ getMaxlength() + ") ");
			setOnkeydown(" textCounter(this.form." + getId() + ",document.getElementById('" + getId() + "MaxValue'),"
					+ getMaxlength() + ") ");
			// onKeyDown="textCounter(this.form.message,this.form.remLen,125);"
			// onKeyUp="textCounter(this.form.message,this.form.remLen,125);"></textarea>

		} catch (Exception e) {
			throw new RuntimeException("backingBeanStr( " + backingBeanStr + " )'s " + propertyName + " not found.", e);
		}
		annotation = true;
	}

	public String getClientId(FacesContext context) {

		if (!annotation && getAttributes().get("labelInput") != null) {
			initAnnotation();
		}
		return super.getClientId(context);
	}

	public int getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(int maxlength) {
		this.maxlength = maxlength;
	}
}
