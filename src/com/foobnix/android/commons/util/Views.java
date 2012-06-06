package com.foobnix.android.commons.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Views {

	public static void gone(Activity activity, int... resIds) {
		for (int resId : resIds) {
			View viewById = activity.findViewById(resId);
			if (viewById != null) {
				viewById.setVisibility(View.GONE);
			}
		}
	}

	public static boolean checked(Activity activity, int resId, Boolean state) {
		return checked(activity, resId, state, null);
	}

	public static boolean checked(Activity activity, int resId, Boolean state, OnCheckedChangeListener listener) {
		CheckBox checkbox = (CheckBox) activity.findViewById(resId);
		boolean res = checkbox.isChecked();
		if (state != null) {
			checkbox.setChecked(state);
		}
		if (listener != null) {
			checkbox.setOnCheckedChangeListener(listener);
		}
		return res;
	}

	public static void enabled(Activity activity, int resId, boolean flag) {
		activity.findViewById(resId).setEnabled(flag);
	}

	public static void disable(Activity activity, int resId) {
		activity.findViewById(resId).setEnabled(false);
	}

	public static void gone(View view, int... resIds) {
		for (int resId : resIds) {
			view.findViewById(resId).setVisibility(View.GONE);
		}
	}

	public static ImageView image(View view, int resId, int imgId) {
		ImageView img = (ImageView) view.findViewById(resId);
		img.setImageResource(imgId);
		return img;
	}

	public static ImageView image(Activity view, int resId, int imgId) {
		ImageView img = (ImageView) view.findViewById(resId);
		img.setImageResource(imgId);
		return img;
	}

	public static ImageView image(View view, int resId, Bitmap bm) {
		ImageView img = (ImageView) view.findViewById(resId);
		img.setImageBitmap(bm);
		return img;
	}

	public static ImageView image(Activity view, int resId, Bitmap bm) {
		ImageView img = (ImageView) view.findViewById(resId);
		img.setImageBitmap(bm);
		return img;
	}

	public static void invisible(Activity activity, int resId) {
		activity.findViewById(resId).setVisibility(View.INVISIBLE);
	}

	public static void invisible(View activity, int resId) {
		activity.findViewById(resId).setVisibility(View.INVISIBLE);
	}

	public static View visible(Activity activity, int resId) {
		View view = activity.findViewById(resId);
		view.setVisibility(View.VISIBLE);
		return view;
	}

	public static void visible(View view, int resId) {
		view.findViewById(resId).setVisibility(View.VISIBLE);
	}

	public static void selected(Activity activity, int resId, boolean flag) {
		activity.findViewById(resId).setSelected(flag);
	}

	public static TextView text(final Activity c, int id, final String text) {
		return bindText((TextView) c.findViewById(id), text);
	}

	public static TextView text(final View c, int id, final String text) {
		return bindText((TextView) c.findViewById(id), text);
	}

	public static TextView get(final Activity c, int resID) {
		return (TextView) c.findViewById(resID);
	}

	public static TextView bindText(TextView textView, final String text) {
		if (text == null) {
			textView.setText("");
		} else {
			if (text.contains("<")) {
				textView.setText(Html.fromHtml(text));
			} else {
				textView.setText(text);
			}
		}
		return textView;
	}

	public static View onClickActivity(final Activity c, int id, final Class<?> clazz) {
		View button = c.findViewById(id);
		button.setOnClickListener(onClickActivity(c, clazz));
		return button;
	}

	public static OnClickListener onClickActivity(final Activity c, final Class<?> clazz) {
		return new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				c.startActivity(new Intent(c, clazz));
			}
		};
	}

	public static OnClickListener onClickActivity(final Activity c, final Class<?> clazz, final int flag) {
		return new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(c, clazz);
				intent.setFlags(flag);
				c.startActivity(intent);
			}
		};
	}

	public static View onClick(Activity context, int id, View.OnClickListener onClick) {
		View button = context.findViewById(id);
		if (button == null) {
			return null;
		}
		button.setOnClickListener(onClick);
		return button;
	}

	public static View onClick(View parent, int id, View.OnClickListener onClick) {
		View button = parent.findViewById(id);
		button.setOnClickListener(onClick);
		return button;
	}

	public static View onClick(View parent, int id, View.OnClickListener onClick, Object tag) {
		View button = parent.findViewById(id);
		button.setOnClickListener(onClick);
		button.setTag(tag);
		return button;
	}

}