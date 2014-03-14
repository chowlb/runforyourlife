package com.chowlb.runforyourlife;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.chowlb.runforyourlife.adapters.ItemListAdapter;
import com.chowlb.runforyourlife.async.AddItemAsync;
import com.chowlb.runforyourlife.async.DeleteItemAsync;
import com.chowlb.runforyourlife.async.SavePlayerInfoAsync;
import com.chowlb.runforyourlife.listeners.InventoryItemListListener;
import com.chowlb.runforyourlife.objects.Cache;
import com.chowlb.runforyourlife.objects.Item;
import com.chowlb.runforyourlife.objects.Player;

public class ShowPlayerInventoryActivity extends Activity {
	public static Player player;
	private ItemListAdapter adapter;
	private ListView inventoryLayout; 
	public static ActionBar ab;
	SavePlayerInfoAsync saveInfoActivity = new SavePlayerInfoAsync();
	Cache cache;
	boolean cacheEntry = false;
	Activity activity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_inventory);
		activity = this;
		
		Bundle extras = getIntent().getExtras();
		if(extras != null) {
			player = extras.getParcelable("PLAYER");
			if(extras.containsKey("CACHE")) {
				cache = extras.getParcelable("CACHE");
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
	    if(cacheEntry) {
	    	Log.e("chowlb", "Adding parcelable cache");
	    	ShowCacheInventoryActivity.adapter.notifyDataSetChanged();
	    	data.putExtra("CACHE", cache);
	    }
	    setResult(Activity.RESULT_OK, data);
	    super.onBackPressed();
	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if(itemId == R.id.menu_player_inventory_take_item) {
			
			final SparseArray<Item> checkedItems = adapter.getCheckedItems();
			
			new AlertDialog.Builder(this)
			.setTitle("Store Items")
			.setMessage("Do you wish to store the selected items in Cache:" + String.valueOf(cache.getCacheID()) + "?")
			.setIcon(android.R.drawable.ic_dialog_info)
			.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					int key = 0;
					for(int i = 0; i < checkedItems.size(); i++) {
					   key = checkedItems.keyAt(i);
					   
					   Item item = checkedItems.get(key);
					   
					   Log.e("chowlb", "Cache inv before: " + cache.getInventory().size());
					   if(cache.getInventory().size()+checkedItems.size() <= cache.getInventorySize()) {
							DeleteItemAsync deleteItemActivity = new DeleteItemAsync();
							deleteItemActivity.execute(item, 2);
							player.removeItem(item);
							AddItemAsync addItemActivity = new AddItemAsync();
							addItemActivity.execute(item, cache, 1);
							cache.addItem(item);
							Log.e("chowlb", "Cache inv after: " + cache.getInventory().size());
							adapter.notifyDataSetChanged();
						}else {
							Toast.makeText(activity, "Cache is full. Try selecting less items.",  Toast.LENGTH_SHORT).show();
						}
					}
					ab.setTitle("");
					ab.setTitle(player.getPlayerName() + " - Inventory " + player.getInventory().size() + "/" + player.getInventorySize() );
				}
			})
			.setNegativeButton(android.R.string.no, null).show();
		    return true;
		}else if (itemId == R.id.menu_cache_inventory_add_item) {
			Intent intent = new Intent(this, ShowPlayerInventoryActivity.class);
			Bundle bundle = new Bundle();
			bundle.putParcelable("PLAYER", player);
			Log.e("chowlb", "Returning result cache!");
			bundle.putBoolean("CACHE", true);
			intent.putExtras(bundle);
			startActivityForResult(intent, 1);
			return true;	
		}else {
			return super.onOptionsItemSelected(item);
		}
	}
	

}
