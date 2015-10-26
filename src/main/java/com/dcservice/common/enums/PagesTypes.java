package com.dcservice.common.enums;


public enum PagesTypes {

	TEST("/"), FIELD_LIST("/fields"), FIELD_NEW("/fields/new"), FIELD_EDIT(
			"/fields/"), RESPONSES("/responses");

	private String page;

	private PagesTypes(String page) {
		this.page = page;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPagesContext() {
		return page;
	}

}
