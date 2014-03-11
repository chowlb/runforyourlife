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
	private int deleteType = 1;
	
	public AddItemAsync() { }
	
	@Override
	protected String doInBackground(Object... arg0) {
		Item item = (Item) arg0[0];
		HttpClient client = new HttpClient();
		deleteType = (Integer) arg0[2];
		JSONObject jsonObjSend = new JSONObject();
		try {

			Log.e("chowlb", "Putting item status and id");
			jsonObjSend.put("ITEM_ID", item.getItemId());
			jsonObjSend.put("STATUS",  item.getStatus());
			Log.e("chowlb", "END Putting item status and id");
		}catch(JSONException e) {
			e.printStackTrace();
		}
		
		try {
			//CHECK ADD TYPE:
			//  1 - CACHE 
			//  2 - PLAYER
			if(deleteType == 1) {
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
					Log.e("chowlb", "Putting player id");
					jsonObjSend.put("ID", player.getPlayerID());

					Log.e("chowlb", "END Putting player id");
						
				}catch(JSONException e) {
					e.printStackTrace();
				}
				Log.e("chowlb", "Sending PLAYER Json: " + jsonObjSend.toString());
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

