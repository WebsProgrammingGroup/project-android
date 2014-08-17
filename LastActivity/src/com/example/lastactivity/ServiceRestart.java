package com.example.lastactivity;

import android.content.*;

public class ServiceRestart extends BroadcastReceiver{
	public static final String ACTION_RESTART_PERSISTENTSERVICE = "ACTION.Restart.PersistentService";
	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals(ACTION_RESTART_PERSISTENTSERVICE)) {
			Intent i = new Intent(null, PushService.class);
			context.startService(i);
		}
	}
}
