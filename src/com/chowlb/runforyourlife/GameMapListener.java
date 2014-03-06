package com.chowlb.runforyourlife;


import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;




public class GameMapListener implements OnMapLongClickListener{
	private GoogleMap googleMap;
	Player p;
	FragmentActivity local;
	private LatLng pnt;
	private AddCacheAsyncActivity acaa = new AddCacheAsyncActivity();
	
	
	public GameMapListener(GoogleMap mMap, Player player, FragmentActivity act) {
		//acaa.delegate=this;
		googleMap = mMap;
		p = player;
		local = act;
	}
	
	@Override
	public void onMapLongClick(LatLng point) {
		pnt = point;
				
		final AlertDialog.Builder builder = new AlertDialog.Builder(local);
	    builder.setMessage("Do you want to place a supply cache here?")
	           .setCancelable(false)
	           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	               public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
	            	   acaa.execute(p, pnt);
	               }
	           })
	           .setNegativeButton("No", null);
	    final AlertDialog alert = builder.create();
	    alert.show();
		
		
		
	}
	
//	public void createCache(Integer cacheID) {
//		if(cacheID != null && cacheID > 0) {
//			Marker cache = googleMap.addMarker(new MarkerOptions()
//		    .position(cac)
//		    .draggable(false)
//		    .title("Supply Cache")
//		    .snippet(p.getPlayerName())
//		    .flat(true)
//		    .icon(BitmapDescriptorFactory.fromResource(R.drawable.briefcase_drop_img)));
//		}
//		else {
//			Toast.makeText(local,  "There was an error creating the cache", Toast.LENGTH_LONG).show();
//		}
//	}

	

	
	
}
	
	


