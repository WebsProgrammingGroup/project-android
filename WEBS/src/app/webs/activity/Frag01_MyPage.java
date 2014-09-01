package app.webs.activity;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapThumbnail;
import com.webs.app.R;

public class Frag01_MyPage extends Fragment implements OnClickListener,
		OnScrollListener {
	private Context mCtx;

	private FileInputStream mFileInputStream = null;
	private URL connectUrl = null;
	private String lineEnd = "\r\n";
	private String twoHyphens = "--", boundary = "*****";
	private String imgurl = "http://wpg.azurewebsites.net/upload/"+StaticVar.ID+".jpg";
	Bitmap bmImg;
	back task;
	
	private BootstrapButton CheckIn_btn;
	private BootstrapButton MyStudy_btn;
	private BootstrapButton MyInfo_btn;
	private ListView NoticeBoard;
	private LayoutInflater inflater;
	private NoticeAdapter mNoticeAdapter;
	private Frag01_NoticeDataParser mDataParser;
	private ImageView MyPhoto;
	private DataOutputStream dos;

	public Frag01_CheckIn f01_CheckIn = new Frag01_CheckIn();
	public Frag01_MyStudyGroup f01_MyStudyGroup = new Frag01_MyStudyGroup();
	public Frag01_MyInfo f01_MyInfo = new Frag01_MyInfo();

	private int currentFirstVisibleItem;
	private int currentVisibleItemCount;
	private int currentScrollState;
	private boolean isLoading = false;
	static int BoardDataCount = 0;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCtx = getActivity();
		inflater = (LayoutInflater) mCtx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View ViewLayout = inflater
				.inflate(R.layout.frag01_my_page, null, false);
		task = new back();
		
		task.execute(imgurl);
		CheckIn_btn = (BootstrapButton) ViewLayout
				.findViewById(R.id.f01_btn_checkin);
		MyStudy_btn = (BootstrapButton) ViewLayout
				.findViewById(R.id.f01_btn_my_study_group);
		MyInfo_btn = (BootstrapButton) ViewLayout
				.findViewById(R.id.f01_btn_my_info);
		NoticeBoard = (ListView) ViewLayout.findViewById(R.id.f01_lv_notice);
		MyPhoto = (ImageView) ViewLayout
				.findViewById(R.id.f01_img_my_photo);

		CheckIn_btn.setOnClickListener(this);
		MyStudy_btn.setOnClickListener(this);
		MyInfo_btn.setOnClickListener(this);
		MyPhoto.setOnClickListener(this);

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		StaticVar.NoticeBoardWholeData = new ArrayList<BoardData>();
		StaticVar.NoticeBoardData = new ArrayList<BoardData>();

		mNoticeAdapter = new NoticeAdapter(StaticVar.NoticeBoardWholeData);
		mDataParser = new Frag01_NoticeDataParser(mNoticeAdapter, mCtx);
		mDataParser.start();

		NoticeBoard.setAdapter(mNoticeAdapter);
		NoticeBoard.setOnScrollListener(this);

		BoardDataCount = StaticVar.NoticeBoardData.size();
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

		case R.id.f01_img_my_photo:
			Intent intent = new Intent();
			intent.setAction(intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
			startActivityForResult(intent, 0);
			break;
		default:

			break;
		}
	}

	private class back extends AsyncTask<String, Integer, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... urls) {
			try {
				URL myFileUrl = new URL(urls[0]);
				HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
				conn.setDoInput(true);
				conn.connect();

				InputStream is = conn.getInputStream();
				bmImg = BitmapFactory.decodeStream(is);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			return bmImg;
		}

		protected void onPostExecute(Bitmap img) {
			if(img != null)
			{MyPhoto.setImageBitmap(img);}
			else{}
		}

	}
	
	/* click MyPhoto (ImageView) function */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent){
		super.onActivityResult(requestCode, resultCode, intent);
		
		try {
	        if (resultCode == Activity.RESULT_OK) {
				Uri selPhotoUri = intent.getData();
				Bitmap selPhoto = Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(),
						selPhotoUri);
				String urlString = "http://wpg.azurewebsites.net/webs_image_upload.jsp";
	
				Cursor c = getActivity().getApplicationContext().getContentResolver().query(
						Uri.parse(selPhotoUri.toString()), null, null, null, null);
	
				c.moveToNext();
	
				String absolutePath = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
				Log.d("aURI", absolutePath);
		
				HttpFileUpload(urlString, "", absolutePath);	
				String path = intent.getDataString();
	        }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void HttpFileUpload(String urlString, String params, String fileName) {
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize=4;
			
			Bitmap resize = BitmapFactory.decodeFile(fileName, options);	
			int width = resize.getWidth();
			int height = resize.getHeight();
			
			if(width>400){
				resize = Bitmap.createScaledBitmap(resize, (width=400), (height=height*400/width), true);
			}

			
			MyPhoto.setImageBitmap(resize);
			
			File file = new File("/sdcard/");
			
			FileOutputStream outS ;
			try {
				outS = new FileOutputStream("/sdcard/upload.jpg");
				resize.compress(Bitmap.CompressFormat.JPEG, 100, outS);
				
				outS.close();

			} catch (Exception e) {
			}
			
			fileName = "/sdcard/upload.jpg";
			mFileInputStream = new FileInputStream(fileName);
			connectUrl = new URL(urlString);
			
			
			
			// open connection
			HttpURLConnection conn = (HttpURLConnection) connectUrl
					.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			// write data
			
			// 파일 이름을 놓가 ->filename
			fileName =StaticVar.ID+".jpg";
			Log.d("", "msg");

			dos = new DataOutputStream(conn.getOutputStream());
			Log.d("",dos.toString());
			dos.writeBytes(twoHyphens + boundary + lineEnd);

			dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\""
					+ fileName + "\"" + lineEnd);
			Log.d("", "msg");

			dos.writeBytes(lineEnd);
			
			Log.d("urlString", urlString); 

			int bytesAvailable = mFileInputStream.available();
			int maxBufferSize = 1024;
			int bufferSize = Math.min(bytesAvailable, maxBufferSize);

			byte[] buffer = new byte[bufferSize];
			int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);

			Log.d("Test", "image byte is " + bytesRead);

			// read image
			while (bytesRead > 0) {
				dos.write(buffer, 0, bufferSize);
				bytesAvailable = mFileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
			}

			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

			// close streams
			Log.e("Test", "File is written");
			mFileInputStream.close();
			dos.flush(); // finish upload...

			// get response

			int ch;
			InputStream is = conn.getInputStream();
			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
			String s = b.toString();
			Log.e("Test", "result = " + s);
			dos.close();
			
			File filedelete = new File("/sdcard/upload.jpg");
			filedelete.delete();

		} catch (Exception e) {
			e.printStackTrace();
			Log.d("Test", "exception " + e.getMessage());
		}
	}

	class NoticeAdapter extends BaseAdapter {
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
			BoardData data = arSrc.get(pos);

			Log.e("view", "d" + pos);
			View view = inflater
					.inflate(R.layout.frag01_notice_list_item, null);
			final LinearLayout ContentsBox_U = (LinearLayout) view
					.findViewById(R.id.f01_item_lay_box_up);
			final LinearLayout ContentsBox_D = (LinearLayout) view
					.findViewById(R.id.f01_item_lay_box_down);

			TextView Name = (TextView) view.findViewById(R.id.f01_item_name);
			Name.setText(data.Writer);

			TextView Time = (TextView) view.findViewById(R.id.f01_item_date);
			Time.setText(data.Date);

			TextView Title = (TextView) view.findViewById(R.id.f01_item_title);
			Title.setText(data.Title);

			TextView Title2 = (TextView) view
					.findViewById(R.id.f01_item_title2);
			Title2.setText(data.Title);

			TextView Contents = (TextView) view
					.findViewById(R.id.f01_item_contents);
			Contents.setText(data.Contents);

			TextView BoardNumber = (TextView) view
					.findViewById(R.id.f01_item_num);
			BoardNumber.setText(String.valueOf(pos + 1));

			LinearLayout Whole = (LinearLayout) view
					.findViewById(R.id.f01_item_lay_whole);
			OnClickListener mOnClick = new OnClickListener() {
				boolean show = false;

				@Override
				public void onClick(View v) {
					if (show) {
						ContentsBox_U.setVisibility(View.GONE);
						ContentsBox_D.setVisibility(View.GONE);
						show = false;
					} else {
						ContentsBox_U.setVisibility(View.VISIBLE);
						ContentsBox_D.setVisibility(View.VISIBLE);
						show = true;
					}
				}
			};

			Whole.setOnClickListener(mOnClick);

			return view;
		}
	}

	/* ListView auto loading when view arrive at the end of list */
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.currentFirstVisibleItem = firstVisibleItem;
		this.currentVisibleItemCount = visibleItemCount;
	}

	public void onScrollStateChanged(AbsListView view, int scrollState) {
		this.currentScrollState = scrollState;
		this.isScrollCompleted();
	}

	private synchronized void isScrollCompleted() {
		if (this.currentVisibleItemCount > 0
				&& this.currentScrollState == SCROLL_STATE_IDLE) {
			LoadMoreBoard();
		}
	}

	void LoadMoreBoard() {
		for (int i = 0; i < 5; i++) {
			if (StaticVar.NoticeBoardWholeData.size() > StaticVar.NoticeBoardData
					.size()) {
				StaticVar.NoticeBoardData.add(StaticVar.NoticeBoardWholeData
						.get(BoardDataCount++));
				mNoticeAdapter.notifyDataSetChanged();
			}
		}
	}

}
