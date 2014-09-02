package app.webs.activity;

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
import app.webs.activity.*;
import app.webs.activity.Frag07_Contacts.ContactsListAdapter;
import app.webs.util.*;

public class Frag07_ContactsDataParser extends Thread{
	private static String DATA_PARSER_DEBUG_TAG = "DATA_PARSER";
	
	private Context mCtx;
	private ArrayList<NameValuePair> ParamList;
	private ContactsListAdapter Adapter;
	private LoadingDialog mLoadingDialog;
	
	//Constructors
	public Frag07_ContactsDataParser() {
	}
	public Frag07_ContactsDataParser(ContactsListAdapter adapter, Context ctx) {
		Adapter = adapter;
		mCtx = ctx;
	}
	
	//Parameter Setting Function
	public void setParamList(ArrayList<NameValuePair> paramList){
		ParamList = paramList;
	}
	
	public void run() {
		//Handler for show Dialog
		DialogHandler.sendEmptyMessage(0);
		
		InputStream is;
		if(ParamList == null){
			is = RequestPost(StaticVar.ContactsUrl);
		}else{
			is = RequestPost(StaticVar.ContactsUrl, ParamList);
		}
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
	public InputStream RequestPost(String requestUrl) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost request = new HttpPost(requestUrl);		
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
			JSONArray jArr = json.getJSONArray("member");
			
			StaticVar.mContactWholeData.clear();
			
			for(int i=0 ; i<jArr.length() ; i++){
				ContactData data = new ContactData();
				try {
					JSONObject jObj = jArr.getJSONObject(i);
					data.Name = jObj.getString("Name");
					data.Phone = jObj.getString("Phone");
					data.Photo = jObj.getString("Photo");
					data.ID = jObj.getString("ID");
					data.Major = jObj.getString("Major");
					data.Gender = jObj.getString("Gender");
					StaticVar.mContactWholeData.add(data);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			UiHandler.sendEmptyMessage(0);
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
	
	Handler UiHandler = new Handler(){
		public void handleMessage(Message msg) {
			Adapter.notifyDataSetChanged();	
			mLoadingDialog.DialogDismiss();
		}
	};
}
