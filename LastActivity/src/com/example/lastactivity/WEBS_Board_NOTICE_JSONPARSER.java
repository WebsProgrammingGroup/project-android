package com.example.lastactivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class WEBS_Board_NOTICE_JSONPARSER {
	public List<HashMap<String, Object>> parse(JSONObject jObject) {
		
		JSONArray j_webs_board_notice = null;
		try {
			j_webs_board_notice = jObject
					.getJSONArray("noticeList");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return getwebs_board_notices(j_webs_board_notice);
	}

	private List<HashMap<String, Object>> getwebs_board_notices(
			JSONArray j_webs_board_notice) {
		int notice_count = j_webs_board_notice.length();
		List<HashMap<String, Object>> notice_List = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> notice = null;
		
		for (int i = 0; i < notice_count; i++) {
			try {
				notice = getwebs_board_notice((JSONObject) j_webs_board_notice
						.get(i));
				notice_List.add(notice);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return notice_List;
	}
	private HashMap<String, Object> getwebs_board_notice(JSONObject jnotice) {
		HashMap<String, Object>noticeList =new HashMap<String,Object>();
		String notice="";
		int id=0;
		try {
			notice =jnotice.getString("noticeList");
			id =jnotice.getInt("id");
			noticeList.put("notice",notice);
			noticeList.put("id", id);
		} catch (JSONException e) {
			e.printStackTrace();
			Log.d("erorr", notice);
		}
		return noticeList;
	}
}
























