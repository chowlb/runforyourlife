package com.chowlb.runforyourlife;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity{

	private EditText username;
	private EditText password;
	private TextView loginFailed;
	int userID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		TextView logo = (TextView) findViewById(R.id.logoTV);
		username = (EditText) findViewById(R.id.usernameET);
		password = (EditText) findViewById(R.id.passwordET);
		loginFailed = (TextView) findViewById(R.id.loginFailed);
		Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/28daysfont.ttf");
		logo.setTypeface(typeFace);
		loginFailed.setTypeface(typeFace);
		Button loginButton = (Button) findViewById(R.id.loginButton);
		loginButton.setTypeface(typeFace);
		Button registerButton = (Button) findViewById(R.id.registerButton);
		registerButton.setTypeface(typeFace);
		
		final ConnectivityManager conMgr =  (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {
		} else {
			Toast.makeText(this, "No Network Access Found" , Toast.LENGTH_LONG).show();
			startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
		} 
		
		SharedPreferences prefs = this.getSharedPreferences(
			      "com.chowlb.runforyourlife", Context.MODE_PRIVATE);
		userID = prefs.getInt("USER_ID", -1);
		Log.e("chowlb", "Checking preferences got id: " + userID);
		if(userID >= 0) {
			//Intent intent = new Intent(this, MapActivity.class);
			//String message = String.valueOf(userID);
			//intent.putExtra("USER_ID", message);
			//startActivity(intent);
		}
		
		
	}
	
	public void login(View v) {
		loginFailed.setVisibility(View.GONE);
		String user = username.getText().toString();
	    String pass = password.getText().toString();
	    
	    if(!Utils.isEmpty(user) && !Utils.isEmpty(pass)) {
	    	SigninActivity sa = new SigninActivity(this, this);
	    	sa.execute(user, pass);
	    }
	    else {
	    	Toast.makeText(this,"Username and Password cannot be blank.", Toast.LENGTH_LONG).show();
	    }
	}
	
	
	public void processLogin(String endResult) {
		Log.e("chowlb", "EndResult: " + endResult);
	    if(endResult != null && Integer.parseInt(endResult) >= 0) {
    		Log.e("chowlb", "End result is good! lets login!");
			 SharedPreferences prefs = this.getSharedPreferences(
				      "com.chowlb.runforyourlife", Context.MODE_PRIVATE);
			 prefs.edit().putInt("USER_ID", Integer.parseInt(endResult));
			 Intent intent = new Intent(this, MapActivity.class);
			 intent.putExtra("USER_ID", endResult);
			 intent.putExtra("USER_NAME",  username.getText().toString());
			 this.startActivity(intent);
			 this.finish();
		}
    	else {
    		loginFailed.setVisibility(View.VISIBLE);
    	}
	}
	
	
	
	public void register(View v) {
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}
	
	@Override
	  protected void onPause() {
	    super.onPause();
	    
	  }
	
	
	

	//@Override
	//public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	//	getMenuInflater().inflate(R.menu.main, menu);
	//	return true;
	//}

	
	
}
