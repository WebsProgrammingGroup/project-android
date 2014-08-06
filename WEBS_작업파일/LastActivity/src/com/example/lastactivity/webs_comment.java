package com.example.lastactivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class webs_comment extends Activity implements OnClickListener{
	EditText edt;
	Button btn;
	String url="http://wpg.azurewebsites.net/webs_comment.jsp";
	String result;
	Handler handler =new Handler();
	ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webs_comment);
		edt =(EditText)findViewById(R.id.edit_comment);
		btn =(Button)findViewById(R.id.finsh_button);
		btn.setOnClickListener(this);
		lv =(ListView)findViewById(R.id.lv_commet);
		
		list_comment();
	}

	private void list_comment(){
		
		String strurl ="http://wpg.azurewebsites.net/webs_comment_list.jsp";
		
		DownloadTask downloadTask = new DownloadTask();

		downloadTask.execute(strurl);

	}
	private String downloadUrl(String strurl) throws IOException {
		String data = "";
		InputStream iStream = null;
		try {
			URL url = new URL(strurl);

			// Creating an http connection to communicate with url
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();

			// Connecting to url
			urlConnection.connect();

			// Reading data from url
			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();

			br.close();

		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
		}

		return data;
	}

	private class DownloadTask extends AsyncTask<String, Integer, String> {
		String data = null;

		@Override
		protected String doInBackground(String... url) {
			// TODO Auto-generated method stub
			try {
				data = downloadUrl(url[0]);
				Log.d("url", data);
			} catch (Exception e) {
				// TODO: handle exception
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {

			// The parsing of the xml data is done in a non-ui thread
			ListViewLoaderTask listViewLoaderTask = new ListViewLoaderTask();

			// Start parsing xml data
			listViewLoaderTask.execute(result);

		}

	}

	//
	private class ListViewLoaderTask extends
			AsyncTask<String, Void, SimpleAdapter> {
		JSONObject jObject;

		// Doing the parsing of xml data in a non-ui thread
		@Override
		protected SimpleAdapter doInBackground(String... strJson) {
			try {
				jObject = new JSONObject(strJson[0]);
				WEBS_Board_NOTICE_JSONPARSER testJsonParser = new WEBS_Board_NOTICE_JSONPARSER();
				testJsonParser.parse(jObject);
				Log.i("suss", "here");
			} catch (Exception e) {
				Log.d("JSON Exception1", e.toString());
			}

			// Instantiating json parser class
			WEBS_Board_NOTICE_JSONPARSER testJsonParser = new WEBS_Board_NOTICE_JSONPARSER();
			// A list object to store the parsed countries list
			List<HashMap<String, Object>> testjson = null;
			try {
				// Getting the parsed data as a List construct
				testjson = testJsonParser.parse(jObject);
				Log.i("here4", "success");
			} catch (Exception e) {
				Log.d("Exception", e.toString());
			}

			// Keys used in Hashmap
			String[] from = { "notice", "id" };

			// Ids of views in listview_layout
			int[] to = { R.id.webs_board_notice_list };

			// Instantiating an adapter to store each items
			// R.layout.listview_layout defines the layout of each item
			Collections.reverse(testjson);
			SimpleAdapter adapter = new SimpleAdapter(getBaseContext(),
					testjson, R.layout.webs_board_notice_list_item, from, to);
			
			return adapter;
		}

		@Override
		protected void onPostExecute(SimpleAdapter adapter) {
			// TODO Auto-generated method stub
			lv.setAdapter(adapter);
			for (int i = 0; i < adapter.getCount(); i++) {
				HashMap<String, Object> hm = (HashMap<String, Object>) adapter
						.getItem(i);
				HashMap<String, Object> hmDownload = new HashMap<String, Object>();
			}
			
		}
	}
	private void sendData(String comment) {
		showDialog(1);
		Log.d("here", "no problem");
		final ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("comment", comment));
		Log.d("here1", "no problem");

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {

				InputStream is = requestPost(url, list);
				result = streamToString(is);
				Log.d("result", result);
				handler.post(new Runnable() {

					@Override
					public void run() {
						
						
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
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String x;
		x=edt.getText().toString();
		sendData(x);
		finish();
	}

}
