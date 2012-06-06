/**
 * 
 */
package com.foobnix.android.commons.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.foobnix.android.commons.util.LOG;

/**
 * @author iivanenko
 * 
 */
public class ImageUtil {
	private static FileCache fileCache;

	public ImageUtil(Context context) {
		fileCache = new FileCache(context);
	}

	public static void cleanCache() {
		if (fileCache != null) {
			fileCache.clear();
		}
	}

	public Bitmap fetchImageOriginal(String imageUrl) {
		File file = fileCache.getFile(imageUrl);
		Bitmap bitmap;
		try {
			bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
			if (bitmap != null) {
				return bitmap;
			}
		} catch (FileNotFoundException e1) {
			LOG.e(e1);
		}

		try {
			HttpGet httpRequest = new HttpGet(URI.create(imageUrl));
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(httpRequest);
			HttpEntity entity = response.getEntity();
			BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
			InputStream instream = bufHttpEntity.getContent();
			Bitmap decodeStream = BitmapFactory.decodeStream(instream);
			decodeStream.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(file));
			return decodeStream;
		} catch (Exception e) {
			return null;
		}

	}

}
