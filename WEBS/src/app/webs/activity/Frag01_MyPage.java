package app.webs.activity;

import java.util.*;

import android.content.*;
import android.os.*;
import android.support.v4.app.*;
import android.text.*;
import android.util.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.*;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.beardedhen.androidbootstrap.*;
import com.webs.app.R;

public class Frag01_MyPage extends Fragment implements OnClickListener, OnScrollListener{
	private Context mCtx;
	
	private BootstrapButton CheckIn_btn;
	private BootstrapButton MyStudy_btn;
	private BootstrapButton MyInfo_btn;
	private ListView NoticeBoard;
	private LayoutInflater inflater;
	private ArrayList<BoardData> ArrBoardData;
	private ArrayList<BoardData> ArrBoardWholeData;
	private NoticeAdapter mNoticeAdapter;
	
	public Frag01_CheckIn f01_CheckIn = new Frag01_CheckIn();
	public Frag01_MyStudyGroup f01_MyStudyGroup = new Frag01_MyStudyGroup();
	public Frag01_MyInfo f01_MyInfo = new Frag01_MyInfo();

	int currentFirstVisibleItem;
	int currentVisibleItemCount;
	int currentScrollState;
	boolean isLoading = false;
	int BoardDataCount = 0;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCtx = getActivity();
		inflater = (LayoutInflater)mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View ViewLayout = inflater.inflate(R.layout.frag01_my_page, null, false);
		CheckIn_btn = (BootstrapButton)ViewLayout.findViewById(R.id.f01_btn_checkin);
		MyStudy_btn = (BootstrapButton)ViewLayout.findViewById(R.id.f01_btn_my_study_group);
		MyInfo_btn = (BootstrapButton)ViewLayout.findViewById(R.id.f01_btn_my_info);
		NoticeBoard = (ListView)ViewLayout.findViewById(R.id.f01_lv_notice);
		
		CheckIn_btn.setOnClickListener(this);
		MyStudy_btn.setOnClickListener(this);
		MyInfo_btn.setOnClickListener(this);
		
		ArrBoardWholeData = new ArrayList<BoardData>();
		ArrBoardData = new ArrayList<BoardData>();
		BoardData mData = new BoardData();
		mData.Writer = "남건백";
		mData.Title = "제목제목";
		mData.Contents = "컨텐츠컨텐츠";
		mData.UpTime = "1928-32-23 00:00:00";
		
		ArrBoardWholeData.add(mData);
		ArrBoardWholeData.add(mData);
		ArrBoardWholeData.add(mData);
		ArrBoardWholeData.add(mData);
		ArrBoardWholeData.add(mData);
		ArrBoardWholeData.add(mData);
		ArrBoardWholeData.add(mData);
		ArrBoardWholeData.add(mData);
		ArrBoardWholeData.add(mData);
		ArrBoardWholeData.add(mData);
		ArrBoardWholeData.add(mData);
		ArrBoardWholeData.add(mData);
		ArrBoardWholeData.add(mData);
		ArrBoardWholeData.add(mData);
		ArrBoardWholeData.add(mData);
		ArrBoardWholeData.add(mData);
		ArrBoardWholeData.add(mData);
		ArrBoardWholeData.add(mData);
		ArrBoardWholeData.add(mData);
		ArrBoardWholeData.add(mData);
		ArrBoardWholeData.add(mData);
		ArrBoardWholeData.add(mData);
		
		mNoticeAdapter = new NoticeAdapter(ArrBoardData);
		NoticeBoard.setAdapter(mNoticeAdapter);
		NoticeBoard.setOnScrollListener(this);
		LoadMoreBoard();
		
