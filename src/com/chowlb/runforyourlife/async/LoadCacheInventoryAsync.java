package com.chowlb.runforyourlife.async;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.chowlb.runforyourlife.interfaces.AsyncMarkerInterface;
import com.chowlb.runforyourlife.objects.Item;
import com.chowlb.runforyourlife.utils.HttpClient;

public class LoadCacheInventoryAsync extends AsyncTask<String, Void, List<Item>>{

    public AsyncMarkerInterface delegate = null;
    List<Item> items= new ArrayList<Item>();
    
    public LoadCacheInventoryAsync() {
      
   }
   
    @Override
    protected void onPreExecute() {
       super.onPreExecute();
       
   }
    
    
	@Override
	protected List<Item> doInBackground(String... arg0) {
		//Log.e("chowlb", "LOADING CACHE INVENTORY");
		String cacheid = (String) arg0[0];
		String link="http://www.chowlb.com/runforyourlife/getcacheinventory_app.php";
		JSONObject jsonObjSend = new JSONObject();
		
		try {
			jsonObjSend.put("CACHEID", cacheid);			
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
					item = new Item(jsonObj.getInt("CACHE_ITEM_ID"), jsonObj.getInt("CACHEINV_DB_ID"), jsonObj.getString("ITEM_NAME"),
							 jsonObj.getString("ITEM_DESCRIPTION"), jsonObj.getString("ITEM_TYPE"),
							 jsonObj.getString("STATUS"), jsonObj.getInt("ITEM_ATTRIBUTE"), jsonObj.getString("ITEM_RARITY"));
					items.add(item);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}		
		//Log.e("chowlb", "Returning cache items: " + items.size());
		return items;	
		
	}

	 @Override
	  protected void onPostExecute(List<Item> result){
		 super.onPostExecute(result);
		 delegate.handleInventory(result);
	 }

}


