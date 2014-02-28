package com.chowlb.runforyourlife;

import android.app.ActionBar;
import android.app.Activity;
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
			player = (Player) extras.getParcelable("PLAYER");
			Log.e("chowlb", "Player name passed: " + player.getPlayerName());
			ActionBar ab = getActionBar();
			ab.setIcon(R.drawable.ic_action_military_backpack_radio_256);
			ab.setTitle("");
			ab.setTitle(player.getPlayerName() + " - Inventory" );
			
			Log.e("chowlb", "Inventory count: " + player.getInventory().size());
			
			ListView inventoryLayout = (ListView) findViewById(R.id.inventoryListView);
			adapter = new ItemListAdapter(this, player.getInventory());
			inventoryLayout.setAdapter(adapter);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_inventory, menu);
		return true;
	}
	
//	public void handleInventory(String invResult) {
//		String[] separated = invResult.split("<br>");
//		Log.e("chowlb", "Inventory list size: " + separated.length);
//	    if(invResult != null && separated.length > 0) {
//	    	Item item = new Item();
//	    	for(int i = 0; i < separated.length; i++) {
//	    		String[] items = separated[i].split(";");
//	    		if(items.length>0) {
//	    			item.setItemId(Integer.parseInt(items[0].toString()));
//	    			item.setName(items[1]);
//	    			item.setDescription(items[2]);
//	    			item.setItemType(items[3]);
//	    			item.setStatus(items[4]);
//	    			item.setAttribute(Integer.parseInt(items[5].toString()));
//	    			 			
//	    			
//	    			if(!player.addItem(item)) {
//	    				Toast.makeText(this, "Inventory is full!", Toast.LENGTH_LONG).show();
//	    			}
//	    			
//	    			ListView inventoryLayout = (ListView) findViewById(R.id.inventoryListView);
//	    			adapter = new ItemListAdapter(this, player.getInventory());
//	    			inventoryLayout.setAdapter(adapter);
//	    			//inventoryLayout.setOnItemClickListener(new ListListener(result, listactivity));
//	    		}
//	    	}
//	    }else {
//	    	Toast.makeText(this,  "There was an error getting inventory. Check your network settings.", Toast.LENGTH_LONG).show();
//	    }
//	    
//	    
//	    
//	}

}
