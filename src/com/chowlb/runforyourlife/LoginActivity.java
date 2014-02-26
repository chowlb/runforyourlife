package com.chowlb.runforyourlife;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity{

	private EditText username;
	private EditText password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		TextView logo = (TextView) findViewById(R.id.logoTV);
		username = (EditText) findViewById(R.id.usernameET);
		password = (EditText) findViewById(R.id.passwordET);
		Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/28daysfont.ttf");
		logo.setTypeface(typeFace);
		Button loginButton = (Button) findViewById(R.id.loginButton);
		loginButton.setTypeface(typeFace);
		Button registerButton = (Button) findViewById(R.id.registerButton);
		registerButton.setTypeface(typeFace);
		
		final ConnectivityManager conMgr =  (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {
		    //notify user you are online
		} else {
			Toast.makeText(this, "No Network Access Found" , Toast.LENGTH_LONG).show();
			startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
		} 
		
	    
	}
	
	public void login(View v) {
		Intent intent = new Intent(this, MapActivity.class);
		startActivity(intent);
	}
	
	public void register(View v) {
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}
	
	@Override
	  protected void onPause() {
	    super.onPause();
	    
	  }
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
}
