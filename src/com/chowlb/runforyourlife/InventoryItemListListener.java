package com.chowlb.runforyourlife;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;



public class InventoryItemListListener implements OnItemClickListener, OnLongClickListener{
	List<Item> listItems;
	
	Activity activity;
	
	public InventoryItemListListener(List<Item> aListItems, Activity anActivity) {
		listItems = aListItems;
		activity = anActivity;
	}
	
	public void onItemClick(AdapterView parent, View view, int pos, long id) {
		Intent i = new Intent(activity, SingleItemActivity.class);
		Item item = (Item) parent.getAdapter().getItem(pos);
		ItemListAdapter la = (ItemListAdapter) parent.getAdapter();
		Bundle bundle = new Bundle();
		bundle.putParcelable("ITEM", item);
		i.putExtras(bundle);
        activity.startActivity(i);
	}

	@Override
	public boolean onLongClick(View arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
	
	

