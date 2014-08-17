package com.example.lastactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class BaseActivity extends SlidingFragmentActivity {

	protected ListFragment mFrag;

	public BaseActivity() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager()
					.beginTransaction();
			mFrag = new MenuListFragment();
			t.replace(R.id.menu_frame, mFrag);
			t.commit();
		} else {
			mFrag = (ListFragment) this.getSupportFragmentManager()
					.findFragmentById(R.id.menu_frame);
		}

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public void fragmentReplace(int reqNewFragmentIndex) {

		Fragment newFragment =null;
		
		switch (reqNewFragmentIndex) {
		case 0:
			Intent intent0 =new Intent(this,WEBS_MYPAGE.class);
			startActivity(intent0);
			break;
		case 1:
			Log.d("s", "s");
			Intent intent1 =new Intent(this,WEBS_Board.class);
			startActivity(intent1);
			break;
		case 2:
			Intent intent2 =new Intent(this,WEBS_SCHEDULE.class);
			startActivity(intent2);
			break;
		case 4:
			Intent intent4 = new Intent(this,Fragment2.class);
			startActivity(intent4);
			break;
		case 5:
			Intent intent5 =new Intent(this,WEBS_OB_YB_NOTICE.class);
			startActivity(intent5);
			break;
		case 6:
			Intent intent6 =new Intent(this,WEBS_STUDY.class);
			startActivity(intent6);
			break;
		default:
			break;

		}

	}

	

}