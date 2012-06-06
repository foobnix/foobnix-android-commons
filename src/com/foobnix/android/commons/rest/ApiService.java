package com.foobnix.android.commons.rest;

import java.util.List;

public interface ApiService<M> {
	List<M> getAll();

	boolean save(M model);

	boolean delete(M model);

	M getById(int modelId);

	boolean syncronize();

	M saveResult(M model);

}
