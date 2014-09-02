package app.webs.activity;

import java.util.*;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.webs.app.*;

import android.content.*;
import android.os.*;
import android.support.v4.app.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.AbsListView.*;
import app.webs.activity.Frag01_MyPage.*;
import app.webs.imageloader.*;
import app.webs.util.*;

public class Frag04_AnonyBoard extends android.support.v4.app.Fragment implements OnClickListener, OnScrollListener{
	private Context mCtx;
	private LayoutInflater inflater;
	
	private ListView AnonyBoard_lv;
	private AnomyBoardAdapter mAnomyBoardAdapter;
	private Frag04_AnomyBoardDataParser mDataParser;
	private BootstrapButton WritePost_btn;
	
	private int currentFirstVisibleItem;
	private int currentVisibleItemCount;
	private int currentScrollState;
	private boolean isLoading = false;
	public static int BoardDataCount = 0;
	
	public Frag04_WritePost frag04_WritePost = new Frag04_WritePost();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCtx = getActivity();
		inflater = (LayoutInflater)mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View ViewLayout = inflater.inflate(R.layout.frag04_anonymity_board, null, false);
		AnonyBoard_lv = (ListView)ViewLayout.findViewById(R.id.f04_lv_anony_board);
		WritePost_btn = (BootstrapButton)ViewLayout.findViewById(R.id.f04_btn_wirte_post);
		
		WritePost_btn.setOnClickListener(this);
		
		StaticVar.AnonyBoardWholeData = new ArrayList<BoardData>();
		StaticVar.AnonyBoardData = new ArrayList<BoardData>();
		
		mAnomyBoardAdapter = new AnomyBoardAdapter(StaticVar.AnonyBoardData);
		mDataParser = new Frag04_AnomyBoardDataParser(mAnomyBoardAdapter, mCtx);
		mDataParser.start();
		
		AnonyBoard_lv.setAdapter(mAnomyBoardAdapter);
		AnonyBoard_lv.setOnScrollListener(this);
		
		BoardDataCount = StaticVar.AnonyBoardData.size();
		
		return ViewLayout;
	}

	@Override
	public void onClick(View v) {
		FragmentTransaction ft;
		switch (v.getId()) {
		case R.id.f04_btn_wirte_post:
			ft = getFragmentManager().beginTransaction();
			ft.setCustomAnimations(R.anim.viewin3, R.anim.viewout3);
			ft.replace(R.id.a02_frag_frame, frag04_WritePost);
			ft.commit();
			StaticVar.FragPointer = frag04_WritePost;
			break;
		default:
			break;
		}
		
	}
	
	class AnomyBoardAdapter extends BaseAdapter{
		private ArrayList<BoardData> arSrc;
		
		public AnomyBoardAdapter(ArrayList<BoardData> aarSrc) {
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
			final BoardData data = arSrc.get(pos);
			
			Log.e("view", "d"+pos);
            View view = inflater.inflate(R.layout.frag04_anony_board_list_item, null);
            final LinearLayout ContentsBox_U = (LinearLayout)view.findViewById(R.id.f04_item_lay_box_up);
            final LinearLayout ContentsBox_M = (LinearLayout)view.findViewById(R.id.f04_item_lay_box_mid);
            final LinearLayout ContentsBox_D = (LinearLayout)view.findViewById(R.id.f04_item_lay_box_down);
            
            TextView Name = (TextView)view.findViewById(R.id.f04_item_name);
            Name.setText("누군가");
            
            TextView Time = (TextView)view.findViewById(R.id.f04_item_date);
            Time.setText(data.Date.substring(0, 16));
            
            TextView Title = (TextView)view.findViewById(R.id.f04_item_title);
            Title.setText(data.Title);
            
            TextView Title2 = (TextView)view.findViewById(R.id.f04_item_title2);
            Title2.setText(data.Title);
            
            TextView Contents = (TextView)view.findViewById(R.id.f04_item_txt_contents);
            Contents.setText(data.Contents);
            
            TextView BoardNumber = (TextView)view.findViewById(R.id.f04_item_num);
            BoardNumber.setText("No."+data.BoardIdx);
            
            LinearLayout Whole = (LinearLayout)view.findViewById(R.id.f04_item_lay_whole);
            OnClickListener mOnClick = new OnClickListener() {
				boolean show = false;
				
				@Override
				public void onClick(View v) {
					if(show){
						ContentsBox_U.setVisibility(View.GONE);
						ContentsBox_M.setVisibility(View.GONE);
						ContentsBox_D.setVisibility(View.GONE);
						show = false;
					}else{
						ContentsBox_U.setVisibility(View.VISIBLE);
						ContentsBox_M.setVisibility(View.VISIBLE);
						ContentsBox_D.setVisibility(View.VISIBLE);
						show = true;
					}
				}
			};
			
			TextView Comments = (TextView)view.findViewById(R.id.f04_item_comment);
			Comments.setText("댓글 "+data.Comment+"개");
			
			Comments.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mCtx, Act03_ShowComment.class);
					intent.putExtra("PostID", data.BoardIdx);
					intent.putExtra("Type", 3);
					startActivity(intent);
				}
			});
			
            Whole.setOnClickListener(mOnClick);
            
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
	
	private void LoadMoreBoard(){
		for(int i=0 ; i<10 ; i++){
			Log.i("LoadMore", "Whole:"+StaticVar.AnonyBoardWholeData.size()+"&Data:"+StaticVar.AnonyBoardData.size());
			if(StaticVar.AnonyBoardWholeData.size() > StaticVar.AnonyBoardData.size()){
				StaticVar.AnonyBoardData.add(StaticVar.AnonyBoardWholeData.get(BoardDataCount++));
				mAnomyBoardAdapter.notifyDataSetChanged();
			}
		}
	}
}
