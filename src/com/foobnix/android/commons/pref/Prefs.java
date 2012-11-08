/**
 * 
 */
package com.foobnix.android.commons.pref;

import android.content.Context;

/**
 * @author iivanenko
 * 
 */
public class Prefs {

	public static StrPref Str;
	public static IntPref Int;
	public static BoolPref Bool;

	public static void initPrefs(Context context, String name) {
		Str = StrPref.getInstance(context, name);
		Int = IntPref.getInstance(context, name);
		Bool = BoolPref.getInstance(context, name);
	}

}
