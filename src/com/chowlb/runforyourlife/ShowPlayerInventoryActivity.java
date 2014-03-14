package com.chowlb.runforyourlife;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;
import android.widget.Toast;

import com.chowlb.runforyourlife.adapters.ItemListAdapter;
import com.chowlb.runforyourlife.async.SavePlayerInfoAsync;
import com.chowlb.runforyourlife.listeners.InventoryItemListListener;
import com.chowlb.runforyourlife.objects.Player;

public class ShowPlayerInventoryActivity extends Activity {
	public static Player player;
	private ItemListAdapter adapter;
	private ListView inventoryLayout; 
	public static ActionBar ab;
	SavePlayerInfoAsync saveInfoActivity = new SavePlayerInfoAsync();
	boolean cacheEntry = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_inventory);
		//local = this;
		
		Bundle extras = getIntent().getExtras();
		if(extras != null) {
			player = extras.getParcelable("PLAYER");
			boolean gpsEnabled = extras.getBoolean("GPSENABLED");
			if(!gpsEnabled){
				Toast.makeText(this, "Without GPS enabled you can only view your inventory.",  Toast.LENGTH_LONG).show();
			}
			if(extras.containsKey("FROMCACHE")) {
				cacheEntry = true;
			}
			
			ab = getActionBar();
			ab.setIcon(R.drawable.ic_action_ic_action_military_backpack_radio);
			ab.setTitle("");
			ab.setTitle(player.getPlayerName() + " - Inventory " + player.getInventory().size() + "/" + player.getInventorySize() );
			
			
			inventoryLayout = (ListView) findViewById(R.id.inventoryListView);
			adapter = new ItemListAdapter(this, player.getInventory());
			inventoryLayout.setAdapter(adapter);
			inventoryLayout.setOnItemClickListener(new InventoryItemListListener(this));
			inventoryLayout.setOnItemLongClickListener(new InventoryItemListListener(this));
		}
		else{
			Toast.makeText(this, "Error retrieving inventory.",  Toast.LENGTH_LONG).show();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		if(Build.VERSION.SDK_INT >= 11) {
			selectMenu(menu);
		}
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if(Build.VERSION.SDK_INT < 11) {
			selectMenu(menu);
		}
		return true;
	}

	public void selectMenu(Menu menu) {
		menu.clear();
		MenuInflater inflater = getMenuInflater();
		if(cacheEntry) {
			inflater.inflate(R.menu.show_inventory_from_cache, menu);
		}
		else{
			inflater.inflate(R.menu.show_inventory, menu);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	public void onBackPressed() {
	    Intent data = new Intent();
	    data.putExtra("PLAYER", player);
	    setResult(Activity.RESULT_OK, data);
	    super.onBackPressed();
	}
	
	
	
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		int itemId = item.getItemId();
//		if(itemId == android.R.id.home) {
//			Intent data = new Intent();
//		    data.putExtra("PLAYER", player);
//		    setResult(Activity.RESULT_OK, data);
//		    finish();
//		    return true;
//		}else if (itemId == R.id.menu_inventory_quit) {
//			android.os.Process.killProcess(android.os.Process.myPid());
//			return true;
//		} else if(itemId == R.id.menu_inventory_logout){
//			SharedPreferences prefs = local.getSharedPreferences("com.chowlb.runforyourlife", Context.MODE_PRIVATE);
//			SharedPreferences.Editor editor = prefs.edit();
//			editor.clear();
//			editor.commit();
//			doExit();
//			return true;		
//		}else {
//			return super.onOptionsItemSelected(item);
//		}
//	}

	
	public void doExit() {
		new AlertDialog.Builder(this)
		.setTitle("Exit")
		.setMessage("Do you wish to Exit the game? You will have to login again. Choose 'Quit' to save login info.")
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				saveInfoActivity.execute(player);
				android.os.Process.killProcess(android.os.Process.myPid());
				
			}
		})
		.setNegativeButton(android.R.string.no, null).show();
		
	}
	
	
	

}
