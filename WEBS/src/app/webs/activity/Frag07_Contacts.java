package app.webs.activity;

import java.io.*;
import java.net.*;
import java.util.*;

import org.json.*;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.webs.app.*;

import android.content.*;
import android.graphics.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import app.webs.imageloader.*;

public class Frag07_Contacts extends android.support.v4.app.Fragment{
	private Context mCtx;
	private ListView Contacts_lv;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCtx = getActivity();
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View ViewLayout = inflater.inflate(R.layout.frag07_contacts, null, false);
		
		String strurl ="http://wpg.azurewebsites.net/member1.jsp?member";
		DownloadTask downloadTask  = new DownloadTask();
		downloadTask.execute(strurl);
		
		Contacts_lv =(ListView)ViewLayout.findViewById(R.id.f07_lv_contacts);
		return ViewLayout;
	}
	private class ContactsItem{
		String Name;
		String Phone;
		String PhotoUrl;
	}
	private class ContactsListAdapter extends BaseAdapter{
		ArrayList<ContactsItem> arSrc;
		LayoutInflater inflater;

		ContactsListAdapter(ArrayList<ContactsItem> aarSrc){
			inflater = (LayoutInflater)mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			arSrc = aarSrc;
		}
		
		@Override
		public int getCount() {
			return arSrc.size();
		}

		@Override
		public Object getItem(int pos) {
			return arSrc.get(pos);
		}

		@Override
		public long getItemId(int pos) {
			return pos;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			final int pos = position;
			if (view == null) {
				view = inflater.inflate(R.layout.frag07_contacts_list_item, parent, false);
			}
			BootstrapCircleThumbnail Photo = (BootstrapCircleThumbnail)view.findViewById(R.id.f07_item_photo);
			TextView Name = (TextView)view.findViewById(R.id.f07_item_name);
			BootstrapButton PhoneNum = (BootstrapButton)view.findViewById(R.id.f07_item_tel);
			
			ImageLoader mLoader = new ImageLoader(mCtx);
			if(arSrc.get(position).PhotoUrl == null ){
				Photo.image.setImageResource(R.drawable.ic_app);
			}else{
				mLoader.DisplayImage(arSrc.get(position).PhotoUrl,Photo.image);
			}
			Name.setText(arSrc.get(position).Name);
			
			return view;
		}		
	}
		
	private class DownloadTask extends AsyncTask<String, Integer, String>{
		private String DownloadData =null;
		
		@Override
		protected String doInBackground(String... url) {
			try {
				DownloadData =downloadUrl(url[0]);
				Log.d("url", DownloadData);
			} catch (Exception e) {
				Log.d("Background Task",e.toString());
			}
			return DownloadData;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// The parsing of the xml data is done in a non-ui thread 
//			ListViewLoaderTask listViewLoaderTask = new ListViewLoaderTask();
			// Start parsing xml data
//			listViewLoaderTask.execute(result);  
		}
		
		String downloadUrl(String strurl) throws IOException{
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
	}
	/////////////////////////////////////////////////////////////////////////////////////////
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
//	        int[] to = { R.id.f07_item_name, R.id.f07_item_photo, R.id.f07_item_tel};
//
//	        // Instantiating an adapter to store each items
//	        // R.layout.listview_layout defines the layout of each item	        
//	        SimpleAdapter adapter = new SimpleAdapter(mCtx, testjson, R.layout.frag07_contacts_list_item, from, to);  
//	        
//			return adapter;
//		}
//		
//
//		@Override
//		protected void onPostExecute(SimpleAdapter adapter) {
//			// TODO Auto-generated method stub
//			Contacts_lv.setAdapter(adapter);
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
//	public class TestJsonParser {
//		public List<HashMap<String, Object>> parse(JSONObject jObject) {
//
//			JSONArray jTest = null;
//			
//			try {
//				jTest = jObject.getJSONArray("member");
//				
//			} catch (JSONException e) {
//				// TODO: handle exception
//				e.printStackTrace();
//				
//			}
//			return getmembers(jTest);
//		}
//
//		private List<HashMap<String, Object>> getmembers(JSONArray jTest) {
//			int mem_count = jTest.length();
//			List<HashMap<String, Object>> mem_List = new ArrayList<HashMap<String, Object>>();
//			HashMap<String, Object> member = null;
//
//			for (int i = 0; i < mem_count; i++) {
//				try {
//					member = getmember((JSONObject) jTest.get(i));
//					mem_List.add(member);
//					
//				} catch (JSONException e) {
//					// TODO: handle exception
//					e.printStackTrace();
//			
//				}
//			}
//			return mem_List;
//		}
//
//		private HashMap<String, Object> getmember(JSONObject jmember) {
//			HashMap<String, Object> member = new HashMap<String, Object>();
//			String name = "";
//			String photo = "";
//			String phone = "";
//
//			try {
//				phone = jmember.getString("phone");
//				photo = jmember.getString("photo");
//				name = jmember.getString("member");
//				String details = "phone" + phone;
//				member.put("name", name);
//				member.put("photo", R.drawable.ic_app);
//				member.put("details", details);
//				member.put("photo_path",photo);
//				
//			} catch (JSONException e) {
//				// TODO: handle exception
//				e.printStackTrace();
//				
//			}
//
//			return member;
//		}
//	}
//	 private class ImageLoaderTask extends AsyncTask<HashMap<String, Object>, Void, HashMap<String, Object>>{
//
//			@Override
//			protected HashMap<String, Object> doInBackground(HashMap<String, Object>... hm) {
//				
//				InputStream iStream=null;
//				String imgUrl = (String) hm[0].get("photo_path");
//				int position = (Integer) hm[0].get("position");
//				String x =String.valueOf(position);
//				Log.d("positon", x);
//				URL url;
//				try {
//					url = new URL(imgUrl);
//					
//					// Creating an http connection to communicate with url
//		            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//
//		            // Connecting to url	            
//		            urlConnection.connect();
//
//		            // Reading data from url 
//		            iStream = urlConnection.getInputStream();
//		            
//		            // Getting Caching directory 
//		            File cacheDirectory = mCtx.getCacheDir();
//		            
//		            // Temporary file to store the downloaded image 
//		            File tmpFile = new File(cacheDirectory.getPath() + "/wpta_"+position+".png");
//		            // The FileOutputStream to the temporary file
//		            FileOutputStream fOutStream = new FileOutputStream(tmpFile);
//		            
//		            // Creating a bitmap from the downloaded inputstream
//		            Bitmap b = BitmapFactory.decodeStream(iStream);	            
//		            
//		            // Writing the bitmap to the temporary file as png file
//		            b.compress(Bitmap.CompressFormat.PNG,100, fOutStream);	            
//		            
//		            // Flush the FileOutputStream
//		            fOutStream.flush();
//		            
//		            //Close the FileOutputStream
//		            fOutStream.close();	            
//		            
//		            // Create a hashmap object to store image path and its position in the listview
//		            HashMap<String, Object> hmBitmap = new HashMap<String, Object>();
//		            
//		            // Storing the path to the temporary image file
//		            hmBitmap.put("photo",tmpFile.getPath());
//		            
//		            // Storing the position of the image in the listview
//		            hmBitmap.put("position",position);	            
//		            
//		            // Returning the HashMap object containing the image path and position
//		            return hmBitmap;	            
//		            
//
//				}catch (Exception e) {				
//					e.printStackTrace();
//				}
//				return null;
//			}
//			
//			@Override
//			protected void onPostExecute(HashMap<String, Object> result) {
//				// Getting the path to the downloaded image
//				String path = (String) result.get("photo");			
//				
//				// Getting the position of the downloaded image
//				int position = (Integer) result.get("position");
//				
//				// Getting adapter of the listview
//				SimpleAdapter adapter = (SimpleAdapter ) Contacts_lv.getAdapter();
//				
//				// Getting the hashmap object at the specified position of the listview
//				HashMap<String, Object> hm = (HashMap<String, Object>) adapter.getItem(position);	
//				
//				// Overwriting the existing path in the adapter 
//				hm.put("photo",path);
//				
//				// Noticing listview about the dataset changes
//				adapter.notifyDataSetChanged();	
//			}
//	 }
}
