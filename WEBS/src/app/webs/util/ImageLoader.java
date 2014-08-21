//package app.webs.util;
//
//import java.io.*;
//import java.net.*;
//import java.util.*;
//
//import android.graphics.*;
//import android.os.*;
//import android.util.*;
//import android.widget.*;
//
//public class ImageLoader extends AsyncTask<HashMap<String, Object>, Void, HashMap<String, Object>>{
//
//	@Override
//	protected HashMap<String, Object> doInBackground(HashMap<String, Object>... hm) {
//		
//		InputStream iStream=null;
//		String imgUrl = (String) hm[0].get("photo_path");
//		int position = (Integer) hm[0].get("position");
//		String x =String.valueOf(position);
//		Log.d("positon", x);
//		URL url;
//		try {
//			url = new URL(imgUrl);
//			
//			// Creating an http connection to communicate with url
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//
//            // Connecting to url	            
//            urlConnection.connect();
//
//            // Reading data from url 
//            iStream = urlConnection.getInputStream();
//            
//            // Getting Caching directory 
//            File cacheDirectory = getBaseContext().getCacheDir();
//            
//            // Temporary file to store the downloaded image 
//            File tmpFile = new File(cacheDirectory.getPath() + "/wpta_"+position+".png");
//            // The FileOutputStream to the temporary file
//            FileOutputStream fOutStream = new FileOutputStream(tmpFile);
//            
//            // Creating a bitmap from the downloaded inputstream
//            Bitmap b = BitmapFactory.decodeStream(iStream);	            
//            
//            // Writing the bitmap to the temporary file as png file
//            b.compress(Bitmap.CompressFormat.PNG,100, fOutStream);	            
//            
//            // Flush the FileOutputStream
//            fOutStream.flush();
//            
//            //Close the FileOutputStream
//            fOutStream.close();	            
//            
//            // Create a hashmap object to store image path and its position in the listview
//            HashMap<String, Object> hmBitmap = new HashMap<String, Object>();
//            
//            // Storing the path to the temporary image file
//            hmBitmap.put("photo",tmpFile.getPath());
//            
//            // Storing the position of the image in the listview
//            hmBitmap.put("position",position);	            
//            
//            // Returning the HashMap object containing the image path and position
//            return hmBitmap;	            
//            
//
//		}catch (Exception e) {				
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	@Override
//	protected void onPostExecute(HashMap<String, Object> result) {
//		// Getting the path to the downloaded image
//		String path = (String) result.get("photo");			
//		
//		// Getting the position of the downloaded image
//		int position = (Integer) result.get("position");
//		
//		// Getting adapter of the listview
//		SimpleAdapter adapter = (SimpleAdapter ) lv.getAdapter();
//		
//		// Getting the hashmap object at the specified position of the listview
//		HashMap<String, Object> hm = (HashMap<String, Object>) adapter.getItem(position);	
//		
//		// Overwriting the existing path in the adapter 
//		hm.put("photo",path);
//		
//		// Noticing listview about the dataset changes
//		adapter.notifyDataSetChanged();	
//	}
//}