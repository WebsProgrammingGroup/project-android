package app.webs.activity;

import android.app.*;
import android.content.*;
import android.graphics.Shader.*;
import android.graphics.drawable.*;
import android.os.*;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
	private static boolean mAppCloseFlag = false;
	
	private SharedPreferences mPrefs;

    public final Fragment f01_MyPage = new Frag01_MyPage();
    public android.support.v4.app.Fragment f02_MyTimeTable = new Frag02_MyTimeTable();
    public android.support.v4.app.Fragment f03_FreeBoard = new Frag03_FreeBoard();
    public android.support.v4.app.Fragment f04_AnonymityBoard = new Frag04_AnonymityBoard();
    public android.support.v4.app.Fragment f05_Scedule = new Frag05_Schedule();
    public android.support.v4.app.Fragment f06_StudyGroup = new Frag06_StudyGroup();
    public android.support.v4.app.Fragment f07_Contacts = new Frag07_Contacts();
    public android.support.v4.app.Fragment f09_AppSetting = new Frag09_AppSetting();
    public android.support.v4.app.Fragment f10_Credit = new Frag10_Credit();
    public android.support.v4.app.Fragment f99_ComingSoon = new Frag99_ComingSoon();
    
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light);
		super.onCreate(savedInstanceState);
		mCtx = this;
		//initialize activity
		mPrefs = getSharedPreferences("AppSetting", android.content.Context.MODE_PRIVATE);
		
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
        
        getSupportFragmentManager().beginTransaction().add(R.id.a02_frag_frame, f01_MyPage).commit();

	}
	public void SelectMenu(int Frag){
		FragmentTransaction ft;
		switch (Frag) {
		case 1:
			ft = getSupportFragmentManager().beginTransaction();
//			ft.setCustomAnimations(R.anim.viewin3, R.anim.viewout3);
			ft.replace(R.id.a02_frag_frame, f01_MyPage);
			ft.commit();
			StaticVar.FragPointer = f01_MyPage;
			break;
		case 2:
			ft = getSupportFragmentManager().beginTransaction();
//			ft.setCustomAnimations(R.anim.viewin3, R.anim.viewout3);
			ft.replace(R.id.a02_frag_frame, f99_ComingSoon);
			ft.commit();
			StaticVar.FragPointer = f99_ComingSoon;
			break;
		case 3:
			ft = getSupportFragmentManager().beginTransaction();
//			ft.setCustomAnimations(R.anim.viewin3, R.anim.viewout3);
			ft.replace(R.id.a02_frag_frame, f03_FreeBoard);
			ft.commit();
			StaticVar.FragPointer = f03_FreeBoard;
			break;
		case 4:
			ft = getSupportFragmentManager().beginTransaction();
//			ft.setCustomAnimations(R.anim.viewin3, R.anim.viewout3);
			ft.replace(R.id.a02_frag_frame, f04_AnonymityBoard);
			ft.commit();
			StaticVar.FragPointer = f04_AnonymityBoard;
			break;
		case 5:
			ft = getSupportFragmentManager().beginTransaction();
//			ft.setCustomAnimations(R.anim.viewin3, R.anim.viewout3);
			ft.replace(R.id.a02_frag_frame, f05_Scedule);
			ft.commit();
			StaticVar.FragPointer = f05_Scedule;
			break;
		case 6:
			ft = getSupportFragmentManager().beginTransaction();
//			ft.setCustomAnimations(R.anim.viewin3, R.anim.viewout3);
			ft.replace(R.id.a02_frag_frame, f99_ComingSoon);
			ft.commit();
			StaticVar.FragPointer = f99_ComingSoon;
			break;
		case 7:	
			ft = getSupportFragmentManager().beginTransaction();
//			ft.setCustomAnimations(R.anim.viewin3, R.anim.viewout3);
			ft.replace(R.id.a02_frag_frame, f07_Contacts);
			ft.commit();
			StaticVar.FragPointer = f07_Contacts;
			break;
		case 8:
			SharedPreferences.Editor editor = mPrefs.edit();
            //private info remove
			editor.remove("ID");
			editor.remove("PW");
			editor.remove("Name");
			editor.remove("Birth");
			editor.remove("Gender");
			editor.remove("Grd");
			editor.remove("Level");
			editor.remove("Major");
			editor.remove("Phone");
			editor.remove("Photo");
			editor.remove("Feess");
			
			//app setting remove
            editor.remove("PushAlarm");
            editor.remove("AutoLogin");
            editor.remove("PwUsage");
            editor.remove("AppClosingPW");
            
            editor.commit();
            
			Intent it;
			it = new Intent(mCtx, Act00_Login.class);
			it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(it);
			finish();
			break;
		case 9:
			ft = getSupportFragmentManager().beginTransaction();
//			ft.setCustomAnimations(R.anim.viewin3, R.anim.viewout3);
			ft.replace(R.id.a02_frag_frame, f09_AppSetting);
			ft.commit();
			StaticVar.FragPointer = f09_AppSetting;
			break;
		case 10:
			ft = getSupportFragmentManager().beginTransaction();
//			ft.setCustomAnimations(R.anim.viewin3, R.anim.viewout3);
			ft.replace(R.id.a02_frag_frame, f10_Credit);
			ft.commit();
			StaticVar.FragPointer = f10_Credit;
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
            mAppCloseFlag = false;
        }
    };
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) { 
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(isBaseFrag()){
				if(mAppCloseFlag == false) {
				    Toast.makeText(this, "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
				    mAppCloseFlag = true;
				    AppCloseHandler.sendEmptyMessageDelayed(0, 2000);
				} else { 
					 finish();
				}
			}
	    }
		return true;
		
	}
    private boolean isBaseFrag(){
    	FragmentTransaction ft ;
    	if(StaticVar.FragPointer instanceof Frag01_CheckIn){
    		ft = getSupportFragmentManager().beginTransaction();
			ft.setCustomAnimations(R.anim.viewin4, R.anim.viewout4);
			ft.replace(R.id.a02_frag_frame, f01_MyPage);
			ft.commit();
			StaticVar.FragPointer = f01_MyPage;
    		return false;
    	}else if(StaticVar.FragPointer instanceof Frag01_MyStudyGroup){
    		ft = getSupportFragmentManager().beginTransaction();
			ft.setCustomAnimations(R.anim.viewin4, R.anim.viewout4);
			ft.replace(R.id.a02_frag_frame, f01_MyPage);
			ft.commit();
			StaticVar.FragPointer = f01_MyPage;
    		return false;
    	}else if(StaticVar.FragPointer instanceof Frag01_MyInfo){
    		ft = getSupportFragmentManager().beginTransaction();
			ft.setCustomAnimations(R.anim.viewin4, R.anim.viewout4);
			ft.replace(R.id.a02_frag_frame, f01_MyPage);
			ft.commit();
			StaticVar.FragPointer = f01_MyPage;
    		return false;
    	}else{
    		return true;
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
