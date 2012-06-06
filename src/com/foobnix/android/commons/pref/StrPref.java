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
public class StrPref extends PrefName<String> {

	private static StrPref instance;

	public static StrPref getInstance(Context context, String name) {
		if (instance == null) {
			return new StrPref(context, name);
		}
		return instance;

	}

	private StrPref(Context context, String name) {
		super(context, name);
	}

	@Override
	public String get(String key) {
		SharedPreferences settings = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		String value = settings.getString(key, "");
		LOG.d("StrPref GET", key, value);
		return value;
	}

	@Override
	public void put(String key, String value) {
		SharedPreferences settings = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);
		editor.commit();
		LOG.d("StrPref PUT", key, value);
	}

	@Override
	public String get(int resid) {
		return get(context.getResources().getResourceEntryName(resid));

	}

	@Override
	public void put(int resid, String value) {
		put(context.getResources().getResourceEntryName(resid), value);

	}

}
