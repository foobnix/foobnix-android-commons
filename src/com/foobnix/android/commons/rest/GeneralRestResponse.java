package com.foobnix.android.commons.rest;

public class GeneralRestResponse {
	private boolean success;
	private ErrorResponse error;

	protected GeneralRestResponse() {
		success = false;// default

	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public ErrorResponse getError() {
		return error;
	}

	public void setError(ErrorResponse error) {
		this.error = error;
	}

}
