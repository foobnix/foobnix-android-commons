/**
 * 
 */
package com.foobnix.android.commons.pref;

import android.content.Context;

/**
 * @author iivanenko
 *
 */
public abstract class PrefName<T> {
	protected final Context context;
	protected final String name;

	public PrefName(Context context, String name) {
		this.context = context;
		this.name = name;
	}

	public abstract T get(String key);

	public abstract void put(String key, T value);

	public abstract T get(int resId);

	public abstract void put(int resId, T value);



	

}
