package app.webs.activity;

import java.util.*;

import android.content.*;
import android.os.*;
import android.support.v4.app.*;
import android.util.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AbsListView.OnScrollListener;
import app.webs.imageloader.*;

import com.beardedhen.androidbootstrap.*;
import com.webs.app.R;

public class Frag03_FreeBoard extends android.support.v4.app.Fragment
		implements OnClickListener, OnScrollListener{
	private Context mCtx;
	private LayoutInflater inflater;
	
	private ListView FreeBoard_lv;
	private FreeBoardAdapter mFreeBoardAdapter;
	private Frag03_FreeBoardDataParser mDataParser;
	private BootstrapButton WritePost_btn;
	
	private int currentFirstVisibleItem;
	private int currentVisibleItemCount;
	private int currentScrollState;
	private boolean isLoading = false;
	public static int BoardDataCount = 0;
	
	public Frag03_WritePost frag03_WritePost = new Frag03_WritePost();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCtx = getActivity();
		inflater = (LayoutInflater)mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View ViewLayout = inflater.inflate(R.layout.frag03_free_board, null, false);
		FreeBoard_lv = (ListView)ViewLayout.findViewById(R.id.f03_lv_free_board);
		WritePost_btn = (BootstrapButton)ViewLayout.findViewById(R.id.f03_btn_wirte_post);
		
		WritePost_btn.setOnClickListener(this);
		
		StaticVar.FreeBoardWholeData = new ArrayList<BoardData>();
		StaticVar.FreeBoardData = new ArrayList<BoardData>();
		
		mFreeBoardAdapter = new FreeBoardAdapter(StaticVar.FreeBoardData);
		mDataParser = new Frag03_FreeBoardDataParser(mFreeBoardAdapter, mCtx);
		mDataParser.start();
		
		FreeBoard_lv.setAdapter(mFreeBoardAdapter);
		FreeBoard_lv.setOnScrollListener(this);
		
		BoardDataCount = StaticVar.FreeBoardData.size();
		
		return ViewLayout;
	}

	@Override
	public void onClick(View v) {
		FragmentTransaction ft;
		switch (v.getId()) {
		case R.id.f03_btn_wirte_post:
			ft = getFragmentManager().beginTransaction();
			ft.setCustomAnimations(R.anim.viewin3, R.anim.viewout3);
			ft.replace(R.id.a02_frag_frame, frag03_WritePost);
			ft.commit();
			StaticVar.FragPointer = frag03_WritePost;
			break;
		default:
			break;
		}
		
	}
	
	class FreeBoardAdapter extends BaseAdapter{
		private ArrayList<BoardData> arSrc;
		
		public FreeBoardAdapter(ArrayList<BoardData> aarSrc) {
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
            View view = inflater.inflate(R.layout.frag03_free_board_list_item, null);
            final LinearLayout ContentsBox_U = (LinearLayout)view.findViewById(R.id.f03_item_lay_box_up);
            final LinearLayout ContentsBox_M = (LinearLayout)view.findViewById(R.id.f03_item_lay_box_mid);
            final LinearLayout ContentsBox_D = (LinearLayout)view.findViewById(R.id.f03_item_lay_box_down);
            
            TextView Name = (TextView)view.findViewById(R.id.f03_item_name);
            Name.setText(data.Writer);
            
            TextView Time = (TextView)view.findViewById(R.id.f03_item_date);
            Time.setText(data.Date.substring(0, 16));
            
            TextView Title = (TextView)view.findViewById(R.id.f03_item_title);
            Title.setText(data.Title);
            
            TextView Title2 = (TextView)view.findViewById(R.id.f03_item_title2);
            Title2.setText(data.Title);
            
            TextView Contents = (TextView)view.findViewById(R.id.f03_item_txt_contents);
            Contents.setText(data.Contents);
            
            TextView BoardNumber = (TextView)view.findViewById(R.id.f03_item_num);
            BoardNumber.setText("No."+data.BoardIdx);
            
            LinearLayout Whole = (LinearLayout)view.findViewById(R.id.f03_item_lay_whole);
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
			
			TextView Comments = (TextView)view.findViewById(R.id.f03_item_comment);
			Comments.setText("댓글 "+data.Comment+"개");
			
			Comments.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mCtx, Act03_ShowComment.class);
					intent.putExtra("PostID", data.BoardIdx);
					intent.putExtra("Type", 2);
					startActivity(intent);
				}
			});

			ImageView Photo_iv = (ImageView)view.findViewById(R.id.f03_item_photo);
			ImageLoader mImageLoader = new ImageLoader(mCtx);
			mImageLoader.DisplayImage(StaticVar.ImageBaseUrl+data.WriterIdx+".jpg", Photo_iv);
			
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
			Log.i("LoadMore", "Whole:"+StaticVar.FreeBoardWholeData.size()+"&Data:"+StaticVar.FreeBoardData.size());
			if(StaticVar.FreeBoardWholeData.size() > StaticVar.FreeBoardData.size()){
				StaticVar.FreeBoardData.add(StaticVar.FreeBoardWholeData.get(BoardDataCount++));
				mFreeBoardAdapter.notifyDataSetChanged();
			}
		}
	}
}
