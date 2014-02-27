package com.chowlb.runforyourlife;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;

public class MapActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		String username = getIntent().getStringExtra("USER_NAME");
		int userid = getIntent().getIntExtra("USER_ID", -1);
		
		setTitle("");
		setTitle(username);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	
	public void doExit() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this)
		.setTitle("Exit")
		.setMessage("Do you wish to Exit the game?")
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		})
		.setNegativeButton(android.R.string.no, null).show();
		
	}
	
	@Override
	public void onBackPressed() {
		doExit();
	}

}
