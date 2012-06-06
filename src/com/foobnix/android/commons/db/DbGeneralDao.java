package com.foobnix.android.commons.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.foobnix.android.commons.util.LOG;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.j256.ormlite.dao.Dao;

public abstract class DbGeneralDao<AppModel extends RequireId, DbModel extends DbTable> implements
		DbModelTransformation<AppModel, DbModel>, DaoRestMethods<AppModel, DbModel> {
	private final GeneralDatabaseHelper databaseHelper;
	private final Class<DbModel> clazz;

	public DbGeneralDao(GeneralDatabaseHelper databaseHelper, Class<DbModel> clazz) {
		this.databaseHelper = databaseHelper;
		this.clazz = clazz;
	}

	public Dao<DbModel, Integer> getRealDao() {
		try {
			return databaseHelper.getDbDao(clazz);
		} catch (SQLException e) {
			LOG.e(e);
			return null;
		}
	}

	@Override
	public boolean save(AppModel route) {
		try {
			LOG.d("DB Save");
			getRealDao().createOrUpdate(createDb(route));
		} catch (SQLException e) {
			LOG.e(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean cleanTable() {
		return databaseHelper.cleanTable(clazz);
	}

	@Override
	public List<AppModel> getAll() {
		try {
			List<DbModel> queryForAll = getRealDao().queryForAll();
			return transform(queryForAll);
		} catch (SQLException e) {
			LOG.e(e);
		}
		return Collections.emptyList();
	}

	public List<AppModel> transform(List<DbModel> queryForAll) {
		if (queryForAll == null || queryForAll.isEmpty()) {
			return new ArrayList<AppModel>();
		}

		Collection<AppModel> result = Collections2.transform(queryForAll, new Function<DbModel, AppModel>() {
			@Override
			public AppModel apply(DbModel input) {
				return createApp(input);
			}
		});
		return new ArrayList<AppModel>(result);
	}

	@Override
	public boolean delete(AppModel model) {
		try {
			LOG.d("DB Delete id", model.getId());
			getRealDao().deleteById(model.getId());
		} catch (SQLException e) {
			LOG.e(e);
			return false;
		}
		return true;
	}

	@Override
	public AppModel getById(int itemId) {
		try {
			LOG.d("get by id", itemId);
			DbModel db = getRealDao().queryForId(itemId);
			if (db != null) {
				return createApp(db);
			}
		} catch (SQLException e) {
			LOG.e(e);
			return null;
		}
		return null;
	}

	public GeneralDatabaseHelper getDatabaseHelper() {
		return databaseHelper;
	}

}
