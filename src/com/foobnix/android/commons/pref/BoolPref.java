/**
 * 
 */
package com.foobnix.android.commons.pref;

import android.content.Context;
import android.content.SharedPreferences;

import com.foobnix.android.commons.util.LOG;

/**
 * @author iivanenko
 * 
 */
public class BoolPref extends PrefName<Boolean> {

	private static BoolPref instance;

	public static BoolPref getInstance(Context context, String name) {
		if (instance == null) {
			return new BoolPref(context, name);
		}
		return instance;

	}

	private BoolPref(Context context, String name) {
		super(context, name);
	}

	@Override
	public Boolean get(String key) {
		SharedPreferences settings = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		boolean value = settings.getBoolean(key, false);
		LOG.d("StrPref GET", key, value);
		return value;
	}

	@Override
	public void put(String key, Boolean value) {
		SharedPreferences settings = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(key, value);
		editor.commit();
		LOG.d("StrPref PUT", key, value);
	}

	@Override
	public Boolean get(int resid) {
		return false;
	}

	@Override
	public void put(int resid, Boolean value) {
	}

}
