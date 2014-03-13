package com.chowlb.runforyourlife;

import com.chowlb.runforyourlife.adapters.ItemListAdapter;
import com.chowlb.runforyourlife.async.SavePlayerInfoAsync;
import com.chowlb.runforyourlife.listeners.InventoryItemListListener;
import com.chowlb.runforyourlife.objects.Player;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

public class ShowPlayerInventoryActivity extends Activity {
	private Player player;
	private ItemListAdapter adapter;
	private ListView inventoryLayout; 
	//private Activity local;
	SavePlayerInfoAsync saveInfoActivity = new SavePlayerInfoAsync();
	
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
			//Log.e("chowlb", "Player name passed: " + player.getPlayerName());
			ActionBar ab = getActionBar();
			ab.setIcon(R.drawable.ic_action_ic_action_military_backpack_radio);
			ab.setTitle("");
			ab.setTitle(player.getPlayerName() + " - Inventory" );
			
			
			inventoryLayout = (ListView) findViewById(R.id.inventoryListView);
			adapter = new ItemListAdapter(this, player.getInventory());
			inventoryLayout.setAdapter(adapter);
			inventoryLayout.setOnItemClickListener(new InventoryItemListListener(player.getInventory(),this, adapter, player ));
			inventoryLayout.setOnItemLongClickListener(new InventoryItemListListener(player.getInventory(), this, adapter, player));
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
