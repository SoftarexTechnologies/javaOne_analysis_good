package com.dcservice.common.helpers;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.dcservice.common.enums.PagesTypes;

public class RedirectHelper  extends BaseHelper  {

	public static final String ID_PARAMETER = "id";

	public static void goTo(PagesTypes type) {
		if (type != null) {
			sendRedirect(getLink(type));
		}
	}

	public static void goTo(PagesTypes type, Long id) {
		if (type != null) {
			sendRedirect(getLink(type).concat(id.toString()));
		}
	}

	public static String getLink(PagesTypes type) {
		return type.getPagesContext();
	}

	public static void sendRedirect(String url) {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		StringBuilder sb = new StringBuilder("");
		sb.append(getServerURL()).append(request.getContextPath());
		if (!url.startsWith("/")) {
			sb.append("/");
		}
		sb.append(url);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(sb.toString());
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
