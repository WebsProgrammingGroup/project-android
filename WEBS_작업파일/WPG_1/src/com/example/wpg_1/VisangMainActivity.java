package com.example.wpg_1;




import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class VisangMainActivity extends Activity {

	ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		String strurl ="http://wpg.azurewebsites.net/member1.jsp?member";
		
		DownloadTask downloadTask  = new DownloadTask();
		
		downloadTask.execute(strurl);
		
		lv =(ListView)findViewById(R.id.listView1);
	}
	
	private String downloadUrl(String strurl) throws IOException{
		 String data = "";
	        InputStream iStream = null;
	        try{
	                URL url = new URL(strurl);
	                
	                // Creating an http connection to communicate with url 
	                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

	                // Connecting to url 
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
	private class DownloadTask extends AsyncTask<String, Integer, String>{
		String data =null;
		@Override
		protected String doInBackground(String... url) {
			// TODO Auto-generated method stub
			try {
				data =downloadUrl(url[0]);
				Log.d("url", data);
			} catch (Exception e) {
				// TODO: handle exception
				Log.d("Background Task",e.toString());
			}
			return data;
		}
		  @Override
          protected void onPostExecute(String result) {
          	
                  // The parsing of the xml data is done in a non-ui thread 
                  ListViewLoaderTask listViewLoaderTask = new ListViewLoaderTask();
                  
                  // Start parsing xml data
                  listViewLoaderTask.execute(result);                        
                  
          }
		
	}
	
//
	private class ListViewLoaderTask extends AsyncTask<String, Void, SimpleAdapter>{
    	JSONObject jObject;
    	// Doing the parsing of xml data in a non-ui thread 
		@Override
		protected SimpleAdapter doInBackground(String... strJson) {
			try{
	        	jObject = new JSONObject(strJson[0]);
	        	TestJsonParser testJsonParser = new TestJsonParser();
	        	testJsonParser.parse(jObject);
	        	Log.i("suss", "here");
	        }catch(Exception e){
	        	Log.d("JSON Exception1",e.toString());
	        }
			
			// Instantiating json parser class
			TestJsonParser testJsonParser = new TestJsonParser();
			// A list object to store the parsed countries ㄹlist
	        List<HashMap<String, Object>> testjson = null;
	        try{
	        	// Getting the parsed data as a List construct
	        	testjson = testJsonParser.parse(jObject);
	        	Log.i("here4", "success");
	        }catch(Exception e){
	        	Log.d("Exception",e.toString());
	        }	       

	        // Keys used in Hashmap 
	        String[] from = { "name","photo","details"};

	        // Ids of views in listview_layout
	        int[] to = { R.id.name,R.id.photo,R.id.tel};

	        // Instantiating an adapter to store each items
	        // R.layout.listview_layout defines the layout of each item	        
	        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), testjson, R.layout.list_item, from, to);  
	        
			return adapter;
		}
		

		@Override
		protected void onPostExecute(SimpleAdapter adapter) {
			// TODO Auto-generated method stub
			lv.setAdapter(adapter);
			for(int i=0; i<adapter.getCount();i++){
				HashMap<String, Object> hm = (HashMap<String, Object>) adapter.getItem(i);
				String imgUrl = (String) hm.get("photo_path");;
				ImageLoaderTask imageLoaderTask =new ImageLoaderTask();
				HashMap<String, Object>hmDownload =new HashMap<String, Object>();
				hm.put("photo_path",imgUrl);
				hm.put("position", i);
				imageLoaderTask.execute(hm);
				Log.d(imgUrl, imgUrl);
			}
		}
	}
	
	 private class ImageLoaderTask extends AsyncTask<HashMap<String, Object>, Void, HashMap<String, Object>>{

			@Override
			protected HashMap<String, Object> doInBackground(HashMap<String, Object>... hm) {
				
				InputStream iStream=null;
				String imgUrl = (String) hm[0].get("photo_path");
				int position = (Integer) hm[0].get("position");
				String x =String.valueOf(position);
				Log.d("positon", x);
				URL url;
				try {
					url = new URL(imgUrl);
					
					// Creating an http connection to communicate with url
		            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

		            // Connecting to url	            
		            urlConnection.connect();

		            // Reading data from url 
		            iStream = urlConnection.getInputStream();
		            
		            // Getting Caching directory 
		            File cacheDirectory = getBaseContext().getCacheDir();
		            
		            // Temporary file to store the downloaded image 
		            File tmpFile = new File(cacheDirectory.getPath() + "/wpta_"+position+".png");	            
		            String y =String.valueOf(tmpFile);
		            Log.d("tmpfile",y);
		            // The FileOutputStream to the temporary file
		            FileOutputStream fOutStream = new FileOutputStream(tmpFile);
		            
		            // Creating a bitmap from the downloaded inputstream
		            Bitmap b = BitmapFactory.decodeStream(iStream);	            
		            
		            // Writing the bitmap to the temporary file as png file
		            b.compress(Bitmap.CompressFormat.PNG,100, fOutStream);	            
		            
		            // Flush the FileOutputStream
		            fOutStream.flush();
		            
		            //Close the FileOutputStream
		            fOutStream.close();	            
		            
		            // Create a hashmap object to store image path and its position in the listview
		            HashMap<String, Object> hmBitmap = new HashMap<String, Object>();
		            
		            // Storing the path to the temporary image file
		            hmBitmap.put("photo",tmpFile.getPath());
		            
		            // Storing the position of the image in the listview
		            hmBitmap.put("position",position);	            
		            
		            // Returning the HashMap object containing the image path and position
		            return hmBitmap;	            
		            

				}catch (Exception e) {				
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(HashMap<String, Object> result) {
				// Getting the path to the downloaded image
				String path = (String) result.get("photo");			
				
				// Getting the position of the downloaded image
				int position = (Integer) result.get("position");
				
				// Getting adapter of the listview
				SimpleAdapter adapter = (SimpleAdapter ) lv.getAdapter();
				
				// Getting the hashmap object at the specified position of the listview
				HashMap<String, Object> hm = (HashMap<String, Object>) adapter.getItem(position);	
				
				// Overwriting the existing path in the adapter 
				hm.put("photo",path);
				
				// Noticing listview about the dataset changes
				adapter.notifyDataSetChanged();	
			}
	 }

	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }
	
}

