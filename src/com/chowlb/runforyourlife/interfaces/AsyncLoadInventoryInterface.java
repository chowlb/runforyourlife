package com.chowlb.runforyourlife.interfaces;

import java.util.List;

import com.chowlb.runforyourlife.objects.Item;

public interface AsyncLoadInventoryInterface {

	public void handleInventoryLoading(List<Item> items);
	
}
