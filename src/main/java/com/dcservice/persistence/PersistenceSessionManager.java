package com.dcservice.persistence;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public final class PersistenceSessionManager {
	private static final String HibernateSessionAttribute = "HibernateSessionAttribute";

	private static PersistenceSessionManager instance = new PersistenceSessionManager();

	private PersistenceSession instanceBean = null;

	private PersistenceSessionManager() {
	}

	public static PersistenceSessionManager getInstance() {
		return instance;
	}

	public PersistenceSession bean() {
		if (FacesContext.getCurrentInstance() != null
				&& FacesContext.getCurrentInstance().getExternalContext()
						.getRequest() != null) {
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();

			if (request.getAttribute(HibernateSessionAttribute) == null) {
				request.setAttribute(HibernateSessionAttribute,
						new PersistenceSession());

			}

			instanceBean = (PersistenceSession) request
					.getAttribute(HibernateSessionAttribute);

			return instanceBean;
		}
		if (instanceBean == null) {
			instanceBean = new PersistenceSession();
		}
		return instanceBean;
	}

	public static PersistenceSession getBean() {
		return getInstance().bean();
	}
}
