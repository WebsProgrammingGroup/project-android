package com.example.lastactivity;

import com.example.lastactivity.PushService.LocalBinder;

import android.app.Activity;
import android.content.*;
import android.os.*;
import android.util.*;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class First_Page extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_page);
		Button btn1 = (Button)findViewById(R.id.button1);
		btn1.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent =new Intent(this, Main_menu.class);
		startActivity(intent);
	}
    protected void onStart(){
    	StaticVar.isStarted = true;
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
