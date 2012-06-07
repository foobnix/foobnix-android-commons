package com.foobnix.android.commons.image2;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;

public class AsyncImageLoader {
	Context context;
	private ImageCache imageCache;
	private Queue<QueueTask> queue = new ConcurrentLinkedQueue<QueueTask>();
	AsyncTask asyncTask;

	public AsyncImageLoader(Context context) {
		this.context = context;
		imageCache = new ImageCache(context);

	}

	public void addToQueue(String url, AsyncResponse response) {
		queue.add(new QueueTask(url, response));
		if (asyncTask == null || asyncTask.getStatus() == Status.FINISHED) {
			asyncTask = new AsyncTask<Void, Void, Void>() {
				@Override
				protected Void doInBackground(Void... params) {
					while (!queue.isEmpty()) {
						final QueueTask poll = queue.poll();
						final Bitmap bitmap = imageCache.getBitmap(poll.getUrl(), 300, 300);
						((Activity) context).runOnUiThread(new Runnable() {
							@Override
							public void run() {
								poll.getResponse().onSuccess(bitmap);
							}
						});

					}
					return null;
				}

			}.execute();
		}
	}

	class QueueTask {
		private String url;
		private AsyncResponse response;

		public QueueTask(String url, AsyncResponse response) {
			this.url = url;
			this.response = response;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public AsyncResponse getResponse() {
			return response;
		}

		public void setResponse(AsyncResponse response) {
			this.response = response;
		}

	}

	public interface AsyncResponse {
		public void onSuccess(Bitmap bitmap);
	}

}
