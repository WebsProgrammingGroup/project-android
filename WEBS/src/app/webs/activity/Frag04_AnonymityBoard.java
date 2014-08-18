package app.webs.activity;

import com.webs.app.*;

import android.os.*;
import android.view.*;
import android.widget.*;

public class Frag04_AnonymityBoard extends android.support.v4.app.Fragment{
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View ViewLayout = inflater.inflate(R.layout.frag04_anonymity_board, null, false);
		return ViewLayout;
	}
}
