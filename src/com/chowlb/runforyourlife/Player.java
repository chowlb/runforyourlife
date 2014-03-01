package com.chowlb.runforyourlife;

import java.util.ArrayList;
import java.util.List;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable{
	private String playerName;
	private int playerID;
	private List<Item> inventory;
	private int	health;
	private int daysSurvived;
	private String lastLogin;
	private int baseAttack = 10;
	private Location position;
	private int inventorySize = 15;
	
	public Player(int playerID, String name, int health, int days, String loginDate) {
		this.playerID = playerID;
		this.playerName = name;
		this.health = health;
		this.daysSurvived = days;
		this.lastLogin = loginDate;
		this.inventory = new ArrayList<Item>();
	}

	public boolean addItem(Item item) {
		if(inventory.size() < inventorySize) {
			inventory.add(item);
			return true;
		}else {
			return false;
		}	
	}
	

	public Item returnItemAtPos(int pos) {
		return inventory.get(pos);
	}
	
	
	public String getPlayerName() {
		return playerName;
	}
	
	public boolean isInvDefined() {
		if(this.inventory == null) {
			return false;
		}
		else {
			return true;
		}
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getBaseAttack() {
		return baseAttack;
	}

	public void setBaseAttack(int baseAttack) {
		this.baseAttack = baseAttack;
	}

	public Location getPosition() {
		return position;
	}

	public void setPosition(Location position) {
		this.position = position;
	}
	
	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public boolean testFight(Zombie zed) {
		//get items in hand
		//Item right = this.rightHand.getAttack();
		//Item left = this.leftHand.getAttack();
		
		return true;
	}
	
	public List<Item> getInventory() {
		return inventory;
	}
	
	public void setInventory(List<Item> inventory) {
		this.inventory = inventory;
	}

//	public Item getLeftHand() {
//		return leftHand;
//	}
//
//	public void setLeftHand(Item leftHand) {
//		this.leftHand = leftHand;
//	}
//	
//	public Item getRightHand() {
//		return rightHand;
//	}
//
//	public void setRightHand(Item rightHand) {
//		this.rightHand = rightHand;
//	}

	public int getDaysSurvived() {
		return daysSurvived;
	}

	public void setDaysSurvived(int daysSurvived) {
		this.daysSurvived = daysSurvived;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	
	//EVERYTHING BELOW HERE IS FOR THE PARCELABLE
	
	public int describeContents() {
		return 0;
	}
	
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(this.playerID);
		out.writeString(this.playerName);
		out.writeInt(this.health);
		out.writeInt(this.daysSurvived);
		out.writeString(this.lastLogin);
		out.writeTypedList(this.inventory);
	}
	
	public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
		public Player createFromParcel(Parcel in) {
			return new Player(in);
		}
		
		public Player[] newArray(int size) {
			return new Player[size];
		}
	};
	
	public Player(Parcel in){
		readFromParcel(in);
	}
	
	public void readFromParcel(Parcel in) {
		this.playerID = in.readInt();
		this.playerName = in.readString();
		this.health = in.readInt();
		this.daysSurvived = in.readInt();
		this.lastLogin = in.readString();
		if(this.inventory == null){
			this.inventory = new ArrayList<Item>();
		}
		in.readTypedList(this.inventory, Item.CREATOR);
	}
	
}
