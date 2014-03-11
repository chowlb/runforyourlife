package com.chowlb.runforyourlife.async;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.chowlb.runforyourlife.interfaces.AsyncInterface;
import com.chowlb.runforyourlife.objects.Player;
import com.chowlb.runforyourlife.utils.HttpClient;
import com.chowlb.runforyourlife.utils.Utils;

import android.os.AsyncTask;
import android.util.Log;

public class SigninAsync extends AsyncTask<String, Void, Player>{

   public AsyncInterface delegate = null;
   private Player player;
   
   //flag 0 means get and 1 means post.(By default it is get.)
   public SigninAsync() {  }

  
    protected void onPreExecute(){
    	
    }
	
	//SIGN IN EXECUTE, FIRST ARGUMENT IS TYPE OF LOGIN
    //0 is new
    //1 is regular login
    //2 is cached login
	@Override
	protected Player doInBackground(String... arg0) {
		
		int loginType = Integer.parseInt(arg0[0]);
		String username = (String) arg0[1];
		String link="";
		JSONObject jsonObjSend = new JSONObject();
		if(loginType==1) {
			String password = (String) arg0[2];
			
				try {
					jsonObjSend.put("USERNAME", username);
					jsonObjSend.put("PASSWORD",  Utils.encryptPassword(password));				
				}catch(JSONException e) {
					e.printStackTrace();
				}
				
			link = "http://www.chowlb.com/runforyourlife/login_app.php";	
		}else {
			link = "http://www.chowlb.com/runforyourlife/updatelogin_app.php";
			try {
				jsonObjSend.put("USERID",  username);		
			}catch(JSONException e) {
				e.printStackTrace();
			}
		}
		try {
			HttpClient client = new HttpClient();
			Log.e("chowlb", "Sending json: " + jsonObjSend);
			JSONArray jsonResponse = client.postJsonData(jsonObjSend.toString(), link);
			if(jsonResponse.length() > 0) {
				JSONObject jsonObj  = jsonResponse.getJSONObject(0);
				player = new Player(jsonObj.getInt("USER_ID"), jsonObj.getString("USER_NAME"), jsonObj.getInt("HEALTH"),
						 jsonObj.getInt("DAYS_SURVIVED"), jsonObj.getString("LAST_LOGIN"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}		
		
		return player;
		
	}

	 @Override
	   protected void onPostExecute(Player result){
		 delegate.processLogin(result);
		 
	 }
}
