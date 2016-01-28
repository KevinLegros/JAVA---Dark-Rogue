package com.gmail.kevinlegros.DarkRogue.Skill;

import org.lwjgl.util.vector.Vector2f;

import com.gmail.kevinlegros.DarkRogue.DarkRogue;
import com.gmail.kevinlegros.DarkRogue.Entity.Entity;
import com.gmail.kevinlegros.DarkRogue.Sprite.Screen;
import com.gmail.kevinlegros.DarkRogue.Sprite.Sprite;

public class FerociousClaw extends Skill {
	
	public Sprite spriteIcon;
	public Sprite[] spriteAnim;
	
	private float ratio;
	
	public FerociousClaw(Vector2f position, String name, int range, float ratio, Entity originEntity) {
		super(SKILLTYPE_MELEE, position, 64, 64, name, originEntity.name);
		this.ratio = ratio;
		spriteAnim = DarkRogue.spriteLoader.getSkillAnim(SKILLANIM_FEROCIOUSCLAW);
		this.range = range;
		fatigueCost = 10;
		damage = 5;
		minDamage = 3;
		maxDamage = 7;
		animTime = 5;
		maxAnimTime = 5;
	}
	
	public void update() {
		if(curSprite < spriteAnim.length - 1) {
			animTime--;
			if(animTime < 0) {
				curSprite++;
				animTime = maxAnimTime;
			}
		} else {
			animOver = true;
		}
	}
	
	public void render(Screen screen) {
		screen.render(spriteAnim[curSprite], position.x, position.y, w, h, ratio);
	}
}
