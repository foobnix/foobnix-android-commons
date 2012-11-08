package com.foobnix.android.commons.db;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.foobnix.android.commons.util.LOG;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class GeneralDatabaseHelper extends OrmLiteSqliteOpenHelper {

	private final static Map<Class<? extends DbTable>, Dao<? extends DbTable, Integer>> TABLES = new HashMap<Class<? extends DbTable>, Dao<? extends DbTable, Integer>>();
	private final Context context;

	public GeneralDatabaseHelper(Context context, List<Class<? extends DbTable>> tables, String name, int version) {
		super(context, name, null, version);
		this.context = context;
		for (Class<? extends DbTable> table : tables) {
			if (!TABLES.containsKey(table)) {
				TABLES.put(table, null);
			}
		}
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
		try {
			for (Class<?> table : TABLES.keySet()) {
				TableUtils.createTable(connectionSource, table);
			}
		} catch (SQLException e) {
			LOG.e("Could not create new table for Thing", e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion,
			int newVersion) {
		try {
			for (Class<?> table : TABLES.keySet()) {
				TableUtils.dropTable(connectionSource, table, true);
			}
			onCreate(sqLiteDatabase, connectionSource);
		} catch (SQLException e) {
			LOG.e("Could not upgrade the tables", e);
		}
	}

	public <T extends DbTable> boolean cleanTable(Class<T> tableClass) {
		try {
			TableUtils.clearTable(getConnectionSource(), tableClass);
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	@Override
	public void close() {
		super.close();
		for (Dao<?, Integer> dao : TABLES.values()) {
			dao = null;
		}
	}

	public <T extends DbTable> Dao<T, Integer> getDbDao(Class<T> tableClass) throws SQLException {
		Dao<? extends DbTable, Integer> dao = TABLES.get(tableClass);
		if (dao == null) {
			dao = getDao(tableClass);
			TABLES.put(tableClass, dao);
		}
		return (Dao<T, Integer>) dao;
	}

	public Context getContext() {
		return context;
	}
}