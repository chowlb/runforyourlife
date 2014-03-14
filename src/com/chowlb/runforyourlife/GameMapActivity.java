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
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chowlb.runforyourlife.async.AddCacheAsync;
import com.chowlb.runforyourlife.async.LoadAllCachesAsync;
import com.chowlb.runforyourlife.async.LoadPlayerInventoryAsync;
import com.chowlb.runforyourlife.async.SavePlayerInfoAsync;
import com.chowlb.runforyourlife.interfaces.AsyncLoadInventoryInterface;
import com.chowlb.runforyourlife.listeners.GameMapListener;
import com.chowlb.runforyourlife.objects.Cache;
import com.chowlb.runforyourlife.objects.Item;
import com.chowlb.runforyourlife.objects.Player;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.SupportMapFragment;

public class GameMapActivity extends FragmentActivity 
	implements 
	AsyncLoadInventoryInterface, 
	ConnectionCallbacks,
    OnConnectionFailedListener,
    LocationListener,
    OnMyLocationButtonClickListener{

	public static Player player;
	public static GoogleMap googleMap;
	private FragmentActivity local;
	public static LocationClient mLocationClient;
	
	public static HashMap<String, Cache> markerHashMap = new HashMap<String, Cache>();
	
	SavePlayerInfoAsync saveInfoActivity = new SavePlayerInfoAsync();
	
	private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(1000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		local = this;		
		Intent i = getIntent();
		Bundle extras = getIntent().getExtras();
		if(extras != null) {			
			player = i.getParcelableExtra("PLAYER");
			setTitle("");
			setTitle(player.getPlayerName());
		}
		else {
			Log.e("chowlb", "NO EXTRAS PASSED");
		}
		
		setUpMapIfNeeded();
        setUpLocationClientIfNeeded();
        new LoadAllCachesAsync(this, googleMap).execute();
		
		
	}
	
	
	@Override
    public boolean onMyLocationButtonClick() {
		 Toast.makeText(this, "MyLocation button clicked Location: " + mLocationClient.getLastLocation(), Toast.LENGTH_SHORT).show();
		// Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }
	
	@Override 
    public void onPause() {
		super.onPause();
		if (mLocationClient != null) {
            mLocationClient.disconnect();
        }
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		setUpMapIfNeeded();
        setUpLocationClientIfNeeded();
        mLocationClient.connect();
		LoadPlayerInventoryAsync lpia = new LoadPlayerInventoryAsync();
		lpia.delegate = this;
		lpia.execute(player);	
		//if(mLocationClient.getLastLocation() == null){
		//	Toast.makeText(this, "Can't find your location. Your activity will be serverly limited. Try turning on your GPS dingus.", Toast.LENGTH_SHORT).show();
		//}
	}
	
	 private void setUpMapIfNeeded() {
	    // Do a null check to confirm that we have not already instantiated the map.
	    if (googleMap == null) {

			//Log.e("chowlb", "gooleMap is null");
	    	// Try to obtain the map from the SupportMapFragment.
	    	googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.main_map)).getMap();
	        // Check if we were successful in obtaining the map.
	        if (googleMap != null) {
	        	googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
	        	googleMap.setMyLocationEnabled(true);
	        	googleMap.setOnMyLocationButtonClickListener(this);
	        	googleMap.setOnMarkerClickListener(new GameMapListener(local));
	        }
	    }
	    
	}

	 private void setUpLocationClientIfNeeded() {
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(getApplicationContext(), 
            		this,  // ConnectionCallbacks
                    this); // OnConnectionFailedListener
        }
    }

    /**
     * Implementation of {@link LocationListener}.
     */
    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getApplicationContext(), "Location = " + location, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        mLocationClient.requestLocationUpdates(
                REQUEST,
                this);  // LocationListener
    }

    /**
     * Callback called when disconnected from GCore. Implementation of {@link ConnectionCallbacks}.
     */
    @Override
    public void onDisconnected() {
        // Do nothing
    	buildAlertMessageNoGps();
    }

    /**
     * Implementation of {@link OnConnectionFailedListener}.
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Do nothing
    	buildAlertMessageNoGps();
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
	                    Intent intent = new Intent(local, ShowPlayerInventoryActivity.class);
	                   
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.menu_showInventory) {
			Intent intent = new Intent(local, ShowPlayerInventoryActivity.class);
			Bundle bundle = new Bundle();
			bundle.putParcelable("PLAYER", player);
			intent.putExtras(bundle);
			startActivityForResult(intent, 1);
			return true;
		} else if(itemId == R.id.menu_addDrop) {
			
			if(mLocationClient.getLastLocation() != null){
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
			}else {
				Toast.makeText(this, "Location not found, cannot place cache", Toast.LENGTH_SHORT).show();
			}
		    
		    return true;
		} else if(itemId == R.id.menu_logout){
			SharedPreferences prefs = local.getSharedPreferences("com.chowlb.runforyourlife", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			editor.clear();
			editor.commit();
			doExit();
			return true;		
		} else if(itemId == R.id.menu_refresh_caches){
			new LoadAllCachesAsync(this, googleMap).execute();
			return true;		
		}else {
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void addMarkerService() {
		
		AlertDialog.Builder editalert = new AlertDialog.Builder(local);

		editalert.setTitle("Lock It Up!");
		editalert.setMessage("Enter a 4 Digit PIN number or cancel.");

		
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
		
		editalert.setPositiveButton("SET", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int whichButton) {
		    	addCache(input.getText().toString());
		    }
		});
		editalert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int whichButton) {
		    	addCache(null);
		    }
		});
		
		editalert.show();
		
	}
	
	public void addCache(String input) {
		new AddCacheAsync(this, googleMap).execute(player, mLocationClient.getLastLocation(), input);
	}
	
	public void doExit() {
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
	    
	    Log.e("chowlb", "Returning result from activity");
	    
	    if(requestCode == 1 && resultCode == Activity.RESULT_OK){
	    	player = data.getParcelableExtra("PLAYER");
	    }
	}
	
	@Override
	public void handleInventoryLoading(List<Item> invResult) {
		
		player.setInventory(invResult);
		Log.e("chowlb", "LoadPlayerInventorySize: " + player.getInventory().size());
	}
}
