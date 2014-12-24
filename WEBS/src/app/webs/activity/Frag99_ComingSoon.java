package app.webs.activity;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.webs.app.*;

import android.content.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

public class Frag99_ComingSoon extends android.support.v4.app.Fragment{
	private Context mCtx;
	
	private FontAwesomeText icon1;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCtx = getActivity();
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View ViewLayout = inflater.inflate(R.layout.frag99_coming_soon, null, false);
		icon1 = (FontAwesomeText)ViewLayout.findViewById(R.id.f99_icon);
		icon1.startRotate(mCtx, true, FontAwesomeText.AnimationSpeed.SLOW);
		
		return ViewLayout;
	}
}
