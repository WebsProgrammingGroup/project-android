package app.webs.Activity;

import java.util.*;

import org.apache.http.*;
import org.apache.http.message.*;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.webs.app.*;

import android.os.*;
import android.app.*;
import android.content.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import app.webs.DataType.*;
import app.webs.Util.*;

public class Act00_Login extends Activity implements OnClickListener, OnKeyListener{
	private Context mCtx;
	private SharedPreferences mPrefs;
	
	private BootstrapButton JoinBtn;
	private BootstrapButton LoginBtn;
	private BootstrapEditText ID_et;
	private BootstrapEditText PW_et;
	
	private String IdStr;
	private String PwStr;
	
	private Act00_LoginDataParser mDataParser;
	private LoadingDialog mLoadingDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act00_login);
		mCtx = this;
		
		mPrefs = getSharedPreferences("AppSetting", android.content.Context.MODE_PRIVATE);
    	StaticVar.ID = mPrefs.getString("ID", null);
    	StaticVar.isPushAlarm = mPrefs.getBoolean("PushAlarm", true);
		StaticVar.isAutoLogin = mPrefs.getBoolean("AutoLogin", true);
		StaticVar.isAppClose = mPrefs.getBoolean("PwUsage", false);
		StaticVar.AppClosingPW = mPrefs.getString("AppClosingPW", null);
		
    	Intent it;
    	if(StaticVar.isAutoLogin){
    		if(StaticVar.isAppClose){
    			it = new Intent(mCtx, Act00_AppClose.class);
    			it.putExtra("intent", "release");
    			it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    			startActivity(it);			
    			finish();
    		}else if(StaticVar.ID != null){
    			//loading private info
    			LoginData data = new LoginData();
    			data.Name = mPrefs.getString("Name", null);
				data.Phone = mPrefs.getString("Phone", null);
				data.Photo = mPrefs.getString("Photo", null);
				data.ID = mPrefs.getString("ID", null);
				data.Level = mPrefs.getString("PW", null);
				data.Major = mPrefs.getString("Major", null);
				data.Gender = mPrefs.getString("Gender", null);
				data.Fees = mPrefs.getString("Fees", null);
				data.Grd = mPrefs.getString("Grd", null);
				data.Birth = mPrefs.getString("Birth", null);
				data.Level = mPrefs.getString("Level", null);
				StaticVar.mLoginData = data;
				
				//move activity
        		it = new Intent(mCtx, Act02_BaseActivity.class);
    			it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    			startActivity(it);			
    			finish();
        	}
    	}
    	
		JoinBtn = (BootstrapButton)findViewById(R.id.a00_btn_join);
		LoginBtn = (BootstrapButton)findViewById(R.id.a00_btn_login);
		ID_et = (BootstrapEditText)findViewById(R.id.a00_te_id);
		PW_et = (BootstrapEditText)findViewById(R.id.a00_te_pw);
		
		JoinBtn.setOnClickListener(this);
		LoginBtn.setOnClickListener(this);
		PW_et.setOnKeyListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent i;
		switch (v.getId()) {
		case R.id.a00_btn_login:
			Login();
			break;
		case R.id.a00_btn_join:
			i = new Intent(this, Act01_JoinUs.class);
			startActivity(i);
			overridePendingTransition(R.anim.viewin3, R.anim.viewout3);
			break;
		default:
			break;
		}
	}
	
	public synchronized void Login(){
		IdStr = ID_et.getText().toString();
		PwStr = PW_et.getText().toString();
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		
		if(IdStr.equals("")){
			alert.setMessage("ID을 입력해주세요");
			alert.setPositiveButton("확인", null);
			alert.show();
		}else if(PwStr.equals("")){
			alert.setMessage("PW를 입력해주세요");
			alert.setPositiveButton("확인", null);
			alert.show();
		}else{
			Log.i("dial","show");
			mLoadingDialog = new LoadingDialog(this);
			mLoadingDialog.DialogShow();
			
			mDataParser = new Act00_LoginDataParser(mCtx,UiHandler);
			final ArrayList<NameValuePair> paramList = new ArrayList<NameValuePair>();
			paramList.add(new BasicNameValuePair("ID", IdStr));
			paramList.add(new BasicNameValuePair("PW", PwStr));
			mDataParser.setParamList(paramList);
			mDataParser.start();
		}
	}

	Handler UiHandler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what == 0){ // Login Fail
				Toast.makeText(mCtx, "로그인을 실패했습니다.", 0).show();
			}else{ // Login Success
				SharedPreferences.Editor editor = mPrefs.edit();
                editor.putString("ID", IdStr);
                editor.putString("PW", PwStr);
                editor.putString("Name", StaticVar.mLoginData.Name);
                editor.putString("Birth", StaticVar.mLoginData.Birth);
                editor.putString("Gender", StaticVar.mLoginData.Gender);
                editor.putString("Grd", StaticVar.mLoginData.Grd);
                editor.putString("Level", StaticVar.mLoginData.Level);
                editor.putString("Major", StaticVar.mLoginData.Major);
                editor.putString("Phone", StaticVar.mLoginData.Phone);
                editor.putString("Photo", StaticVar.mLoginData.Photo);
                editor.putString("Fees", StaticVar.mLoginData.Fees);
                editor.commit();
				
				Intent it;
				it = new Intent(mCtx, Act02_BaseActivity.class);
				it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(it);
				
				finish();
				overridePendingTransition(R.anim.viewin3, R.anim.viewout3);
			}
			mLoadingDialog.DialogDismiss();
		}
	};
	
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent e) {
		Log.i("onKey",e.toString());
		if(v.getId() == R.id.a00_te_pw && keyCode == KeyEvent.KEYCODE_ENTER && e.getAction() == KeyEvent.ACTION_UP){
			Login();
		}
		return false;
	}
}
