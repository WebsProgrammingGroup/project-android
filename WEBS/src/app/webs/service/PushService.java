package app.webs.service;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.params.*;
import org.json.*;

import com.webs.app.*;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.support.v4.app.*;
import android.telephony.*;
import android.util.*;
import app.webs.activity.*;

public class PushService extends android.app.Service{
	private static SharedPreferences mPrefs;
	private final IBinder mBinder = new LocalBinder();
	private NotificationManager NM = null;
	private Vibrator Vib;
	private Bitmap LargeIcon;
<<<<<<< HEAD
=======
	private Bitmap LargeIcon_Birth;
>>>>>>> Ver1.0
	private Notification Noti;
	private String UUID;
	private String PushServerUrl;
	private static PushThread thread;
	private static boolean isStart = false;
	
	// 컴포넌트에 반환해줄 IBinder를 위한 클래스
	public class LocalBinder extends Binder{
		public PushService getService(){
			return PushService.this;
		}
	}
	private void clearApplicationCache(java.io.File dir){
        if(dir==null)
            dir = getCacheDir();
        else;
        if(dir==null)
            return;
        else;
        java.io.File[] children = dir.listFiles();
        try{
            for(int i=0;i<children.length;i++)
                if(children[i].isDirectory())
                    clearApplicationCache(children[i]);
                else children[i].delete();
        }
        catch(Exception e){}
    }
	
	public void onCreate(){
		Log.i("ConnectService", "onCreate");
		mPrefs = getSharedPreferences("AppSetting", android.content.Context.MODE_PRIVATE);
    	StaticVar.isPushAlarm = mPrefs.getBoolean("PushAlarm", true);
    	
		Vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		NM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		UUID = GetDevicesUUID(getApplicationContext());
		PushServerUrl = StaticVar.PushMessageUrl + "?uuid=" + UUID;
		thread = new PushThread();
<<<<<<< HEAD
		LargeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
=======
		LargeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.noti);
		LargeIcon_Birth = BitmapFactory.decodeResource(getResources(), R.drawable.noti_birth);
>>>>>>> Ver1.0
		unregisterRestartAlarm();
	}
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	
	void registerRestartAlarm() {
		Log.d("D", "registerRestartAlarm");
	    Intent intent = new Intent(this, ServiceRestart.class);
	    intent.setAction(ServiceRestart. ACTION_RESTART_PERSISTENTSERVICE);
	    
	    PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);
	    long firstTime = SystemClock.elapsedRealtime();
	    firstTime += 1000; // 10초 후에 알람이벤트 발생
	    
	    AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
	    am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, 10*1000, sender);
	}
	void unregisterRestartAlarm() {
	    Log.d("D", "unregisterRestartAlarm");
	    Intent intent = new Intent(this, ServiceRestart.class);
	    
	    intent.setAction(ServiceRestart.ACTION_RESTART_PERSISTENTSERVICE);
	    PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);
	    
	    AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
	    am.cancel(sender);
	}
	public void CreateNotification(String title, String contents, String img){
<<<<<<< HEAD
		Noti = new NotificationCompat.Builder(getApplicationContext())
		.setContentTitle(title)
		.setContentText(contents)
<<<<<<< HEAD
		.setSmallIcon(R.drawable.ic_launcher)
=======
		.setSmallIcon(R.drawable.noti_large)
>>>>>>> Ver1.0
		.setLargeIcon(LargeIcon)
		.setTicker(contents)
		.setAutoCancel(true)
		.build();
	
	    NM.notify(11234, Noti);
=======
		if(StaticVar.isPushAlarm){
			Noti = new NotificationCompat.Builder(getApplicationContext())
			.setContentTitle(title)
			.setContentText(contents)
			.setSmallIcon(R.drawable.noti_large)
			.setLargeIcon(LargeIcon)
			.setTicker(contents)
			.setAutoCancel(true)
			.setDefaults(Notification.DEFAULT_ALL)
			.setStyle(new NotificationCompat.BigTextStyle().bigText(contents))
			.build();
		
		    NM.notify(11234, Noti);
		}
>>>>>>> origin/gunbaek
	}
	public int onStartCommand(Intent intent, int flags, int startId){
		if(!isStart){
			thread.start();
		}
		return START_REDELIVER_INTENT;
	}
	private class PushThread extends Thread implements Runnable {
		public void run() {
			isStart = true;
			DefaultHttpClient client = null ;
			try {
				while(true){
					clearApplicationCache(null);
					client = new DefaultHttpClient();
					Log.i("url", PushServerUrl);
					HttpGet HG = new HttpGet();
					HG.setURI(new URI(PushServerUrl));
					HttpResponse response = client.execute(HG);
	
					HttpParams params = client.getParams();
					HttpConnectionParams.setConnectionTimeout(params, 3000);
					HttpConnectionParams.setSoTimeout(params, 3000);
					response.setParams(params);
					
					BufferedReader bufreader = new BufferedReader(
							new InputStreamReader(response.getEntity().getContent(), "utf-8"));
					String line = null;
					String result = "";
	
					while ((line = bufreader.readLine()) != null) {
						result += line; 
					}
					Log.i("res",result);
					if(!result.equals("null") && !result.equals("")){
						JSONObject json = new JSONObject(result);														
						JSONArray jArr = json.getJSONArray("PushArr");	
						int compare = 0;
						
						for (int i = 0; i < jArr.length(); i++) {
							json = jArr.getJSONObject(i);
	
							int pushId = json.getInt("id");
							String title = json.getString("title");
							String contents = json.getString("Contents-str");
							String img = json.getString("Contents-img");
							String year = json.getString("year");
							String month = json.getString("month");
							String day = json.getString("day");
							String hour = json.getString("hour");
							
							SimpleDateFormat Formatter = new SimpleDateFormat("yyyy-MM-dd-hh", Locale.KOREA);
							Date currentTime = new Date ();
							Date PushDate = Formatter.parse(year + "-" + month + "-" + day + "-" + hour);
							compare = currentTime.compareTo(PushDate);
							
							if(compare > 0){//지정 시각이 지났을 경우
								Log.i("compare", compare+"현재:"+currentTime.toString()+"/비교:"+PushDate.toString());
								
								Date yesterday = new Date ( );
							    yesterday.setTime ( currentTime.getTime() - ((long) 1000 * 60 * 60 * 24 ) );
							     
								compare = yesterday.compareTo(PushDate);
								if(compare < 0){//하루가 안넘었을 경우에만
									CreateNotification(title, contents, img);
								}Log.i("compare2", compare+"어제:"+yesterday.toString()+"/비교:"+PushDate.toString());
								
								HttpGet HG2 = new HttpGet();
								HG2.setURI(new URI(StaticVar.PushConfirmUrl+"?uuid="+UUID+"&pushid="+pushId));
								client.execute(HG2);
								break;
							}
						}	
					}
					Thread.sleep(300000);
				}
			} catch (Exception e) {
				e.printStackTrace();
				client.getConnectionManager().shutdown(); // 연결 지연 종료
			}
		}
	}
	private String GetDevicesUUID(Context aContext) {
		final TelephonyManager tm = (TelephonyManager) aContext.getSystemService(Context.TELEPHONY_SERVICE);

		final String tmDevice, tmSerial, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = ""
		+ android.provider.Settings.Secure.getString(
		getContentResolver(),
		android.provider.Settings.Secure.ANDROID_ID);

		UUID deviceUuid = new UUID(androidId.hashCode(),
		((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		String deviceId = deviceUuid.toString();

		return deviceId;
	}
}