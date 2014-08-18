package app.webs.activity;

import android.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

import com.webs.app.R;

public class Frag00_SilidingMenu extends android.support.v4.app.Fragment implements OnClickListener{
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
		switch(v.getId()){
		case R.id.f00_menu_MyPage:
			break;
		case R.id.f00_menu_MyTimetable:
			break;
		case R.id.f00_menu_FreeBoard:
			break;
		case R.id.f00_menu_AnonymityBoard:
			break;
		case R.id.f00_menu_Gallery:
			break;
		case R.id.f00_menu_StudyGroup:
			break;
		case R.id.f00_menu_Contacts:
			break;
		case R.id.f00_menu_Logout:
			break;
		case R.id.f00_menu_AppSetting:
			break;
		case R.id.f00_menu_Credit:
			break;
		default:
			break;
		}
	}

}
