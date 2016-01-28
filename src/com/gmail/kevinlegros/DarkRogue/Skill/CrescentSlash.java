package com.gmail.kevinlegros.DarkRogue.Skill;

import org.lwjgl.util.vector.Vector2f;

import com.gmail.kevinlegros.DarkRogue.DarkRogue;
import com.gmail.kevinlegros.DarkRogue.Entity.Entity;
import com.gmail.kevinlegros.DarkRogue.Sprite.Screen;
import com.gmail.kevinlegros.DarkRogue.Sprite.Sprite;

public class CrescentSlash extends Skill{
	
	public int school;
	public Sprite spriteIcon;
	public Sprite[] spriteAnim;
	
	private float ratio;
	
	public CrescentSlash(Vector2f position, String name, int range, float ratio, Entity originEntity) {
		super(SKILLTYPE_MELEE, position, 64, 64, name, originEntity.name);
		this.ratio = ratio;
		spriteAnim = DarkRogue.spriteLoader.getSkillAnim(SKILLANIM_CRESCENTSLASH_S);
		school = SPELLSCHOOL_PYROMANCY;
		this.range = range;
		fatigueCost = 10;
		damage = 10;
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
