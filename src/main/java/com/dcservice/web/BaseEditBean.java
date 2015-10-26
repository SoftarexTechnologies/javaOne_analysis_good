package com.dcservice.web;

public abstract class BaseEditBean extends BaseValidationBean {

	private static final long serialVersionUID = 7593288408823317222L;

	public void submit(){

			validate();
			if (isFormValid()) {
				onSubmit();
				afterSave();
			}

		
	}

	public abstract void onSubmit();
	
	protected abstract void afterSave();
	
	protected abstract void validate();
	
}
