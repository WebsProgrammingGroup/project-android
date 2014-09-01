package app.webs.activity;

import java.util.*;

import android.content.*;
import android.support.v4.app.*;
import app.webs.service.*;
import app.webs.util.*;

public class StaticVar {
	//URL Variables
	public static String LoginUrl = "http://wpg.azurewebsites.net/webs_login.jsp";
	public static String JoinUrl = "http://wpg.azurewebsites.net/webs_join.jsp";
	public static String NoticeUrl = "http://wpg.azurewebsites.net/webs_notice_list.jsp";
	public static String ScheduleUrl = "http://wpg.azurewebsites.net/webs_schedule.jsp";
	public static String ContactsUrl = "http://wpg.azurewebsites.net/webs_contacts.jsp";
	public static String ImageBaseUrl = "http://wpg.azurewebsites.net/upload/";
	public static String FreeBoardUrl = "http://wpg.azurewebsites.net/webs_free_board_list.jsp";
	public static String AddFreeBoardUrl = "http://wpg.azurewebsites.net/webs_add_free_board.jsp";
	public static String AnomyBoardUrl = "http://wpg.azurewebsites.net/webs_anony_board_list.jsp";
	public static String AddAnonyBoardUrl = "http://wpg.azurewebsites.net/webs_add_anony_board.jsp";
	public static String AddPushMessageUrl = "http://wpg.azurewebsites.net/webs_add_notification.jsp";
	public static String PushMessageUrl = "http://wpg.azurewebsites.net/webs_push_msg.jsp";
	public static String PushConfirmUrl = "http://wpg.azurewebsites.net/webs_push_confirm.jsp";
	
	//Service Variables
	public static Boolean isBound = false;
	public static ServiceConnection mServiceConncetion = null;
	public static PushService mService = null;
	
	//Board Variables
	public static ArrayList<ContactData> ContactWholeData = null;
	public static ArrayList<ContactData> ContactData = null;
	public static ArrayList<BoardData> NoticeBoardWholeData = null;
	public static ArrayList<BoardData> NoticeBoardData = null;
	public static ArrayList<BoardData> FreeBoardWholeData = null;
	public static ArrayList<BoardData> FreeBoardData = null;
	public static ArrayList<BoardData> AnonyBoardWholeData = null;
	public static ArrayList<BoardData> AnonyBoardData = null;
	
	//App Setting Variables
	public static String ID = null;
	public static LoginData mLoginData = null;
	public static String AppClosingPW = null;
	public static Boolean isPushAlarm = false;
	public static Boolean isAutoLogin = false;
	public static Boolean isAppClose = false;
	public static Fragment FragPointer = null;
	
}
