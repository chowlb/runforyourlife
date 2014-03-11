package com.chowlb.runforyourlife.listeners;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

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
		lcia.delegate = this;
		lcia.execute(String.valueOf(cache.getCacheID()));
		
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

	

	
}
	
	


