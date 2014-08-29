package app.webs.activity;

import java.util.*;

import android.content.*;
import android.support.v4.app.*;
import app.webs.service.*;

public class StaticVar {
	public static Boolean isBound = false;
	public static ServiceConnection mServiceConncetion = null;
	public static PushService mService = null;
	
	public static LoginData mLoginData = null;
	public static ArrayList<ContactData> mContactData = null;
	public static ArrayList<ContactData> mSearchContactData = null;
	
	public static String ID = null;
	public static String AppClosingPW = null;
	
	public static Boolean isPushAlarm = false;
	public static Boolean isAutoLogin = false;
	public static Boolean isAppClose = false;
	
	public static Fragment FragPointer = null;
}
