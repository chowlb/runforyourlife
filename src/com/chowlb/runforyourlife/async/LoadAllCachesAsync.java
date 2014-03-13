package com.chowlb.runforyourlife.async;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.chowlb.runforyourlife.GameMapActivity;
import com.chowlb.runforyourlife.R;
import com.chowlb.runforyourlife.objects.Cache;
import com.chowlb.runforyourlife.utils.JSONParser;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LoadAllCachesAsync extends AsyncTask<Void, Void, List<Cache>>{

	List<Cache> cacheList = new ArrayList<Cache>();
    GoogleMap mapIn;
    Context context;
	ProgressDialog mProgressDialog;
    public LoadAllCachesAsync(Context con, GoogleMap map) {
      context = con;
      mapIn = map;
   }
    
    @Override
   	protected void onPreExecute() {
   		  mProgressDialog = new ProgressDialog(context);
 		  mProgressDialog =ProgressDialog.show(context, "", "Loading Caches, Please Wait",true,false);
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
				cache.setPin(c.getString("PIN"));
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
		 
		 if (mProgressDialog != null || mProgressDialog.isShowing()){
	         mProgressDialog.dismiss();
		 }
		 
	 }
	 
	 
	public void createCacheFromList(List<Cache> caches) {
		if(caches != null && caches.size() >0) {
			for(int i=0; i<caches.size(); i++) {
				LatLng position = new LatLng(caches.get(i).getLatitude(), caches.get(i).getLongitude());
				
				BitmapDescriptor bmpfac;
				if(caches.get(i).getOwnerID() == 0) {
					bmpfac = BitmapDescriptorFactory.fromResource(R.drawable.ic_crate);
				}else if(caches.get(i).getOwnerID() == GameMapActivity.player.getPlayerID()){
					bmpfac = BitmapDescriptorFactory.fromResource(R.drawable.briefcase_drop_personal_img);
				}else {
					bmpfac = BitmapDescriptorFactory.fromResource(R.drawable.briefcase_drop_img);
				}
				Marker newMarker = mapIn.addMarker(new MarkerOptions()
			        .position(position)
			        .draggable(false)
			        .title(caches.get(i).getCacheText())
			        .snippet(caches.get(i).getOwner())
			        .flat(false)
			        .icon(bmpfac));

				GameMapActivity.markerHashMap.put(newMarker.getId(), caches.get(i));
			}
		}
		else {
			Toast.makeText(context,  "There was an error gathering all the caches", Toast.LENGTH_LONG).show();
		}
	}

}

