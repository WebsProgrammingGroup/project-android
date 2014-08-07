package com.example.lastactivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Main_menu extends BaseActivity implements OnClickListener {
	Button btn1;
	ImageView imgv;
	FileInputStream mFileInputStream = null;
	URL connectUrl = null;
	String lineEnd = "\r\n";
	String twoHyphens = "--", boundary = "*****";
	String imgurl = "http://wpg.azurewebsites.net/upload/test";
	Bitmap bmImg;
	back task;
	ListView lv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		task = new back();
		imgv = (ImageView) findViewById(R.id.imageV);
		initial();

		String strurl = "http://wpg.azurewebsites.net/webs_notice_List.jsp?";

		DownloadTask downloadTask = new DownloadTask();

		downloadTask.execute(strurl);
		task.execute(imgurl);
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

	}

	private void initial() {
		// TODO Auto-generated method stub
		btn1 = (Button) findViewById(R.id.main_btn);
		btn1.setOnClickListener(this);
		lv = (ListView) findViewById(R.id.main_Listview);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.main_btn:
			Intent intent = new Intent();
			intent.setAction(intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
			startActivityForResult(intent, 0);

			break;
		}
	}

	// 여기까지는 문제 없음
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

	private class back extends AsyncTask<String, Integer, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... urls) {
			// TODO Auto-generated method stub
			try {
				URL myFileUrl = new URL(urls[0]);
				HttpURLConnection conn = (HttpURLConnection) myFileUrl
						.openConnection();
				conn.setDoInput(true);
				conn.connect();

				InputStream is = conn.getInputStream();

				bmImg = BitmapFactory.decodeStream(is);

			} catch (IOException e) {
				e.printStackTrace();
			}
			return bmImg;
		}

		protected void onPostExecute(Bitmap img) {
			imgv.setImageBitmap(bmImg);
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent)

	{

		super.onActivityResult(requestCode, resultCode, intent);

		Uri selPhotoUri = intent.getData();

		try {
			Bitmap selPhoto = Images.Media.getBitmap(getContentResolver(),
					selPhotoUri);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String urlString = "http://wpg.azurewebsites.net/webs_image_upload.jsp";

		// 절대경로를 획득한다!!! 중요~

		Cursor c = getContentResolver().query(
				Uri.parse(selPhotoUri.toString()), null, null, null, null);

		c.moveToNext();

		String absolutePath = c.getString(c
				.getColumnIndex(MediaStore.MediaColumns.DATA));
		Log.d("aURI", absolutePath);
		System.out.println("절대 경로 = " + absolutePath);

		// 파일 업로드 시작!

		DoFileUpload(urlString, absolutePath);

		String path = intent.getDataString();

		Toast.makeText(this, path, Toast.LENGTH_LONG).show();

	}// onActivityResult

	public void DoFileUpload(String apiUrl, String absolutePath) {

		HttpFileUpload(apiUrl, "", absolutePath);

	}// DoFileUpload

	public void HttpFileUpload(String urlString, String params, String fileName) {
		try {

			mFileInputStream = new FileInputStream(fileName);
			connectUrl = new URL(urlString);
			Log.d("Test", "mFileInputStream  is " + mFileInputStream);

			// open connection
			HttpURLConnection conn = (HttpURLConnection) connectUrl
					.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);

			// write data
			
			//파일 이름을 놓가 ->filename
			fileName = "test";
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\""
					+ fileName + "\"" + lineEnd);
			dos.writeBytes(lineEnd);

			int bytesAvailable = mFileInputStream.available();
			int maxBufferSize = 1024;
			int bufferSize = Math.min(bytesAvailable, maxBufferSize);

			byte[] buffer = new byte[bufferSize];
			int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);

			Log.d("Test", "image byte is " + bytesRead);

			// read image
			while (bytesRead > 0) {
				dos.write(buffer, 0, bufferSize);
				bytesAvailable = mFileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
			}

			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

			// close streams
			Log.e("Test", "File is written");
			mFileInputStream.close();
			dos.flush(); // finish upload...

			// get response

			// 여기서 문제가 생김
			int ch;
			InputStream is = conn.getInputStream();
			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
			String s = b.toString();
			Log.e("Test", "result = " + s);
			dos.close();

		} catch (Exception e) {
			Log.d("Test", "exception " + e.getMessage());
			// TODO: handle exception
		}
	}
}