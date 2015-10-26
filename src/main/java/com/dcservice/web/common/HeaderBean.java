package com.dcservice.web.common;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.dcservice.common.exceptions.PersistenceBeanException;
import com.dcservice.persistence.dao.DaoManager;
import com.dcservice.persistence.models.responses.QResponse;
import com.dcservice.web.BasePageBean;

@ManagedBean
@SessionScoped
public class HeaderBean extends BasePageBean {

	private static final long serialVersionUID = 7844098857850505763L;

	static int responsesCount;
	static {
		responsesCount = 0;
	}

	@Override
	protected void onConstruct() {
		try {
			updateResponsesCount();
		} catch (IllegalAccessException | PersistenceBeanException e) {
			log.error(e.getMessage(), e);
		}
	}

	public static void updateResponsesCount() throws IllegalAccessException,
			PersistenceBeanException {
		responsesCount = (int) DaoManager.query().from(QResponse.response)
				.count();
	}

	@Override
	protected boolean isPageBean() {
		return false;
	}

}
