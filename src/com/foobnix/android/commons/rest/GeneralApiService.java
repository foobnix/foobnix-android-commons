package com.foobnix.android.commons.rest;

import java.util.List;

import com.foobnix.android.commons.db.DbGeneralDao;
import com.foobnix.android.commons.db.DbTable;
import com.foobnix.android.commons.db.RequireId;
import com.foobnix.android.commons.util.LOG;

public class GeneralApiService<AppModel extends RequireId, DbModel extends DbTable, Response extends RestListResponse<AppModel>>
		implements ApiService<AppModel> {

	private final DbGeneralDao<AppModel, DbModel> dao;
	private final RestService<AppModel, Response> remote;

	public GeneralApiService(DbGeneralDao<AppModel, DbModel> dao, RestService<AppModel, Response> remote) {
		this.dao = dao;
		this.remote = remote;
	}

	@Override
	public boolean syncronize() {
		LOG.d("Syncronize");
		dao.cleanTable();
		Response allRemote = remote.getAll();
		if (allRemote != null && allRemote.isSuccess()) {
			for (AppModel place : allRemote.getModels()) {
				dao.save(place);
			}
		}
		return true;
	}

	@Override
	public List<AppModel> getAll() {
		return dao.getAll();
	}

	@Override
	public AppModel getById(int modelId) {
		return dao.getById(modelId);
	}

	@Override
	public boolean save(AppModel model) {
		Response save = remote.save(model);
		if (save == null || !save.isSuccess() || save.getModels() == null || save.getModels().isEmpty()
				|| save.getModels().get(0) == null) {
			return false;
		}
		AppModel result = save.getModels().get(0);
		model.setId(result.getId());
		return dao.save(model);
	}

	@Override
	public AppModel saveResult(AppModel model) {
		Response save = remote.save(model);

		if (save == null || !save.isSuccess() || save.getModels() == null || save.getModels().isEmpty()) {
			return null;
		}
		AppModel result = save.getModels().get(0);
		if (result == null) {
			return null;
		}

		model.setId(result.getId());

		if (!dao.save(model)) {
			return null;
		}
		return result;
	}

	@Override
	public boolean delete(AppModel model) {
		Response delete = remote.delete(model);
		if (delete != null && delete.isSuccess()) {
			return dao.delete(model);
		}
		return false;
	}

	public DbGeneralDao<AppModel, DbModel> getDao() {
		return dao;
	}

	public RestService<AppModel, Response> getRemote() {
		return remote;
	}

}
