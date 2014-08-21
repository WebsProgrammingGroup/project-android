package app.webs.activity;

import java.io.*;
import java.net.*;
import java.util.*;

import org.json.*;

import com.webs.app.*;

import android.graphics.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

public class Frag07_Contacts extends android.support.v4.app.Fragment{
	ListView lv;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View ViewLayout = inflater.inflate(R.layout.frag07_contacts_list_item, null, false);
		
		String strurl ="http://wpg.azurewebsites.net/member1.jsp?member";
//		DownloadTask downloadTask  = new DownloadTask();
//		downloadTask.execute(strurl);
		
		//lv =(ListView)findViewById(R.id.f07);
		return ViewLayout;
	}
	
	private String downloadUrl(String strurl) throws IOException{
		 String data = "";
	        InputStream iStream = null;
	        try{
	                URL url = new URL(strurl);
	                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
	                urlConnection.connect();

	                // Reading data from url 
	                iStream = urlConnection.getInputStream();
	                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
	                StringBuffer sb  = new StringBuffer();
	                String line = "";
	                while( ( line = br.readLine())  != null){
	                	sb.append(line);
	                }
	                data = sb.toString();
	                br.close();
	        }catch(Exception e){
	                Log.d("Exception while downloading url", e.toString());
	        }finally{
	                iStream.close();
	        }
	        return data;
	}
	
//	private class DownloadTask extends AsyncTask<String, Integer, String>{
//		String data =null;
//		@Override
//		protected String doInBackground(String... url) {
//			try {
//				data =downloadUrl(url[0]);
//				Log.d("url", data);
//			} catch (Exception e) {
//				Log.d("Background Task",e.toString());
//			}
//			return data;
//		}
//		
//		@Override
//		protected void onPostExecute(String result) {
//			// The parsing of the xml data is done in a non-ui thread 
//			ListViewLoaderTask listViewLoaderTask = new ListViewLoaderTask();
//			// Start parsing xml data
//			listViewLoaderTask.execute(result);  
//		}
//	}
//	
//	private class ListViewLoaderTask extends AsyncTask<String, Void, SimpleAdapter>{
//    	JSONObject jObject;
//    	// Doing the parsing of xml data in a non-ui thread 
//		@Override
//		protected SimpleAdapter doInBackground(String... strJson) {
//			try{
//	        	jObject = new JSONObject(strJson[0]);
//	        	TestJsonParser testJsonParser = new TestJsonParser();
//	        	testJsonParser.parse(jObject);
//	        	Log.i("suss", "here");
//	        }catch(Exception e){
//	        	Log.d("JSON Exception1",e.toString());
//	        }
//			
//			// Instantiating json parser class
//			TestJsonParser testJsonParser = new TestJsonParser();
//			// A list object to store the parsed countries ㄹlist
//	        List<HashMap<String, Object>> testjson = null;
//	        try{
//	        	// Getting the parsed data as a List construct
//	        	testjson = testJsonParser.parse(jObject);
//	        	Log.i("here4", "success");
//	        }catch(Exception e){
//	        	Log.d("Exception",e.toString());
//	        }	       
//
//	        // Keys used in Hashmap 
//	        String[] from = { "name","photo","details"};
//
//	        // Ids of views in listview_layout
//	        int[] to = { R.id.f07_item_name, R.id.f07_item_img, R.id.f07_item_tel};
//
//	        // Instantiating an adapter to store each items
//	        // R.layout.listview_layout defines the layout of each item	        
//	        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), testjson, R.layout.list_item, from, to);  
//	        
//			return adapter;
//		}
//		
//
//		@Override
//		protected void onPostExecute(SimpleAdapter adapter) {
//			// TODO Auto-generated method stub
//			lv.setAdapter(adapter);
//			for(int i=0; i<adapter.getCount();i++){
//				HashMap<String, Object> hm = (HashMap<String, Object>) adapter.getItem(i);
//				String imgUrl = (String) hm.get("photo_path");
//				ImageLoaderTask imageLoaderTask =new ImageLoaderTask();
//				HashMap<String, Object>hmDownload =new HashMap<String, Object>();
//				hm.put("photo_path",imgUrl);
//				hm.put("position", i);
//				imageLoaderTask.execute(hm);
//				Log.d(imgUrl, imgUrl); 
//			}
//		}
//	}
}
