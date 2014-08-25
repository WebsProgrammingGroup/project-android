package app.webs.activity;

import android.app.*;
import android.content.*;
import android.graphics.Shader.*;
import android.graphics.drawable.*;
import android.os.*;
import android.support.v4.app.Fragment;
import android.util.*;
import android.view.*;
import android.widget.*;
import app.webs.service.*;
import app.webs.service.PushService.LocalBinder;

import com.actionbarsherlock.app.*;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.*;
import com.jeremyfeinstein.slidingmenu.lib.app.*;
import com.webs.app.*;
import com.webs.app.R;

public class Act02_BaseActivity extends SlidingFragmentActivity  {
	public Context mCtx;
	private boolean m_close_flag = false;
	private SharedPreferences mPrefs;

    public android.support.v4.app.Fragment f01_MyPage = new Frag01_MyPage();
    public android.support.v4.app.Fragment f02_MyTimeTable = new Frag02_MyTimeTable();
    public android.support.v4.app.Fragment f03_FreeBoard = new Frag03_FreeBoard();
    public android.support.v4.app.Fragment f04_AnonymityBoard = new Frag04_AnonymityBoard();
    public android.support.v4.app.Fragment f05_Gallery = new Frag05_Schedule();
    public android.support.v4.app.Fragment f06_StudyGroup = new Frag06_StudyGroup();
    public android.support.v4.app.Fragment f07_Contacts = new Frag07_Contacts();
    public android.support.v4.app.Fragment f09_AppSetting = new Frag09_AppSetting();
    public android.support.v4.app.Fragment f10_Credit = new Frag10_Credit();
    
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light);
		super.onCreate(savedInstanceState);
		mCtx = this;
		//initialize activity
		mPrefs = getSharedPreferences("AutoLogin", android.content.Context.MODE_PRIVATE);
		
		setBehindContentView(R.layout.frag00_silidingmenu_dummy);

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.f00_silidingmenu_dummy_frame, new Frag00_SilidingMenu())
				.commit();

		// configure the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		// getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.ab_bg_black));
		
		ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setDisplayShowHomeEnabled(true);
		ab.setDisplayShowTitleEnabled(true);
		ab.setTitle("IT Venture in INHA");
		ab.setIcon(R.drawable.ic_app);
		ab.setHomeButtonEnabled(true);
		
		BitmapDrawable bg = (BitmapDrawable)getResources().getDrawable(R.drawable.bg_striped);
        bg.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
        getSupportActionBar().setBackgroundDrawable(bg);
        
        setContentView(R.layout.act02_base);
        
        getSupportFragmentManager().beginTransaction().add(
                R.id.a02_frag_frame, f01_MyPage).commit();

	}
	public void SelectMenu(int Frag){
		switch (Frag) {
		case 1:
			getSupportFragmentManager().beginTransaction().replace(R.id.a02_frag_frame, f01_MyPage).commit();
			break;
		case 2:
			getSupportFragmentManager().beginTransaction().replace(R.id.a02_frag_frame, f02_MyTimeTable).commit();
			break;
		case 3:
			getSupportFragmentManager().beginTransaction().replace(R.id.a02_frag_frame, f03_FreeBoard).commit();
			break;
		case 4:
			getSupportFragmentManager().beginTransaction().replace(R.id.a02_frag_frame, f04_AnonymityBoard).commit();
			break;
		case 5:
			getSupportFragmentManager().beginTransaction().replace(R.id.a02_frag_frame, f05_Gallery).commit();
			break;
		case 6:
			getSupportFragmentManager().beginTransaction().replace(R.id.a02_frag_frame, f06_StudyGroup).commit();
			break;
		case 7:
			getSupportFragmentManager().beginTransaction().replace(R.id.a02_frag_frame, f07_Contacts).commit();
			break;
		case 8:
			SharedPreferences.Editor editor = mPrefs.edit();
            editor.remove("ID");
            editor.commit();
            
			Intent it;
			it = new Intent(mCtx, Act00_Login.class);
			it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(it);
			finish();
			break;
		case 9:
			getSupportFragmentManager().beginTransaction().replace(R.id.a02_frag_frame, f09_AppSetting).commit();
			break;
		case 10:
			getSupportFragmentManager().beginTransaction().replace(R.id.a02_frag_frame, f10_Credit).commit();
			break;
		default:
			break;
		}
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		}	
		return super.onOptionsItemSelected(item);
	}
	
	/* back key for finish app*/
	Handler AppCloseHandler = new Handler() {
        public void handleMessage(Message msg) {
            m_close_flag = false;
        }
    };
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) { 
		if(keyCode == KeyEvent.KEYCODE_BACK){
	    	 if(m_close_flag == false) {
	             Toast.makeText(this, "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
	             m_close_flag = true;
	             AppCloseHandler.sendEmptyMessageDelayed(0, 2000);
	         } else { 
	        	 finish();
	         }
	    	 return true;
	    }else{
	    	 return super.onKeyDown(keyCode, event);
	    }
	}
    
    /* Push Service Start */
    protected void onStart(){
    	StaticVar.isBound = true;
    	startService(new Intent(this, PushService.class));
    	if(StaticVar.mServiceConncetion == null){
    		//ServiceConnection init
    		StaticVar.mServiceConncetion =  new ServiceConnection() {
            	@Override
            	public void onServiceDisconnected(ComponentName name){
            		StaticVar.isBound = false;
            		StaticVar.mServiceConncetion = null;
            		Log.i("onServiceDisconnected",name.toShortString());
            	}
    			@Override
    			public void onServiceConnected(ComponentName name, IBinder service) {
    	        	LocalBinder binder = (LocalBinder) service;
    	        	StaticVar.mService = binder.getService();
    	        	StaticVar.isBound = true;		
            		Log.i("onServiceconnected",name.toShortString());	
    			}
			};
    	}
        super.onStart();
    }
}
