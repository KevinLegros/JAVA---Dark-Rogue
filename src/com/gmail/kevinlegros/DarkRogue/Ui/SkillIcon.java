package com.gmail.kevinlegros.DarkRogue.Ui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;

import com.gmail.kevinlegros.DarkRogue.DarkRogue;
import com.gmail.kevinlegros.DarkRogue.Sprite.Font;
import com.gmail.kevinlegros.DarkRogue.Sprite.Screen;
import com.gmail.kevinlegros.DarkRogue.Sprite.Sprite;

public class SkillIcon {
	public static final int SKILLICON_DEFAULT = 0;
	public static final int SKILLICON_FLAMESPEAR = 1;
	public static final int SKILLICON_FLAMESPEAR_HOVER = 2;
	public static final int SKILLICON_CRESCENTSLASH = 3;
	public static final int SKILLICON_CRESCENTSLASH_HOVER = 4;
	
	public int x;
	public int y;
	public int w = 64;
	public int h = 64;
	
	public Sprite sprite;
	public int hover;
	
	public String skillName;
	
	public boolean selected = false;
	public int skillType;
	public float ratio;
	
	public int range;
	
	
	public List<String> description = new ArrayList<String>();
	
	public SkillIcon(int x, int y, int skillType, float ratio) {
		this.x = x;
		this.y = y;
		this.skillType = skillType;
		this.ratio = ratio;
		sprite = DarkRogue.spriteLoader.getSkillIcon(this.skillType);
		switch(skillType) {
			case SKILLICON_FLAMESPEAR: 
				hover = SKILLICON_FLAMESPEAR_HOVER;
				skillName = "Flame Spear";
				range = 5;
				description.add("Not implemented yet");
				break;
			case SKILLICON_CRESCENTSLASH:
				hover = SKILLICON_CRESCENTSLASH_HOVER;
				skillName = "Crescent Slash";
				range = 1;
				description.add("Swing your sword in an arc, dealing");
				description.add("10 damage to a single enemy");
				break;
		}
	}
	
	public void update() {
		if(selected) {
			sprite = DarkRogue.spriteLoader.getSkillIcon(hover);
		} else {
			if(mouseIn(Mouse.getX(), DarkRogue.WHEIGHT - Mouse.getY())) {
				sprite = DarkRogue.spriteLoader.getSkillIcon(hover);
			} else {
				sprite = DarkRogue.spriteLoader.getSkillIcon(skillType);
			}
		}
	}
	
	public boolean mouseIn(int mx, int my) {
		return !(mx < x || my < y || mx > x + w || my > y + h);
	}
	
	public void render(Screen screen) {
		if(mouseIn(Mouse.getX(), DarkRogue.WHEIGHT - Mouse.getY())) {
			for(int i = 0; i < description.size(); i++) {
				Font.renderBlack(description.get(i), x + 1, y - description.size() * 20 + i * 20 + 1, 0.5f);
				Font.render(description.get(i), x, y - description.size() * 20 + i * 20, 0.5f);
			}
		}
		screen.render(sprite, screen.xoff + x + w / 2, screen.yoff + y + h / 2, w, h, ratio);
	}
}
