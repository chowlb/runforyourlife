package com.chowlb.runforyourlife;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class SigninActivity extends AsyncTask<String, Void, String>{
   private Context context; 
   private String endResult;
   private LoginActivity activity;
   //flag 0 means get and 1 means post.(By default it is get.)
   public SigninActivity(Context context, LoginActivity la) {
      this.context = context;
      this.activity = la;

    }

   public String getEndResult() {
	   return endResult;
   }
   
    protected void onPreExecute(){
    	
    }
	
	
	@Override
	protected String doInBackground(String... arg0) {
		
		String username = (String) arg0[0];
		String password = (String) arg0[1];
		

		
		String link="http://www.chowlb.com/runforyourlife/login_app.php";
		
		Log.e("chowlb", "Background username: " + username);
		
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
	    parameters.add(new BasicNameValuePair("username", username));
	    parameters.add(new BasicNameValuePair("password", Utils.encryptPassword(password)));
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(link);
		
		InputStream inputStream = null;
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			
			inputStream = entity.getContent();
			reader = new BufferedReader((new InputStreamReader(inputStream)));
			
			String line;
			while((line = reader.readLine())!=null) {
				sb.append(line);
			}
				
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(reader != null) {
					reader.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return sb.toString();		
		
	}

	 @Override
	   protected void onPostExecute(String result){
		 this.activity.processLogin(result);
		 
	 }
}