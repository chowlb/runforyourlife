package com.chowlb.runforyourlife.objects;

import java.util.ArrayList;
import java.util.List;


public class Inventory  {
	private int inventorySize = 15;
	private List<Item> items;
	
	public Inventory() {
		items = new ArrayList<Item>();
	}
	
	public void addItem(Item item) {
		items.add(item);
	}
	
	public void loadInventory(int playerID) {
		
	}
	
	public Item returnItemAtPos(int pos) {
		return items.get(pos);
	}
	
	public int getInventorySize() {
		return this.inventorySize;
	}
	
	public List<Item> getItems() {
		return items;
	}
	
	public int getInventoryCount() {
		return items.size();
	}
	
}
