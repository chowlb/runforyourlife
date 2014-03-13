package com.chowlb.runforyourlife;

import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.chowlb.runforyourlife.adapters.ItemListAdapter;
import com.chowlb.runforyourlife.listeners.CacheInventoryItemListListener;
import com.chowlb.runforyourlife.objects.Cache;
import com.chowlb.runforyourlife.objects.Item;
import com.chowlb.runforyourlife.objects.Player;

public class ShowCacheInventoryActivity extends Activity{
	private Cache cache;
	private ItemListAdapter adapter;
	private ListView inventoryLayout;
	public static Player player;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_inventory);
		
		Bundle extras = getIntent().getExtras();
		if(extras != null) {
			cache = extras.getParcelable("CACHE");
			player = extras.getParcelable("PLAYER");
			
			ActionBar ab = getActionBar();
			if(cache.getOwnerID() == 0) {
				ab.setIcon(R.drawable.ic_crate);
			}else if(cache.getOwnerID() == GameMapActivity.player.getPlayerID()){
				ab.setIcon(R.drawable.briefcase_drop_personal_img);
			}else {
				ab.setIcon(R.drawable.briefcase_drop_img);
			}
			ab.setTitle("");
			if(cache.getOwner().equals("")) {
				ab.setTitle(cache.getCacheText() + ":" + cache.getCacheID());
			}else {
				ab.setTitle(cache.getCacheText() + ":" + cache.getCacheID() + " - " + cache.getOwner());
			}
			
			
			
			
			inventoryLayout = (ListView) findViewById(R.id.inventoryListView);
			adapter = new ItemListAdapter(this, cache.getInventory());
			inventoryLayout.setAdapter(adapter);
			inventoryLayout.setOnItemClickListener(new CacheInventoryItemListListener(cache.getInventory(),this, adapter, cache, player ));
			inventoryLayout.setOnItemLongClickListener(new CacheInventoryItemListListener(cache.getInventory(), this, adapter, cache, player));
		}
		else{
			Toast.makeText(this, "Error retrieving inventory.",  Toast.LENGTH_LONG).show();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_cache_inventory, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if(itemId == R.id.menu_cache_inventory_take_item) {
			
			ItemListAdapter itemListAdapter = (ItemListAdapter) inventoryLayout.getAdapter();
			HashMap<Integer, Item> checkedItems = itemListAdapter.getCheckedItems();
			Toast.makeText(this, "Number of checked items: " + checkedItems.size(), Toast.LENGTH_SHORT).show();
		    return true;
		}else if (itemId == R.id.menu_cache_inventory_add_item) {
			//android.os.Process.killProcess(android.os.Process.myPid());
			return true;	
		}else {
			return super.onOptionsItemSelected(item);
		}
	}
	
	
	
	@Override
	public void onBackPressed() {
		Intent data = new Intent();
	    data.putExtra("PLAYER", player);
	    setResult(Activity.RESULT_OK, data);
	    super.onBackPressed();
	}

}
