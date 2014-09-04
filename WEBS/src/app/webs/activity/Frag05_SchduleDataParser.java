package app.webs.Activity;

import java.io.*;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.entity.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.json.*;

import android.content.*;
import android.os.*;
import android.util.*;
import app.webs.Activity.Frag05_Schedule.ScheduleDataAdapter;
import app.webs.DataType.*;
import app.webs.Util.*;

public class Frag05_SchduleDataParser extends Thread{
	private static String DATA_PARSER_DEBUG_TAG = "DATA_PARSER";
	private Context mCtx;
	private ArrayList<NameValuePair> ParamList;
	private ScheduleDataAdapter Adapter;
	private LoadingDialog mLoadingDialog;
	private Handler mHandler;
	
	//Constructors
	public Frag05_SchduleDataParser() {
	}
	public Frag05_SchduleDataParser(ScheduleDataAdapter adapter, Context ctx, Handler handler) {
		Adapter = adapter;
		mCtx = ctx;
		mHandler = handler;
	}
	
	//Parameter Setting Function
	public void setParamList(ArrayList<NameValuePair> paramList){
		ParamList = paramList;
	}
	
	public void run() {
		//Handler for show Dialog
		DialogHandler.sendEmptyMessage(0);
		
		InputStream is = RequestPost(StaticVar.ScheduleUrl, ParamList);
		String RecvString = StreamToString(is);
		JsonParser(RecvString);
		Log.e(DATA_PARSER_DEBUG_TAG, RecvString);
		mLoadingDialog.DialogDismiss();
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
			
			StaticVar.SchduleWholeData.clear();
			
			for(int i=0 ; i<jArr.length() ; i++){
				ScheduleData data = new ScheduleData();
				try {
					JSONObject jObj = jArr.getJSONObject(i);
					data.ID = jObj.getInt("IDX");
					data.SchduleType = jObj.getString("Type");
					data.Title = jObj.getString("Title");
					data.Contents = jObj.getString("Contents");
					
					StaticVar.SchduleWholeData.add(data);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			mHandler.sendEmptyMessage(0);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	Handler DialogHandler = new Handler(){
		public void handleMessage(Message msg) {
			mLoadingDialog = new LoadingDialog(mCtx);
			mLoadingDialog.DialogShow();
		}
	};
}

