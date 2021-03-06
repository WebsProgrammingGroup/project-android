package app.webs.activity;

import java.io.*;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.entity.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.json.*;


import android.os.*;
import android.util.*;
import app.webs.activity.*;
import app.webs.activity.Frag05_Schedule.DayScheduleAdapter;

public class Frag05_SchduleDataParser extends Thread{
	private static String DATA_PARSER_DEBUG_TAG = "DATA_PARSER";
	private ArrayList<NameValuePair> ParamList;
	private ArrayList<DaySchedule> ArrList;
	private DayScheduleAdapter Adapter;
	
	//Constructors
	public Frag05_SchduleDataParser() {
	}
	public Frag05_SchduleDataParser(DayScheduleAdapter adapter, ArrayList<DaySchedule> arrList) {
		ArrList = arrList;
		Adapter = adapter;
	}
	
	//Parameter Setting Function
	public void setParamList(ArrayList<NameValuePair> paramList){
		ParamList = paramList;
	}
	
	public void run() {
		InputStream is = RequestPost(StaticVar.ScheduleUrl, ParamList);
		String RecvString = StreamToString(is);
		JsonParser(RecvString);
		Log.e(DATA_PARSER_DEBUG_TAG, RecvString);
	}
	
	public InputStream RequestPost(String requestUrl, ArrayList<NameValuePair> paramList) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost request = new HttpPost(requestUrl);
			request.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));			
			HttpResponse response = client.execute(request);
			
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			return is;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String StreamToString(InputStream is) {
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
	
	private void JsonParser(String targetString)
	{
		try {
			JSONObject json = new JSONObject(targetString);														
			JSONArray jArr = json.getJSONArray("today_Date");
			ArrList.clear();
			
			for(int i=0 ; i<jArr.length() ; i++){
				DaySchedule data = new DaySchedule();
				try {
					JSONObject jObj = jArr.getJSONObject(i);
					data.SchduleType = jObj.getString("Type");
					data.Title = jObj.getString("Title");
					data.Contents = jObj.getString("Contents");
					ArrList.add(data);
					Log.e(DATA_PARSER_DEBUG_TAG, ""+i);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			UiHandler.sendEmptyMessage(0);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	Handler UiHandler = new Handler(){
		public void handleMessage(Message msg) {
			Adapter.notifyDataSetChanged();			
		}
	};
}

