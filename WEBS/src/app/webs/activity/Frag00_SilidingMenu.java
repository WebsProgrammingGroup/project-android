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
	private LinearLayout PushBtn;
	private LinearLayout WriteNoticeBtn;
	private LinearLayout AdminMenu;
	private LinearLayout PushBtn_UnderLine1;
	private LinearLayout PushBtn_UnderLine2;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View ViewLayout = inflater.inflate(R.layout.frag00_silidingmenu, null, false);
		
		MyPageBtn = (LinearLayout)ViewLayout.findViewById(R.id.f00_menu_my_page);
		MyTimeTableBtn = (LinearLayout)ViewLayout.findViewById(R.id.f00_menu_my_timetable);
		FreeBoardBtn = (LinearLayout)ViewLayout.findViewById(R.id.f00_menu_freeboard);
		AnonymityBoardBtn = (LinearLayout)ViewLayout.findViewById(R.id.f00_menu_anonyboard);
		GalleryBtn = (LinearLayout)ViewLayout.findViewById(R.id.f00_menu_gallery);
		StudyGroupBtn = (LinearLayout)ViewLayout.findViewById(R.id.f00_menu_studygroup);
		ContactsBtn = (LinearLayout)ViewLayout.findViewById(R.id.f00_menu_contacts);
		LogoutBtn = (LinearLayout)ViewLayout.findViewById(R.id.f00_menu_logout);
		AppSettingBtn = (LinearLayout)ViewLayout.findViewById(R.id.f00_menu_appsetting);
		CreditBtn = (LinearLayout)ViewLayout.findViewById(R.id.f00_menu_credit);
		PushBtn = (LinearLayout)ViewLayout.findViewById(R.id.f00_menu_push);
		WriteNoticeBtn = (LinearLayout)ViewLayout.findViewById(R.id.f00_menu_write_notice);
		PushBtn_UnderLine1 = (LinearLayout)ViewLayout.findViewById(R.id.f00_menu_push_underline1);
		PushBtn_UnderLine2 = (LinearLayout)ViewLayout.findViewById(R.id.f00_menu_push_underline2);
		AdminMenu = (LinearLayout)ViewLayout.findViewById(R.id.f00_menu_admin);
		
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
		PushBtn.setOnClickListener(this);
		WriteNoticeBtn.setOnClickListener(this);
		
		if(Integer.valueOf(StaticVar.mLoginData.Level) >= 4){
			AdminMenu.setVisibility(View.VISIBLE);
			PushBtn_UnderLine1.setVisibility(View.VISIBLE);
			PushBtn_UnderLine2.setVisibility(View.VISIBLE);
		}
		
		return ViewLayout;
	}
	@Override
	public void onClick(View v) {	
		Act02_BaseActivity CallingActivity = (Act02_BaseActivity) getActivity();
		
		switch(v.getId()){
			case R.id.f00_menu_my_page:
				CallingActivity.SelectMenu(1);
				CallingActivity.toggle();
				Log.i(DEBUG_LOG_MENU,"MyPage");
				break;
			case R.id.f00_menu_my_timetable:
				CallingActivity.SelectMenu(2);
				CallingActivity.toggle();
				Log.i(DEBUG_LOG_MENU,"MyTimetable");
				break;
			case R.id.f00_menu_freeboard:
				CallingActivity.SelectMenu(3);
				CallingActivity.toggle();
				Log.i(DEBUG_LOG_MENU,"FreeBoard");
				break;
			case R.id.f00_menu_anonyboard:
				CallingActivity.SelectMenu(4);
				CallingActivity.toggle();
				Log.i(DEBUG_LOG_MENU,"AnonymityBoard");
				break;
			case R.id.f00_menu_gallery:
				CallingActivity.SelectMenu(5);
				CallingActivity.toggle();
				Log.i(DEBUG_LOG_MENU,"Gallery");
				break;
			case R.id.f00_menu_studygroup:
				CallingActivity.SelectMenu(6);
				CallingActivity.toggle();
				Log.i(DEBUG_LOG_MENU,"StudyGroup");
				break;
			case R.id.f00_menu_contacts:
				CallingActivity.SelectMenu(7);
				CallingActivity.toggle();
				Log.i(DEBUG_LOG_MENU,"Contacts");
				break;
			case R.id.f00_menu_logout:
				CallingActivity.SelectMenu(8);
				CallingActivity.toggle();
				Log.i(DEBUG_LOG_MENU,"Logout");
				break;
			case R.id.f00_menu_appsetting:
				CallingActivity.SelectMenu(9);
				CallingActivity.toggle();
				Log.i(DEBUG_LOG_MENU,"AppSetting");
				break;
			case R.id.f00_menu_credit:
				CallingActivity.SelectMenu(10);
				CallingActivity.toggle();
				Log.i(DEBUG_LOG_MENU,"Credit");
				break;
			case R.id.f00_menu_push:
				CallingActivity.SelectMenu(11);
				CallingActivity.toggle();
				Log.i(DEBUG_LOG_MENU,"Push Message");
				break;
			case R.id.f00_menu_write_notice:
				CallingActivity.SelectMenu(12);
				CallingActivity.toggle();
				Log.i(DEBUG_LOG_MENU,"Write Notice");
				break;
			default:
				break;
		}
	}

}
