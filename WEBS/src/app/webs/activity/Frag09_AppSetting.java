package app.webs.activity;

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
	
	private SharedPreferences mPrefs;
	private Context mCtx = null;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		
		return ViewLayout;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.f09_lay_push_alarm:
			if(StaticVar.isPushAlarm){
				StaticVar.isPushAlarm = false;
				PushAlarm_chk.setChecked(false);
			}else{
				StaticVar.isPushAlarm = true;
				PushAlarm_chk.setChecked(true);
			}
			break;
		case R.id.f09_lay_auto_login:
			if(StaticVar.isAutoLogin){
				StaticVar.isAutoLogin = false;
				AutoLogin_chk.setChecked(false);
			}else{
				StaticVar.isAutoLogin = true;
				AutoLogin_chk.setChecked(true);
			}
			break;
		case R.id.f09_lay_app_pw:
			if(StaticVar.isPwUsage){
				StaticVar.isPwUsage = false;
				PwUsage_chk.setChecked(false);
			}else{
				StaticVar.isPwUsage = true;
				PwUsage_chk.setChecked(true);
			}
			break;
		case R.id.f09_lay_app_pw_change:
			
			break;
		default:
			break;
		}
		
	}
}
