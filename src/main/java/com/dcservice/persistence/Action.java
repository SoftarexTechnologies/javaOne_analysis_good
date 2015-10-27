package com.dcservice.persistence;


public abstract class Action implements IAction {
	
	@Override
	public void onBeforeExecute() {
		return;
	}

	@Override
	public void onExecuted() {
		return;
	}

	@Override
	public void execute(Object obj) {
		this.execute();
	}

	public abstract void execute();
}
