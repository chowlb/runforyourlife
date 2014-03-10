package com.chowlb.runforyourlife;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

public class ShowCacheInventoryActivity extends Activity{
	private Cache cache;
	private ItemListAdapter adapter;
	private ListView inventoryLayout; 
	private Activity local;
	private Player player;
	//SavePlayerInfo saveInfoActivity = new SavePlayerInfo();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_inventory);
		local = this;
		
		Bundle extras = getIntent().getExtras();
		if(extras != null) {
			cache = extras.getParcelable("CACHE");
			player = extras.getParcelable("PLAYER");
			
			ActionBar ab = getActionBar();
			ab.setIcon(R.drawable.briefcase_drop_img);
			ab.setTitle("");
			ab.setTitle(cache.getCacheText() + ":" + cache.getCacheID() + " - " + cache.getOwner());
			
			
			
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
	public void onBackPressed() {
	    Intent data = new Intent();
	    data.putExtra("PLAYER", player);
	    setResult(Activity.RESULT_OK, data);
	    super.onBackPressed();
	}

}
