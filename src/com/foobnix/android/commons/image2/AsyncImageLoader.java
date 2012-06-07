package com.foobnix.android.commons.image2;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

public class AsyncImageLoader {
	Context context;
	private ImageCache imageCache;
	public AsyncImageLoader(Context context) {
		this.context = context;
		imageCache = new ImageCache(context);
	}

	public void addToQueue(final String url, final AsyncResponse response) {
		new AsyncTask<Void, Void, Bitmap>() {
			@Override
			protected Bitmap doInBackground(Void... params) {
				return imageCache.getBitmap(url, 300, 300);
			}

			@Override
			protected void onPostExecute(Bitmap result) {
				response.onSuccess(result);
			};
			
		}.execute();
	}



	public interface AsyncResponse {
		public void onSuccess(Bitmap bitmap);
	}

}
