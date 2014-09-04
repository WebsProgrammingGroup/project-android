package app.webs.Activity;

import java.io.*;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.entity.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.json.*;

import com.webs.app.*;


import android.content.*;
import android.os.*;
import android.util.*;
import app.webs.Activity.*;
import app.webs.DataType.*;

public class Act00_LoginDataParser extends Thread{
	private Context mCtx;
	private Handler LoginHandler;
	
	private static String DATA_PARSER_DEBUG_TAG = "DATA_PARSER";
	private ArrayList<NameValuePair> ParamList;
	
	//Constructors
	public Act00_LoginDataParser(Context Ctx, Handler loginHandler) {
		mCtx = Ctx;
		LoginHandler = loginHandler;
	}
	
	//Parameter Setting Function
	public void setParamList(ArrayList<NameValuePair> paramList){
		ParamList = paramList;
	}
	
	public void run() {
		InputStream is = RequestPost(StaticVar.LoginUrl, ParamList);
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
			JSONArray jArr = json.getJSONArray("MyInfo");
			int result = 0; // 1 = Login Success , 0 = Login Fail
			
			for(int i=0 ; i<jArr.length() ; i++){
				LoginData data = new LoginData();
				try {
					JSONObject jObj = jArr.getJSONObject(i);
					data.Name = jObj.getString("Name");
					data.Phone = jObj.getString("Phone");
					data.Photo = jObj.getString("Photo");
					data.ID = jObj.getString("ID");
					data.Major = jObj.getString("Major");
					data.Gender = jObj.getString("Gender");
					data.Fees = jObj.getString("Fees");
					data.Grd = jObj.getString("Grd");
					data.Birth = jObj.getString("Birth");
					data.Level = jObj.getString("Level");
					
					if(data.Level.equals("0")){
						result = 0;
					}else{
						StaticVar.mLoginData = data;
						result = 1;
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			LoginHandler.sendEmptyMessage(result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
}

