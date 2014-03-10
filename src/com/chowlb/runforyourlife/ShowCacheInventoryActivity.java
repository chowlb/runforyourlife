package com.chowlb.runforyourlife;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class ShowCacheInventoryActivity extends Activity {
	private Cache cache;
	private ItemListAdapter adapter;
	private ListView inventoryLayout; 
	private Activity local;
	//SavePlayerInfo saveInfoActivity = new SavePlayerInfo();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_inventory);
		local = this;
		
		Bundle extras = getIntent().getExtras();
		if(extras != null) {
			cache = extras.getParcelable("CACHE");
			
			ActionBar ab = getActionBar();
			ab.setIcon(R.drawable.briefcase_drop_img);
			ab.setTitle("");
			ab.setTitle(cache.getCacheText() + ":" + cache.getCacheID() + " - " + cache.getOwner());
			
			
			
			inventoryLayout = (ListView) findViewById(R.id.inventoryListView);
			adapter = new ItemListAdapter(this, cache.getInventory());
			inventoryLayout.setAdapter(adapter);
			inventoryLayout.setOnItemClickListener(new CacheInventoryItemListListener(cache.getInventory(),this, adapter, cache ));
			//inventoryLayout.setOnItemLongClickListener(new CacheInventoryItemListListener(cache.getInventory(), this, adapter, cache));
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


	
//	@Override
//	public void onBackPressed() {
//	    Intent data = new Intent();
//	    data.putExtra("PLAYER", player);
//	    setResult(Activity.RESULT_OK, data);
//	    super.onBackPressed();
//	}
	
	
	
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

	
//	public void doExit() {
//		// TODO Auto-generated method stub
//		new AlertDialog.Builder(this)
//		.setTitle("Exit")
//		.setMessage("Do you wish to Exit the game? You will have to login again. Choose 'Quit' to save login info.")
//		.setIcon(android.R.drawable.ic_dialog_alert)
//		.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				saveInfoActivity.execute(player);
//				android.os.Process.killProcess(android.os.Process.myPid());
//				
//			}
//		})
//		.setNegativeButton(android.R.string.no, null).show();
//		
//	}
	
	
	

}
