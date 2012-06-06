package com.foobnix.android.commons.rest;

public interface RestService<M, R extends GeneralRestResponse> {

	R getAll();

	R save(M model);

	R delete(M model);

}
