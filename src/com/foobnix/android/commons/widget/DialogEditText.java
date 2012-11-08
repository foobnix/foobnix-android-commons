package com.foobnix.android.commons.widget;

import com.google.common.base.Strings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

public abstract class DialogEditText extends AlertDialog.Builder {

	private EditText line;

	public DialogEditText(Context c, String title, String text) {
		super(c);
		setTitle(title);
		line = new EditText(c);
		if (!Strings.isNullOrEmpty(text)) {
			line.setText(text);
		}
		setView(line);

		setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.cancel();
			}
		});

		setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				String text = getText();
				if (!Strings.isNullOrEmpty(text)) {
					onEnterText(text);
				}
			}
		});
		show();
	}

	public String getText() {
		return line.getText().toString();
	}

	public abstract void onEnterText(String text);

}
