package com.chowlb.runforyourlife;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class DeleteItem extends AsyncTask<Object, Void, String>{
	public AsyncInterface delegate = null;
	private static final String URL = "http://www.chowlb.com/runforyourlife/deleteuseritem_app.php";
	
	public DeleteItem() { }
	
	@Override
	protected String doInBackground(Object... arg0) {
		String result = null;
		Item item = (Item) arg0[0];
		JSONObject jsonObjSend = new JSONObject();
		try {
			
			
			jsonObjSend.put("USERNAME", item.getOwner());
			jsonObjSend.put("ITEM_DB_ID", item.getItemDBID());
			
			
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
