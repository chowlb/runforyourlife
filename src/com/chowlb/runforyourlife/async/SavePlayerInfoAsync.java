package com.chowlb.runforyourlife.async;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.chowlb.runforyourlife.interfaces.AsyncInterface;
import com.chowlb.runforyourlife.objects.Player;
import com.chowlb.runforyourlife.utils.HttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class SavePlayerInfoAsync extends AsyncTask<Object, Void, String>{
	public AsyncInterface delegate = null;
	private static final String URL = "http://www.chowlb.com/runforyourlife/saveuserinfo_app.php";
	
	public SavePlayerInfoAsync() { }
	
	@Override
	protected String doInBackground(Object... arg0) {
		String result = null;
		Player player = (Player) arg0[0];		
		JSONObject jsonObjSend = new JSONObject();
		try {
			
			jsonObjSend.put("USER_ID", player.getPlayerID());
			jsonObjSend.put("USERNAME", player.getPlayerName());
			jsonObjSend.put("HEALTH", player.getHealth());
			jsonObjSend.put("DAYS_SURVIVED", player.getDaysSurvived());
			
			JSONArray invArray = new JSONArray();
			
			for(int i=0; i<player.getInventory().size(); i++) {
				JSONObject item = new JSONObject();
				item.put("ITEM_ID", player.getInventory().get(i).getItemId());
				item.put("ITEM_STATUS", 1);
				item.put("ITEM_DB_ID", player.getInventory().get(i).getItemDBID());
				invArray.put(item);
			}
			jsonObjSend.put("ITEMS", invArray);
			Log.e("chowlb", "SAVING JSON: " + jsonObjSend.toString());
			
		}catch(JSONException e) {
			e.printStackTrace();
		}
		
		try {
			HttpClient client = new HttpClient();
			client.postJsonData(jsonObjSend.toString(), URL);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	protected void onPostExecute(String result) {
		
	}

}
