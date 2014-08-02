package com.example.lastactivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MenuListFragment extends ListFragment {
	

	public MenuListFragment() {

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInatanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
		SampleAdapter adapter = new SampleAdapter(getActivity());
		adapter.add(new SampleItem("마이페이지", R.drawable.ic_launcher));
		adapter.add(new SampleItem("게시판", R.drawable.ic_launcher));
		adapter.add(new SampleItem("일정", R.drawable.ic_launcher));
		adapter.add(new SampleItem("갤러리", R.drawable.ic_launcher));
		adapter.add(new SampleItem("비상연락망", R.drawable.ic_launcher));
		adapter.add(new SampleItem("OB/YB", R.drawable.ic_launcher));
		adapter.add(new SampleItem("스터디", R.drawable.ic_launcher));
		setListAdapter(adapter);
	}

	private class SampleItem {
		public String tag;
		public int iconRes;

		public SampleItem(String tag, int iconRes) {
			this.tag = tag;
			this.iconRes = iconRes;
		}
	}

	public class SampleAdapter extends ArrayAdapter<SampleItem> {

		public SampleAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(
						R.layout.row, null);
			}
			ImageView icon = (ImageView) convertView
					.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView
					.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);

			return convertView;
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		Log.d("success","sucess");
		switch (position) {
		case 0:
			((BaseActivity)getActivity()).fragmentReplace(0);
//			Intent intent = new Intent(mctx, Fragment1.class);
//			startActivity(intent);
			break;
		case 1:
			((BaseActivity)getActivity()).fragmentReplace(1);
			break;
		case 2:
			((BaseActivity)getActivity()).fragmentReplace(2);
			break;
		case 4:
			((BaseActivity)getActivity()).fragmentReplace(4);
			break;
		case 5:
			((BaseActivity)getActivity()).fragmentReplace(5);
			break;
		case 6:
			((BaseActivity)getActivity()).fragmentReplace(6);
		default:
			break;

		}
		super.onListItemClick(l, v, position, id);
	}
}
