package com.chowlb.runforyourlife;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

public class AddCacheAsyncActivity extends AsyncTask<Object, Void, Cache>{

    public AsyncMapInterface delegate = null;
    Cache res = null;
    
    public AddCacheAsyncActivity() {
      
   }
    
    @Override
    protected void onPreExecute() {
       super.onPreExecute();
       
   }
	
	@Override
	protected Cache doInBackground(Object... arg0) {
		
		Player player = (Player) arg0[0];
		Location location = (Location) arg0[1];
		String URL="http://www.chowlb.com/runforyourlife/addcache_app.php";
		
		JSONObject jsonObjSend = new JSONObject();
		try {
			jsonObjSend.put("OWNERID", player.getPlayerID());
			jsonObjSend.put("OWNERNAME", player.getPlayerName());
			jsonObjSend.put("LATITUDE", location.getLatitude());
			jsonObjSend.put("LONGITUDE", location.getLongitude());			
		}catch(JSONException e) {
			e.printStackTrace();
		}
		try {
			HttpClient client = new HttpClient();
			Log.e("chowlb", "Sending json: " + jsonObjSend);
			JSONArray jsonResponse = client.postJsonData(jsonObjSend.toString(), URL);
			JSONObject jsonResponseObj = jsonResponse.getJSONObject(0);
			Log.e("chowlb", "GET INT: " + jsonResponseObj.getInt("CACHE_ID"));
			res = new Cache(jsonResponseObj.getInt("CACHE_ID"), player.getPlayerName(), player.getPlayerID(), location.getLatitude(), location.getLongitude());			
		}catch(Exception e) {
			e.printStackTrace();
		}
		Log.e("chowlb", "added cache to db with id: " + res.getCacheID());
		return res;		
		
	}

	 @Override
	   protected void onPostExecute(Cache result){
		 super.onPostExecute(result);
		 Log.e("chowlb", "sending  cache to delegate with id: " + res.getCacheID());
		 delegate.createCache(result);
		 
	 }

}

