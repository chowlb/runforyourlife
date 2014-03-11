package com.chowlb.runforyourlife;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
 
public class SplashScreenActivity extends Activity {
 
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1000;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        TextView logo = (TextView) findViewById(R.id.chowlbLogoTV);
		
		Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/pixelfont.ttf");
		logo.setTypeface(typeFace);
		
        
        new Handler().postDelayed(new Runnable() {
        	
        	@Override
        	public void run() {
        		Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
        		startActivity(i);
        		finish();
        	}
        	
        }, SPLASH_TIME_OUT);
    }
    
}
