package app.webs.activity;

import com.beardedhen.androidbootstrap.*;
import com.webs.app.*;
import com.webs.app.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

public class Act00_Login extends Activity implements OnClickListener{
	private BootstrapButton JoinBtn;
	private BootstrapButton LoginBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act00_login);
		
		JoinBtn = (BootstrapButton)findViewById(R.id.a00_btn_join);
		LoginBtn = (BootstrapButton)findViewById(R.id.a00_btn_login);
		
		JoinBtn.setOnClickListener(this);
		LoginBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent i;
		switch (v.getId()) {
		case R.id.a00_btn_login:
			i = new Intent(this, Act02_BaseActivity.class);
			startActivity(i);
			finish();
			overridePendingTransition(R.anim.viewin3, R.anim.viewout3);
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

}
