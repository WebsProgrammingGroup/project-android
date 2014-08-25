package app.webs.activity;

import java.util.*;

import android.content.*;
import app.webs.service.*;

public class StaticVar {
	public static Boolean isBound = false;
	public static ServiceConnection mServiceConncetion = null;
	public static PushService mService = null;
	public static LoginData mLoginData= null;
	
	public static String ID = null;
	
	public static Boolean isPushAlarm = false;
	public static Boolean isAutoLogin = false;
	public static Boolean isPwUsage = false;

}
