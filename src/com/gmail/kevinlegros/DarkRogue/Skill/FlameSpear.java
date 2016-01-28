package com.gmail.kevinlegros.DarkRogue.Skill;

import org.lwjgl.util.vector.Vector2f;

import com.gmail.kevinlegros.DarkRogue.Entity.Entity;
import com.gmail.kevinlegros.DarkRogue.Sprite.Screen;
import com.gmail.kevinlegros.DarkRogue.Sprite.Sprite;

public class FlameSpear extends Skill {
	
	public int school;
	public Sprite spriteIcon;
	
	private float ratio;
	
	public FlameSpear(Vector2f position, String name, int range, float ratio, Entity originEntity) {
		super(SKILLTYPE_SPELL, position, 64, 64, name, originEntity.name);
		this.ratio = ratio;
		school = SPELLSCHOOL_PYROMANCY;
		this.range = range;
		manaCost = 10;
		damage = 10;
		skillName = "Flame Spear";
	}
	
	public void update() {
		
	}
	
	public void render(Screen screen) {
		
	}
}
