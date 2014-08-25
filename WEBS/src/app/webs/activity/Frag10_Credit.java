package app.webs.activity;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.webs.app.*;

import android.content.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

public class Frag10_Credit extends android.support.v4.app.Fragment implements OnClickListener{
	private Context mCtx;
	
	private FontAwesomeText icon1;
	private FontAwesomeText icon2;
	private FontAwesomeText icon3;
	private FontAwesomeText icon4;
	private FontAwesomeText icon5;
	private FontAwesomeText icon6;
	private FontAwesomeText icon7;
	private FontAwesomeText icon8;
	private FontAwesomeText icon9;
	private FontAwesomeText icon10;
	
	private BootstrapButton Android_btn;
	private BootstrapButton PC_btn;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCtx = getActivity();
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View ViewLayout = inflater.inflate(R.layout.frag10_credit, null, false);
		icon1 = (FontAwesomeText)ViewLayout.findViewById(R.id.f10_icon_1);
		icon2 = (FontAwesomeText)ViewLayout.findViewById(R.id.f10_icon_2);
		icon3 = (FontAwesomeText)ViewLayout.findViewById(R.id.f10_icon_3);
		icon4 = (FontAwesomeText)ViewLayout.findViewById(R.id.f10_icon_4);
		icon5 = (FontAwesomeText)ViewLayout.findViewById(R.id.f10_icon_5);
		icon6 = (FontAwesomeText)ViewLayout.findViewById(R.id.f10_icon_6);
		icon7 = (FontAwesomeText)ViewLayout.findViewById(R.id.f10_icon_7);
		icon8 = (FontAwesomeText)ViewLayout.findViewById(R.id.f10_icon_8);
		icon9 = (FontAwesomeText)ViewLayout.findViewById(R.id.f10_icon_9);
		icon10 = (FontAwesomeText)ViewLayout.findViewById(R.id.f10_icon_10);
		Android_btn = (BootstrapButton)ViewLayout.findViewById(R.id.f10_btn_android);
		PC_btn = (BootstrapButton)ViewLayout.findViewById(R.id.f10_btn_pc);
		
//		icon1.setOnClickListener(this);
//		icon2.setOnClickListener(this);
//		icon3.setOnClickListener(this);
//		icon4.setOnClickListener(this);
//		icon5.setOnClickListener(this);
//		icon6.setOnClickListener(this);
//		icon7.setOnClickListener(this);
//		icon8.setOnClickListener(this);
//		icon9.setOnClickListener(this);
//		icon10.setOnClickListener(this);
		Android_btn.setOnClickListener(this);
		PC_btn.setOnClickListener(this);
		
		
		return ViewLayout;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.f10_icon_1:
			RotateFonts(1);
			break;
		case R.id.f10_icon_2:
			RotateFonts(1);
			break;
		case R.id.f10_icon_3:
			RotateFonts(1);
			break;
		case R.id.f10_icon_4:
			RotateFonts(1);
			break;
		case R.id.f10_icon_5:
			RotateFonts(1);
			break;
		case R.id.f10_icon_6:
			RotateFonts(2);
			break;
		case R.id.f10_icon_7:
			RotateFonts(2);
			break;
		case R.id.f10_icon_8:
			RotateFonts(2);
			break;
		case R.id.f10_icon_9:
			RotateFonts(2);
			break;
		case R.id.f10_icon_10:
			RotateFonts(2);
			break;
		case R.id.f10_btn_android:
			RotateFonts(1);
			break;
		case R.id.f10_btn_pc:
			RotateFonts(2);
			break;
		default:
			break;
		}
	}
	public void RotateFonts(int team){
		if(team == 1){
			icon1.startRotate(mCtx, true, FontAwesomeText.AnimationSpeed.SLOW);
			icon2.startRotate(mCtx, true, FontAwesomeText.AnimationSpeed.SLOW);
			icon3.startRotate(mCtx, true, FontAwesomeText.AnimationSpeed.SLOW);
			icon4.startRotate(mCtx, true, FontAwesomeText.AnimationSpeed.SLOW);
			icon5.startRotate(mCtx, true, FontAwesomeText.AnimationSpeed.SLOW);
			icon5.startFlashing(mCtx, true, FontAwesomeText.AnimationSpeed.MEDIUM);
			icon6.stopAnimation();
			icon7.stopAnimation();
			icon8.stopAnimation();
			icon9.stopAnimation();
			icon10.stopAnimation();
		}else{
			icon1.stopAnimation();
			icon2.stopAnimation();
			icon3.stopAnimation();
			icon4.stopAnimation();
			icon5.stopAnimation();
			icon6.startRotate(mCtx, true, FontAwesomeText.AnimationSpeed.SLOW);
			icon7.startRotate(mCtx, true, FontAwesomeText.AnimationSpeed.SLOW);
			icon8.startRotate(mCtx, true, FontAwesomeText.AnimationSpeed.SLOW);
			icon9.startRotate(mCtx, true, FontAwesomeText.AnimationSpeed.SLOW);
			icon10.startRotate(mCtx, true, FontAwesomeText.AnimationSpeed.SLOW);
		}
	}
}
