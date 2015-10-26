package com.dcservice.common.helpers;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class ValidatorHelper extends BaseHelper {

	private final static String ERROR_CLASS = "ui-state-error";
	private final static String STYLE_CLASS_ATTR = "styleClass";
	private final static String TITLE_ATTR = "title";

	public static void markNotValid(UIComponent component, String tmpMessage,
			FacesContext context) {
		setStyleClass(component, ERROR_CLASS);
	}

	private static void setStyleClass(UIComponent component, String styleClass) {
		if (component.getAttributes().get(STYLE_CLASS_ATTR) == null) {
			component.getAttributes().put(STYLE_CLASS_ATTR, styleClass);
		} else {
			component.getAttributes().put(
					STYLE_CLASS_ATTR,
					String.format("%s %s",
							component.getAttributes().get(STYLE_CLASS_ATTR)
									.toString().replaceAll(styleClass, "")
									.trim(), styleClass));
		}
	}

	public static void markValid(UIComponent component, FacesContext context) {

		if (component == null) {
			return;
		}

		if (component.getAttributes() != null
				&& component.getAttributes().get(STYLE_CLASS_ATTR) != null) {
			component.getAttributes().put(
					STYLE_CLASS_ATTR,
					component.getAttributes().get(STYLE_CLASS_ATTR).toString()
							.replaceAll(ERROR_CLASS, "").trim());
		}
		component.getAttributes().put(TITLE_ATTR, "");

		while (component.getParent() != null) {
			component = component.getParent();
		}
	}

}
