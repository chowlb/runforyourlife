package com.chowlb.runforyourlife.interfaces;

import java.util.List;

import com.chowlb.runforyourlife.objects.Item;
import com.chowlb.runforyourlife.objects.Player;

public interface AsyncInterface {
	
	//void handleInventory(List<Item> result);
	
	void processLogin(Player player);
	
	//void handleCacheInventory(List<Item> result);

}
