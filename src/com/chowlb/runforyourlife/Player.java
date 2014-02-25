package com.chowlb.runforyourlife;

import android.location.Location;

public class Player {
	private String playerName;
	private int playerID;
	private Inventory inventory;
	private int	health;
	private Item leftHand;
	private Item rightHand;
	private int baseAttack = 10;
	private Location position;
	
	public Player(int playerID, String name, int health, int baseAttack) {
		this.playerID = playerID;
		this.playerName = name;
		this.health = health;
		this.baseAttack = baseAttack;
		inventory.loadInventory(playerID);
	}

	public String getPlayerName() {
		return playerName;
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
	
	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Item getLeftHand() {
		return leftHand;
	}

	public void setLeftHand(Item leftHand) {
		this.leftHand = leftHand;
	}
	
	public Item getRightHand() {
		return rightHand;
	}

	public void setRightHand(Item rightHand) {
		this.rightHand = rightHand;
	}
}
