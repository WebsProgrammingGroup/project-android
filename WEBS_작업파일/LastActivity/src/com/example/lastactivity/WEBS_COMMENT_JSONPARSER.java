package com.example.lastactivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class WEBS_COMMENT_JSONPARSER {
	String notice="";
public List<HashMap<String, Object>> parse(JSONObject jObject) {
		
		JSONArray j_webs_board_free_notice = null;
		try {
			j_webs_board_free_notice = jObject
					.getJSONArray("comment_list");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return getwebs_free_board_notices(j_webs_board_free_notice);
	}

	private List<HashMap<String, Object>> getwebs_free_board_notices(
			JSONArray j_webs_board_notice) {
		int notice_count = j_webs_board_notice.length();
		List<HashMap<String, Object>> notice_List = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> notice = null;
		
		for (int i = 0; i < notice_count; i++) {
			try {
				notice = getwebs_free_board_notice((JSONObject) j_webs_board_notice
						.get(i));
				notice_List.add(notice);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return notice_List;
	}
	private HashMap<String, Object> getwebs_free_board_notice(JSONObject jnotice) {
		HashMap<String, Object>noticeList =new HashMap<String,Object>();
		
		try {
			notice =jnotice.getString("comment");
			noticeList.put("list",notice);
		} catch (JSONException e) {
			e.printStackTrace();
			Log.d("erorr", notice);
		}
		return noticeList;
	}
}
