package com.gmail.kevinlegros.DarkRogue.Skill;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import com.gmail.kevinlegros.DarkRogue.Sprite.Screen;

public class Skill {
	public static final int SKILLTYPE_MELEE = 0;
	public static final int SKILLTYPE_RANGED = 1;
	public static final int SKILLTYPE_SPELL = 2;
	
	public static final int SPELLSCHOOL_DEFAULT = 0;
	public static final int SPELLSCHOOL_PYROMANCY = 1;
	public static final int SPELLSCHOOL_LIQUIFACTION = 2;
	public static final int SPELLSCHOOL_CALEFACTIOn = 3;
	public static final int SPELLSCHOOL_DOLORES_INFLICTUS = 4;
	
	public static final int SKILLANIM_CRESCENTSLASH_S = 0;
	public static final int SKILLANIM_CRESCENTSLASH_N = 1;
	public static final int SKILLANIM_CRESCENTSLASH_W = 2;
	public static final int SKILLANIM_CRESCENTSLASH_E = 3;
	public static final int SKILLANIM_FEROCIOUSCLAW = 4;
	
	protected int type;
	
	Vector2f position;
	Vector2f speed;
	public int w;
	public int h;
	
	public int fatigueCost = 0;
	public int manaCost = 0;
	public int damage;
	public int minDamage = 0;
	public int maxDamage = 0;
	
	public String skillName;
	
	public int range = 1;

	
	public int curSprite = 0;
	public int animTime = 5;
	public int maxAnimTime = 5;
	public boolean animOver = false;

	
	public String originEntity;
	
	public Skill(int type, Vector2f position, int w, int h, String name, String originEntity) {
		this.type = type;
		this.position = new Vector2f(position.x, position.y);
		this.w = w;
		this.h = h;
		this.skillName = name;
		this.originEntity = originEntity;
	}
	
	public void update() {
		if(speed.x > 0 || speed.y > 0) move();
	}
	
	public void move() {
		
	}
	
	public void render(Screen screen) {
		
	}
}
