package app.webs.activity;

import android.content.*;
import android.graphics.Shader.*;
import android.graphics.drawable.*;
import android.os.*;
import android.util.*;
import android.view.*;

import com.actionbarsherlock.app.*;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.*;
import com.jeremyfeinstein.slidingmenu.lib.app.*;
import com.webs.app.*;
import com.webs.app.R;

public class Act02_MyPage extends SlidingFragmentActivity  {
	public Context mCtx;
	
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
        
    	setContentView(R.layout.frag01_my_page);

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
