package app.webs.activity;

import java.util.*;

import org.apache.http.*;
import org.apache.http.message.*;

import com.beardedhen.androidbootstrap.*;
import com.webs.app.*;
import com.webs.app.R;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import app.webs.util.*;

public class Act01_JoinUs extends Activity implements OnClickListener{
	private Context mCtx;
	private static final int DATE_DIALOG_ID = 0;
	private int mYear = 1990, mMonth = 1, mDay = 1;
	
	private BootstrapButton JoinBtn;
	private BootstrapEditText NameET;
	private BootstrapEditText IdET;
	private BootstrapEditText PwET;
	private BootstrapEditText BirthdayET;
	private BootstrapEditText ClubPwET;
	private BootstrapEditText PhoneNumET;
	private BootstrapEditText MajorET;
	private RadioGroup GenderGroup;
	private RadioGroup GrdGroup;
		
	private Act01_JoinDataParser mDataParser;
	private String IdStr;
	private String NameStr;
	private String PwStr;
	private String GenderStr;
	private String BirthStr;
	private String GrdStr;
	private String PhoneStr;
	private String MajorStr;
	private String ClubPwStr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act01_join_us);
		mCtx = this;
		
		JoinBtn = (BootstrapButton)findViewById(R.id.a01_btn_join);
		NameET = (BootstrapEditText)findViewById(R.id.a01_et_name);
		IdET = (BootstrapEditText)findViewById(R.id.a01_et_id);
		PwET = (BootstrapEditText)findViewById(R.id.a01_et_pw);
		BirthdayET = (BootstrapEditText)findViewById(R.id.a01_et_birthday);
		ClubPwET = (BootstrapEditText)findViewById(R.id.a01_et_club_pw);
		PhoneNumET = (BootstrapEditText)findViewById(R.id.a01_et_hp);
		MajorET = (BootstrapEditText)findViewById(R.id.a01_et_major);
		GenderGroup = (RadioGroup)findViewById(R.id.a01_ra_gender);
		GrdGroup = (RadioGroup)findViewById(R.id.a01_ra_graduation);
		
		BirthdayET.setOnClickListener(this);
		JoinBtn.setOnClickListener(this);
		
		mDataParser = new Act01_JoinDataParser(mCtx, UiHandler);
				
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.a01_et_birthday:
			showDialog(DATE_DIALOG_ID);
			break;
		case R.id.a01_btn_join:
			Join();
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.viewin4, R.anim.viewout4);
	}
	
	/* birthday pick up with dialog */
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
		}
		return null;
	}
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			BirthdayET.setText(new StringBuilder().append(mYear).append("-")
					.append(mMonth + 1).append("-").append(mDay).append(""));
		}
	};
	
	/* Join Parser */
	Handler UiHandler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what == 0){ // Login Fail
				Toast.makeText(mCtx, "동방 비밀번호를 확인해주세요.", 0).show();
			}else if(msg.what == 1){ 
				Toast.makeText(mCtx, "회원가입실패 / 이미 가입된 ID일 수 있습니다.", 0).show();
			}else{ // Login Success
				Toast.makeText(mCtx, "회원가입되었습니다.", 0).show();
				Intent it;
				it = new Intent(mCtx, Act00_Login.class);
				it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(it);
				
				finish();
				overridePendingTransition(R.anim.viewin4, R.anim.viewout4);
			}
		}
	};
	
	private synchronized void Join(){
		IdStr = IdET.getText().toString();
		PwStr = PwET.getText().toString();
		BirthStr = BirthdayET.getText().toString();
		PhoneStr = PhoneNumET.getText().toString();
		MajorStr = MajorET.getText().toString();
		NameStr = NameET.getText().toString();
		ClubPwStr = ClubPwET.getText().toString();
		
		if (GenderGroup.getCheckedRadioButtonId() == R.id.a01_ra_gender_male) {
			GenderStr = "남";
		}else{
			GenderStr = "여";
		}
		if (GrdGroup.getCheckedRadioButtonId() == R.id.a01_ra_grd_ob) {
			GrdStr = "No";
		}else{
			GrdStr = "Yes";
		}
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		
		if(IdStr.equals("")){
			alert.setMessage("학번을 입력해주세요");
			alert.setPositiveButton("확인", null);
			alert.show();
		}else if(NameStr.length() != 8){
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
			mDataParser = new Act01_JoinDataParser(mCtx, UiHandler);
			final ArrayList<NameValuePair> paramList = new ArrayList<NameValuePair>();
			paramList.add(new BasicNameValuePair("ID", IdStr));
			paramList.add(new BasicNameValuePair("PW", PwStr));
			paramList.add(new BasicNameValuePair("Name", PwStr));
			paramList.add(new BasicNameValuePair("Phone",PhoneStr));
			paramList.add(new BasicNameValuePair("Gender", GenderStr));
			paramList.add(new BasicNameValuePair("Major", MajorStr));
			paramList.add(new BasicNameValuePair("Grd", GrdStr));
			paramList.add(new BasicNameValuePair("ClubPw", ClubPwStr));
			paramList.add(new BasicNameValuePair("Birth", BirthStr));
			
			mDataParser.setParamList(paramList);
			mDataParser.start();
		}
	}
}
