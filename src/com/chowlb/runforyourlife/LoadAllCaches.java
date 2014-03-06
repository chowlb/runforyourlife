package com.chowlb.runforyourlife;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class LoadAllCaches extends AsyncTask<String, Void, List<Cache>>{

    public AsyncMapInterface delegate = null;
    List<Cache> cacheList = new ArrayList<Cache>();
	
    public LoadAllCaches() {
      
   }
    
    @Override
    protected void onPreExecute() {
       super.onPreExecute();
       
   }
   
	
	@Override
	protected List<Cache> doInBackground(String... urls) {
		
		String link="http://www.chowlb.com/runforyourlife/getallcaches_app.php";
		JSONParser jsonParser = new JSONParser();
		JSONArray caches = jsonParser.getJSONFromUrl(link);			
		
		try{
			Log.e("chowlb", "Cache list size: " + caches.length());
			Log.e("chowlb", "Caches: " + caches.toString());
			for(int i=0; i<caches.length(); i++) {
				JSONObject c = caches.getJSONObject(i);
				Log.e("chowlb", "CacheID: " + c.getInt("CACHE_ID"));
				Cache cache = new Cache(c.getInt("CACHE_ID"), c.getString("OWNER_NAME"), c.getInt("OWNER_ID"), c.getDouble("LATITUDE"), c.getDouble("LONGITUDE"));
				cacheList.add(cache);
			}
		}catch(JSONException e) {
			Log.e("chowlb", "Error parsing JSON: " + e.toString());
		}
		Log.e("chowlb", "CacheList Size: " + cacheList.size());
		return cacheList;
	}

	 @Override
	   protected void onPostExecute(List<Cache> result){
		 super.onPostExecute(result);
		 
		 if(null != result && !result.isEmpty()) { //checks not null or empty
			 delegate.createCacheFromList(result);
         }
         else{
             Log.e("chowlb", "No more data");
         }
		 
	 }

}

