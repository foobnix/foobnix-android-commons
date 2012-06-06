package com.foobnix.android.commons.ui.list;

import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.foobnix.android.commons.util.Views;

public class SearchActivity extends Activity implements OnItemClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.search_list);

		ListView list = (ListView) findViewById(0);

		@SuppressWarnings("unchecked")
		ModelAdapter adapter = new ModelAdapter(this, R.layout.book_item, R.id.text, Collections.EMPTY_LIST);
		adapter.setNotifyOnChange(true);
		list.setAdapter(adapter);
		list.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}

	class ModelAdapter extends ArrayAdapter<Model> {

		public ModelAdapter(Context context, int layout, int textViewResourceId, List<Model> objects) {
			super(context, layout, textViewResourceId, objects);
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = super.getView(position, convertView, parent);
			final Model item = getItem(position);
			Views.text(view, R.id.text, item.getText());
			return view;
		}
	}

	class Model {
		private String text;

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}

	static class R {
		static class layout {
			public static int search_list;
			public static int book_item;
		}

		static class id {
			public static int text;
		}

	}

}
