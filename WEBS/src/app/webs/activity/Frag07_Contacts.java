package app.webs.activity;

import java.util.*;

import org.apache.http.*;
import org.apache.http.message.*;

import android.content.*;
import android.net.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.*;
import app.webs.imageloader.*;
import app.webs.util.*;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.webs.app.R;

public class Frag07_Contacts extends android.support.v4.app.Fragment implements OnClickListener, OnKeyListener{
	private Context mCtx;

	private BootstrapButton Title_btn;
	private ListView Contacts_lv;
	private BootstrapButton Search_btn;
	private BootstrapEditText Search_et;
	
	private ContactsListAdapter mContactsListAdapter;
	private ContactsListAdapter mContactsSeachListAdapter;
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
		
		if(StaticVar.mContactWholeData == null){
			StaticVar.mContactWholeData = new ArrayList<ContactData>();
		}
		
		mContactsListAdapter = new ContactsListAdapter(mCtx, R.layout.frag07_contacts_list_item, StaticVar.mContactWholeData);
		mDataParser = new Frag07_ContactsDataParser(mContactsListAdapter, mCtx);
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
//		mDataParser = new Frag07_ContactsDataParser(mContactsListAdapter);
//		final ArrayList<NameValuePair> paramList = new ArrayList<NameValuePair>();
//		paramList.add(new BasicNameValuePair("SearchStr", SearchStr));
//		mDataParser.setParamList(paramList);
//		mDataParser.start();
		
		if(StaticVar.mContactWholeData == null){
			mDataParser = new Frag07_ContactsDataParser(mContactsListAdapter, mCtx);
			mDataParser.start();
		}else{
			if(StaticVar.mContactData == null){
				StaticVar.mContactData = new ArrayList<ContactData>();
			}else{
				StaticVar.mContactData.clear();
			}
			//Search in ContactData
			for(int i = 0 ; i < StaticVar.mContactWholeData.size() ; i++){
				if(StaticVar.mContactWholeData.get(i).Name.matches("(.*)"+SearchStr+"(.*)")){
					StaticVar.mContactData.add(StaticVar.mContactWholeData.get(i));
				}
			}
			mContactsSeachListAdapter = new ContactsListAdapter(mCtx, R.layout.frag07_contacts_list_item,
					StaticVar.mContactData);
			Contacts_lv.setAdapter(mContactsSeachListAdapter);
			
		}
	}
	public void SearchContacts(){
		if(StaticVar.mContactWholeData == null){
			mDataParser = new Frag07_ContactsDataParser(mContactsListAdapter, mCtx);
			mDataParser.start();
		}
		Contacts_lv.setAdapter(mContactsListAdapter);
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
			
			ImageView Photo = (ImageView)view.findViewById(R.id.f07_item_photo);
			
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
			
			ImageLoader mLoader = new ImageLoader(mCtx);
			final String PhotoUrl = arSrc.get(pos).ID;
			mLoader.DisplayImage(StaticVar.ImageBaseUrl+PhotoUrl+".jpg", Photo);
			
			return view;
		}		
	}
}

class ContactData{
	public String Name;
	public String Phone;
	public String Photo;
	public String ID;
	public String Major;
	public String Gender;
}