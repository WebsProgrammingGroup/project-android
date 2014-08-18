package app.webs.activity;

import android.app.*;
import android.content.*;
import android.graphics.Shader.*;
import android.graphics.drawable.*;
import android.os.*;
import android.support.v4.app.Fragment;
import android.util.*;
import android.view.*;

import com.actionbarsherlock.app.*;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.*;
import com.jeremyfeinstein.slidingmenu.lib.app.*;
import com.webs.app.*;
import com.webs.app.R;

public class Act02_BaseActivity extends SlidingFragmentActivity  {
	public Context mCtx;

    public android.support.v4.app.Fragment f01_MyPage = new Frag01_MyPage();
    public android.support.v4.app.Fragment f02_MyTimeTable = new Frag02_MyTimeTable();
    public android.support.v4.app.Fragment f03_FreeBoard = new Frag03_FreeBoard();
    public android.support.v4.app.Fragment f04_AnonymityBoard = new Frag04_AnonymityBoard();
    public android.support.v4.app.Fragment f05_Gallery = new Frag05_Gallery();
    public android.support.v4.app.Fragment f06_StudyGroup = new Frag06_StudyGroup();
    public android.support.v4.app.Fragment f07_Contacts = new Frag07_Contacts();
    public android.support.v4.app.Fragment f08_Logout = new Frag08_Logout();
    public android.support.v4.app.Fragment f09_AppSetting = new Frag09_AppSetting();
    public android.support.v4.app.Fragment f10_Credit = new Frag10_Credit();
    
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light);
		super.onCreate(savedInstanceState);
		mCtx = this;
		//initialize activitydd
		
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
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		//getSupportActionBar().setIcon(R.drawable.app_ic);
		getSupportActionBar().setHomeButtonEnabled(true);
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
			getSupportFragmentManager().beginTransaction().replace(R.id.a02_frag_frame, f08_Logout).commit();
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
}
