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

import android.os.AsyncTask;
import android.util.Log;

public class LoadInventoryActivity extends AsyncTask<String, Void, String>{

    public AsyncInterface delegate = null;
  
    public LoadInventoryActivity() {
      
   }
   
	
	@Override
	protected String doInBackground(String... arg0) {
		
		int interaction = Integer.parseInt((String) arg0[0].toString());
		String username = (String) arg0[1];
		//Log.e("chowlb", "LoadInventoryActivity username: " + username);
		String link="http://www.chowlb.com/runforyourlife/getinventory_app.php";
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
	    
		
		parameters.add(new BasicNameValuePair("username", username));
		
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
				//Log.e("chowlb", "Line: " + line);
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
		 delegate.handleInventory(result);
	 }

}

