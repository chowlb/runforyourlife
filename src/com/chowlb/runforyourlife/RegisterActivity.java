package com.chowlb.runforyourlife;

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

public class RegisterActivity extends LoginBaseActivity implements AsyncInterface {
	EditText username;
	EditText email;
	EditText emailVerify;
	EditText password;
	EditText passwordVerify;
	TextView passwordFail;
	TextView emailFail;
	TextView passwordStrength;
	TextView register;
	Typeface typeFace;
	String em; 
	String pw; 
	String emv;
	String pwv;
	AddUserActivity aua = new AddUserActivity();
	
	
	public void startRegisterActivity() {
		setContentView(R.layout.activity_register);
		aua.delegate = this;
		loadInvAct.delegate = this;
		
		register = (TextView) findViewById(R.id.registerTitle);
		emailFail = (TextView) findViewById(R.id.emailVerifyFailTV);
		passwordFail = (TextView) findViewById(R.id.passwordVerifyFailTV);
		passwordStrength = (TextView) findViewById(R.id.passwordStrength);
		Button regButton = (Button) findViewById(R.id.registerButtonRegister);
		
		typeFace = Typeface.createFromAsset(getAssets(), "fonts/28daysfont.ttf");
		
		register.setTypeface(typeFace);
		passwordStrength.setTypeface(typeFace);
		passwordFail.setTypeface(typeFace);
		emailFail.setTypeface(typeFace);
		regButton.setTypeface(typeFace);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		final ConnectivityManager conMgr =  (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {
			startRegisterActivity();
		} 
		else {
			Toast.makeText(this, "No Network Access Found" , Toast.LENGTH_LONG).show();
			startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
		} 
	}
	
	public void register(View v) {
		username = (EditText) findViewById(R.id.usernameRegisterET);
		email = (EditText) findViewById(R.id.emailRegisterET);
		emailVerify = (EditText) findViewById(R.id.emailRegisterVerifyET);
		password = (EditText) findViewById(R.id.passwordRegisterET);
		passwordVerify = (EditText) findViewById(R.id.passwordRegisterVerifyET);
		em = email.getText().toString();
		pw = password.getText().toString();
		emv = emailVerify.getText().toString();
		pwv = passwordVerify.getText().toString();
		
		emailFail.setVisibility(View.GONE);
		passwordFail.setVisibility(View.GONE);
		passwordStrength.setVisibility(View.GONE);
		
		if(testUserInput()) {
			aua.execute(username.getText().toString(), pw, em);				
		}		
	}
	
	public void processLogin(Player p) {
		if(p != null) {
			SharedPreferences prefs = this.getSharedPreferences("com.chowlb.runforyourlife", Context.MODE_PRIVATE);
	    	SharedPreferences.Editor editor = prefs.edit();
		 
	    	editor.putInt("USER_ID", p.getPlayerID());
		 
	    	editor.commit();
	    	player = p;
			 
	    	Log.e("chowlb", "Player name on login: " + player.getPlayerName());
			
	    	loadInvAct.execute(player);
		}else {
	    		Toast.makeText(this, "Something happened with registration, the username could be taken or the email address is already registered", Toast.LENGTH_LONG).show();
				
		}
			 
	   
	}

	public boolean testUserInput() {
		boolean passed = false;
		
		if(!Utils.isEmpty(username.getText().toString())) {
			if(!Utils.isEmpty(em)) {
				if(em.contains("@")) {
					if(em.equals(emv)) {
						if(pw.equals(pwv)) {
							if(Utils.getPasswordStrength(pw) == 0) {
								passwordStrength.setVisibility(View.VISIBLE);
							}else {
								//GOOD TO GO!! ADD THAT USER YEAH!\
								passed = true;
							}
						}else {
							passwordFail.setVisibility(View.VISIBLE);
							
						}
					}else {
						emailFail.setVisibility(View.VISIBLE);
					}
				}else {
					//doesn't look like a valid email
					Toast.makeText(this,"Verify valid Email address.", Toast.LENGTH_LONG).show();
				}
			}else {
				//email empty
				Toast.makeText(this,"Email cannot be blank.", Toast.LENGTH_LONG).show();
			}
		}else {
			//username blank
			Toast.makeText(this,"Username cannot be blank.", Toast.LENGTH_LONG).show();
		}
		
		return passed;
	}
	
	
}
