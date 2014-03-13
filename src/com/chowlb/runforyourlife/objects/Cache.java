package com.chowlb.runforyourlife.objects;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

public class Cache implements Parcelable {
	private int cacheID;
	private String cacheText = "Supply Cache";
	private String owner = "";
	private int ownerID;
	private LatLng location;
	private double longitude;
	private double latitude;
	private String pin;
	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	private List<Item> inventory;
	private int inventorySize = 5;
	
	public Cache() {
		this.inventory = new ArrayList<Item>();
	}
	
	public Cache(int id, String o, int oid, LatLng loc) {
		cacheID = id;
		owner = o;
		ownerID = oid;
		location = loc;
		latitude = loc.latitude;
		longitude = loc.longitude;
		this.inventory = new ArrayList<Item>();
	}
	
	public Cache(int id, String o, int oid, double lat, double lon) {
		cacheID = id;
		owner = o;
		ownerID = oid;
		location = new LatLng(lat, lon);
		latitude = lat;
		longitude = lon;
		this.inventory = new ArrayList<Item>();
	}

	public int getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}

	public int getInventorySize() {
		return inventorySize;
	}

	public void setInventorySize(int inventorySize) {
		this.inventorySize = inventorySize;
	}

	public int getCacheID() {
		return cacheID;
	}

	public void setCacheID(int cacheID) {
		this.cacheID = cacheID;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public String getCacheText() {
		return cacheText;
	}

	public void setCacheText(String text) {
		this.cacheText = text;
	}

	public LatLng getLocation() {
		return location;
	}

	public void setLocation(LatLng location) {
		this.location = location;
	}
	
	
	
	
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public boolean addItem(Item item) {
		if(inventory.size() < inventorySize) {
			inventory.add(item);
			return true;
		}else {
			return false;
		}	
	}
	

	public Item getItemAtPos(int pos) {
		return inventory.get(pos);
	}
	
	public void removeItemAtPos(int pos) {
		inventory.remove(pos);
	}
	
	public List<Item> getInventory() {
		return inventory;
	}
	
	public void setInventory(List<Item> inventory) {
		this.inventory = inventory;
	}
	
	
	public int describeContents() {
		return 0;
	}
	
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(this.cacheID);
		out.writeString(this.owner);
		out.writeInt(this.ownerID);
		out.writeDouble(this.location.latitude);
		out.writeDouble(this.location.longitude);
		out.writeTypedList(this.inventory);
		out.writeDouble(this.latitude);
		out.writeDouble(this.longitude);
		out.writeString(this.pin);
		
	}
	
	public static final Parcelable.Creator<Cache> CREATOR = new Parcelable.Creator<Cache>() {
		public Cache createFromParcel(Parcel in) {
			return new Cache(in);
		}
		
		public Cache[] newArray(int size) {
			return new Cache[size];
		}
	};
	
	public Cache(Parcel in){
		readFromParcel(in);
	}
	
	public void readFromParcel(Parcel in) {
		this.cacheID = in.readInt();
		this.owner = in.readString();
		this.ownerID = in.readInt();
		double lat = in.readDouble();
		double lon = in.readDouble();
		this.location = new LatLng(lat, lon);
		
		if(this.inventory == null){
			this.inventory = new ArrayList<Item>();
		}
		in.readTypedList(this.inventory, Item.CREATOR);
		
		this.latitude = in.readDouble();
		this.longitude = in.readDouble();
		this.pin = in.readString();
	}
	
	
}
