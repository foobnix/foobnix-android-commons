package com.foobnix.android.commons.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CustomWebView extends WebView {

	public CustomWebView(Context context, AttributeSet attrs) {
		super(context, attrs);

		getSettings().setJavaScriptEnabled(true);
		getSettings().setBuiltInZoomControls(true);
		requestFocus();
		setWebViewClient(new WebClient());
		setClickable(true);

		setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_UP:
					if (!v.hasFocus()) {
						v.requestFocus();
					}
					break;
				}
				return false;
			}
		});
	}

	public CustomWebView(Context context) {
		this(context, null);
	}

	class WebClient extends WebViewClient {
	}

}
