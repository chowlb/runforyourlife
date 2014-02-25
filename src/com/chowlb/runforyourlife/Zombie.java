package com.chowlb.runforyourlife;

import android.location.Location;

public class Zombie {
	private int health;
	private int attack;
	private int speed;
	private boolean alert;
	private Location position;
	
	public Zombie() {
		this.health = 100;
		this.attack = 5;
		this.speed = 1;
		this.alert = false;
	}
	
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public boolean isAlert() {
		return alert;
	}
	public void setAlert(boolean alert) {
		this.alert = alert;
	}
	public Location getPosition() {
		return position;
	}
	public void setPosition(Location position) {
		this.position = position;
	}
}
