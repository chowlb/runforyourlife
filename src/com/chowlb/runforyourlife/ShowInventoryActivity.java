package com.chowlb.runforyourlife;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class ShowInventoryActivity extends Activity {
	private Player player;
	
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
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_inventory, menu);
		return true;
	}

}
