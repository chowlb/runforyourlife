package com.chowlb.runforyourlife;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public abstract class LoginBaseActivity extends Activity implements AsyncInterface{

	protected Player player;
	protected LoadInventoryActivity loadInvAct = new LoadInventoryActivity();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	protected void startGameMap() {
		Intent intent = new Intent(this, GameMapActivity.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable("PLAYER", player);
		intent.putExtras(bundle);
		//Log.e("chowlb", "starting activity");
		this.startActivity(intent);
		this.finish();
	}
		
	@Override
	public void handleInventory(List<Item> invResult) {

		player.setInventory(invResult);
		//Log.e("chowlb", "Set inventory: " + invResult.size());
	    startGameMap();
	}

}
