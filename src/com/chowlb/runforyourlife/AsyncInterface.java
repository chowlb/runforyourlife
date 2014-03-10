package com.chowlb.runforyourlife;

import java.util.List;

public interface AsyncInterface {
	
	void handleInventory(List<Item> result);
	
	void processLogin(Player player);

}
