package com.example.lastactivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class WEBS_SCHEDULE_TODAY_JSONPARSER {
	public List<HashMap<String, Object>>parse (JSONObject jobject){
		JSONArray j_date =null;
		try {
			j_date=jobject.getJSONArray("today_Date");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	
	return get_dates(j_date);
}
	private List<HashMap<String, Object>>get_dates(JSONArray j_date){
		int date=j_date.length();
		List<HashMap<String, Object>>j_list=new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object>Date=null;
		for (int i = 0; i < date; i++) {
			try {
				Date =get_date((JSONObject)j_date.get(i));
				j_list.add(Date);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		return j_list;
	}
	private HashMap<String, Object>get_date(JSONObject j_date){
		HashMap<String, Object>today_Date =new HashMap<String,Object>();
		String today="";
		String today_date="";
		String today_Month="";
		String today_Year="";
		try {
			today_date=j_date.getString("today_day");
			today_Month=j_date.getString("today_Month");
			today_Year=j_date.getString("today_Year");
			today=today_Year+"/"+today_Month+"/"+today_date;
			Log.i("suc", today_Year+today_date+today_Month);
			today_Date.put("today_Date", today);
		} catch (JSONException e) {
			// TODO: handle exception
		}
		
		return today_Date;
	}
}



















