package app.webs.activity;

import java.util.*;

import org.apache.http.*;
import org.apache.http.message.*;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.webs.app.*;

import android.os.*;
import android.app.*;
import android.content.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act00_login);
		mCtx = this;
		
		mPrefs = getSharedPreferences("AutoLogin", android.content.Context.MODE_PRIVATE);
    	StaticVar.ID = mPrefs.getString("ID", null);
    	
    	Intent it;
    	if(StaticVar.ID != null){
    		it = new Intent(mCtx, Act02_BaseActivity.class);
			it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(it);			
			finish();
			overridePendingTransition(R.anim.viewin3, R.anim.viewout3);
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
			overridePendingTransition(R.anim.viewin4, R.anim.viewout4);
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
                editor.commit();
				
				Intent it;
				it = new Intent(mCtx, Act02_BaseActivity.class);
				it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(it);
				
				finish();
				overridePendingTransition(R.anim.viewin3, R.anim.viewout3);
			}
		}
	};
	
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent e) {
		if(v.getId() == R.id.a00_te_pw && keyCode == KeyEvent.KEYCODE_ENTER){
			Login();
		}
		return false;
	}
}
