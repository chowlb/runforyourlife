package com.chowlb.runforyourlife.listeners;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chowlb.runforyourlife.GameMapActivity;
import com.chowlb.runforyourlife.ShowCacheInventoryActivity;
import com.chowlb.runforyourlife.async.LoadCacheInventoryAsync;
import com.chowlb.runforyourlife.interfaces.AsyncMarkerInterface;
import com.chowlb.runforyourlife.objects.Cache;
import com.chowlb.runforyourlife.objects.Item;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.Marker;




public class GameMapListener implements OnMarkerClickListener, AsyncMarkerInterface{
	
	private Cache cache;
	FragmentActivity local;
	
	public GameMapListener(FragmentActivity act) {
		local = act;
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
				
		cache = (Cache) GameMapActivity.markerHashMap.get(marker.getId());
		Log.e("chowlb", "PIN: " + cache.getPin());
		
		final Location locMarker = new Location("marker");
		locMarker.setLatitude(marker.getPosition().latitude);
		locMarker.setLongitude(marker.getPosition().longitude);
		
		final Location last = GameMapActivity.mLocationClient.getLastLocation();
		
		//if(last == null) {
		//	Toast.makeText(local, "Location not found, cannot open caches. Check GPS settings.", Toast.LENGTH_SHORT).show();
		//}else {
			if(!cache.getPin().equals("null")) {
				AlertDialog.Builder editalert = new AlertDialog.Builder(local);
	
				editalert.setTitle("Locked!");
				editalert.setMessage("Enter the 4 Digit PIN number.");
	
				final EditText input = new EditText(local);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				        LinearLayout.LayoutParams.MATCH_PARENT,
				        LinearLayout.LayoutParams.MATCH_PARENT);
				input.setLayoutParams(lp);
				editalert.setView(input);
				input.setInputType(InputType.TYPE_CLASS_NUMBER);
				InputFilter[] filterArray = new InputFilter[1];
				filterArray[0] = new InputFilter.LengthFilter(4);
				input.setFilters(filterArray);
				
				editalert.setPositiveButton("UNLOCK", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int whichButton) {
				    	if(cache.getPin().equals(input.getText().toString())) {
				    		showInventory(locMarker, last);
				    	}
				    	else {
				    		Toast.makeText(local, "INVALID PIN", Toast.LENGTH_SHORT).show();
				    	}
	
				    }
				});
				editalert.setNegativeButton("CANCEL", null);
				
				editalert.show();
			}
			else {
				showInventory(locMarker, last);
			}
		//}

		return false;
	}
	
	
	private void showInventory(Location locMarker, Location last) {
		float distance = 0; //= getDistanceInMiles(locMarker, last);
		
		//THIS IS CHECKING TO SEE IF YOU'RE 1/10th OF A MILE FROM THE CACHE
		/******************************************************************************
		// TODO change this back after testing!!!!!!!!
		//if(distance <= 0.10) {
		 * *****************************************************************************
		 */
		if(distance <= 0.10) {
			LoadCacheInventoryAsync lcia = new LoadCacheInventoryAsync();
			lcia.delegate = this;
			lcia.execute(String.valueOf(cache.getCacheID()));
		}else {
			Toast.makeText(local, "Too far from cache to open.", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	public void handleInventory(List<Item> invResult) {
		cache.setInventory(invResult);
		
		Intent intent = new Intent(local, ShowCacheInventoryActivity.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable("CACHE", cache);
		bundle.putParcelable("PLAYER", GameMapActivity.player);
		intent.putExtras(bundle);
		local.startActivityForResult(intent, 1);
	}

	
	public float getDistanceInMiles(Location p1, Location p2) {
	    double lat1 = ((double)p1.getLatitude());
	    double lng1 = ((double)p1.getLongitude());
	    double lat2 = ((double)p2.getLatitude());
	    double lng2 = ((double)p2.getLongitude());
	    float [] dist = new float[1];
	    Location.distanceBetween(lat1, lng1, lat2, lng2, dist);
	    return dist[0] * 0.000621371192f;
	}

	
	
}
	
	


