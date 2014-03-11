package com.chowlb.runforyourlife;

import com.chowlb.runforyourlife.interfaces.AsyncInterface;
import com.chowlb.runforyourlife.objects.Player;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public abstract class LoginBaseActivity extends Activity implements AsyncInterface{

	protected Player player;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	protected void startGameMap() {
		Intent intent = new Intent(this, GameMapActivity.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable("PLAYER", player);
		intent.putExtras(bundle);
		this.startActivity(intent);
		this.finish();
	}
		


}
