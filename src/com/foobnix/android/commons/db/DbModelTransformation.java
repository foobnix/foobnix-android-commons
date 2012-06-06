package com.foobnix.android.commons.db;


public interface DbModelTransformation<AppModel, DbModel extends DbTable> {

	DbModel createDb(AppModel app);

	AppModel createApp(DbModel db);

}
