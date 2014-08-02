package com.example.lastactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class WEBS_BOARD_NOTICE_LIST extends Activity {
	TextView title;
	String t_title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webs_board_notice_s_list);
		Intent intent=getIntent();
		t_title=intent.getStringExtra("id");
		title =(TextView)findViewById(R.id.title_list);
		title.setText("제목:"+t_title);
		Log.i("success", "here");
		
	}
	
}
