package app.webs.activity;

import java.util.*;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.webs.app.*;

import android.content.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout.LayoutParams;

public class Frag05_Schedule extends android.support.v4.app.Fragment 
	implements OnClickListener, OnItemClickListener{
	private Context mCtx;
	
	private EditText Year_et;
	private EditText Month_et;
	private BootstrapButton Go_btn;
	private BootstrapButton AddEvent_btn;
	private BootstrapButton DayScheduleDate_btn;
	private LinearLayout DaySchedule_lay;
	private ListView DaySchedule_lv;
	private GridView Calendar;	
	
	private ArrayList<String> mItems;
	private CalendarAdapter adapter;
	
	private int YearValue;
	private int MonthValue;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCtx = getActivity();
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View ViewLayout = inflater.inflate(R.layout.frag05_schedule, null, false);
		
		Year_et = (EditText)ViewLayout.findViewById(R.id.f05_et_year);
		Month_et = (EditText)ViewLayout.findViewById(R.id.f05_et_month);
		Go_btn = (BootstrapButton)ViewLayout.findViewById(R.id.f05_btn_search);
		AddEvent_btn = (BootstrapButton)ViewLayout.findViewById(R.id.f05_btn_add_event);
		DayScheduleDate_btn = (BootstrapButton)ViewLayout.findViewById(R.id.f05_btn_schedule_date);
		DaySchedule_lv = (ListView)ViewLayout.findViewById(R.id.f05_lv_schedule_date);
		DaySchedule_lay = (LinearLayout)ViewLayout.findViewById(R.id.f05_lay_schedule_date);
		
		GridView gird = (GridView)ViewLayout.findViewById(R.id.f05_calendar);
		mItems = new ArrayList<String>();
		adapter = new CalendarAdapter(mCtx,R.layout.frag05_schedule_calendar_item,mItems);
		
		Go_btn.setOnClickListener(this);
		gird.setOnItemClickListener(this);
		gird.setAdapter(adapter);
		
		// 오늘 날짜를 세팅 해준다.
		Date date = new Date();
		YearValue = date.getYear() + 1900;
		MonthValue = date.getMonth() + 1;
		Year_et.setText(String.valueOf(YearValue));
		Month_et.setText(String.valueOf(MonthValue));
		fillDate(YearValue,MonthValue);
		
		return ViewLayout;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.f05_btn_search:
			int year = Integer.parseInt(Year_et.getText().toString());
			int mon = Integer.parseInt(Month_et.getText().toString());
			fillDate(year, mon);
			break;
		case R.id.f05_btn_add_event:
			
			break;
		default: 	
			break;
		}		
	}
	private class CalendarAdapter extends ArrayAdapter<String>{
		private ArrayList<String> arSrc;
		
		public CalendarAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
			super(context, textViewResourceId, objects);
			arSrc = objects;
		}
		
		@Override
        public View getView(int position, View convertView, ViewGroup parent) {
                View view = convertView;
                if (view == null) {
                    LayoutInflater inflater = (LayoutInflater)mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = inflater.inflate(R.layout.frag05_schedule_calendar_item, null);
                }

            	TextView btn = (TextView) view.findViewById(R.id.f05_txt_calendar_item);
                if(arSrc.get(position) != null){
                	btn.setText(arSrc.get(position));
                }
                
                return view;
        }
	}
	private void fillDate(int year, int mon) {
		mItems.clear();

		Date current = new Date(year - 1900, mon - 1, 1);
		int day = current.getDay(); // 요일도 int로 저장.

		for (int i = 0; i < day; i++) {
			mItems.add("");
		}

		current.setDate(32);// 32일까지 입력하면 1일로 바꿔준다.
		int last = 32 - current.getDate();

		for (int i = 1; i <= last; i++) {
			mItems.add(i + "");
		}
		adapter.notifyDataSetChanged();
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		
		if (mItems.get(position) == null) {

		} else {
			DaySchedule_lay.setVisibility(View.VISIBLE);
			DayScheduleDate_btn.setText(YearValue + "-" + MonthValue + "-" + mItems.get(position) 
					+ "  Today's Schedules");
			
//			Intent intent = new Intent(this, WEBS_SCHEDULE_TODAY.class);//해당 일을 눌렸을때
//			intent.putExtra("tMonth", tM);
//			intent.putExtra("tYear", tY);
//			intent.putExtra("position", mItems.get(position));
//			
//			String c= String.valueOf(position);
//			Log.d("month",textMon.getText().toString());
//			
//			startActivity(intent);
		}
	}
}
