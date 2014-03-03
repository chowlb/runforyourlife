package com.chowlb.runforyourlife;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
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
import com.google.android.gms.maps.model.MarkerOptions;

public class GameMapActivity extends FragmentActivity{

	public Player player;
	private GoogleMap googleMap;
	static final LatLng TutorialsPoint = new LatLng(21 , 57);
	private LocationManager locManager;
	private LocationListener locListener;
	private FragmentActivity local;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		local = this;
		Intent i = getIntent();
		
		Bundle extras = getIntent().getExtras();
		if(extras != null) {
			Log.e("chowlb", "extras");
			
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

		            /*googleMap.addMarker(new MarkerOptions()
		                    .position(
		                            new LatLng(location.getLatitude(), location
		                                    .getLongitude()))
		                    .title("my position")
		                    .icon(BitmapDescriptorFactory
		                            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));*/

		            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
		                    location.getLatitude(), location.getLongitude()), 25.0f));

		        }
		    };
		    locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locListener);
	}
	
	private void buildAlertMessageNoGps() {
	    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
	           .setCancelable(false)
	           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	               public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
	                   startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
	               }
	           })
	           .setNegativeButton("No", new DialogInterface.OnClickListener() {
	               public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
	                    dialog.cancel();
	                    Intent intent = new Intent(local, ShowInventoryActivity.class);
	                   
		       			 Bundle bundle = new Bundle();
		       			 bundle.putParcelable("PLAYER", player);
		       			 bundle.putBoolean("GPSENABLED", false);
		       			 intent.putExtras(bundle);
	                    
		       			 startActivity(intent);
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
		if ( !locManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
	        buildAlertMessageNoGps();
	    }
		else{
			locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locListener);
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
			startActivity(intent);
			return true;
		} else if(itemId == R.id.menu_logout){
			SharedPreferences prefs = this.getSharedPreferences("com.chowlb.runforyourlife", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			editor.clear();
			editor.commit();
			finish();
			return true;
		}else {
			return super.onOptionsItemSelected(item);
		}
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
				finish();
			}
		})
		.setNegativeButton(android.R.string.no, null).show();
		
	}
	
	@Override
	public void onBackPressed() {
		doExit();
	}

}
