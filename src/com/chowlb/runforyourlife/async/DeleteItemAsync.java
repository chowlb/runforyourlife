package com.chowlb.runforyourlife.async;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.chowlb.runforyourlife.interfaces.AsyncInterface;
import com.chowlb.runforyourlife.objects.Item;
import com.chowlb.runforyourlife.utils.HttpClient;

public class DeleteItemAsync extends AsyncTask<Object, Void, String>{
	public AsyncInterface delegate = null;
	private static String URL = "http://www.chowlb.com/runforyourlife/deleteuseritem_app.php";
	
	public DeleteItemAsync() { }
	
	@Override
	protected String doInBackground(Object... arg0) {
		String result = null;
		Item item = (Item) arg0[0];
		int deleteType = (Integer) arg0[1];
		JSONObject jsonObjSend = new JSONObject();
		try {
			jsonObjSend.put("ITEM_DB_ID", item.getItemDBID());
		}catch(JSONException e) {
			e.printStackTrace();
		}
		
		try {
			HttpClient client = new HttpClient();
			//CHECK DELETETYPE:
			//  1 - CACHE 
			//  2 - PLAYER
			if(deleteType == 1) {
				URL = "http://www.chowlb.com/runforyourlife/deletecacheitem_app.php";
			}
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
