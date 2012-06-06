package com.foobnix.android.commons.image;

import java.io.File;

import android.content.Context;

import com.google.common.base.Strings;

public class FileCache {

	private File cacheDir;

	public FileCache(Context context) {
		// Find the dir to save cached images
		if (context == null || android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "Podorozhniki");
		} else {
			cacheDir = context.getCacheDir();
		}
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
	}

	public File getFile(String url) {
		if (Strings.isNullOrEmpty(url)) {
			url = "empty";
		}
		String filename = String.valueOf(url.hashCode());
		File f = new File(cacheDir, filename);
		return f;

	}

	public void clear() {
		if (cacheDir == null) {
			return;
		}
		File[] files = cacheDir.listFiles();
		for (File f : files)
			f.delete();
	}

}