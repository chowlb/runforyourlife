package com.chowlb.runforyourlife.async;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.chowlb.runforyourlife.GameMapActivity;
import com.chowlb.runforyourlife.R;
import com.chowlb.runforyourlife.objects.Cache;
import com.chowlb.runforyourlife.utils.JSONParser;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LoadAllCachesAsync extends AsyncTask<Void, Void, List<Cache>>{

	List<Cache> cacheList = new ArrayList<Cache>();
    GoogleMap mapIn;
    Context context;
	
    public LoadAllCachesAsync(Context con, GoogleMap map) {
      context = con;
      mapIn = map;
   }
    
    @Override
    protected void onPreExecute() {
       super.onPreExecute();
       
   }
   
	
	@Override
	protected List<Cache> doInBackground(Void... params) {
		
		String link="http://www.chowlb.com/runforyourlife/getallcaches_app.php";
		JSONParser jsonParser = new JSONParser();
		JSONArray caches = jsonParser.getJSONFromUrl(link);			
		
		try{
			for(int i=0; i<caches.length(); i++) {
				JSONObject c = caches.getJSONObject(i);
				Cache cache = new Cache(c.getInt("CACHE_ID"), c.getString("OWNER_NAME"), c.getInt("OWNER_ID"), c.getDouble("LATITUDE"), c.getDouble("LONGITUDE"));
				cacheList.add(cache);
			}
		}catch(JSONException e) {
			Log.e("chowlb", "Error parsing JSON: " + e.toString());
		}
		return cacheList;
	}

	 @Override
	   protected void onPostExecute(List<Cache> result){
		 super.onPostExecute(result);
		 
		 if(null != result && !result.isEmpty()) { //checks not null or empty
			 createCacheFromList(result);
         }
         else{
             Log.e("chowlb", "No more data");
         }
		 
	 }
	 
	 
	public void createCacheFromList(List<Cache> caches) {
		if(caches != null && caches.size() >0) {
			for(int i=0; i<caches.size(); i++) {
				LatLng position = new LatLng(caches.get(i).getLatitude(), caches.get(i).getLongitude());
				Marker newMarker = mapIn.addMarker(new MarkerOptions()
			        .position(position)
			        .draggable(false)
			        .title(caches.get(i).getCacheText())
			        .snippet(caches.get(i).getOwner())
			        .flat(false)
			        .icon(BitmapDescriptorFactory.fromResource(R.drawable.briefcase_drop_img)));

				GameMapActivity.markerHashMap.put(newMarker.getId(), caches.get(i));
			}
		}
		else {
			Toast.makeText(context,  "There was an error gathering all the caches", Toast.LENGTH_LONG).show();
		}
	}

}

