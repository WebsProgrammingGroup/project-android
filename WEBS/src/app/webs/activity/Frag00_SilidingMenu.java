package app.webs.activity;

import android.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import com.webs.app.R;

public class Frag00_SilidingMenu extends android.support.v4.app.Fragment implements OnClickListener{
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View ViewLayout = inflater.inflate(R.layout.frag00_silidingmenu, null, false);
		return ViewLayout;
	}
	@Override
	public void onClick(View v) {		
	}

}
