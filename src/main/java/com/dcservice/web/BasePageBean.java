package com.dcservice.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;

import com.ocpsoft.shade.org.apache.commons.logging.Log;
import com.ocpsoft.shade.org.apache.commons.logging.LogFactory;

public abstract class BasePageBean implements Serializable {

	private static final long serialVersionUID = -7281819428768535761L;
	protected final Log log = LogFactory.getLog(this.getClass());
	
	@PostConstruct
	public void init() {
		onConstruct();
	}

	protected abstract void onConstruct();

	protected boolean isPageBean() {
		return true;
	}
	
	public void updateJS(String str) {
		RequestContext context = RequestContext.getCurrentInstance();
		if (context != null) {
			context.update(str);
		}
	}

	public HttpServletRequest getRequest() {
		return (HttpServletRequest) getExternalContext().getRequest();
	}

	public ServletResponse getResponse() {
		return (ServletResponse) getExternalContext().getResponse();
	}

	public FacesContext getContext() {
		return FacesContext.getCurrentInstance();
	}

	public ExternalContext getExternalContext() {
		return getContext().getExternalContext();
	}

	public String getRequestContextPath() {
		return getContext().getExternalContext().getRequestContextPath();
	}

	public boolean isPostback() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return facesContext.isPostback();
	}

}
