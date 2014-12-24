package app.webs.activity;

import android.content.*;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.*;
import android.os.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

import com.actionbarsherlock.app.*;
import com.webs.app.*;


public class Act00_AppClose extends SherlockActivity{
	Context mCtx;
	private TextView TitleText;
	private ImageView PwImg1, PwImg2, PwImg3, PwImg4;
	private ImageView PwBtn1, PwBtn2, PwBtn3, PwBtn4, PwBtn5, PwBtn6, PwBtn7, PwBtn8, PwBtn9, PwBtn0, PwBtnBack;
	private String pwStr1 = null;
	private String pwStr2 = null;
	private String intent;
	
	private SharedPreferences mPrefs;
	private SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act00_password);
		mCtx = this;
		
//		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//		getSupportActionBar().setDisplayShowHomeEnabled(false);
//		getSupportActionBar().setDisplayShowTitleEnabled(true);
//		//getSupportActionBar().setIcon(R.drawable.app_ic);
//		getSupportActionBar().setHomeButtonEnabled(false);
//		BitmapDrawable bg = (BitmapDrawable)getResources().getDrawable(R.drawable.bg_striped);
//        bg.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
//        getSupportActionBar().setBackgroundDrawable(bg);
        
		PwImg1 = (ImageView)findViewById(R.id.a00_img_pw1);
		PwImg2 = (ImageView)findViewById(R.id.a00_img_pw2);
		PwImg3 = (ImageView)findViewById(R.id.a00_img_pw3);
		PwImg4 = (ImageView)findViewById(R.id.a00_img_pw4);
		PwBtn0 = (ImageView)findViewById(R.id.a00_btn_numpad0);
		PwBtn1 = (ImageView)findViewById(R.id.a00_btn_numpad1);
		PwBtn2 = (ImageView)findViewById(R.id.a00_btn_numpad2);
		PwBtn3 = (ImageView)findViewById(R.id.a00_btn_numpad3);
		PwBtn4 = (ImageView)findViewById(R.id.a00_btn_numpad4);
		PwBtn5 = (ImageView)findViewById(R.id.a00_btn_numpad5);
		PwBtn6 = (ImageView)findViewById(R.id.a00_btn_numpad6);
		PwBtn7 = (ImageView)findViewById(R.id.a00_btn_numpad7);
		PwBtn8 = (ImageView)findViewById(R.id.a00_btn_numpad8);
		PwBtn9 = (ImageView)findViewById(R.id.a00_btn_numpad9);
		PwBtnBack = (ImageView)findViewById(R.id.a00_btn_numpad_back);
		TitleText = (TextView)findViewById(R.id.a00_txt_title);
		
		a0OnClickListener a0ClickListener = new a0OnClickListener();
		PwBtn0.setOnClickListener(a0ClickListener);
		PwBtn1.setOnClickListener(a0ClickListener);
		PwBtn2.setOnClickListener(a0ClickListener);
		PwBtn3.setOnClickListener(a0ClickListener);
		PwBtn4.setOnClickListener(a0ClickListener);
		PwBtn5.setOnClickListener(a0ClickListener);
		PwBtn6.setOnClickListener(a0ClickListener);
		PwBtn7.setOnClickListener(a0ClickListener);
		PwBtn8.setOnClickListener(a0ClickListener);
		PwBtn9.setOnClickListener(a0ClickListener);
		PwBtn0.setOnClickListener(a0ClickListener);
		PwBtnBack.setOnClickListener(a0ClickListener);
		
		pwStr1 = new String();
		
		Intent it = getIntent();
		intent = it.getStringExtra("intent");
		
		mPrefs = mCtx.getSharedPreferences("AppSetting", android.content.Context.MODE_PRIVATE);
		editor = mPrefs.edit();
		
	}
	class a0OnClickListener implements OnClickListener{
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.a00_btn_numpad0:
				if(pwStr1.isEmpty()){
					pwStr1 = "0";
				}else if(pwStr1.length() < 4){
					pwStr1 += '0';
				}
				break;
			case R.id.a00_btn_numpad1:
				if(pwStr1.isEmpty()){
					pwStr1 = "1";
				}else if(pwStr1.length() < 4){
					pwStr1 += '1';
				}				
				break;
			case R.id.a00_btn_numpad2:
				if(pwStr1.isEmpty()){
					pwStr1 = "2";
				}else if(pwStr1.length() < 4){
					pwStr1 += '2';
				}
				break;
			case R.id.a00_btn_numpad3:
				if(pwStr1.isEmpty()){
					pwStr1 = "3";
				}else if(pwStr1.length() < 4){
					pwStr1 += '3';
				}				
				break;
			case R.id.a00_btn_numpad4:
				if(pwStr1.isEmpty()){
					pwStr1 = "4";
				}else if(pwStr1.length() < 4){
					pwStr1 += '4';
				}
				break;
			case R.id.a00_btn_numpad5:
				if(pwStr1.isEmpty()){
					pwStr1 = "5";
				}else if(pwStr1.length() < 4){
					pwStr1 += '5';
				}
				break;
			case R.id.a00_btn_numpad6:
				if(pwStr1.isEmpty()){
					pwStr1 = "6";
				}else if(pwStr1.length() < 4){
					pwStr1 += '6';
				}
				break;
			case R.id.a00_btn_numpad7:
				if(pwStr1.isEmpty()){
					pwStr1 = "7";
				}else if(pwStr1.length() < 4){
					pwStr1 += '7';
				}
				break;
			case R.id.a00_btn_numpad8:
				if(pwStr1.isEmpty()){
					pwStr1 = "8";
				}else if(pwStr1.length() < 4){
					pwStr1 += '8';
				}
				break;
			case R.id.a00_btn_numpad9:
				if(pwStr1.isEmpty()){
					pwStr1 = "9";
				}else if(pwStr1.length() < 4){
					pwStr1 += '9';
				}
				break;
			case R.id.a00_btn_numpad_back:
				if(pwStr1.length() > 0){
					pwStr1 = pwStr1.substring(0, pwStr1.length()-1);
				}
				break;
			default:
				break;
			}//end of switch
			
			switch (pwStr1.length()) {
			case 0:
				PwImg1.setImageResource(R.drawable.dot_orange);
				PwImg2.setImageResource(R.drawable.dot_orange);
				PwImg3.setImageResource(R.drawable.dot_orange);
				PwImg4.setImageResource(R.drawable.dot_orange);
				break;
			case 1:
				PwImg1.setImageResource(R.drawable.dot_blue);
				PwImg2.setImageResource(R.drawable.dot_orange);
				PwImg3.setImageResource(R.drawable.dot_orange);
				PwImg4.setImageResource(R.drawable.dot_orange);
				break;
			case 2:
				PwImg1.setImageResource(R.drawable.dot_blue);
				PwImg2.setImageResource(R.drawable.dot_blue);
				PwImg3.setImageResource(R.drawable.dot_orange);
				PwImg4.setImageResource(R.drawable.dot_orange);
				break;
			case 3:
				PwImg1.setImageResource(R.drawable.dot_blue);
				PwImg2.setImageResource(R.drawable.dot_blue);
				PwImg3.setImageResource(R.drawable.dot_blue);
				PwImg4.setImageResource(R.drawable.dot_orange);
				break;
			case 4:
				PwImg1.setImageResource(R.drawable.dot_orange);
				PwImg2.setImageResource(R.drawable.dot_orange);
				PwImg3.setImageResource(R.drawable.dot_orange);
				PwImg4.setImageResource(R.drawable.dot_orange);
				
				if(intent.equals("set")){ //비번 세팅
					if(pwStr2 == null){
						//첫번째 입력 끝일 때
						pwStr2 = pwStr1;
						pwStr1 = new String();
						TitleText.setText("비밀번호를 한번 더 입력해주세요");
					}else{
						//두번째 입력 끝일 때
						if(pwStr1.equals(pwStr2)){
							//두 번의 입력이 같을 때
							editor.putBoolean("AutoLogin", true);
							editor.putBoolean("PwUsage", true);
							editor.putString("AppClosingPW", pwStr1);
							editor.commit();
							StaticVar.AppClosingPW = pwStr1;
							Toast.makeText(mCtx, "비밀번호 설정 성공", 0).show();	
							finish();
						}else{
							//두 번의 입력이 다를 때
							pwStr1 = new String();
							pwStr2 = null;
							Toast.makeText(mCtx, "입력하신 비밀번호가 서로 틀립니다.\n 비밀번호를 확인해주세요", 0).show();
							TitleText.setText("비밀번호를  입력해주세요");
							
						}
					}
				}else if(intent.equals("change")){ //비번 변경
					if(pwStr2 == null){
						//첫번째 입력 끝일 때
						pwStr2 = pwStr1;
						pwStr1 = new String();
						TitleText.setText("비밀번호를 한번 더 입력해주세요");
					}else{
						//두번째 입력 끝일 때
						if(pwStr1.equals(pwStr2)){
							//두 번의 입력이 같을 때
							editor.putBoolean("AutoLogin", true);
							editor.putBoolean("PwUsage", true);
							editor.putString("AppClosingPW", pwStr1);
							editor.commit();
							StaticVar.AppClosingPW = pwStr1;
							
							Toast.makeText(mCtx, "비밀번호 설정 성공", 0).show();		
							finish();
						}else{
							//두 번의 입력이 다를 때
							pwStr1 = new String();
							pwStr2 = null;
							Toast.makeText(mCtx, "입력하신 비밀번호가 서로 틀립니다.\n 비밀번호를 확인해주세요", 0).show();
							TitleText.setText("비밀번호를  입력해주세요");
						}
					}
				}else { // 잠금 해제
					if(pwStr1.equals(StaticVar.AppClosingPW)){
						Intent i =  new Intent(mCtx, Act02_BaseActivity.class);
						startActivity(i);
						finish();
						overridePendingTransition(R.anim.viewin1, R.anim.viewout1);
					}else{
						Toast.makeText(mCtx, "비밀번호가 틀렸습니다.\n 다시 시도해주세요", 0).show();
						pwStr1 = new String();
					}
				}
				break;
			default:
				break;
			}//end of switch
		}
	}
}
