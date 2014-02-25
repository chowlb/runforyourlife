package com.chowlb.runforyourlife;

public class Item_Supply extends Item{
	private int amount;
	private int statusEffect;
	
	public Item_Supply(String name, String description, int amount, int statusEffect, String itemType) {
		super(name, description, itemType);
		this.amount = amount;
		this.statusEffect = statusEffect;
	}

	public int getAttack() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getStatusEffect() {
		return statusEffect;
	}
	
	public void setStatusEffect(int statusEffect) {
		this.statusEffect = statusEffect;
	}

}