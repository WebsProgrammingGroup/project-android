package app.webs.Activity;

import java.util.*;

import org.apache.http.*;
import org.apache.http.message.*;
import org.json.*;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.webs.app.*;

import android.content.*;
import android.os.*;
import android.support.v4.app.*;
import android.util.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout.LayoutParams;
import app.webs.DataType.*;
import app.webs.Util.*;

public class Frag05_Schedule extends android.support.v4.app.Fragment 
	implements OnClickListener, OnItemClickListener, OnKeyListener{
	private static final int DELETE_SCHEDULE_ID = 4;
	private Context mCtx;
	private LayoutInflater inflater;
    
	private EditText Year_et;
	private EditText Month_et;
	private BootstrapButton Go_btn;
	private BootstrapButton AddEvent_btn;
	private BootstrapButton ScheduleDataDate_btn;
	private LinearLayout ScheduleData_lay;
	private ListView ScheduleData_lv;
	private GridView Calendar;	

	private Frag05_AddScedule f05_AddSchedule = new Frag05_AddScedule();
	private DeleteDataOnLongClickListener mDeleteDataOnLongClickListener;
	private ArrayList<String> ArrCalendar;
	private CalendarAdapter mCalendarAdapter;
	
	private ScheduleDataAdapter mScheduleDataAdapter;
	
	private String YearValue;
	private String MonthValue;
	private String DayValue;
	private Frag05_SchduleDataParser mDataParser;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCtx = getActivity();
		inflater = (LayoutInflater)mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View ViewLayout = inflater.inflate(R.layout.frag05_schedule, null, false);
		
		Year_et = (EditText)ViewLayout.findViewById(R.id.f05_et_year);
		Month_et = (EditText)ViewLayout.findViewById(R.id.f05_et_month);
		Go_btn = (BootstrapButton)ViewLayout.findViewById(R.id.f05_btn_search);
		AddEvent_btn = (BootstrapButton)ViewLayout.findViewById(R.id.f05_btn_add_event);
		ScheduleDataDate_btn = (BootstrapButton)ViewLayout.findViewById(R.id.f05_btn_schedule_date);
		ScheduleData_lv = (ListView)ViewLayout.findViewById(R.id.f05_lv_schedule_date);
		ScheduleData_lay = (LinearLayout)ViewLayout.findViewById(R.id.f05_lay_schedule_date);
		Calendar = (GridView)ViewLayout.findViewById(R.id.f05_calendar);
		
		ArrCalendar = new ArrayList<String>();
		mCalendarAdapter = new CalendarAdapter(mCtx,R.layout.frag05_schedule_calendar_item,ArrCalendar);
		Calendar.setOnItemClickListener(this);
		Calendar.setAdapter(mCalendarAdapter);
		
		StaticVar.SchduleWholeData = new ArrayList<ScheduleData>();
		mScheduleDataAdapter = new ScheduleDataAdapter(mCtx, R.layout.frag05_schedule_day_item, StaticVar.SchduleWholeData);
		ScheduleData_lv.setAdapter(mScheduleDataAdapter);
		mDeleteDataOnLongClickListener = new DeleteDataOnLongClickListener(mCtx,DELETE_SCHEDULE_ID,UiHandler);
		ScheduleData_lv.setOnItemLongClickListener(mDeleteDataOnLongClickListener);

		AddEvent_btn.setOnClickListener(this);
		Go_btn.setOnClickListener(this);
		Month_et.setOnKeyListener(this);
		
		// 오늘 날짜를 세팅 해준다.
		Date date = new Date();
		YearValue = String.valueOf(date.getYear() + 1900);
		MonthValue = String.valueOf(date.getMonth() + 1);
		Year_et.setText(YearValue);
		Month_et.setText(MonthValue);
		FillDate(YearValue,MonthValue);
		
		if(Integer.valueOf(StaticVar.mLoginData.Level) >= 4){
			AddEvent_btn.setVisibility(View.VISIBLE);
		}
		
		return ViewLayout;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.f05_btn_search:
			YearValue = Year_et.getText().toString();
			MonthValue = Month_et.getText().toString();
			FillDate(YearValue, MonthValue);
			break;
		case R.id.f05_btn_add_event:
			FragmentTransaction ft;
			ft = getFragmentManager().beginTransaction();
			ft.setCustomAnimations(R.anim.viewin3, R.anim.viewout3);
			ft.replace(R.id.a02_frag_frame, f05_AddSchedule);

			ft.commit();
			StaticVar.FragPointer = f05_AddSchedule;
			break;
		default: 	
			break;
		}		
	}
	
	class CalendarAdapter extends ArrayAdapter<String>{
		private ArrayList<String> arSrc;
		
		public CalendarAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
			super(context, textViewResourceId, objects);
			arSrc = objects;
		}
		
		@Override
        public View getView(int position, View convertView, ViewGroup parent) {
                View view = convertView;
                if (view == null) {
                    view = inflater.inflate(R.layout.frag05_schedule_calendar_item, null);
                }

            	TextView btn = (TextView) view.findViewById(R.id.f05_txt_calendar_item);
                if(arSrc.get(position) != null){
                	btn.setText(arSrc.get(position));
                }
                return view;
        }
	}
	
	class ScheduleDataAdapter extends ArrayAdapter<ScheduleData>{
		private ArrayList<ScheduleData> arSrc;
		
		public ScheduleDataAdapter(Context context, int textViewResourceId, ArrayList<ScheduleData> objects) {
			super(context, textViewResourceId, objects);
			arSrc = objects;
		}
		
		@Override
        public View getView(int position, View convertView, ViewGroup parent) {
                View view = convertView;
                if (view == null) {
                    view = inflater.inflate(R.layout.frag05_schedule_day_item, null);
                }
                
                FontAwesomeText iconView = (FontAwesomeText)view.findViewById(R.id.f05_schdule_day_item_icon);
                
            	TextView titleTextView = (TextView) view.findViewById(R.id.f05_schdule_day_item_title);
                if(arSrc.get(position) != null){
                	titleTextView.setText(arSrc.get(position).Title);
                }
                TextView contentsTextView = (TextView) view.findViewById(R.id.f05_schdule_day_item_contents);
                if(arSrc.get(position) != null){
                	contentsTextView.setText(arSrc.get(position).Contents);
                }

                Log.i("DayS/getView",String.valueOf(position));
                return view;
        }
	}
	
	private void FillDate(String year, String mon) {
		ArrCalendar.clear();

		Date current = new Date(Integer.valueOf(year) - 1900, Integer.valueOf(mon) - 1, 1);
		int day = current.getDay(); // 요일도 int로 저장.
		for (int i = 0; i < day; i++) {
			ArrCalendar.add("");
		}

		current.setDate(32);// 32일까지 입력하면 1일로 바꿔준다.
		int last = 32 - current.getDate();

		for (int i = 1; i <= last; i++) {
			ArrCalendar.add(i + "");
		}
		mCalendarAdapter.notifyDataSetChanged();
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

		if (ArrCalendar.get(position) == null) {

		} else {
			DayValue = ArrCalendar.get(position);
			
			ScheduleData_lay.setVisibility(View.VISIBLE);
			ScheduleDataDate_btn.setText(YearValue + "-" + MonthValue + "-" + DayValue + "  Today's Schedules");

			final ArrayList<NameValuePair> paramList = new ArrayList<NameValuePair>();
			paramList.add(new BasicNameValuePair("today_Year", YearValue));
			paramList.add(new BasicNameValuePair("today_Month", MonthValue));
			paramList.add(new BasicNameValuePair("today_day", DayValue));
			
			mDataParser = new Frag05_SchduleDataParser(mScheduleDataAdapter, mCtx, UiHandler);
			mDataParser.setParamList(paramList);
			mDataParser.start();						
		}
	}
	
	Handler UiHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				//load proccess
				mScheduleDataAdapter.notifyDataSetChanged();
				break;
			case 1:		
				//delete process
				ArrayList<NameValuePair> paramList = new ArrayList<NameValuePair>();
				paramList.add(new BasicNameValuePair("today_Year", YearValue));
				paramList.add(new BasicNameValuePair("today_Month", MonthValue));
				paramList.add(new BasicNameValuePair("today_day", DayValue));
				
				mDataParser = new Frag05_SchduleDataParser(mScheduleDataAdapter, mCtx, UiHandler);
				mDataParser.setParamList(paramList);
				mDataParser.start();
				break;

			default:
				break;
			}
		}
	};
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent e) {
		Log.i("onKey",e.toString());
		if(v.getId() == R.id.f05_et_month && keyCode == KeyEvent.KEYCODE_ENTER && e.getAction() == KeyEvent.ACTION_UP){
			YearValue = Year_et.getText().toString();
			MonthValue = Month_et.getText().toString();
			FillDate(YearValue, MonthValue);
		}
		return false;
	}
}