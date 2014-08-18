package app.webs.activity;

import com.beardedhen.androidbootstrap.*;
import com.webs.app.*;
import com.webs.app.R;

import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

public class Act01_JoinUs extends Activity implements OnClickListener{
	private static final int DATE_DIALOG_ID = 0;
	private int mYear = 1990, mMonth = 1, mDay = 1;
	
	private BootstrapButton JoinBtn;
	private BootstrapEditText NameET;
	private BootstrapEditText IdET;
	private BootstrapEditText PwET;
	private BootstrapEditText BirthdayET;
	private BootstrapEditText ClubPwET;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act01_join_us);
		
		JoinBtn = (BootstrapButton)findViewById(R.id.a01_btn_join);
		NameET = (BootstrapEditText)findViewById(R.id.a01_et_name);
		IdET = (BootstrapEditText)findViewById(R.id.a01_et_id);
		PwET = (BootstrapEditText)findViewById(R.id.a01_et_pw);
		BirthdayET = (BootstrapEditText)findViewById(R.id.a01_et_birthday);
		ClubPwET = (BootstrapEditText)findViewById(R.id.a01_et_club_pw);
		
		BirthdayET.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.a01_et_birthday:
			showDialog(DATE_DIALOG_ID);
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
}
