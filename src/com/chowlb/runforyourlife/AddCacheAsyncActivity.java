package com.chowlb.runforyourlife;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddCacheAsyncActivity extends AsyncTask<Object, Void, Cache>{

	
    Cache res = null;
    GoogleMap mapIn;
    Context context;
    
    public AddCacheAsyncActivity(Context con, GoogleMap map) {
      mapIn = map;
      context = con;
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
			if(jsonResponse.length()>0) {
				JSONObject jsonResponseObj = jsonResponse.getJSONObject(0);
				Log.e("chowlb", "GET INT: " + jsonResponseObj.getInt("CACHE_ID"));
				res = new Cache(jsonResponseObj.getInt("CACHE_ID"), player.getPlayerName(), player.getPlayerID(), location.getLatitude(), location.getLongitude());		
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return res;		
	}

	 @Override
	   protected void onPostExecute(Cache result){
		 super.onPostExecute(result);
		 Log.e("chowlb", "sending  cache to delegate with id: " + res.getCacheID());
		 if(result != null) {
			 	LatLng position = new LatLng(result.getLatitude(), result.getLongitude());
				Log.e("chowlb",  "Creating Cache with cacheID: " + result.getCacheID());
				if(mapIn == null) {
					Log.e("chowlb", "MAP IS NULL!");
				}
				Marker newMarker = mapIn.addMarker(new MarkerOptions()
			    .position(position)
			    .draggable(false)
			    .title(result.getCacheText())
			    .snippet(result.getOwner())
			    .flat(false)
			    .icon(BitmapDescriptorFactory.fromResource(R.drawable.briefcase_drop_img)));
				GameMapActivity.markerHashMap.put(newMarker.getId(), result);
			
			}
			else {
				Toast.makeText(context,  "There was an error creating the cache", Toast.LENGTH_LONG).show();
			}
		 
	 }
	 
	

}

