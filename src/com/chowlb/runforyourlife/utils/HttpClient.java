package com.chowlb.runforyourlife.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class HttpClient {
    static InputStream is = null;
    static JSONObject jObj = null;
    static JSONArray jsonArray = null;
    static String json = "";
	
	public JSONArray postJsonData(String jsonData, String URL){
		try{
			org.apache.http.client.HttpClient client = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(URL);
			
			List<NameValuePair> nvList = new ArrayList<NameValuePair>();
			BasicNameValuePair bnvp = new BasicNameValuePair("json", jsonData);
			
			nvList.add(bnvp);
			httppost.setEntity(new UrlEncodedFormEntity(nvList));
						
			HttpResponse response = client.execute(httppost);
			HttpEntity httpEntity = response.getEntity();
            is = httpEntity.getContent();
			if(response != null) {
				try {
		            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
		            StringBuilder sb = new StringBuilder();
		            String line = null;
		            while ((line = reader.readLine()) != null) {
		                sb.append(line);
		            }
		            is.close();
		            json = sb.toString();
		            
		        } catch (Exception e) {
		            Log.e("Buffer Error", "Error converting result " + e.toString());
		        }      

		        // try parse the string to a JSON object
		        try {
		            if(!json.equals("null")){
		                jsonArray = new JSONArray(json);
		                 Log.d("jsonArray:: ",  jsonArray+"");
		            }else{
		                jsonArray = null;
		            }

		        } catch (JSONException e) {
		            Log.e("JSON Parser", "Error parsing data " + e.toString());
		        }


		        // return JSON String
		        return jsonArray;
			}
		} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(Throwable t) {
			t.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
}
