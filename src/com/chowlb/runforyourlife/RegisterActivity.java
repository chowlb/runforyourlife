package com.chowlb.runforyourlife;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements AsyncInterface {
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
	private Player player;
	LoadInventoryActivity loadInvAct = new LoadInventoryActivity();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
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
	
	public void register(View v) {
		username = (EditText) findViewById(R.id.usernameRegisterET);
		email = (EditText) findViewById(R.id.emailRegisterET);
		emailVerify = (EditText) findViewById(R.id.emailRegisterVerifyET);
		password = (EditText) findViewById(R.id.passwordRegisterET);
		passwordVerify = (EditText) findViewById(R.id.passwordRegisterVerifyET);
		String em = email.getText().toString();
		String pw = password.getText().toString();
		String emv = emailVerify.getText().toString();
		String pwv = passwordVerify.getText().toString();
		
		emailFail.setVisibility(View.GONE);
		passwordFail.setVisibility(View.GONE);
		passwordStrength.setVisibility(View.GONE);
		if(!Utils.isEmpty(username.getText().toString())) {
			if(!Utils.isEmpty(em)) {
				if(em.contains("@")) {
					if(em.equals(emv)) {
						if(pw.equals(pwv)) {
							if(Utils.getPasswordStrength(pw) == 0) {
								passwordStrength.setVisibility(View.VISIBLE);
							}else {
								//GOOD TO GO!! ADD THAT USER YEAH!\
								AddUserActivity aua = new AddUserActivity(this, this);
								aua.execute(username.getText().toString(), pw, em);	
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
		
	}
	
	public void processRegistration(String endResult) {
		//Log.e("chowlb", "EndResult: " + endResult);
		String[] separated = endResult.split(";");
	    if(endResult != null && separated.length > 2) {
			 
    		SharedPreferences prefs = this.getSharedPreferences(
				      "com.chowlb.runforyourlife", Context.MODE_PRIVATE);
			 SharedPreferences.Editor editor = prefs.edit();
			 editor.putInt("USER_ID", Integer.parseInt(separated[0]));
			 editor.commit();
			 
			 Intent intent = new Intent(RegisterActivity.this, GameMapActivity.class);
			 Bundle bundle = new Bundle();
			 player = new Player(Integer.parseInt(separated[0]), separated[1].toString(),
					 Integer.parseInt(separated[4]), Integer.parseInt(separated[3]), separated[2].toString());
			 
			 loadInvAct.execute("1", player.getPlayerName());
			 
			 bundle.putParcelable("PLAYER", player);
			 intent.putExtras(bundle);
			 
			 
			 this.startActivity(intent);
			 this.finish();
			 
	    }else if(Integer.parseInt(endResult) == -3) {
	    	Toast.makeText(this, "This Username is taken.", Toast.LENGTH_LONG).show();
    	}else if(Integer.parseInt(endResult) == -4) {
    		Toast.makeText(this, "This Email has been registered previously.", Toast.LENGTH_LONG).show();
    	}
    	else {
    		Toast.makeText(this, "Something happened with registration, check your data connection", Toast.LENGTH_LONG).show();
    	}
	}
	
	public void handleInventory(String invResult) {
		String[] separated = invResult.split("<br>");
		Log.e("chowlb", "Inventory list size: " + separated.length);
	    if(invResult != null && separated.length > 0) {
	    	Item item = new Item();
	    	for(int i = 0; i < separated.length; i++) {
	    		String[] items = separated[i].split(";");
	    		if(items.length>0) {
	    			item.setItemId(Integer.parseInt(items[0].toString()));
	    			item.setName(items[1]);
	    			item.setDescription(items[2]);
	    			item.setItemType(items[3]);
	    			item.setStatus(items[4]);
	    			item.setAttribute(Integer.parseInt(items[5].toString()));
	    			 			
	    			
	    			if(!player.addItem(item)) {
	    				Toast.makeText(this, "Inventory is full!", Toast.LENGTH_LONG).show();
	    			}
	    		}
	    	}
	    }else {
	    	Toast.makeText(this,  "There was an error getting inventory. Check your network settings.", Toast.LENGTH_LONG).show();
	    }
	    
	    
	    
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.register, menu);
//		return true;
//	}

}
