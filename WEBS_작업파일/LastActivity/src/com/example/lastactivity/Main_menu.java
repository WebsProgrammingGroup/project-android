package com.example.lastactivity;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class Main_menu extends BaseActivity {
	Button btn1;
	ImageView imgv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		btn1 = (Button) findViewById(R.id.main_btn);
		imgv = (ImageView) findViewById(R.id.imageV);
		URL imageURL = null;
		URLConnection conn = null;
		InputStream is= null;
		try {
			imageURL = new URL("http://wpg.azurewebsites.net/ddd.png");
			conn = imageURL.openConnection();
			conn.connect();
			is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			Bitmap bitMap = BitmapFactory.decodeStream(bis);
			bis.close();
			is.close();
			imgv.setImageBitmap(bitMap);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	

}
