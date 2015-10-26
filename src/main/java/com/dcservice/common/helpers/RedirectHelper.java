package com.dcservice.common.helpers;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.dcservice.common.enums.PagesTypes;

public class RedirectHelper  extends BaseHelper  {

	public static final String ID_PARAMETER = "id";

	public static void goTo(PagesTypes type) {
		try {
			if (type != null) {
				sendRedirect(getLink(type));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public static void goTo(PagesTypes type, Long id) {
		try {
			if (type != null) {
				sendRedirect(getLink(type).concat(id.toString()));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public static String getLink(PagesTypes type) {
		return type.getPagesContext();
	}

	public static void sendRedirect(String url) {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		String sb = new String("");
		sb = sb + (getServerURL()) + request.getContextPath();
		if (!url.startsWith("/")) {
			sb = sb + "/";
		}
		sb = sb + url;
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(sb);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	public static String getServerURL() {
		return getServerURL((HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest());
	}

	public static String getServerURL(HttpServletRequest request) {
		return request.getRequestURL().substring(0,
				request.getRequestURL().indexOf(request.getContextPath()));
	}

}
