package com.chowlb.runforyourlife;

import java.util.List;

import org.json.JSONObject;

public interface AsyncInterface {
	
	void handleInventory(List<Item> result);
	
	void processLogin(Player player);

}
