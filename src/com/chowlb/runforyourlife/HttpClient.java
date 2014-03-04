package com.chowlb.runforyourlife;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class HttpClient {
	
	public String postJsonData(String jsonData, String URL){
		try{
			StringBuffer buffer = new StringBuffer();
			
			org.apache.http.client.HttpClient client = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(URL);
			
			List<NameValuePair> nvList = new ArrayList<NameValuePair>();
			BasicNameValuePair bnvp = new BasicNameValuePair("json", jsonData);
			
			nvList.add(bnvp);
			httppost.setEntity(new UrlEncodedFormEntity(nvList));
						
			HttpResponse response = client.execute(httppost);
			
			if(response != null) {
				InputStream is = response.getEntity().getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				StringBuilder str = new StringBuilder();
			
				String line = null;
				try{
					while((line = reader.readLine()) != null) {
						str.append(line+"\n");
					}
				}catch(IOException e) {
					e.printStackTrace();
				}finally {
					try {
						is.close();
					}catch(IOException e) {
						e.printStackTrace();
					}
				}
				
				buffer.append(str.toString());
				return buffer.toString();
			}
		}catch(Throwable t) {
			t.printStackTrace();
		}
		return null;
	}
	
}
