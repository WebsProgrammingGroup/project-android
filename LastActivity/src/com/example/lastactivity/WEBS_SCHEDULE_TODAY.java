package com.example.lastactivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class WEBS_SCHEDULE_TODAY extends Activity implements OnClickListener,
		OnItemClickListener {

	String today, today_Month, today_Year, today_day, today_date;
	int today_position;
	ListView list;
	TextView text;
	String result;
	final String url = "http://wpg.azurewebsites.net/webs_schedule.jsp";
	Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webs_schedule_today);

		Intent intent = getIntent();
		today_Month = intent.getStringExtra("tMonth");
		today_Year = intent.getStringExtra("tYear");
		today_day = intent.getStringExtra("position");
		text = (TextView) findViewById(R.id.texttoday);
		text.setText(today_Month + "/" + today_Year + "/" + today_day);
		
		sendData();
		Log.d("d", today_Year);
		list = (ListView) findViewById(R.id.webs_schedule_today_list);
	}

	private void sendData() {
		showDialog(1);

		final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("today_Year", today_Year));
		list.add(new BasicNameValuePair("today_Month", today_Month));
		list.add(new BasicNameValuePair("today_day", today_day));
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {

				InputStream is = requestPost(url, list);
				result = streamToString(is);

				handler.post(new Runnable() {

					@Override
					public void run() {
						Log.d("result", result);
						schedule(result);
					}
				});
			}
		});
		t.start();
	}

	public InputStream requestPost(String requestUrl,
			ArrayList<NameValuePair> list) {

		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost request = new HttpPost(requestUrl);
			request.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));

			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();

			return is;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String streamToString(InputStream is) {

		StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			String str = reader.readLine();
			while (str != null) {
				buffer.append(str);
				str = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	public void schedule(String json) {
		JSONObject jobject;
		Log.d("json", json);
		List<HashMap<String, Object>> date_json =null;
		try {
			WEBS_SCHEDULE_TODAY_JSONPARSER today_json =new WEBS_SCHEDULE_TODAY_JSONPARSER();
			jobject=new JSONObject(json);
			date_json =today_json.parse(jobject);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		WEBS_SCHEDULE_TODAY_JSONPARSER t_jsonparser =new WEBS_SCHEDULE_TODAY_JSONPARSER();
		List<HashMap<String, Object>> t_json =null;
		try {
			jobject=new JSONObject(json);
			t_json = t_jsonparser.parse(jobject);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		String[] from = {"today_Date" };
		Log.i("json1", json);
		// Ids of views in listview_layout
		int[] to = { R.id.webs_schedule_list_text };

		// Instantiating an adapter to store each items
		// R.layout.listview_layout defines the layout of each item
		SimpleAdapter adapter = new SimpleAdapter(getBaseContext(),date_json, R.layout.webs_schedule_today_list_item, from, to);
		
		list.setAdapter(adapter);
		for (int i = 0; i < adapter.getCount(); i++) {
			HashMap<String, Object> hm = (HashMap<String, Object>) adapter
					.getItem(i);
			HashMap<String, Object> hmDownload = new HashMap<String, Object>();

		}
		
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}