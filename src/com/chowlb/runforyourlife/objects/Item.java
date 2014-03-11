package com.chowlb.runforyourlife.objects;

import com.chowlb.runforyourlife.R;
import com.chowlb.runforyourlife.R.color;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable{
//public class Item{
	private int itemId;
	private int itemDBID;
	private String itemType;
	private String status;
	private String name;
	private String description;
	private int attribute;
	private String owner;
	
	public Item() {
		
	}
	
	public Item(int id, int itemDBID, String name, String description, String itemType, String status, int attribute, String owner) {
		this.name = name;
		this.description = description;
		this.itemType = itemType;
		this.itemId = id;
		this.status = status;
		this.attribute = attribute;
		this.itemDBID = itemDBID;
		this.owner = owner;
	}
	
	public Item(int id, int itemDBID, String name, String description, String itemType, String status, int attribute) {
		this.name = name;
		this.description = description;
		this.itemType = itemType;
		this.itemId = id;
		this.status = status;
		this.attribute = attribute;
		this.itemDBID = itemDBID;
	}
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getItemDBID() {
		return itemDBID;
	}

	public void setItemDBID(int itemDBID) {
		this.itemDBID = itemDBID;
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

	public String getImage() {
		return name.replaceAll(" ", "_").toLowerCase() + "_item_img";
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
	
	public int getStatusColor(Context context){
		if(this.status.equals("Mint")){
			return context.getResources().getColor(R.color.MINT);
		}else if(this.status.equals("Good")){
			return context.getResources().getColor(R.color.GOOD);
		}else if(this.status.equals("Fair")){
			return context.getResources().getColor(R.color.FAIR);
		}else if(this.status.equals("Poor")){
			return context.getResources().getColor(R.color.POOR);
		}else if(this.status.equals("Broken")){
			return context.getResources().getColor(R.color.BROKEN);
		}else
			return context.getResources().getColor(R.color.BROKEN);
	}

	//EVERYTHING BELOW HERE IS FOR THE PARCEL
	public int describeContents() {
		return 0;
	}
	
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(this.itemId);
		out.writeInt(this.itemDBID);
		out.writeString(this.itemType);
		out.writeString(this.status);
		out.writeString(this.name);
		out.writeString(this.description);
		out.writeInt(this.attribute);
		out.writeString(this.owner);


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
		this.itemDBID = in.readInt();
		this.itemType = in.readString();
		this.status = in.readString();
		this.name = in.readString();
		this.description = in.readString();
		this.attribute = in.readInt();
		this.owner = in.readString();

		
	}
}
