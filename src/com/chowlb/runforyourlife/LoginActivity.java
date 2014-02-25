package com.chowlb.runforyourlife;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
		username.setTypeface(typeFace);
		password.setTypeface(typeFace);
		Button loginButton = (Button) findViewById(R.id.loginButton);
		loginButton.setTypeface(typeFace);
		
	    
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
