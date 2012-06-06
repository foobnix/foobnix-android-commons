package com.foobnix.android.commons.db;

import java.util.List;

public interface DaoRestMethods<AppModel extends RequireId, DbModel extends DbTable> {

	public abstract boolean save(AppModel route);

	public abstract List<AppModel> getAll();

	public abstract boolean delete(AppModel model);

	public abstract boolean cleanTable();

	AppModel getById(int itemId);

}