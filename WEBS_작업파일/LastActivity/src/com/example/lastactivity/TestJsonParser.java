package com.example.lastactivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class TestJsonParser {
	public List<HashMap<String, Object>> parse(JSONObject jObject) {

		JSONArray jTest = null;
		
		try {
			jTest = jObject.getJSONArray("member");
			
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		return getmembers(jTest);
	}

	private List<HashMap<String, Object>> getmembers(JSONArray jTest) {
		int mem_count = jTest.length();
		List<HashMap<String, Object>> mem_List = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> member = null;

		for (int i = 0; i < mem_count; i++) {
			try {
				member = getmember((JSONObject) jTest.get(i));
				mem_List.add(member);
				
			} catch (JSONException e) {
				// TODO: handle exception
				e.printStackTrace();
		
			}
		}
		return mem_List;
	}

	private HashMap<String, Object> getmember(JSONObject jmember) {
		HashMap<String, Object> member = new HashMap<String, Object>();
		String name = "";
		String photo = "";
		String phone = "";

		try {
			phone = jmember.getString("phone");
			photo = jmember.getString("photo");
			name = jmember.getString("member");
			String details = "phone" + phone;
			member.put("name", name);
			member.put("photo", R.drawable.blank);
			member.put("details", details);
			member.put("photo_path",photo);
			
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}

		return member;
	}
}
