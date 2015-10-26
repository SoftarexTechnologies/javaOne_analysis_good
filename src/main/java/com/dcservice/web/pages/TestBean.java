package com.dcservice.web.pages;

import static com.dcservice.common.helpers.ValidationHelper.isNullOrEmpty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.dcservice.common.exceptions.PersistenceBeanException;
import com.dcservice.persistence.Action;
import com.dcservice.persistence.TransactionExecuter;
import com.dcservice.persistence.dao.DaoManager;
import com.dcservice.persistence.models.FieldResponse;
import com.dcservice.persistence.models.base.BaseModel;
import com.dcservice.persistence.models.fields.Field;
import com.dcservice.persistence.models.fields.QField;
import com.dcservice.persistence.models.responses.Response;
import com.dcservice.web.BaseEditBean;
import com.dcservice.web.common.HeaderBean;
import com.dcservice.web.wrappers.FieldWrapper;

@ManagedBean
@ViewScoped
public class TestBean extends BaseEditBean implements Serializable {

	public static final long serialVersionUID = 7186971769033070058L;

	public List<FieldWrapper> questions;

	public boolean dataSubmited;

	@Override
	protected void onConstruct() {
			List<Field> fields = new ArrayList<Field>(0);
			try {
				fields = DaoManager.query().from(QField.field)
						.where(QField.field.active.eq(true)).list(QField.field);
			} catch (IllegalAccessException | PersistenceBeanException e) {
				log.error(e.getMessage(), e);
			}
			questions = new ArrayList<FieldWrapper>(0);
			fields.forEach(item -> {
				questions.add(new FieldWrapper(item));
				item = null;
			});
			dataSubmited = false;
	}

	@Override
	public void onSubmit() {
		cleanValidation();
		try {
			TransactionExecuter.execute(new Action() {
				@Override
				public void execute() {
					BaseModel response = new Response();
					DaoManager.save(response);
					questions.forEach(question -> {
						FieldResponse fieldResponse = new FieldResponse();
						try {
							fieldResponse.setField(DaoManager.query()
									.from(QField.field)
									.where(QField.field.id.eq(question.getId()))
									.uniqueResult(QField.field));
						} catch (IllegalAccessException | PersistenceBeanException e) {
							log.error(e.getMessage(), e);
						}
						fieldResponse.setResponse((Response) response);
						switch (question.getType()) {
						case COMBOBOX:
						case SINGLE_LINE_TEXT:
						case TEXTAREA:
						case RADIO_BUTTON:
							fieldResponse.setAnswer(question.getAnswer());

						case DATE:
							fieldResponse.setAnswer(question.getDateAnswer()
									.toString());

						case CHECKBOX:
							fieldResponse.setAnswer(String.valueOf(question
									.isBooleanAnswer()));
						}
						DaoManager.save(fieldResponse);

					});
				}
			});
			HeaderBean.updateResponsesCount();
		} catch (IllegalAccessException | PersistenceBeanException e) {
			log.error(e.getMessage(), e);
		}
		
	}

	@Override
	protected void afterSave() {
		dataSubmited = true;
		updateJS("questions_panel");
	}

	public void validate() {
		questions.forEach(question -> {
			if (question.getRequired())
				switch (question.getType()) {
				case TEXTAREA:
				case SINGLE_LINE_TEXT:
				case COMBOBOX:
				case RADIO_BUTTON:
					if (isNullOrEmpty(question.getAnswer())) {
						addException("testRequiredFields");
					}
					break;

				case DATE:
					if (isNullOrEmpty(question.getDateAnswer())) {
						addException("testRequiredFields");
					}
					break;
				case CHECKBOX:
					if (isNullOrEmpty(question.isBooleanAnswer())) {
						addException("testRequiredFields");
					}
					break;
				}
		});
	}

	public List<FieldWrapper> getQuestions() {
		return questions;
	}

	public void setQuestions(List<FieldWrapper> questions) {
		this.questions = questions;
	}

	public boolean isDataSubmited() {
		return dataSubmited;
	}

	public void setDataSubmited(boolean dataSubmited) {
		this.dataSubmited = dataSubmited;
	}

}
