package com.foobnix.android.commons.rest;

import com.google.gson.annotations.SerializedName;

public class RestSingleResponse<M> extends GeneralRestResponse {
	@SerializedName("response")
	private M model;

	public M getModel() {
		return model;
	}

	public void setModel(M model) {
		this.model = model;
	}

}
