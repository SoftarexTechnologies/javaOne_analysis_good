package com.dcservice.web.pages;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.dcservice.persistence.dao.DaoManager;
import com.dcservice.persistence.models.QFieldResponse;
import com.dcservice.persistence.models.fields.Field;
import com.dcservice.persistence.models.fields.QField;
import com.dcservice.persistence.models.responses.QResponse;
import com.dcservice.persistence.models.responses.Response;
import com.dcservice.web.BasePageBean;
import com.dcservice.web.wrappers.ColumnModel;
import com.dcservice.web.wrappers.ResponseWrapper;

@ManagedBean
@ViewScoped
public class ResponsesListBean extends BasePageBean {

	private static final long serialVersionUID = 4254482680299501574L;

	private List<ColumnModel> columns;

	private List<ResponseWrapper> responses;

	@Override
	protected void onConstruct() {
		try {
			List<Response> responsesItems = DaoManager
					.query()
					.from(QResponse.response)
					.rightJoin(QResponse.response.fieldResponses,
							QFieldResponse.fieldResponse).fetch()
					.rightJoin(QFieldResponse.fieldResponse.field).fetch()
					.distinct().list(QResponse.response);
			List<Field> fields = DaoManager.query().from(QField.field)
					.list(QField.field);
			
			columns = new ArrayList<>();
			fields.forEach(field -> {
				columns.add(new ColumnModel(field.getLabel(), field.getLabel()));
			});

			responses = new ArrayList<>();
			responsesItems.forEach(item -> {
				responses.add(new ResponseWrapper(item));
			});

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public List<ColumnModel> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnModel> columns) {
		this.columns = columns;
	}

	public List<ResponseWrapper> getResponses() {
		return responses;
	}

	public void setResponses(List<ResponseWrapper> responses) {
		this.responses = responses;
	}

}
