package app.webs.activity;

import android.content.*;
import android.os.*;
import android.support.v4.app.*;
import android.view.*;
import android.view.View.OnClickListener;

import com.beardedhen.androidbootstrap.*;
import com.webs.app.R;

public class Frag01_MyPage extends Fragment implements OnClickListener{
	private Context mCtx;
	
	private BootstrapButton CheckIn_btn;
	private BootstrapButton MyStudy_btn;
	private BootstrapButton MyInfo_btn;
	
	public Frag01_CheckIn f01_CheckIn = new Frag01_CheckIn();
	public Frag01_MyStudyGroup f01_MyStudyGroup = new Frag01_MyStudyGroup();
	public Frag01_MyInfo f01_MyInfo = new Frag01_MyInfo();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View ViewLayout = inflater.inflate(R.layout.frag01_my_page, null, false);
		CheckIn_btn = (BootstrapButton)ViewLayout.findViewById(R.id.f01_btn_checkin);
		MyStudy_btn = (BootstrapButton)ViewLayout.findViewById(R.id.f01_btn_my_study_group);
		MyInfo_btn = (BootstrapButton)ViewLayout.findViewById(R.id.f01_btn_my_info);
		
		CheckIn_btn.setOnClickListener(this);
		MyStudy_btn.setOnClickListener(this);
		MyInfo_btn.setOnClickListener(this);
		
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
}
