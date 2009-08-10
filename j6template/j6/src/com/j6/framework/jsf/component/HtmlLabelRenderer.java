package com.j6.framework.jsf.component;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.ValueHolder;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.shared_impl.renderkit.JSFAttr;
import org.apache.myfaces.shared_impl.renderkit.RendererUtils;
import org.apache.myfaces.shared_impl.renderkit.html.HTML;
import org.apache.myfaces.shared_impl.renderkit.html.HtmlRenderer;
import org.apache.myfaces.shared_impl.renderkit.html.HtmlRendererUtils;

import com.j6.framework.jsf.application.FacesUtil;

public class HtmlLabelRenderer extends HtmlRenderer {
	private static final Log log = LogFactory.getLog(HtmlLabelRenderer.class);

	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		super.encodeBegin(facesContext, uiComponent); // check for NP

		ResponseWriter writer = facesContext.getResponseWriter();

		encodeBefore(facesContext, writer, uiComponent);

		writer.startElement(HTML.LABEL_ELEM, uiComponent);
		HtmlRendererUtils.writeIdIfNecessary(writer, uiComponent, facesContext);
		HtmlRendererUtils.renderHTMLAttributes(writer, uiComponent, HTML.LABEL_PASSTHROUGH_ATTRIBUTES);

		String forAttr = getFor(uiComponent);

		if (forAttr != null) {
			writer.writeAttribute(HTML.FOR_ATTR, getClientId(facesContext, uiComponent, forAttr), JSFAttr.FOR_ATTR);
		} else {
			if (log.isWarnEnabled()) {
				log.warn("Attribute 'for' of label component with id " + uiComponent.getClientId(facesContext)
						+ " is not defined");
			}
		}

		// MyFaces extension: Render a label text given by value
		// TODO: Move to extended component
		if (uiComponent instanceof ValueHolder) {
			String text = RendererUtils.getStringValue(facesContext, uiComponent);
			if (text != null) {
				writer.writeText(text, "value");
			}
		}

		writer.flush(); // close start tag

		encodeAfterStart(facesContext, writer, uiComponent);
	}

	protected void encodeAfterStart(FacesContext facesContext, ResponseWriter writer, UIComponent uiComponent)
			throws IOException {
	}

	protected void encodeBefore(FacesContext facesContext, ResponseWriter writer, UIComponent uiComponent)
			throws IOException {
	}

	protected String getFor(UIComponent component) {
		if (component instanceof HtmlOutputLabel) {
			return ((HtmlOutputLabel) component).getFor();
		} else {
			return (String) component.getAttributes().get(JSFAttr.FOR_ATTR);
		}
	}

	protected String getClientId(FacesContext facesContext, UIComponent uiComponent, String forAttr) {
		return RendererUtils.getClientId(facesContext, uiComponent, forAttr);
	}

	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		super.encodeEnd(facesContext, uiComponent); // check for NP

		ResponseWriter writer = facesContext.getResponseWriter();

		encodeBeforeEnd(facesContext, writer, uiComponent);

		writer.endElement(HTML.LABEL_ELEM);
		// append required notation.
		if (((UIInput) FacesUtil.findComponentInRoot(getFor(uiComponent))).isRequired())
			writer.append("<span class='standardTextMandatory'>*</span>");
		//
		encodeAfter(facesContext, writer, uiComponent);
	}

	protected void encodeBeforeEnd(FacesContext facesContext, ResponseWriter writer, UIComponent uiComponent)
			throws IOException {
	}

	protected void encodeAfter(FacesContext facesContext, ResponseWriter writer, UIComponent uiComponent)
			throws IOException {
	}
}
