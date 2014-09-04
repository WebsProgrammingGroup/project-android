package app.webs.Activity;

import java.util.*;

import org.apache.http.*;
import org.apache.http.message.*;

import com.beardedhen.androidbootstrap.*;
import com.webs.app.*;
import com.webs.app.R;

import android.app.*;
import android.content.*;
import android.os.*;
import android.telephony.*;
import android.util.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import app.webs.DataType.*;
import app.webs.Util.*;

public class Frag01_MyInfo extends android.support.v4.app.Fragment implements OnClickListener{
	private Context mCtx;
	private static final int MYINFO_DATE_DIALOG_ID = 7;
	public static int mYear = 1990, mMonth = 1, mDay = 1;
	private Act02_BaseActivity BaseAct;
	
	private BootstrapButton UploadBtn;
	private BootstrapEditText NameET;
	private BootstrapEditText IdET;
	private BootstrapEditText PwET;
	public static BootstrapEditText BirthdayET;
	private BootstrapEditText ClubPwET;
	private BootstrapEditText PhoneNumET;
	private BootstrapEditText MajorET;
	private RadioGroup GenderGroup;
	private RadioGroup GrdGroup;
	private RadioButton MaleBtn;
	private RadioButton FemaleBtn;
	private RadioButton YBBtn;
	private RadioButton OBBtn;
	
	
	private Frag01_MyInfoDataParser mDataParser;
	private LoadingDialog mLoadingDialog;
	private String IdStr;
	private String NameStr;
	private String PwStr;
	private String GenderStr;
	private String BirthStr;
	private String GrdStr;
	private String PhoneStr;
	private String MajorStr;
	private SharedPreferences mPrefs;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCtx = getActivity();
		BaseAct = (Act02_BaseActivity) getActivity();
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View ViewLayout = inflater.inflate(R.layout.frag01_my_info, null, false);

		
		UploadBtn = (BootstrapButton)ViewLayout.findViewById(R.id.f01_btn_upload);
		NameET = (BootstrapEditText)ViewLayout.findViewById(R.id.f01_et_name);
		IdET = (BootstrapEditText)ViewLayout.findViewById(R.id.f01_et_id);
		PwET = (BootstrapEditText)ViewLayout.findViewById(R.id.f01_et_pw);
		BirthdayET = (BootstrapEditText)ViewLayout.findViewById(R.id.f01_et_birthday);
		PhoneNumET = (BootstrapEditText)ViewLayout.findViewById(R.id.f01_et_hp);
		MajorET = (BootstrapEditText)ViewLayout.findViewById(R.id.f01_et_major);
		GenderGroup = (RadioGroup)ViewLayout.findViewById(R.id.f01_ra_gender);
		GrdGroup = (RadioGroup)ViewLayout.findViewById(R.id.f01_ra_graduation);
		MaleBtn = (RadioButton)ViewLayout.findViewById(R.id.f01_ra_gender_male);
		FemaleBtn = (RadioButton)ViewLayout.findViewById(R.id.f01_ra_gender_female);
		OBBtn = (RadioButton)ViewLayout.findViewById(R.id.f01_ra_grd_ob);
		YBBtn = (RadioButton)ViewLayout.findViewById(R.id.f01_ra_grd_yb);
		
		BirthdayET.setOnClickListener(this);
		UploadBtn.setOnClickListener(this);
		
		mDataParser = new Frag01_MyInfoDataParser(UiHandler);
		PhoneNumET.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
		SetInfo();
		
