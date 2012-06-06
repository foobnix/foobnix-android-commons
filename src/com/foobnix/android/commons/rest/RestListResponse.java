/**
 * 
 */
package com.foobnix.android.commons.rest;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * @author iivanenko
 * 
 */
public class RestListResponse<M> extends GeneralRestResponse {
	@SerializedName("response")
	private List<M> models;

	public List<M> getModels() {
		return models;
	}

	public void setModels(List<M> models) {
		this.models = models;
	}

}
