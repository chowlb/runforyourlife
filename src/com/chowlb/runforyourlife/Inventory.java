package com.chowlb.runforyourlife;

public class Inventory {
	Item[] items = new Item[10];
	
	public Inventory() {
	}
	
	public void loadInventory(int playerID) {
		
	}
	
	public String toString() {
		String listOfItems = "";
		
		for(int i=0; i < items.length; i++) {
			if(listOfItems.equals("")) {
				listOfItems = items[i].getName();
			}else {
				listOfItems = listOfItems + ", " + items[i].getName();
			}
		}
		
		return listOfItems;
	}
}