		return ViewLayout;
	}
	
	@Override
	public void onClick(View v) {
		FragmentTransaction ft;
		switch (v.getId()) {
		case R.id.f01_btn_checkin:
			ft = getFragmentManager().beginTransaction();
			ft.setCustomAnimations(R.anim.viewin3, R.anim.viewout3);
			ft.replace(R.id.a02_frag_frame, f01_CheckIn);
			
			ft.commit();
			StaticVar.FragPointer = f01_CheckIn;
			break;
		case R.id.f01_btn_my_study_group:
			ft = getFragmentManager().beginTransaction();
			ft.setCustomAnimations(R.anim.viewin3, R.anim.viewout3);
			ft.replace(R.id.a02_frag_frame, f01_MyStudyGroup);
			
			ft.commit();
			StaticVar.FragPointer = f01_MyStudyGroup;
			break;
		case R.id.f01_btn_my_info:
			ft = getFragmentManager().beginTransaction();
			ft.setCustomAnimations(R.anim.viewin3, R.anim.viewout3);
			ft.replace(R.id.a02_frag_frame, f01_MyInfo);
			
			ft.commit();
			StaticVar.FragPointer = f01_MyInfo;
			break;
		default:
			break;
		}
	}

	
	class NoticeAdapter extends BaseAdapter{
		private ArrayList<BoardData> arSrc;
		
		public NoticeAdapter(ArrayList<BoardData> aarSrc) {
			arSrc = aarSrc;
		}
		
		@Override
		public int getCount() {
			return arSrc.size();
		}

		@Override
		public Object getItem(int pos) {
			return arSrc.get(pos);
		}

		@Override
		public long getItemId(int pos) {
			return arSrc.get(pos).WriterIdx;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final int pos = position;
			BoardData data = ArrBoardData.get(pos);
			
			Log.e("view", "d"+pos);
            View view = inflater.inflate(R.layout.frag01_notice_list_item, null);
            
            TextView Name = (TextView)view.findViewById(R.id.f01_item_name);
            Name.setText(data.Writer);
            TextView Time = (TextView)view.findViewById(R.id.f01_item_time);
            Time.setText(data.UpTime);
            TextView Title = (TextView)view.findViewById(R.id.f01_item_txt_title);
            Title.setText(data.Title);
            TextView Contents = (TextView)view.findViewById(R.id.f01_item_txt_contents);
            Contents.setText(data.Contents);
            
            final LinearLayout ContentsBox_U = (LinearLayout)view.findViewById(R.id.f01_item_lay_box_up);
            final LinearLayout ContentsBox_D = (LinearLayout)view.findViewById(R.id.f01_item_lay_box_down);
            
            LinearLayout Whole = (LinearLayout)view.findViewById(R.id.f01_item_lay_whole);
            Whole.setOnClickListener(new OnClickListener() {
				boolean show = false;
				
				@Override
				public void onClick(View v) {
					if(show){
						ContentsBox_U.setVisibility(View.GONE);
						ContentsBox_D.setVisibility(View.GONE);
						show = false;
					}else{
						ContentsBox_U.setVisibility(View.VISIBLE);
						ContentsBox_D.setVisibility(View.VISIBLE);
						show = true;
					}
				}
			});
            
            return view;
		}
	}
	
	/* ListView auto loading when view arrive at the end of list*/
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
	    this.currentFirstVisibleItem = firstVisibleItem;
	    this.currentVisibleItemCount = visibleItemCount;
	}

	public void onScrollStateChanged(AbsListView view, int scrollState) {
	    this.currentScrollState = scrollState;
	    this.isScrollCompleted();
	 }

	private synchronized void isScrollCompleted() {
	    if (this.currentVisibleItemCount > 0 && this.currentScrollState == SCROLL_STATE_IDLE) {
	    	LoadMoreBoard();
	    }
	}
	
	void LoadMoreBoard(){
		for(int i=0 ; i<5 ; i++){
			if(ArrBoardWholeData.size() > ArrBoardData.size()){
				ArrBoardData.add(ArrBoardWholeData.get(BoardDataCount++));
				mNoticeAdapter.notifyDataSetChanged();
			}
		}
	}
	
}
class BoardData{
	public int BoardIdx;
	public int WriterIdx;
	public String Writer;
	public String Title;
	public String Contents;
	public String UpTime;
}
