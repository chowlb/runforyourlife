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
import android.os.AsyncTask;

public class AddUserActivity extends AsyncTask<String, Void, String>{
   private Context context;
   private RegisterActivity activity;
   String endResult = null;
   //flag 0 means get and 1 means post.(By default it is get.)
   public AddUserActivity(Context context, RegisterActivity ra) {
      this.context = context;
      this.activity = ra;

    }

   public String getEndResult() {
	   return this.endResult;
   }
   
    protected void onPreExecute(){
    	
    }
	
	
	@Override
	protected String doInBackground(String... arg0) {
		
		String username = (String) arg0[0];
		String password = (String) arg0[1];
		String email = (String) arg0[2];

		
		String link="http://www.chowlb.com/runforyourlife/adduser_app.php";
		
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
	    parameters.add(new BasicNameValuePair("username", username));
	    parameters.add(new BasicNameValuePair("password", Utils.encryptPassword(password)));
	    parameters.add(new BasicNameValuePair("email", email));
		
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
		 this.activity.processRegistration(result);
	 }

}

