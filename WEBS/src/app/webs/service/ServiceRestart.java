package app.webs.Service;

import android.content.*;
import app.webs.Activity.*;

public class ServiceRestart extends BroadcastReceiver{
	public static final String ACTION_RESTART_PERSISTENTSERVICE = "ACTION.Restart.PersistentService";
	@Override
	public void onReceive(Context context, Intent intent) {
		if(!StaticVar.isBound){
			if(intent.getAction().equals(ACTION_RESTART_PERSISTENTSERVICE)) {
				Intent i = new Intent(null, PushService.class);
				context.startService(i);
				StaticVar.isBound = true;
			}else if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
				Intent i = new Intent(null, PushService.class);
				context.startService(i);
				StaticVar.isBound = true;
			}
		}
	}
}
