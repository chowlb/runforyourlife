package com.chowlb.runforyourlife;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.chowlb.runforyourlife.adapters.ItemListAdapter;
import com.chowlb.runforyourlife.async.AddItemAsync;
import com.chowlb.runforyourlife.async.DeleteItemAsync;
import com.chowlb.runforyourlife.listeners.CacheInventoryItemListListener;
import com.chowlb.runforyourlife.objects.Cache;
import com.chowlb.runforyourlife.objects.Item;
import com.chowlb.runforyourlife.objects.Player;

public class ShowCacheInventoryActivity extends Activity{
	private Cache cache;
	private ItemListAdapter adapter;
	private ListView inventoryLayout;
	public static Player player;
	Activity activity;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_inventory);
		activity = this;
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
			
			final SparseArray<Item> checkedItems = adapter.getCheckedItems();
			
			new AlertDialog.Builder(this)
			.setTitle("Pickup Items")
			.setMessage("Do you wish to pickup the selected items?")
			.setIcon(android.R.drawable.ic_dialog_info)
			.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					int key = 0;
					for(int i = 0; i < checkedItems.size(); i++) {
					   key = checkedItems.keyAt(i);
					   
					   Item item = checkedItems.get(key);
					   
					   
					   if(player.getInventory().size()+checkedItems.size() <= player.getInventorySize()) {
							DeleteItemAsync deleteItemActivity = new DeleteItemAsync();
							deleteItemActivity.execute(item, 1);
							cache.removeItem(item);
							AddItemAsync addItemActivity = new AddItemAsync();
							addItemActivity.execute(item, player, 2);
							adapter.notifyDataSetChanged();
							player.addItem(item);
						}else {
							Toast.makeText(activity, "Inventory is full. Try selecting less items.",  Toast.LENGTH_SHORT).show();
						}
					}
				}
			})
			.setNegativeButton(android.R.string.no, null).show();
		    return true;
		}else if (itemId == R.id.menu_cache_inventory_add_item) {
			Intent intent = new Intent(this, ShowPlayerInventoryActivity.class);
			Bundle bundle = new Bundle();
			bundle.putParcelable("PLAYER", player);
			bundle.putBoolean("GPSENABLED", true);
			bundle.putBoolean("FROMCACHE", true);
			intent.putExtras(bundle);
			startActivityForResult(intent, 1);
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
