package com.foobnix.android.commons.gson;

import java.lang.reflect.Type;
import java.util.Map;

import com.foobnix.android.commons.util.LOG;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @author iivanenko
 * @param <T>
 * 
 */
public class Gsons {
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYY_l_MM_l_DD = "yyyy/MM/dd";
	private final static ExcludeServerTagStrategy exludeServer = new ExcludeServerTagStrategy();
	private final static ExcludeAllTagStrategy exludeAll = new ExcludeAllTagStrategy();

	public static Map<String, String> toMapModel(String json) {
		return Gsons.toSimpleModel(json, new TypeToken<Map<String, String>>() {}.getType());
	}

	public static <T> T toModel(String json, Class<T> clazz) {
		Gson gson = createGSON(true);
		LOG.d("Gson to model: ", json, clazz);
		T fromJson = null;
		try {
			fromJson = gson.fromJson(json, clazz);
		} catch (Exception e) {
			LOG.e(e);
			try {
				fromJson = clazz.newInstance();
			} catch (Exception e1) {
				LOG.e(e);
				new RuntimeException("GsonConverter instance error");
			}
		}
		return fromJson;
	}

	public static <T> T toSimpleModel(String json, Type type) {
		Gson gson = createGSON(true);

		LOG.d("Gson to model: ", json, type);
		T fromJson = null;
		try {
			fromJson = gson.fromJson(json, type);
		} catch (Exception e) {
			fromJson = null;
			LOG.e(e);
		}
		return fromJson;
	}

	public static <T> T toSimpleModel(String json, Class<T> clazz) {
		Gson gson = createGSON(true);

		LOG.d("Gson to model: ", json, clazz);
		T fromJson = null;
		try {
			fromJson = gson.fromJson(json, clazz);
		} catch (RuntimeException e) {
			fromJson = null;
			LOG.e(e);
		} catch (Exception e) {
			fromJson = null;
			LOG.e(e);
		}
		return fromJson;
	}

	public static String toJson(Object model, Type type) {
		Gson gson = createGSON(true);
		String json = gson.toJson(model, type);
		LOG.d("Model to gson: ", json, model);
		return json;
	}

	public static String toJson(Object model, Class<?> clazz) {
		Gson gson = createGSON(true);
		String json = gson.toJson(model, clazz);
		LOG.d("Model to gson: ", json, model);
		return json;
	}

	public static String toJsonNoExclude(Object model, Type type) {
		Gson gson = createGSON(false);
		String json = gson.toJson(model, type);
		LOG.d("Model to gson: ", json, model);
		return json;
	}

	private static Gson createGSON(boolean exclude) {
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat(YYYY_MM_DD_HH_MM_SS);
		if (exclude) {
			builder.setExclusionStrategies(exludeServer, exludeAll);
		} else {
			builder.setExclusionStrategies(exludeAll);
		}
		Gson gson = builder.create();
		return gson;
	}
}
