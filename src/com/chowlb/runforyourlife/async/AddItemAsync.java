package com.chowlb.runforyourlife.async;

import org.json.JSONException;
import org.json.JSONObject;

import com.chowlb.runforyourlife.objects.Cache;
import com.chowlb.runforyourlife.objects.Item;
import com.chowlb.runforyourlife.objects.Player;
import com.chowlb.runforyourlife.utils.HttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class AddItemAsync extends AsyncTask<Object, Void, String>{
	private static String URL = "http://www.chowlb.com/runforyourlife/insertuseritem_app.php";
	private Player player;
	private Cache cache;
	private int addType = 1;
	
	public AddItemAsync() { }
	
	@Override
	protected String doInBackground(Object... arg0) {
		Item item = (Item) arg0[0];
		HttpClient client = new HttpClient();
		addType = (Integer) arg0[2];
		JSONObject jsonObjSend = new JSONObject();
		Log.e("chowlb", "Add TYPE: " + addType);
		try {
			jsonObjSend.put("ITEM_ID", item.getItemId());
			jsonObjSend.put("STATUS",  item.getStatus());
		}catch(JSONException e) {
			e.printStackTrace();
		}
		
		try {
			//CHECK ADD TYPE:
			//  1 - CACHE 
			//  2 - PLAYER
			if(addType == 1) {
				Log.e("chowlb", "ADD TYPE: CACHE with ItemID: " + item.getItemId());
				cache = (Cache) arg0[1];
				URL = "http://www.chowlb.com/runforyourlife/insertcacheitem_app.php";
				try {
					jsonObjSend.put("ID", cache.getCacheID());	
				}catch(JSONException e) {
					e.printStackTrace();
				}
				Log.e("chowlb", "Sending CACHE Json: " + jsonObjSend.toString());
				client.postJsonData(jsonObjSend.toString(), URL);
			}
			else {
				player = (Player) arg0[1];
				try {
					jsonObjSend.put("ID", player.getPlayerID());			
				}catch(JSONException e) {
					e.printStackTrace();
				}
				client.postJsonData(jsonObjSend.toString(), URL);
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
	}

}

