package com.chowlb.runforyourlife;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	EditText username;
	EditText email;
	EditText emailVerify;
	EditText password;
	EditText passwordVerify;
	TextView passwordFail;
	TextView emailFail;
	TextView passwordStrength;
	Typeface typeFace;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		TextView register = (TextView) findViewById(R.id.registerTitle);
		emailFail = (TextView) findViewById(R.id.emailVerifyFailTV);
		passwordFail = (TextView) findViewById(R.id.passwordVerifyFailTV);
		passwordStrength = (TextView) findViewById(R.id.passwordStrength);
		typeFace = Typeface.createFromAsset(getAssets(), "fonts/28daysfont.ttf");
		register.setTypeface(typeFace);
		passwordStrength.setTypeface(typeFace);
		passwordFail.setTypeface(typeFace);
		emailFail.setTypeface(typeFace);
		Button regButton = (Button) findViewById(R.id.registerButtonRegister);
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
		
	}
	
	public void processRegistration(String endResult) {
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
	    }else if(Integer.parseInt(endResult) == -3) {
	    	Toast.makeText(this, "This Username is taken.", Toast.LENGTH_LONG).show();
    	}else if(Integer.parseInt(endResult) == -4) {
    		Toast.makeText(this, "This Email has been registered previously.", Toast.LENGTH_LONG).show();
    	}
    	else {
    		Toast.makeText(this, "Something happened with registration, check your data connection", Toast.LENGTH_LONG).show();
    	}
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.register, menu);
//		return true;
//	}

}
