package com.chowlb.runforyourlife;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

public class ShowInventoryActivity extends Activity {
	private Player player;
	private ItemListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_inventory);
		
		
		Bundle extras = getIntent().getExtras();
		if(extras != null) {
			player = extras.getParcelable("PLAYER");
			boolean gpsEnabled = extras.getBoolean("GPSENABLED");
			if(!gpsEnabled){
				Toast.makeText(this, "Without GPS enabled you can only view your inventory.",  Toast.LENGTH_LONG).show();
			}
			Log.e("chowlb", "Player name passed: " + player.getPlayerName());
			ActionBar ab = getActionBar();
			ab.setIcon(R.drawable.ic_action_military_backpack_radio_256);
			ab.setTitle("");
			ab.setTitle(player.getPlayerName() + " - Inventory" );
			
			Log.e("chowlb", "Inventory count: " + player.getInventory().size());
			
			ListView inventoryLayout = (ListView) findViewById(R.id.inventoryListView);
			adapter = new ItemListAdapter(this, player.getInventory());
			inventoryLayout.setAdapter(adapter);
			inventoryLayout.setOnItemClickListener(new InventoryItemListListener(player.getInventory(),this ));
		}
		else{
			Toast.makeText(this, "Error retrieving inventory.",  Toast.LENGTH_LONG).show();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_inventory, menu);
		return true;
	}
	
	
	
	

}
