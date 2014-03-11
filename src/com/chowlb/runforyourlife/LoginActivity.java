package com.chowlb.runforyourlife;

import com.chowlb.runforyourlife.async.SigninAsync;
import com.chowlb.runforyourlife.interfaces.AsyncInterface;
import com.chowlb.runforyourlife.objects.Player;
import com.chowlb.runforyourlife.utils.Utils;

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

public class LoginActivity extends LoginBaseActivity implements AsyncInterface{

	private EditText username;
	private EditText password;
	private TextView loginFailed;
	private TextView logo;
	int userID;
	
	public void startLoginActivity() {
		
		SharedPreferences prefs = this.getSharedPreferences("com.chowlb.runforyourlife", Context.MODE_PRIVATE);
		userID = prefs.getInt("USER_ID", -1);
		Log.e("chowlb", "Checking preferences got id: " + userID);
		if(userID >= 0) {
			Log.e("chowlb", "USER ID IS > 0");
			SigninAsync sa = new SigninAsync();
			sa.delegate = this;
			sa.execute("2", String.valueOf(userID));
		}else {
		
			setContentView(R.layout.activity_login);
			logo = (TextView) findViewById(R.id.logoTV);
			username = (EditText) findViewById(R.id.usernameET);
			password = (EditText) findViewById(R.id.passwordET);
			loginFailed = (TextView) findViewById(R.id.loginFailed);
			Button loginButton = (Button) findViewById(R.id.loginButton);
			Button registerButton = (Button) findViewById(R.id.registerButton);
			
			Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/28daysfont.ttf");
			
			logo.setTypeface(typeFace);
			loginFailed.setTypeface(typeFace);
			loginButton.setTypeface(typeFace);
			registerButton.setTypeface(typeFace);
			
			loginFailed.setVisibility(View.GONE);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		final ConnectivityManager conMgr =  (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {
			startLoginActivity();
		} 
		else {
			Toast.makeText(this, "No Network Access Found" , Toast.LENGTH_LONG).show();
			startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
			
		} 
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public void login(View v) {
		loginFailed.setVisibility(View.GONE);
		String user = username.getText().toString();
	    String pass = password.getText().toString();
	    
	    if(!Utils.isEmpty(user) && !Utils.isEmpty(pass)) {
	    	SigninAsync sa = new SigninAsync();
			sa.delegate = this;
	    	sa.execute("1", user, pass);
	    }
	    else {
	    	Toast.makeText(this,"Username and Password cannot be blank.", Toast.LENGTH_LONG).show();
	    }
	}
	
	@Override
	public void processLogin(Player p) {
		
		if(p != null) {
			SharedPreferences prefs = this.getSharedPreferences(
				      "com.chowlb.runforyourlife", Context.MODE_PRIVATE);
			 SharedPreferences.Editor editor = prefs.edit();
		 	 editor.putInt("USER_ID", p.getPlayerID());
		 	 editor.commit();
		
		 	 player = p;
			 
			 Log.e("chowlb", "Player name on login: " + player.getPlayerName());
			 			 
			 startGameMap();
		}else{
			
    		setContentView(R.layout.activity_login);
			logo = (TextView) findViewById(R.id.logoTV);
			username = (EditText) findViewById(R.id.usernameET);
			password = (EditText) findViewById(R.id.passwordET);
			loginFailed = (TextView) findViewById(R.id.loginFailed);
			Button loginButton = (Button) findViewById(R.id.loginButton);
			Button registerButton = (Button) findViewById(R.id.registerButton);
			
			Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/28daysfont.ttf");
			
			logo.setTypeface(typeFace);
			loginFailed.setTypeface(typeFace);
			loginButton.setTypeface(typeFace);
			registerButton.setTypeface(typeFace);
    		loginFailed.setVisibility(View.VISIBLE);
		}
	}
	
	public void register(View v) {
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}

	




	
}
