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
import android.widget.Toast;



public class CacheInventoryItemListListener implements OnItemClickListener, OnItemLongClickListener, AsyncInterface{
	List<Item> listItems;
	Adapter adapter;
	Activity activity;
	Cache cache;
	Player player;
	CacheInventoryItemListListener local;
	ItemListAdapter la;
	
	public CacheInventoryItemListListener(List<Item> aListItems, Activity anActivity, Adapter anAdapter, Cache ancache, Player anPlayer) {
		listItems = aListItems;
		activity = anActivity;
		adapter = anAdapter;
		cache = ancache;
		player = anPlayer;
		local = this;
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
		la = (ItemListAdapter) parent.getAdapter();
		final int position = pos;
		
		new AlertDialog.Builder(parent.getContext())
		.setTitle("Pickup - " + item.getName())
		.setMessage("Do you wish to pickup " + item.getName() +"?")
		.setIcon(android.R.drawable.ic_dialog_info)
		.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(player.canAddItem()) {
					DeleteItem deleteItemActivity = new DeleteItem();
					deleteItemActivity.execute(item, 1);
					cache.removeItemAtPos(position);
					AddItem addItemActivity = new AddItem();
					addItemActivity.delegate = local;
					addItemActivity.execute(item, player, 2);
				}else {
					Toast.makeText(activity, "Inventory is full",  Toast.LENGTH_SHORT).show();
				}
			}
		})
		.setNegativeButton(android.R.string.no, null).show();
		
		
		return false;
	}



	@Override
	public void handleInventory(List<Item> invResult) {

		player.setInventory(invResult);
		la.notifyDataSetChanged();
		
	}

	@Override
	public void processLogin(Player player) {
		// TODO Auto-generated method stub
		
	}
	
}
	
	


