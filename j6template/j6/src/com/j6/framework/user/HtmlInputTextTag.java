package com.j6.framework.user;

import java.lang.reflect.Field;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.config.RuntimeConfig;

import com.j6.framework.util.ReflectionUtil;

public class HtmlInputTextTag extends org.apache.myfaces.component.html.ext.HtmlInputText {
	private static final Log log = LogFactory.getLog(HtmlInputTextTag.class);
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

			Field field = ReflectionUtil.getDeclaredField(clazz, propertyName);

			// get parent setting. for embeddable object or isFollowParentRequiredSetting().
			if (backingBeanStr.split("\\.").length > 3) {

				String newBackingBeanStr = backingBeanStr.substring(backingBeanStr.indexOf(".") + 1, backingBeanStr
						.lastIndexOf("."));

				Class parentClazz = ModelValidator.getClass(Class.forName(RuntimeConfig.getCurrentInstance(
						FacesContext.getCurrentInstance().getExternalContext()).getManagedBean(
						backingBeanStr.substring(0, backingBeanStr.indexOf("."))).getManagedBeanClassName()),
						newBackingBeanStr);

				Field parentField = parentClazz.getDeclaredField(newBackingBeanStr.substring(newBackingBeanStr
						.lastIndexOf(".") + 1));

				if (isFollowParentRequiredSetting(parentField, field)) {
					setRequired(true);

				} else if (clazz == com.j6.framework.vo.Time.class) {

					setStyleClass(getStyleClass().replaceAll("input_mask mask_timeWithoutSecond", ""));
					setStyleClass((getStyleClass() + " input_mask mask_timeWithoutSecond"));
					if (getSize() < 0) {
						setSize(10);
					}

					if (getMaxlength() < 0) {
						if (parentField.isAnnotationPresent(org.hibernate.validator.Max.class))
							setMaxlength(parentField.getAnnotation(org.hibernate.validator.Max.class).value());
						else if (parentField.isAnnotationPresent(org.hibernate.validator.Length.class)) {
							setMaxlength(parentField.getAnnotation(org.hibernate.validator.Length.class).max());
						}
					}
				}

				// required
				if (!isRequired() && parentField.isAnnotationPresent(org.hibernate.validator.NotNull.class))
					setRequired(true);
				if (!isRequired() && parentField.isAnnotationPresent(javax.persistence.Id.class))
					setRequired(true);

			} else {
				// required
				if (!isRequired() && field.isAnnotationPresent(org.hibernate.validator.NotNull.class))
					setRequired(true);
			}

			// required
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
			if (getSize() < 0) {
				if (field.isAnnotationPresent(org.hibernate.validator.Max.class))
					setSize(field.getAnnotation(org.hibernate.validator.Max.class).value() >= 100 ? 100 : 50);
				else if (field.isAnnotationPresent(org.hibernate.validator.Length.class))
					setSize(field.getAnnotation(org.hibernate.validator.Length.class).max() >= 100 ? 100 : 50);
				else if (field.getType() == Integer.class || field.getType() == Long.class
						|| field.getType() == Boolean.class || field.getType().isPrimitive())
					setSize(15);
				else
					setSize(25);
			}

		} catch (Exception e) {
			throw new RuntimeException("backingBeanStr( " + backingBeanStr + " )'s " + propertyName + " not found.", e);
		}
		annotation = true;
	}

	/**
	 * 
	 * @param parentField
	 * @param field
	 * @return true - if parent is ManyToOne.class).optional() == false and target field is unique.
	 */
	private boolean isFollowParentRequiredSetting(Field parentField, Field field) {
		// if (parentField.getAnnotation(javax.persistence.Column.class) != null
		// && parentField.getAnnotation(javax.persistence.Column.class).unique() == true) {
		// setRequired(true);
		// }

		// if( parentField.getAnnotation(javax.persistence.JoinColumn.class) != null
		// && parentField.getAnnotation(javax.persistence.JoinColumn.class).nullable() == false ){
		//			
		// }
		if (parentField.getAnnotation(javax.persistence.ManyToOne.class) != null
				&& parentField.getAnnotation(javax.persistence.ManyToOne.class).optional() == false
				&& field.getAnnotation(javax.persistence.Column.class) != null
				&& field.getAnnotation(javax.persistence.Column.class).unique() == true) {
			return true;
		}

		return false;
	}

	public String getClientId(FacesContext context) {

		if (!annotation && getAttributes().get("labelInput") != null) {
			initAnnotation();
		}
		return super.getClientId(context);
	}
}
