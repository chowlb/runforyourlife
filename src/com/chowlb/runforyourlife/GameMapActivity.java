package com.chowlb.runforyourlife;


import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GameMapActivity extends FragmentActivity implements AsyncMapInterface{

	public Player player;
	private GoogleMap googleMap;
	static final LatLng TutorialsPoint = new LatLng(21 , 57);
	private LocationManager locManager;
	private LocationListener locListener;
	private FragmentActivity local;
	SavePlayerInfo saveInfoActivity = new SavePlayerInfo();
	AddCacheAsyncActivity acaa;
	LoadAllCaches lac = new LoadAllCaches();
	static final float COORDINATE_OFFSET = 0.00002f; // You can change this value according to your need
	public static HashMap<String, Cache> markerHashMap = new HashMap<String, Cache>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		local = this;
		lac.delegate = this;
		
		
		Intent i = getIntent();
		Log.e("chowlb", "GAME MAP ONCREATE");
		Bundle extras = getIntent().getExtras();
		if(extras != null) {			
			player = i.getParcelableExtra("PLAYER");
			
			setTitle("");
			setTitle(player.getPlayerName());
			
			locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			
			try {
				if(googleMap == null) {
					googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.main_map)).getMap();
				}
				googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
				googleMap.setMyLocationEnabled(true);
				googleMap.setOnMarkerClickListener(new GameMapListener(local, player));
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		else {
			Log.e("chowlb", "NO EXTRAS PASSED");
		}
		
		locListener = new LocationListener() {

		        @Override
		        public void onStatusChanged(String provider, int status,
		                Bundle extras) {
		            // TODO Auto-generated method stub

		        }

		        @Override
		        public void onProviderEnabled(String provider) {
		            // TODO Auto-generated method stub
		        	
		        }

		        @Override
		        public void onProviderDisabled(String provider) {
		            // TODO Auto-generated method stub
		        	buildAlertMessageNoGps();
		        }

		        @Override
		        public void onLocationChanged(Location location) {

		            Toast.makeText(getApplicationContext(),
		                    location.getLatitude() + " " + location.getLongitude(),
		                    Toast.LENGTH_LONG).show();

		            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
		                    location.getLatitude(), location.getLongitude()), 30.0f));

		        }
		    };
		    locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locListener);
		    
		   
		    lac.execute("");
		    
	}
	
	private void buildAlertMessageNoGps() {
	    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
	           .setCancelable(false)
	           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	               public void onClick(final DialogInterface dialog, final int id) {
	            	   Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	                   startActivity(intent);
	               }
	           })
	           .setNegativeButton("No", new DialogInterface.OnClickListener() {
	               public void onClick(final DialogInterface dialog, final int id) {
	                    dialog.cancel();
	                    Intent intent = new Intent(local, ShowInventoryActivity.class);
	                   
		       			 Bundle bundle = new Bundle();
		       			 bundle.putParcelable("PLAYER", player);
		       			 bundle.putBoolean("GPSENABLED", false);
		       			 intent.putExtras(bundle);
	                    
		       			startActivityForResult(intent, 1);
	               }
	           });
	    final AlertDialog alert = builder.create();
	    alert.show();
	}
	
	@Override 
    public void onPause() {
		super.onPause();
		locManager.removeUpdates(locListener);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if ( locManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
	       	locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1500, 0, locListener);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.menu_showInventory) {
			Intent intent = new Intent(local, ShowInventoryActivity.class);
			Bundle bundle = new Bundle();
			bundle.putParcelable("PLAYER", player);
			bundle.putBoolean("GPSENABLED", true);
			intent.putExtras(bundle);
			startActivityForResult(intent, 1);
			return true;
		} else if(itemId == R.id.menu_addDrop) {
			final AlertDialog.Builder builder = new AlertDialog.Builder(local);
		    builder.setMessage("Do you want to place a supply cache here?")
		           .setCancelable(false)
		           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		               public void onClick(final DialogInterface dialog, final int id) {
		            	   addMarkerService();
		               }
		           })
		           .setNegativeButton("No", null);
		    final AlertDialog alert = builder.create();
		    alert.show();
		    return true;
		} else if(itemId == R.id.menu_logout){
			SharedPreferences prefs = local.getSharedPreferences("com.chowlb.runforyourlife", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			editor.clear();
			editor.commit();
			doExit();
			return true;		
		}else {
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void addMarkerService() {
		new AddCacheAsyncActivity(this, googleMap).execute(player, locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
	}
	
	public void doExit() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this)
		.setTitle("Exit")
		.setMessage("Do you wish to Exit the game?")
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				saveInfoActivity.execute(player);
				finish();
				
			}
		})
		.setNegativeButton(android.R.string.no, null).show();
		
	}
	
	@Override
	public void onBackPressed() {
		doExit();
	}
		
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);

	    if(requestCode == 1 && resultCode == Activity.RESULT_OK){
	        
	    	player = data.getParcelableExtra("PLAYER");
	        //Do whatever you want with yourData
	    }
	}

	@Override
	public void createCacheFromList(List<Cache> caches) {
		if(caches != null && caches.size() >0) {
			for(int i=0; i<caches.size(); i++) {
			Marker newMarker = googleMap.addMarker(new MarkerOptions()
		        .position(caches.get(i).getLocation())
		        .draggable(false)
		        .title(caches.get(i).getCacheText())
		        .snippet(caches.get(i).getOwner())
		        .flat(false)
		        .icon(BitmapDescriptorFactory.fromResource(R.drawable.briefcase_drop_img)));

				markerHashMap.put(newMarker.getId(), caches.get(i));
			}
		}
		else {
			Toast.makeText(local,  "There was an error gathering all the caches", Toast.LENGTH_LONG).show();
		}
	}

	
	

}
