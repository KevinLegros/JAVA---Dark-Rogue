package com.gmail.kevinlegros.DarkRogue.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import com.gmail.kevinlegros.DarkRogue.DarkRogue;
import com.gmail.kevinlegros.DarkRogue.Skill.Skill;
import com.gmail.kevinlegros.DarkRogue.Sprite.Screen;
import com.gmail.kevinlegros.DarkRogue.Sprite.Sprite;
import com.gmail.kevinlegros.DarkRogue.World.World;

public class Player extends Entity {	
	private int life = 100;
	private Sprite head;
	
	public Skill currentActiveSkill;
	public boolean currentSkillUsed = false;
	public boolean currentSkillUpdating = false;
	
	public List<Skill> enemySkills = new ArrayList<Skill>();
	
	public Player(World world) {
		this.type = PLAYER_ARMORED_IDLE_BODY_S;
		init(world);
		stats = new EntityStats(30, 30, 30, 5);
		head = DarkRogue.spriteLoader.getSprite(PLAYER_ARMORED_IDLE_HEAD_S);
	}
	
	public void update() {
		updateRangeGrid();
		
		if((int) position.x / World.TILE_SIZE != curTile.position.x) {
			if((int) position.x / World.TILE_SIZE < curTile.position.x) {
				turnLeft();
			} else if((int) position.x / World.TILE_SIZE > curTile.position.x) {
				turnRight();
			}
			curTile = world.tiles[((int) position.x / World.TILE_SIZE) + ((int) position.y / World.TILE_SIZE) * World.TILE_SIZE];
		}
		if((int) position.y / World.TILE_SIZE != curTile.position.y) {
			if((int) position.y / World.TILE_SIZE < curTile.position.y) {
				turnUp();
			} else if((int) position.y / World.TILE_SIZE > curTile.position.y) {
				turnDown();
			}
			curTile = world.tiles[((int) position.x / World.TILE_SIZE) + ((int) position.y / World.TILE_SIZE) * World.TILE_SIZE];
		}
		if(currentSkillUsed) currentSkillUpdating = true;
		if(currentSkillUpdating) {
			if(currentActiveSkill != null) {
				if(currentActiveSkill.animOver) {
					currentSkillUpdating = false;
					currentActiveSkill = null;
					world.ui.unselectSkill();
					currentSkillUsed = false;
					world.canUpdate = true;
				} else {
					currentActiveSkill.update();
				}
			}
		}
		for(int i = 0; i < enemySkills.size(); i++) {
			enemySkills.get(i).update();
			if(enemySkills.get(i).animOver) {
				Random r = new Random();
				int eDmg = r.nextInt(enemySkills.get(i).maxDamage - enemySkills.get(i).minDamage);
				int damage = hurt(enemySkills.get(i).minDamage + eDmg);
				world.battleLog.addMessage(enemySkills.get(i).originEntity+" hit Player for "+damage+" damage");
				enemySkills.remove(i);
			}
		}
	}
	
	public void move(Vector2f pos) {
		if(pos.x / World.TILE_SIZE < 0) return;
		if(pos.y / World.TILE_SIZE < 0) return;
		if(pos.x / World.TILE_SIZE > World.WORLD_WIDTH) return;
		if(pos.y / World.TILE_SIZE > World.WORLD_HEIGHT) return;
		position.x = pos.x;
		position.y = pos.y;
		world.hoveredTile = null;
		world.canUpdate = true;
		
		currentActiveSkill = null;
		world.ui.unselectSkill();
	}
	
	public void turnLeft() {
		//rotation = -90.0f;
		sprite = DarkRogue.spriteLoader.getSprite(PLAYER_ARMORED_IDLE_BODY_W);
		head = DarkRogue.spriteLoader.getSprite(PLAYER_ARMORED_IDLE_HEAD_W);
	}
	
	public void turnRight() {
		//rotation = 90.0f;
		sprite = DarkRogue.spriteLoader.getSprite(PLAYER_ARMORED_IDLE_BODY_E);
		head = DarkRogue.spriteLoader.getSprite(PLAYER_ARMORED_IDLE_HEAD_E);
	}

	public void turnUp() {
		//rotation = 0.0f;
		sprite = DarkRogue.spriteLoader.getSprite(PLAYER_ARMORED_IDLE_BODY_N);
		head = DarkRogue.spriteLoader.getSprite(PLAYER_ARMORED_IDLE_HEAD_N);
	}
	
	public void turnDown() {
		//rotation = 180.0f;
		sprite = DarkRogue.spriteLoader.getSprite(PLAYER_ARMORED_IDLE_BODY_S);
		head = DarkRogue.spriteLoader.getSprite(PLAYER_ARMORED_IDLE_HEAD_S);
	}
	
	public void setActiveSkill(Skill skill) {
		currentActiveSkill = skill;
		aggroRange = skill.range;
	}
	
	public void addEnemySkill(Skill skill) {
		enemySkills.add(skill);
	}
	
	public int hurt(int damage) {
		life -= damage;
		if(life <= 0) world.darkRogue.mainMenu();
		return damage;
	}
	
	public int getLife() {
		return life;
	}
	
	public void render(Screen screen) {
		screen.render(head, position.x, position.y - h, w, h, ratio);
		screen.render(sprite, position.x, position.y, w, h, ratio);
		if(currentSkillUpdating && currentActiveSkill != null) currentActiveSkill.render(screen);
		if(enemySkills.size() > 0) enemySkills.get(0).render(screen);
	}
}
