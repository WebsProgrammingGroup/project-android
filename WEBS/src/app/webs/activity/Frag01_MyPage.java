package app.webs.activity;

import com.webs.app.*;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class Frag01_MyPage extends android.support.v4.app.Fragment{
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View ViewLayout = inflater.inflate(R.layout.frag01_my_page, null, false);
		
		return ViewLayout;
	}
}
