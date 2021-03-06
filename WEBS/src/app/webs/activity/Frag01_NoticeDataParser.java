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
import app.webs.activity.Frag01_MyPage.NoticeAdapter;
import app.webs.util.*;

public class Frag01_NoticeDataParser extends Thread{
	private static String DATA_PARSER_DEBUG_TAG = "DATA_PARSER";
	
	private Context mCtx;
	private ArrayList<NameValuePair> ParamList;
	private NoticeAdapter Adapter;
	private LoadingDialog mLoadingDialog;
	
	//Constructors
	public Frag01_NoticeDataParser() {
	}
	public Frag01_NoticeDataParser(NoticeAdapter adapter, Context ctx) {
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
			is = RequestPost(StaticVar.NoticeUrl);
		}else{
			is = RequestPost(StaticVar.NoticeUrl, ParamList);
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
			JSONArray jArr = json.getJSONArray("noticeList");
			
			StaticVar.NoticeBoardWholeData.clear();
			
			for(int i=0 ; i<jArr.length() ; i++){
				BoardData data = new BoardData();
				try {
					JSONObject jObj = jArr.getJSONObject(i);
					data.BoardIdx = jObj.getInt("IDX");
					data.WriterIdx = jObj.getInt("UserID");
					data.Writer = jObj.getString("UserName");
					data.Title = jObj.getString("Title");
					data.Contents = jObj.getString("Contents");
					data.Date = jObj.getString("Date");
					data.Comment = jObj.getInt("Comment");
					
					StaticVar.NoticeBoardWholeData.add(data);
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
			for(int i=0 ; i<5 ; i++){
				if(StaticVar.NoticeBoardWholeData.size() > StaticVar.NoticeBoardData.size()){
					StaticVar.NoticeBoardData.add(StaticVar.NoticeBoardWholeData.get(Frag01_MyPage.BoardDataCount++));
					Adapter.notifyDataSetChanged();
				}
			}
			mLoadingDialog.DialogDismiss();
		}
	};
}
class BoardData{
	public int BoardIdx;
	public int WriterIdx;
	public String Writer;
	public String Title;
	public String Contents;
	public String Date;
	public int Comment;
}

