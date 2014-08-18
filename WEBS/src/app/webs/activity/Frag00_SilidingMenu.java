package app.webs.activity;

import android.*;
import android.content.*;
import android.os.*;
import android.support.v4.app.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

import com.jeremyfeinstein.slidingmenu.lib.app.*;
import com.webs.app.R;

public class Frag00_SilidingMenu extends android.support.v4.app.Fragment implements OnClickListener{
	private static final String DEBUG_LOG_MENU = "MENU_CLICKED";
	
	private LinearLayout MyPageBtn;
	private LinearLayout MyTimeTableBtn;
	private LinearLayout FreeBoardBtn;
	private LinearLayout AnonymityBoardBtn;
	private LinearLayout GalleryBtn;
	private LinearLayout StudyGroupBtn;
	private LinearLayout ContactsBtn;
	private LinearLayout LogoutBtn;
	private LinearLayout AppSettingBtn;
	private LinearLayout CreditBtn;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View ViewLayout = inflater.inflate(R.layout.frag00_silidingmenu, null, false);
		
		MyPageBtn = (LinearLayout)ViewLayout.findViewById(R.id.f00_menu_MyPage);
		MyTimeTableBtn = (LinearLayout)ViewLayout.findViewById(R.id.f00_menu_MyTimetable);
		FreeBoardBtn = (LinearLayout)ViewLayout.findViewById(R.id.f00_menu_FreeBoard);
		AnonymityBoardBtn = (LinearLayout)ViewLayout.findViewById(R.id.f00_menu_AnonymityBoard);
		GalleryBtn = (LinearLayout)ViewLayout.findViewById(R.id.f00_menu_Gallery);
		StudyGroupBtn = (LinearLayout)ViewLayout.findViewById(R.id.f00_menu_StudyGroup);
		ContactsBtn = (LinearLayout)ViewLayout.findViewById(R.id.f00_menu_Contacts);
		LogoutBtn = (LinearLayout)ViewLayout.findViewById(R.id.f00_menu_Logout);
		AppSettingBtn = (LinearLayout)ViewLayout.findViewById(R.id.f00_menu_AppSetting);
		CreditBtn = (LinearLayout)ViewLayout.findViewById(R.id.f00_menu_Credit);
		
		MyPageBtn.setOnClickListener(this);
		MyTimeTableBtn.setOnClickListener(this);
		FreeBoardBtn.setOnClickListener(this);
		AnonymityBoardBtn.setOnClickListener(this);
		GalleryBtn.setOnClickListener(this);
		StudyGroupBtn.setOnClickListener(this);
		ContactsBtn.setOnClickListener(this);
		LogoutBtn.setOnClickListener(this);
		AppSettingBtn.setOnClickListener(this);
		CreditBtn.setOnClickListener(this);
		
		return ViewLayout;
	}
	@Override
	public void onClick(View v) {	
		Act02_BaseActivity CallingActivity = (Act02_BaseActivity) getActivity();
		
		switch(v.getId()){
			case R.id.f00_menu_MyPage:
				CallingActivity.SelectMenu(1);
				CallingActivity.toggle();
				Log.i(DEBUG_LOG_MENU,"MyPage");
				break;
			case R.id.f00_menu_MyTimetable:
				CallingActivity.SelectMenu(2);
				CallingActivity.toggle();
				Log.i(DEBUG_LOG_MENU,"MyTimetable");
				break;
			case R.id.f00_menu_FreeBoard:
				CallingActivity.SelectMenu(3);
				CallingActivity.toggle();
				Log.i(DEBUG_LOG_MENU,"FreeBoard");
				break;
			case R.id.f00_menu_AnonymityBoard:
				CallingActivity.SelectMenu(4);
				CallingActivity.toggle();
				Log.i(DEBUG_LOG_MENU,"AnonymityBoard");
				break;
			case R.id.f00_menu_Gallery:
				CallingActivity.SelectMenu(5);
				CallingActivity.toggle();
				Log.i(DEBUG_LOG_MENU,"Gallery");
				break;
			case R.id.f00_menu_StudyGroup:
				CallingActivity.SelectMenu(6);
				CallingActivity.toggle();
				Log.i(DEBUG_LOG_MENU,"StudyGroup");
				break;
			case R.id.f00_menu_Contacts:
				CallingActivity.SelectMenu(7);
				CallingActivity.toggle();
				Log.i(DEBUG_LOG_MENU,"Contacts");
				break;
			case R.id.f00_menu_Logout:
				CallingActivity.SelectMenu(8);
				CallingActivity.toggle();
				Log.i(DEBUG_LOG_MENU,"Logout");
				break;
			case R.id.f00_menu_AppSetting:
				CallingActivity.SelectMenu(9);
				CallingActivity.toggle();
				Log.i(DEBUG_LOG_MENU,"AppSetting");
				break;
			case R.id.f00_menu_Credit:
				CallingActivity.SelectMenu(10);
				CallingActivity.toggle();
				Log.i(DEBUG_LOG_MENU,"Credit");
				break;
			default:
				break;
		}
	}

}