		return ViewLayout;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.f01_et_birthday:
			BaseAct.showDialog(MYINFO_DATE_DIALOG_ID);
			break;
		case R.id.f01_btn_upload:
			Update();
			break;
		default:
			break;
		}
	}
	
	private void SetInfo(){
		Log.i("Birth", StaticVar.mLoginData.Birth);
		NameET.setText(StaticVar.mLoginData.Name);
		IdET.setText(StaticVar.mLoginData.ID);
		PhoneNumET.setText(StaticVar.mLoginData.Phone);
		MajorET.setText(StaticVar.mLoginData.Major);
		BirthdayET.setText(StaticVar.mLoginData.Birth);
		
		if(StaticVar.mLoginData.Grd.equals("Yes")){
			YBBtn.setChecked(true);
			OBBtn.setChecked(false);
		}else{
			YBBtn.setChecked(false);
			OBBtn.setChecked(true);	
		}
	}
	
	private synchronized void Update(){
		IdStr = IdET.getText().toString();
		PwStr = PwET.getText().toString();
		BirthStr = BirthdayET.getText().toString();
		PhoneStr = PhoneNumET.getText().toString();
		MajorStr = MajorET.getText().toString();
		NameStr = NameET.getText().toString();
		
		
		if (GrdGroup.getCheckedRadioButtonId() == R.id.a01_ra_grd_ob) {
			GrdStr = "No";
		}else{
			GrdStr = "Yes";
		}
		
		AlertDialog.Builder alert = new AlertDialog.Builder(mCtx);
		
		if(IdStr.equals("")){
			alert.setMessage("학번을 입력해주세요");
			alert.setPositiveButton("확인", null);
			alert.show();
		}else if(IdStr.length() != 8){
			alert.setMessage("학번를 확인해주세요");
			alert.setPositiveButton("확인", null);
			alert.show();
		}else if(NameStr.equals("")){
			alert.setMessage("이름를 입력해주세요");
			alert.setPositiveButton("확인", null);
			alert.show();
		}else if(PwStr.equals("")){
			alert.setMessage("비밀번호를 입력해주세요");
			alert.setPositiveButton("확인", null);
			alert.show();
		}else{
			mLoadingDialog = new LoadingDialog(mCtx);
			mLoadingDialog.DialogShow();
			
			mDataParser = new Frag01_MyInfoDataParser(UiHandler);
			final ArrayList<NameValuePair> paramList = new ArrayList<NameValuePair>();
			paramList.add(new BasicNameValuePair("ID", IdStr));
			paramList.add(new BasicNameValuePair("PW", PwStr));
			paramList.add(new BasicNameValuePair("Name", NameStr));
			paramList.add(new BasicNameValuePair("Phone",PhoneStr));
			paramList.add(new BasicNameValuePair("Gender", GenderStr));
			paramList.add(new BasicNameValuePair("Major", MajorStr));
			paramList.add(new BasicNameValuePair("Grd", GrdStr));
			paramList.add(new BasicNameValuePair("Birth", BirthStr));
			
			mDataParser.setParamList(paramList);
			mDataParser.start();
		}
	}
	
	Handler UiHandler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what == 0){
				Toast.makeText(mCtx, "회원정보 수정 실패", 0).show();
			}else if(msg.what == 1){ 
				Toast.makeText(mCtx, "회원정보 수정 성공", 0).show();
				
				mPrefs = mCtx.getSharedPreferences("AppSetting", android.content.Context.MODE_PRIVATE);				
    			LoginData data = new LoginData();
    			data.Name = NameStr;
				data.Phone = PhoneStr;
				data.Photo = null;
				data.ID = StaticVar.mLoginData.ID;
				data.Level = StaticVar.mLoginData.Level;
				data.Major = MajorStr;
				data.Gender = StaticVar.mLoginData.Gender;
				data.Fees = StaticVar.mLoginData.Fees;
				data.Grd = GrdStr;
				data.Birth = BirthStr;
				StaticVar.mLoginData = data;
				
				SharedPreferences.Editor editor = mPrefs.edit();
                editor.putString("Name", StaticVar.mLoginData.Name);
                editor.putString("Birth", StaticVar.mLoginData.Birth);
                editor.putString("Gender", StaticVar.mLoginData.Gender);
                editor.putString("Grd", StaticVar.mLoginData.Grd);
                editor.putString("Level", StaticVar.mLoginData.Level);
                editor.putString("Major", StaticVar.mLoginData.Major);
                editor.putString("Phone", StaticVar.mLoginData.Phone);
                editor.putString("Photo", StaticVar.mLoginData.Photo);
                editor.putString("Fees", StaticVar.mLoginData.Fees);
                editor.commit();
                SetInfo();
				BaseAct.isBaseFrag();
			}else{ 
				Toast.makeText(mCtx, "회원정보 수정 실패", 0).show();
			}
			mLoadingDialog.DialogDismiss();
		}
	};
}
