package com.chowlb.runforyourlife;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class LoadInventoryActivity extends AsyncTask<Player, Void, List<Item>>{

    public AsyncInterface delegate = null;
    List<Item> items= new ArrayList<Item>();
    
    public LoadInventoryActivity() {
      
   }
   
    @Override
    protected void onPreExecute() {
       super.onPreExecute();
       
   }
    
    
	@Override
	protected List<Item> doInBackground(Player... arg0) {
		
		Player player = (Player) arg0[0];
		String link="http://www.chowlb.com/runforyourlife/getinventory_app.php";
		JSONObject jsonObjSend = new JSONObject();
		
		try {
			jsonObjSend.put("USERID", player.getPlayerID());			
		}catch(JSONException e) {
			e.printStackTrace();
		}
		
		try {
			HttpClient client = new HttpClient();
			Log.e("chowlb", "Sending json: " + jsonObjSend);
			JSONArray jsonResponse = client.postJsonData(jsonObjSend.toString(), link);
			if(jsonResponse.length() > 0) {
				for(int i=0; i<jsonResponse.length(); i++) {
					JSONObject jsonObj  = jsonResponse.getJSONObject(i);
					Item item = new Item();
					item = new Item(jsonObj.getInt("PLAYER_ITEM_ID"), jsonObj.getInt("PLAYERINV_DB_ID"), jsonObj.getString("ITEM_NAME"),
							 jsonObj.getString("ITEM_DESCRIPTION"), jsonObj.getString("ITEM_TYPE"),
							 jsonObj.getString("STATUS"), jsonObj.getInt("ITEM_ATTRIBUTE"), player.getPlayerName());
					items.add(item);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}		
		Log.e("chowlb", "Returning items: " + items.size());
		return items;	
		
	}

	 @Override
	  protected void onPostExecute(List<Item> result){
		 super.onPostExecute(result);
		 Log.e("chowlb", "Calling on postexcute with result size: " + result.size());
		 delegate.handleInventory(result);
	 }

}

