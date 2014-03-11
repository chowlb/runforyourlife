package com.chowlb.runforyourlife.listeners;

import java.util.List;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.chowlb.runforyourlife.GameMapActivity;
import com.chowlb.runforyourlife.ShowCacheInventoryActivity;
import com.chowlb.runforyourlife.async.LoadCacheInventoryAsync;
import com.chowlb.runforyourlife.interfaces.AsyncMarkerInterface;
import com.chowlb.runforyourlife.objects.Cache;
import com.chowlb.runforyourlife.objects.Item;
import com.chowlb.runforyourlife.objects.Player;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.Marker;




public class GameMapListener implements OnMarkerClickListener, AsyncMarkerInterface{
	
	private Cache cache;
	private Player player;
	FragmentActivity local;
	LoadCacheInventoryAsync lcia = new LoadCacheInventoryAsync();
	
	public GameMapListener(FragmentActivity act, Player p) {
		player = p;
		local = act;
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		cache = (Cache) GameMapActivity.markerHashMap.get(marker.getId());
		LoadCacheInventoryAsync lcia = new LoadCacheInventoryAsync();
		Location locMarker = new Location("marker");
		locMarker.setLatitude(marker.getPosition().latitude);
		locMarker.setLongitude(marker.getPosition().longitude);
		Location last = GameMapActivity.locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		float distance = getDistanceInMiles(locMarker, last);
		
		
		if(distance <= 0.10) {
			lcia.delegate = this;
			lcia.execute(String.valueOf(cache.getCacheID()));
		}else {
			Toast.makeText(local, "Too far from cache to open.", Toast.LENGTH_LONG).show();
		}
		
		return false;
	}
	
	
	@Override
	public void handleInventory(List<Item> invResult) {
		cache.setInventory(invResult);
		
		Intent intent = new Intent(local, ShowCacheInventoryActivity.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable("CACHE", cache);
		bundle.putParcelable("PLAYER", player);
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
	
	


