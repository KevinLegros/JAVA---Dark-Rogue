package com.gmail.kevinlegros.DarkRogue.Ui;

import org.lwjgl.input.Mouse;

import com.gmail.kevinlegros.DarkRogue.DarkRogue;
import com.gmail.kevinlegros.DarkRogue.Entity.Player;
import com.gmail.kevinlegros.DarkRogue.Skill.CrescentSlash;
import com.gmail.kevinlegros.DarkRogue.Skill.FlameSpear;
import com.gmail.kevinlegros.DarkRogue.Sprite.Font;
import com.gmail.kevinlegros.DarkRogue.Sprite.Screen;

public class Ui {
	private Player player;
	
	public SkillIcon[] skillIcons = new SkillIcon[4];
	public int selectedIndex = -1;
	
	public Ui(Player player) {
		this.player = player;
		skillIcons[0] = new SkillIcon(100, DarkRogue.WHEIGHT - 100, SkillIcon.SKILLICON_FLAMESPEAR, 1.0f);
		skillIcons[1] = new SkillIcon(200, DarkRogue.WHEIGHT - 100, SkillIcon.SKILLICON_CRESCENTSLASH, 1.0f);
	}
	
	public void events() {
		for(int i = 0; i < skillIcons.length; i++) {
			if(skillIcons[i] != null) {
				if(skillIcons[i].mouseIn(Mouse.getX(), DarkRogue.WHEIGHT - Mouse.getY())) {
					if(selectedIndex > -1 && selectedIndex == i) {
						skillIcons[selectedIndex].selected = false;
						selectedIndex = -1;
					} else if(selectedIndex > -1 && selectedIndex != i) {
						skillIcons[selectedIndex].selected = false;
						skillIcons[i].selected = true;
						selectedIndex = i;
						activateSkill();
					} else {
						skillIcons[i].selected = true;
						selectedIndex = i;
						activateSkill();
					}
				}
			}
		}
	}
	
	public void update() {
		for(int i = 0; i < skillIcons.length; i++) {
			if(skillIcons[i] != null) skillIcons[i].update();
		}
	}
	
	public boolean mouseIn() {
		for(int i = 0; i < skillIcons.length; i++) {
			if(skillIcons[i] != null) if(skillIcons[i].mouseIn(Mouse.getX(), DarkRogue.WHEIGHT - Mouse.getY())) return true;
		}
		return false;
	}
	
	public void activateSkill() {
		String skillName = skillIcons[selectedIndex].skillName;
		switch(skillIcons[selectedIndex].skillType) {
			case SkillIcon.SKILLICON_FLAMESPEAR: player.setActiveSkill(new FlameSpear(player.position, skillName, 5, 1.0f, player)); break;
			case SkillIcon.SKILLICON_CRESCENTSLASH: player.setActiveSkill(new CrescentSlash(player.position, skillName, 1, 1.0f, player)); break;
			default: break;
		}
	}
	
	public void selectSkill(int index) {
		if(index > skillIcons.length - 1) return;
		if(selectedIndex > -1) skillIcons[selectedIndex].selected = false;
		selectedIndex = index;
		skillIcons[selectedIndex].selected = true;
	}

	public void unselectSkill() {
		if(selectedIndex < 0) return;
		skillIcons[selectedIndex].selected = false;
		selectedIndex = -1;
	}
	
	public void render(Screen screen) {
		Font.render("Player health: " + player.getLife() + "hp", 10, 10, 0.4f);
		
		for(int i = 0; i < skillIcons.length; i++) {
			if(skillIcons[i] != null) skillIcons[i].render(screen);
		}
	}
}
