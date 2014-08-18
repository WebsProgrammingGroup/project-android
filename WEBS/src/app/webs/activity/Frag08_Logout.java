package app.webs.activity;

import com.webs.app.*;

import android.os.*;
import android.view.*;
import android.widget.*;

public class Frag08_Logout extends android.support.v4.app.Fragment{
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View ViewLayout = inflater.inflate(R.layout.frag08_logout, null, false);
		return ViewLayout;
	}
}
