package com.foobnix.android.commons.util;

import android.content.Context;
import android.widget.Toast;

public class Tasks {
	public static void onlineTask(Context context, Runnable task, String failMsg) {
		if (Res.isOnline(context)) {
			task.run();
		} else {
			Toast.makeText(context, failMsg, Toast.LENGTH_SHORT).show();
		}
	}

}
