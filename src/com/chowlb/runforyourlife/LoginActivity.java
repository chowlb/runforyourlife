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

public class LoginActivity extends Activity implements AsyncInterface{

	private EditText username;
	private EditText password;
	private TextView loginFailed;
	private TextView logo;
	int userID;
	private Player player;
	LoadInventoryActivity loadInvAct = new LoadInventoryActivity();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final ConnectivityManager conMgr =  (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {
			
			loadInvAct.delegate = this;
			
			SharedPreferences prefs = this.getSharedPreferences("com.chowlb.runforyourlife", Context.MODE_PRIVATE);
			userID = prefs.getInt("USER_ID", -1);
			Log.e("chowlb", "Checking preferences got id: " + userID);
			if(userID >= 0) {
				SigninActivity sa = new SigninActivity(this, this);
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
		else {
			Toast.makeText(this, "No Network Access Found" , Toast.LENGTH_LONG).show();
			startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
			
		} 
		
	}
	
	public void login(View v) {
		loginFailed.setVisibility(View.GONE);
		String user = username.getText().toString();
	    String pass = password.getText().toString();
	    
	    if(!Utils.isEmpty(user) && !Utils.isEmpty(pass)) {
	    	SigninActivity sa = new SigninActivity(this, this);
	    	sa.execute("1", user, pass);
	    }
	    else {
	    	Toast.makeText(this,"Username and Password cannot be blank.", Toast.LENGTH_LONG).show();
	    }
	}
	
	
	public void processLogin(String endResult) {
		//Log.e("chowlb", "EndResult: " + endResult);
		String[] separated = endResult.split(";");
	    if(endResult != null && separated.length > 2) {
    		 
    		SharedPreferences prefs = this.getSharedPreferences(
				      "com.chowlb.runforyourlife", Context.MODE_PRIVATE);
			 SharedPreferences.Editor editor = prefs.edit();
			 editor.putInt("USER_ID", Integer.parseInt(separated[0]));
			 editor.commit();
			 player = new Player(Integer.parseInt(separated[0]), separated[1].toString(),
					 Integer.parseInt(separated[4]), Integer.parseInt(separated[3]), separated[2].toString());
			 
			 
			 loadInvAct.execute("1", player.getPlayerName());
			 
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
	
	public void handleInventory(String invResult) {
		String[] separated = invResult.split("<br>");
		//Log.e("chowlb", "Inventory list size: " + separated.length);
	    if(invResult != null && separated.length > 0) {
	    	
	    	for(int i = 0; i < separated.length; i++) {
	    		String[] items = separated[i].split(";");
	    		if(items.length>0) {
	    			Item item = new Item();
	    			item.setItemId(Integer.parseInt(items[0].toString()));
	    			item.setName(items[1]);
	    			item.setDescription(items[2]);
	    			item.setItemType(items[3]);
	    			item.setStatus(items[4]);
	    			item.setAttribute(Integer.parseInt(items[5].toString()));	
	    			item.setItemDBID(Integer.parseInt(items[6].toString()));
	    			item.setOwner(player.getPlayerName());
	    			if(!player.addItem(item)) {
	    				Toast.makeText(this, "Inventory is full!", Toast.LENGTH_LONG).show();
	    			}
	    			//Log.e("chowlb", "Inv size after add: " + player.getInventory().size());
	    		}
	    		
	    	}
	    	
	    }else {
	    	Toast.makeText(this,  "There was an error getting inventory. Check your network settings.", Toast.LENGTH_LONG).show();
	    }
	    
	    startGameMap();
	    
	}
	
	public void startGameMap() {
		Intent intent = new Intent(LoginActivity.this, GameMapActivity.class);
		Bundle bundle = new Bundle();
		//Log.e("chowlb", "Before parcel inv size: " + player.getInventory().size());
		bundle.putParcelable("PLAYER", player);
		intent.putExtras(bundle);
		this.startActivity(intent);
		this.finish();
	}


	//@Override
	//public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	//	getMenuInflater().inflate(R.menu.main, menu);
	//	return true;
	//}

	
	
}
