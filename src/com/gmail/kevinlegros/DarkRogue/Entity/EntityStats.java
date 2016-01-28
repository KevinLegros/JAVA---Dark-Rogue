package com.gmail.kevinlegros.DarkRogue.Entity;

public class EntityStats {
	public int str = 0;
	public int dext = 0;
	public int vit = 0;
	public int critChance = 0;
	
	public int fireRes = 0;
	public int watRes = 0;
	public int darkRes = 0;
	
	public String strName = "Strength";
	public String dextName = "Dexterity";
	public String vitName = "Vitality";
	public String ccName = "Critical Chance";
	
	public EntityStats(int str, int dext, int vit, int critChance) {
		this.str = str;
		this.dext = dext;
		this.vit = vit;
		this.critChance = critChance;
		
		fireRes = 10;
		watRes = 10;
		darkRes = 10;
	}
}
