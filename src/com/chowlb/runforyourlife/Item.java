package com.chowlb.runforyourlife;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable{
//public class Item{
	private int itemId;
	private String itemType;
	private String status;
	private String name;
	private String description;
	private int attribute;
	
	protected Item() {
		
	}
	
	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getAttribute() {
		return attribute;
	}

	public void setAttribute(int attribute) {
		this.attribute = attribute;
	}

	protected Item(int id, String name, String description, String itemType, String status, int attribute) {
		this.name = name;
		this.description = description;
		this.itemType = itemType;
		this.itemId = id;
		this.status = status;
		this.attribute = attribute;
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
	}

	//EVERYTHING BELOW HERE IS FOR THE PARCEL
	public int describeContents() {
		return 0;
	}
	
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(this.itemId);
		out.writeString(this.itemType);
		out.writeString(this.status);
		out.writeString(this.name);
		out.writeString(this.description);
		out.writeInt(this.attribute);


	}
	
	public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
		public Item createFromParcel(Parcel in) {
			return new Item(in);
		}
		
		public Item[] newArray(int size) {
			return new Item[size];
		}
	};
	
	private Item(Parcel in) {
		this.itemId = in.readInt();
		this.itemType = in.readString();
		this.status = in.readString();
		this.name = in.readString();
		this.description = in.readString();
		this.attribute = in.readInt();

		
	}
}
