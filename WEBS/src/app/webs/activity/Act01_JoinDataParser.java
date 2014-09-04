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

public class Act01_JoinDataParser extends Thread{
	private Context mCtx;
	private Handler JoinHandler;
	
	private static String DATA_PARSER_DEBUG_TAG = "DATA_PARSER";
	private ArrayList<NameValuePair> ParamList;
	
	//Constructors
	public Act01_JoinDataParser(Context Ctx, Handler joinHandler) {
		mCtx = Ctx;
		JoinHandler = joinHandler;
	}
	
	//Parameter Setting Function
	public void setParamList(ArrayList<NameValuePair> paramList){
		ParamList = paramList;
	}
	
	public void run() {
		InputStream is = RequestPost(StaticVar.JoinUrl, ParamList);
		String RecvString = StreamToString(is);
		
		if(RecvString.matches("(.*)0(.*)")){				//fail
			JoinHandler.sendEmptyMessage(0);
		}else if(RecvString.matches("(.*)1(.*)")){		//succ
			JoinHandler.sendEmptyMessage(1);
		}else if(RecvString.matches("(.*)2(.*)")){		//club pw
			JoinHandler.sendEmptyMessage(2);
		}else{									//fail
			JoinHandler.sendEmptyMessage(0);
		}
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
}
class JoinData{
	public String Name;
	public String Phone;
	public String ID;
	public String Major;
	public String Gender;
	public String Grd;
	public String Birth;
	public String ClubPw;
}
