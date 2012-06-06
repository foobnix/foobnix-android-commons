/**
 * 
 */
package com.foobnix.android.commons.pref;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author iivanenko
 * 
 */

public class IntPref extends PrefName<Integer> {

	private static IntPref instance;

	public static IntPref getInstance(Context context, String name) {
		if (instance == null) {
			return new IntPref(context, name);
		}
		return instance;
	}

	public IntPref(Context context, String name) {
		super(context, name);
	}

	@Override
	public Integer get(String key) {
		SharedPreferences settings = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return settings.getInt(key, 0);
    }

	@Override
	public void put(String key, Integer value) {
		SharedPreferences settings = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();
    }

	@Override
	public Integer get(int resid) {
		return get(context.getResources().getResourceEntryName(resid));

	}

	@Override
	public void put(int resid, Integer value) {
		put(context.getResources().getResourceEntryName(resid), value);

	}

}
