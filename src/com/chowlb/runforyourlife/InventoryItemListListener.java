package com.chowlb.runforyourlife;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;



public class InventoryItemListListener implements OnItemClickListener{
	List<Item> listItems;
	
	Activity activity;
	
	public InventoryItemListListener(List<Item> aListItems, Activity anActivity) {
		listItems = aListItems;
		activity = anActivity;
	}
	
	public void onItemClick(AdapterView parent, View view, int pos, long id) {
		
		Intent i = new Intent(Intent.ACTION_VIEW);
		Item item = (Item) parent.getAdapter().getItem(pos);
		ItemListAdapter la = (ItemListAdapter) parent.getAdapter();
		//la.addHighlight(etGuid());
		
		//i.setData(Uri.parse(rss.getLink()));
		//activity.startActivity(i);
		//la.notifyDataSetChanged();
	}
}
	
	

