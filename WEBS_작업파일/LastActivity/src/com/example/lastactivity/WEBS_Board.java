package com.example.lastactivity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class WEBS_Board extends Activity  {
	ListView lv_webs_board;
	Context mctx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webs_board);
		mctx = this;
		ArrayList<WEBS_Board_DTO> list = new ArrayList<WEBS_Board_DTO>();
		list.add(new WEBS_Board_DTO("공지사항"));
		list.add(new WEBS_Board_DTO("자유게시판"));
		list.add(new WEBS_Board_DTO("익명게시판"));
		list.add(new WEBS_Board_DTO("질문게시판"));
		list.add(new WEBS_Board_DTO("건의사항"));
		lv_webs_board = (ListView) findViewById(R.id.lv_webs_board);
		WEBS_Board_Adapter adapter = new WEBS_Board_Adapter(
				getApplicationContext(), R.layout.webs_board_list_item, list);
		lv_webs_board.setAdapter(adapter);
		lv_webs_board.setOnItemClickListener(websboard_notice);
		
		
	}
	private AdapterView.OnItemClickListener websboard_notice =new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			switch (position) {
			case 0:
				Intent intent1 = new Intent(mctx,WEBS_Board_NOTICE.class);
				startActivity(intent1);
				break;
			case 1:
				Intent intent2 =new Intent(mctx,WEBS_BOARD_FREE_BOARD.class);
				startActivity(intent2);
				break;
			case 2:
				Intent intent3 = new Intent(mctx, WEBS_ANONYMOUS_BOARD.class);
				startActivity(intent3);
				break;
			case 3:
				Intent intent4 =new Intent(mctx, WEBS_Q_A_NOTICE.class);
				startActivity(intent4);
				break;
			case 4:
				Intent intent5 =new Intent(mctx , WEBS_RECOMMENDATIONS_BOARD.class);
				startActivity(intent5);
				break;
				
			default:
				break;
			}
		}
	
	};
	

}
