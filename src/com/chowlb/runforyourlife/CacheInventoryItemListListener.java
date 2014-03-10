package com.chowlb.runforyourlife;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;



public class CacheInventoryItemListListener implements OnItemClickListener, OnItemLongClickListener{
	List<Item> listItems;
	Adapter adapter;
	Activity activity;
	Cache cache;
	public CacheInventoryItemListListener(List<Item> aListItems, Activity anActivity, Adapter anAdapter, Cache ancache) {
		listItems = aListItems;
		activity = anActivity;
		adapter = anAdapter;
		cache = ancache;
	}
	
	
	
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		Intent i = new Intent(activity, SingleItemActivity.class);
		Item item = (Item) parent.getAdapter().getItem(pos);
		
		Bundle bundle = new Bundle();
		bundle.putParcelable("ITEM", item);
		i.putExtras(bundle);
        activity.startActivity(i);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {
		
		final Item item = (Item) parent.getAdapter().getItem(pos);
		final ItemListAdapter la = (ItemListAdapter) parent.getAdapter();
		final int position = pos;
		
//		new AlertDialog.Builder(parent.getContext())
//		.setTitle("Drop - " + item.getName())
//		.setMessage("Do you wish to drop " + item.getName() +"?")
//		.setIcon(android.R.drawable.ic_dialog_alert)
//		.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				DeleteItem deleteItemActivity = new DeleteItem();
//				deleteItemActivity.execute(item);
//				player.removeItemAtPos(position);
//				la.notifyDataSetChanged();
//			}
//		})
//		.setNegativeButton(android.R.string.no, null).show();
		
		
		return false;
	}

	
	
}
	
	


