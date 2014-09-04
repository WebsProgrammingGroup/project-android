package app.webs.Activity;

import com.webs.app.*;

import android.content.*;
import android.os.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

public class Frag09_AppSetting extends android.support.v4.app.Fragment implements OnClickListener{
	private RelativeLayout PushAlarm_btn;
	private RelativeLayout AutoLogin_btn;
	private RelativeLayout PwUsage_btn;
	private RelativeLayout PwChange_btn;
	
	private CheckBox PushAlarm_chk;
	private CheckBox AutoLogin_chk;
	private CheckBox PwUsage_chk;
	
	private static SharedPreferences mPrefs;
	private Context mCtx = null;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCtx = getActivity();
		mPrefs = mCtx.getSharedPreferences("AppSetting", android.content.Context.MODE_PRIVATE);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View ViewLayout = inflater.inflate(R.layout.frag09_app_setting, null, false);
		PushAlarm_btn = (RelativeLayout) ViewLayout.findViewById(R.id.f09_lay_push_alarm);
		AutoLogin_btn = (RelativeLayout) ViewLayout.findViewById(R.id.f09_lay_auto_login);
		PwUsage_btn = (RelativeLayout) ViewLayout.findViewById(R.id.f09_lay_app_pw);
		PwChange_btn = (RelativeLayout) ViewLayout.findViewById(R.id.f09_lay_app_pw_change);
		PushAlarm_chk = (CheckBox) ViewLayout.findViewById(R.id.f09_chk_push_alarm);
		AutoLogin_chk = (CheckBox) ViewLayout.findViewById(R.id.f09_chk_auto_login);
		PwUsage_chk = (CheckBox) ViewLayout.findViewById(R.id.f09_chk_app_pw);
		
		PushAlarm_btn.setOnClickListener(this);
		AutoLogin_btn.setOnClickListener(this);
		PwUsage_btn.setOnClickListener(this);
		PwChange_btn.setOnClickListener(this);
		PushAlarm_chk.setOnClickListener(this);
		AutoLogin_chk.setOnClickListener(this);
		PwUsage_chk.setOnClickListener(this);
		
		LayoutSetting();		
		return ViewLayout;
	}
	@Override
	public void onClick(View v) {
		Intent it;
        
		switch (v.getId()) {
		case R.id.f09_lay_push_alarm:
			StaticVar.isPushAlarm = !StaticVar.isPushAlarm;
			break;
		case R.id.f09_lay_auto_login:
			StaticVar.isAutoLogin = !StaticVar.isAutoLogin;
			break;
		case R.id.f09_lay_app_pw:
			StaticVar.isAppClose = !StaticVar.isAppClose;
			
			if(StaticVar.isAppClose == true){
				it = new Intent(mCtx, Act00_AppClose.class);
				it.putExtra("intent", "set");
				startActivity(it);
			}
			break;
		case R.id.f09_lay_app_pw_change:

			it = new Intent(mCtx, Act00_AppClose.class);
			it.putExtra("intent", "change");
			startActivity(it);
			
			break;
		case R.id.f09_chk_push_alarm:
			StaticVar.isPushAlarm = !StaticVar.isPushAlarm;
			break;
		case R.id.f09_chk_auto_login:
			StaticVar.isAutoLogin = !StaticVar.isAutoLogin;
			break;
		case R.id.f09_chk_app_pw:
			StaticVar.isAppClose = !StaticVar.isAppClose;
			break;
		default:
			break;
		}
		LayoutSetting();
	}
	void LayoutSetting(){
		SharedPreferences.Editor editor = mPrefs.edit();
		PushAlarm_chk.setChecked(StaticVar.isPushAlarm);
		
		if(StaticVar.isPushAlarm){
			editor.putBoolean("PushAlarm", true);
			PushAlarm_chk.setChecked(true);
			
		}else{
			editor.putBoolean("PushAlarm", false);
			PushAlarm_chk.setChecked(false);
		}
		
		if(StaticVar.isAutoLogin){
			AutoLogin_chk.setChecked(true);
			PwUsage_btn.setVisibility(View.VISIBLE);
			PwChange_btn.setVisibility(View.VISIBLE);
			editor.putBoolean("AutoLogin", true);
		}else{
			StaticVar.isAppClose = false;
			AutoLogin_chk.setChecked(false);
			PwUsage_btn.setVisibility(View.GONE);
			PwChange_btn.setVisibility(View.GONE);
			editor.putBoolean("AutoLogin", false);
		}
		
		if(StaticVar.isAppClose){
			PwUsage_chk.setChecked(true);
			PwChange_btn.setVisibility(View.VISIBLE);
		}else{
			PwUsage_chk.setChecked(false);
			editor.remove("PwUsage");
			editor.remove("AppClosingPW");
			PwChange_btn.setVisibility(View.GONE);			
		}

		editor.commit();
	}
}
