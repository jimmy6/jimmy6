/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.j6.framework.jsf.component;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;

import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRenderer;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;

import com.j6.framework.user.HtmlInputTextArea;

/**
 * @author Manfred Geiler (latest modification by $Author: jimmyau $)
 * @author Anton Koinov
 * @version $Revision: 1.3 $ $Date: 2008/05/03 07:17:35 $
 */
public class HtmlTextareaRenderer extends HtmlRenderer {
	// copy from HtmlTextareaRenderer
	protected boolean isDisabled(FacesContext facesContext, UIComponent uiComponent) {
		if (!UserRoleUtils.isEnabledOnUserRole(uiComponent)) {
			return true;
		} else {
			return isDisabled2(facesContext, uiComponent);
		}
	}

	// copy from HtmlTextareaRenderer
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		RendererUtils.checkParamValidity(facesContext, uiComponent, UIInput.class);

		if (HtmlRendererUtils.isDisplayValueOnly(uiComponent)) {
			HtmlRendererUtils.renderDisplayValueOnly(facesContext, (UIInput) uiComponent);
		} else {
			encodeTextArea(facesContext, uiComponent);
		}
	}

	protected void encodeTextArea(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		ResponseWriter writer = facesContext.getResponseWriter();
		writer.startElement(HTML.TEXTAREA_ELEM, uiComponent);

		String clientId = uiComponent.getClientId(facesContext);
		writer.writeAttribute(HTML.NAME_ATTR, clientId, null);
		HtmlRendererUtils.writeIdIfNecessary(writer, uiComponent, facesContext);

		HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent,
				HTML.TEXTAREA_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED);
		if (isDisabled(facesContext, uiComponent)) {
			writer.writeAttribute(org.apache.myfaces.shared_tomahawk.renderkit.html.HTML.DISABLED_ATTR, Boolean.TRUE,
					null);
		}

		String strValue = org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils.getStringValue(facesContext,
				uiComponent);
		writer.writeText(strValue, org.apache.myfaces.shared_tomahawk.renderkit.JSFAttr.VALUE_ATTR);

		writer.endElement(HTML.TEXTAREA_ELEM);
		if (uiComponent instanceof HtmlInputTextArea) {
			HtmlInputTextArea textArea = (HtmlInputTextArea) uiComponent;
			if (textArea.getMaxlength() > 0)
				writer.append("<span id='" + clientId + "MaxValue' class='standardTextMandatory'>"
						+ (textArea.getMaxlength() - strValue.length()) + "</span> characters left. ");
		}

	}

	protected boolean isDisabled2(FacesContext facesContext, UIComponent uiComponent) {
		// TODO: overwrite in extended HtmlTextareaRenderer and check for enabledOnUserRole
		if (uiComponent instanceof HtmlInputTextarea) {
			return ((HtmlInputTextarea) uiComponent).isDisabled();
		} else {
			return org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils.getBooleanAttribute(uiComponent,
					HTML.DISABLED_ATTR, false);
		}
	}

	public void decode(FacesContext facesContext, UIComponent component) {
		RendererUtils.checkParamValidity(facesContext, component, UIInput.class);
		HtmlRendererUtils.decodeUIInput(facesContext, component);
	}

	public Object getConvertedValue(FacesContext facesContext, UIComponent uiComponent, Object submittedValue)
			throws ConverterException {
		RendererUtils.checkParamValidity(facesContext, uiComponent, UIOutput.class);
		return org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils.getConvertedUIOutputValue(facesContext,
				(UIOutput) uiComponent, submittedValue);
	}

}
