/**
 * 
 */
package com.foobnix.android.commons.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;

/**
 * @author iivanenko
 * 
 */
public class Res {

	public static boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm.getActiveNetworkInfo() == null) {
			return false;
		}
		return cm.getActiveNetworkInfo().isConnected();
	}

	public static String get(Resources r, int idStr) {
		return r.getString(idStr);
	}

	public static boolean getBool(Resources r, int idStr) {
		return r.getBoolean(idStr);
	}

	public static Drawable getDr(Context c, int idStr) {
		return c.getResources().getDrawable(idStr);
	}

	public static String getStr(Context c, int idStr) {
		return c.getResources().getString(idStr);
	}

	public static boolean getBool(Context c, int idStr) {
		return c.getResources().getBoolean(idStr);
	}
}
