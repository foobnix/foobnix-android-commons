package com.foobnix.android.commons.image2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.foobnix.android.commons.util.LOG;
import com.foobnix.android.commons.util.Res;

public class ImageCache {
	private File cacheDir;
	private Context context;

	Map<String, Bitmap> cache = Collections.synchronizedMap(new LruCache<String, Bitmap>(20));

	public ImageCache(Context context) {
		this.context = context;
		initCacheDir();
	}

	public Bitmap getBitmap(String url, int width, int height) {
		if (cache.containsKey(url)) {
			LOG.d("Get from Cache", url);
			return cache.get(url);
		}

		Bitmap bitmapFromFile = getBitmapFromFile(url);
		if (bitmapFromFile != null) {
			LOG.d("Get from SDCArd", url);
			cache.put(url, bitmapFromFile);
			return bitmapFromFile;
		}

		Bitmap bm = null;
		try {
			if (Res.isOnline(context)) {
				bm = download(url, width, height);
				if (bm != null) {
					cache.put(url, bm);
				}
				return bm;
			}
		} catch (IOException e) {
			LOG.e(e);
			return null;
		}

		return bm;
	}

	public Uri createTempUri(Bitmap png) {
		File current = new File(cacheDir, "current.png");
		try {
			FileOutputStream out = new FileOutputStream(current);
			png.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.close();
		} catch (IOException e) {
			LOG.e(e);
			return null;
		}
		return Uri.fromFile(current);
	}

	public File initCacheDir() {
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "cache");
		} else {
			cacheDir = context.getCacheDir();
		}

		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
		return cacheDir;
	}

	public synchronized Bitmap download(String url, int maxWidth, int height) throws ClientProtocolException,
			IOException {
		initCacheDir();
		HttpGet httpGet = new HttpGet(url);
		File urlFile = getImageFile(url);
		LOG.d("Start dowload to file", url, urlFile.getPath());
		FileOutputStream out = new FileOutputStream(urlFile);
		HttpEntity entity = new DefaultHttpClient().execute(httpGet).getEntity();

		Bitmap decodeStream = BitmapFactory.decodeStream(entity.getContent());
		if (decodeStream != null) {
			if (decodeStream.getWidth() > maxWidth) {
				Bitmap result = Bitmap.createScaledBitmap(decodeStream, maxWidth, height, false);
				result.compress(CompressFormat.PNG, 100, out);
			} else {
				decodeStream.compress(CompressFormat.PNG, 100, out);
			}
		} else {
			out.close();
			urlFile.delete();
			return null;
		}

		out.close();
		return BitmapFactory.decodeFile(urlFile.getPath());
	}

	public Bitmap getBitmapFromFile(String url) {
		if (url == null) {
			return null;
		}
		File file = getImageFile(url);
		if (file != null && file.exists() && file.length() != 0) {
			return BitmapFactory.decodeFile(file.getPath());
		}
		return null;
	}

	private File getImageFile(String url) {
		return new File(cacheDir, String.format("f_%s.png", url.hashCode()));
	}

	public void clearAll() {
		initCacheDir();
		if (cacheDir == null) {
			return;
		}
		for (File f : cacheDir.listFiles()) {
			f.delete();
		}
	}

	public void clearOverSize(int size) {
		initCacheDir();
		if (cacheDir == null) {
			return;
		}
		File[] listFiles = cacheDir.listFiles();
		Arrays.sort(listFiles, comparator);
		long count = 0;
		for (File file : listFiles) {
			count += file.length();
			if (count > size) {
				LOG.d("Delete", file.getName());
				file.delete();

			}
		}
	}

	private final static FilesComparator comparator = new FilesComparator();

	final static class FilesComparator implements Comparator<File> {
		@Override
		public int compare(File lhs, File rhs) {
			return (int) (lhs.lastModified() - rhs.lastModified());
		}
	}

}
