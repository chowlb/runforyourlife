package com.chowlb.runforyourlife;

public class Item {
	private String itemType;
	private String name;
	private String description;
	private boolean equipLeft;
	private boolean equipRight;
	
	protected Item(String name, String description, String itemType) {
		this.name = name;
		this.description = description;
		this.itemType = itemType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	};
}
