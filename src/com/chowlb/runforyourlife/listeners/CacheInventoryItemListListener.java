package com.chowlb.runforyourlife.listeners;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;

import com.chowlb.runforyourlife.GameMapActivity;
import com.chowlb.runforyourlife.SingleItemActivity;
import com.chowlb.runforyourlife.adapters.ItemListAdapter;
import com.chowlb.runforyourlife.async.AddItemAsync;
import com.chowlb.runforyourlife.async.DeleteItemAsync;
import com.chowlb.runforyourlife.objects.Cache;
import com.chowlb.runforyourlife.objects.Item;
import com.chowlb.runforyourlife.objects.Player;



public class CacheInventoryItemListListener implements OnItemClickListener, OnItemLongClickListener{
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
		if(cache.getOwnerID() == 0) {
			bundle.putInt("TYPE", 1);
		}else if(cache.getOwnerID() == GameMapActivity.player.getPlayerID()){
			bundle.putInt("TYPE", 2);
		}else {
			bundle.putInt("TYPE", 3);
		}
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
				Log.e("chowlb", "Trying to add item, inventory count: " + player.getInventory().size());
				if(player.canAddItem()) {
					DeleteItemAsync deleteItemActivity = new DeleteItemAsync();
					deleteItemActivity.execute(item, 1);
					cache.removeItemAtPos(position);
					AddItemAsync addItemActivity = new AddItemAsync();
					addItemActivity.execute(item, player, 2);
					la.notifyDataSetChanged();
				}else {
					Toast.makeText(activity, "Inventory is full",  Toast.LENGTH_SHORT).show();
				}
			}
		})
		.setNegativeButton(android.R.string.no, null).show();
		
		
		return false;
	}	
}
	
	


