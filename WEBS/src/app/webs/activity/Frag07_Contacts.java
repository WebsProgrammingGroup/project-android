package app.webs.activity;

import java.io.*;
import java.net.*;
import java.util.*;

import org.apache.http.*;
import org.apache.http.message.*;
import org.json.*;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.webs.app.*;

import android.content.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.*;
import android.widget.*;
import android.widget.TextView.OnEditorActionListener;
import app.webs.imageloader.*;

public class Frag07_Contacts extends android.support.v4.app.Fragment implements OnClickListener, OnKeyListener{
	private Context mCtx;

	private BootstrapButton Title_btn;
	private ListView Contacts_lv;
	private BootstrapButton Search_btn;
	private BootstrapEditText Search_et;
	
	private ArrayList<ContactData> ArrContactData;
	private ContactsListAdapter mContactsListAdapter;
	private Frag07_ContactsDataParser mDataParser;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCtx = getActivity();
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View ViewLayout = inflater.inflate(R.layout.frag07_contacts, null, false);
		Contacts_lv = (ListView)ViewLayout.findViewById(R.id.f07_lv_contacts);
		Search_btn = (BootstrapButton)ViewLayout.findViewById(R.id.f07_btn_name_search);
		Title_btn = (BootstrapButton)ViewLayout.findViewById(R.id.f07_btn_menu_title);
		Search_et = (BootstrapEditText)ViewLayout.findViewById(R.id.f07_et_name_search);
		
		ArrContactData = new ArrayList<ContactData>();
		mContactsListAdapter = new ContactsListAdapter(mCtx, R.layout.frag07_contacts_list_item, ArrContactData);
		mDataParser = new Frag07_ContactsDataParser(mContactsListAdapter,ArrContactData);
		mDataParser.start();
		
		Contacts_lv.setAdapter(mContactsListAdapter);
		Search_btn.setOnClickListener(this);
		Title_btn.setOnClickListener(this);
		Search_et.setOnKeyListener(this);
		
		return ViewLayout;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.f07_btn_name_search:
			SearchContacts(Search_et.getText().toString());
			break;
		case R.id.f07_btn_menu_title:			
			SearchContacts();
			break;
		default:
			break;
		}
	}
	
	public void SearchContacts(String SearchStr){
		mDataParser = new Frag07_ContactsDataParser(mContactsListAdapter,ArrContactData);
		final ArrayList<NameValuePair> paramList = new ArrayList<NameValuePair>();
		paramList.add(new BasicNameValuePair("SearchStr", SearchStr));
		mDataParser.setParamList(paramList);
		mDataParser.start();
	}
	public void SearchContacts(){
		mDataParser = new Frag07_ContactsDataParser(mContactsListAdapter,ArrContactData);
		mDataParser.start();
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent e) {
		if(v.getId() == R.id.f07_et_name_search && keyCode == KeyEvent.KEYCODE_ENTER){
			SearchContacts(Search_et.getText().toString());
		}
		return false;
	}
	
	class ContactsListAdapter extends ArrayAdapter<ContactData>{
		ArrayList<ContactData> arSrc;
		LayoutInflater inflater;
		
		public ContactsListAdapter(Context context, int textViewResourceId,	ArrayList<ContactData> objects) {
			super(context, textViewResourceId, objects);
			arSrc = objects;
			inflater = (LayoutInflater)mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			final int pos = position;
			if (view == null) {
				view = inflater.inflate(R.layout.frag07_contacts_list_item, parent, false);
			}
			
			BootstrapCircleThumbnail Photo = (BootstrapCircleThumbnail)view.findViewById(R.id.f07_item_photo);
			
			TextView Name = (TextView)view.findViewById(R.id.f07_item_name);
			Name.setText(arSrc.get(pos).Name);
			
			BootstrapButton id = (BootstrapButton)view.findViewById(R.id.f07_item_id);
			id.setText(arSrc.get(pos).ID.substring(2, 4)+"학번");
			
			BootstrapButton major = (BootstrapButton)view.findViewById(R.id.f07_item_major);
			major.setText(arSrc.get(pos).Major);
			
			BootstrapButton gender = (BootstrapButton)view.findViewById(R.id.f07_item_gender);
			gender.setText(arSrc.get(pos).Gender);
			
			BootstrapButton PhoneNum = (BootstrapButton)view.findViewById(R.id.f07_item_tel);
			PhoneNum.setText(arSrc.get(pos).Phone);
			PhoneNum.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(Intent.ACTION_DIAL);
	                i.setData(Uri.parse("tel:"+arSrc.get(pos).Phone));
	                startActivity(i);
				}
			});
//			ImageLoader mLoader = new ImageLoader(mCtx);
//			final String PhotoUrl = arSrc.get(pos).Photo;
//			if(PhotoUrl.equals("null") ){
//				Photo.setImage(R.drawable.ic_app);
//			}else{
//				mLoader.DisplayImage(PhotoUrl, Photo.image);
//			}
			
			return view;
		}		
	}
}