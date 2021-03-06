package com.chowlb.runforyourlife.async;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.chowlb.runforyourlife.GameMapActivity;
import com.chowlb.runforyourlife.R;
import com.chowlb.runforyourlife.objects.Cache;
import com.chowlb.runforyourlife.objects.Player;
import com.chowlb.runforyourlife.utils.HttpClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddCacheAsync extends AsyncTask<Object, Void, Cache>{

	
    Cache res = null;
    GoogleMap mapIn;
    Context context;
    
    public AddCacheAsync(Context con, GoogleMap map) {
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
		Log.e("chowlb", "ARG2: " + arg0[2]);
		String URL="http://www.chowlb.com/runforyourlife/addcache_app.php";
		
		JSONObject jsonObjSend = new JSONObject();
		try {
			jsonObjSend.put("OWNERID", player.getPlayerID());
			jsonObjSend.put("OWNERNAME", player.getPlayerName());
			jsonObjSend.put("LATITUDE", location.getLatitude());
			jsonObjSend.put("LONGITUDE", location.getLongitude());	
			if(arg0[2] != null) {
				Log.e("chowlb", "PIN IS NOT NULL ON SET");
				jsonObjSend.put("PIN", (String)arg0[2]);
			}
		}catch(JSONException e) {
			e.printStackTrace();
		}
		try {
			HttpClient client = new HttpClient();
			Log.e("chowlb", "Sending json to add CACHE: " + jsonObjSend);
			JSONArray jsonResponse = client.postJsonData(jsonObjSend.toString(), URL);
			if(jsonResponse.length()>0) {
				JSONObject jsonResponseObj = jsonResponse.getJSONObject(0);
				//Log.e("chowlb", "GET INT: " + jsonResponseObj.getInt("CACHE_ID"));
				res = new Cache(jsonResponseObj.getInt("CACHE_ID"), player.getPlayerName(), player.getPlayerID(), location.getLatitude(), location.getLongitude());		
				if(arg0[2] != null) {
					res.setPin((String)arg0[2]);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return res;		
	}

	 @Override
	   protected void onPostExecute(Cache result){
		 super.onPostExecute(result);
		 //Log.e("chowlb", "sending  cache to delegate with id: " + res.getCacheID());
		 if(result != null) {
			 	LatLng position = new LatLng(result.getLatitude(), result.getLongitude());
			 	BitmapDescriptor bmpfac;
			 	if(result.getOwnerID() == 0) {
					bmpfac = BitmapDescriptorFactory.fromResource(R.drawable.ic_crate);
				}else if(result.getOwnerID() == GameMapActivity.player.getPlayerID()){
					bmpfac = BitmapDescriptorFactory.fromResource(R.drawable.briefcase_drop_personal_img);
				}else {
					bmpfac = BitmapDescriptorFactory.fromResource(R.drawable.briefcase_drop_img);
				}
				Marker newMarker = mapIn.addMarker(new MarkerOptions()
			    .position(position)
			    .draggable(false)
			    .title(result.getCacheText())
			    .snippet(result.getOwner())
			    .flat(false)
			    .icon(bmpfac));
				GameMapActivity.markerHashMap.put(newMarker.getId(), result);
			
			}
			else {
				Toast.makeText(context,  "There was an error creating the cache", Toast.LENGTH_LONG).show();
			}
		 
	 }
	 
	

}

