package com.gmail.kevinlegros.DarkRogue.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.gmail.kevinlegros.DarkRogue.DarkRogue;
import com.gmail.kevinlegros.DarkRogue.Skill.FerociousClaw;
import com.gmail.kevinlegros.DarkRogue.Skill.Skill;
import com.gmail.kevinlegros.DarkRogue.Sprite.Font;
import com.gmail.kevinlegros.DarkRogue.Sprite.Screen;
import com.gmail.kevinlegros.DarkRogue.Sprite.Sprite;
import com.gmail.kevinlegros.DarkRogue.World.Tile;
import com.gmail.kevinlegros.DarkRogue.World.World;

public class Enemy extends Entity {
	
	
	public boolean hasAggro = false;
	private boolean wasAggro = false;
	public int nextMoveAggro = 0;
	public int maxNextMoveAggro = 0;
	public int life = 30;
	
	public List<Tile> atkRange = new ArrayList<Tile>();
	public int attackTime;
	public int maxAttackTime;
	public boolean isAttacking = false;
	public boolean canAttack = false;

	public int curAtkSprite = 0;
	
	public Skill curSkill;
	
	public Enemy(World world) {
		this.type = ENEMY;
		init(world);
		stats = new EntityStats(20, 20, 20, 5);
		sprite = DarkRogue.spriteLoader.getSprite(RAT_IDLE_S);
		orientation = ORIENTATION_S;
		aggroRange = 3;
		range = 1;
		name = "Rat";
	}
	
	public void update() {
		switch(orientation) {
			case ORIENTATION_N:
				sprite = DarkRogue.spriteLoader.getSprite(RAT_IDLE_N);
				break;
			case ORIENTATION_S:
				sprite = DarkRogue.spriteLoader.getSprite(RAT_IDLE_S);
				break;
			case ORIENTATION_E:
				sprite = DarkRogue.spriteLoader.getSprite(RAT_IDLE_E);
				break;
			case ORIENTATION_W:
				sprite = DarkRogue.spriteLoader.getSprite(RAT_IDLE_W);
				break;
		}
		Vector2f newpos = new Vector2f();
		Tile tile = world.player.curTile;
		boolean inAggro = false;
		for(int i = 0; i < inRange.size(); i++) {
			if(inRange.get(i).x == tile.position.x && inRange.get(i).y == tile.position.y) inAggro = true;
		}
		
		if(inRange.size() == 0) updateRangeGrid();
		
		if(!world.canUpdate) {
			wasAggro = inAggro;
			return;
		}
		
		canAttack = true;
		
		if(inAggro || wasAggro) {
			if(nextMoveAggro > 0) {
				nextMoveAggro--;
			} else {
				int thisX = (int) (position.x / World.TILE_SIZE);
				int thisY = (int) (position.y / World.TILE_SIZE);
				if(tile.position.x < thisX && tile.position.y == thisY) {
					// Player is left same line
					newpos = new Vector2f(position.x - World.TILE_SIZE, position.y);
				} else if(tile.position.x > thisX && tile.position.y == thisY) {
					// Player is right same line
					newpos = new Vector2f(position.x + World.TILE_SIZE, position.y);
				} else if(tile.position.x == thisX && tile.position.y < thisY) {
					// Player is above same col
					newpos = new Vector2f(position.x, position.y - World.TILE_SIZE);
				} else if(tile.position.x == thisX && tile.position.y > thisY) {
					// Player is below same col
					newpos = new Vector2f(position.x, position.y + World.TILE_SIZE);
				} else {
					// Player in range, different line+col
					Random r = new Random();
					int hOrV = r.nextInt(100);
					if(hOrV < 50) {
						if(tile.position.x < thisX) {
							newpos = new Vector2f(position.x - World.TILE_SIZE, position.y);
						} else if(tile.position.x > position.x / World.TILE_SIZE) {
							newpos = new Vector2f(position.x + World.TILE_SIZE, position.y);
						}
					} else {
						if(tile.position.y < position.y / World.TILE_SIZE) {
							newpos = new Vector2f(position.x, position.y - World.TILE_SIZE);
						} else if(tile.position.y > position.y / World.TILE_SIZE) {
							newpos = new Vector2f(position.x, position.y + World.TILE_SIZE);
						}
					}
				}
				if(world.canMoveEntity(this, newpos)) move(newpos);
				nextMoveAggro = maxNextMoveAggro;
			}
			wasAggro = false;
		} else {
			Random random = new Random();
			if(random.nextInt(100) < 50) {
				Random r = new Random();
				int nmove = r.nextInt(4);
				switch(nmove) {
					case 0:
						newpos = new Vector2f(position.x - World.TILE_SIZE, position.y);
						if(world.canMoveEntity(this, newpos)) move(newpos);
						break;
					case 1:
						newpos = new Vector2f(position.x + World.TILE_SIZE, position.y);
						if(world.canMoveEntity(this, newpos)) move(newpos);
						break;
					case 2:
						newpos = new Vector2f(position.x, position.y - World.TILE_SIZE);
						if(world.canMoveEntity(this, newpos)) move(newpos);
						break;
					case 3:
						newpos = new Vector2f(position.x, position.y + World.TILE_SIZE);
						if(world.canMoveEntity(this, newpos)) move(newpos);
						break;
				}
			}
		}
		
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

		updateAtkRangeGrid();
		if(curSkill != null && curSkill.animOver) {
			isAttacking = false;
			curSkill = null;
		}
		if(playerInRange() && isAttacking) {
			if((int) position.y / World.TILE_SIZE != world.player.position.y / World.TILE_SIZE) {
				if((int) position.y / World.TILE_SIZE < world.player.position.y / World.TILE_SIZE) {
					turnDown();
				} else if((int) position.y / World.TILE_SIZE > world.player.position.y / World.TILE_SIZE) {
					turnUp();
				}
			}
			if((int) position.x / World.TILE_SIZE != world.player.position.x / World.TILE_SIZE) {
				if((int) position.x / World.TILE_SIZE < world.player.position.x / World.TILE_SIZE) {
					turnRight();
				} else if((int) position.x / World.TILE_SIZE > world.player.position.x / World.TILE_SIZE) {
					turnLeft();
				}
			}
		}
		if(playerInRange() && !isAttacking && canAttack) {
			isAttacking = true;
			attackTime = maxAttackTime;
			curAtkSprite = 0;
			curSkill = new FerociousClaw(world.player.position, "Ferocious Claw", 1, 1.0f, this);
			world.player.addEnemySkill(curSkill);
		}
	}
	
	public void move(Vector2f pos) {
		if(pos.x / World.TILE_SIZE < 0) return;
		if(pos.y / World.TILE_SIZE < 0) return;
		if(pos.x / World.TILE_SIZE > World.WORLD_WIDTH) return;
		if(pos.y / World.TILE_SIZE > World.WORLD_HEIGHT) return;
		position.x = pos.x;
		position.y = pos.y;
		updateRangeGrid();
		canAttack = false;
	}
	
	public void updateAtkRangeGrid() {
		atkRange.clear();
		int minRangeY = (int) (position.y / World.TILE_SIZE) - range;
		int maxRangeY = (int) (position.y / World.TILE_SIZE) + range;
		for(int y = minRangeY; y <= maxRangeY; y++) {
			Tile t = world.getTile((int) (position.x / World.TILE_SIZE) + y * World.TILE_SIZE);
			if(!atkRange.contains(t)) atkRange.add(t);
		}
		int minRangeX = (int) (position.x / World.TILE_SIZE) - range;
		int maxRangeX = (int) (position.x / World.TILE_SIZE) + range;
		for(int x = minRangeX; x <= maxRangeX; x++) {
			Tile t = world.getTile(x + (int) (position.y / World.TILE_SIZE) * World.TILE_SIZE);
			if(!atkRange.contains(t)) atkRange.add(t);
		}
	}
	
	public List<Tile> getAtkRangeGrid() {
		return atkRange;
	}
	
	public boolean playerInRange() {
		return atkRange.contains(world.player.curTile);
	}
	
	public int hurt(int damage) {
		life -= damage;
		if(life <= 0) world.removeEntity(this);
		world.battleLog.addMessage("Player hit "+name+" for "+damage+" damage");
		return damage;
	}
	
	public void turnLeft() {
		//rotation = -90.0f;
		sprite = DarkRogue.spriteLoader.getSprite(RAT_IDLE_W);
		orientation = ORIENTATION_W;
	}
	
	public void turnRight() {
		//rotation = 90.0f;
		sprite = DarkRogue.spriteLoader.getSprite(RAT_IDLE_E);
		orientation = ORIENTATION_E;
	}

	public void turnUp() {
		//rotation = 0.0f;
		sprite = DarkRogue.spriteLoader.getSprite(RAT_IDLE_N);
		orientation = ORIENTATION_N;
	}
	
	public void turnDown() {
		//rotation = 180.0f;
		sprite = DarkRogue.spriteLoader.getSprite(RAT_IDLE_S);
		orientation = ORIENTATION_S;
	}
	
	public boolean mouseIn(int mx, int my) {
		return !(mx < position.x || my < position.y || mx > position.x + w || my > position.y + h);
	}
	
	public void render(Screen screen) {
		int mx = (int) (screen.xoff + Mouse.getX() + w / 2);
		int my = (int) (screen.yoff + (DarkRogue.WHEIGHT - Mouse.getY()) + h / 2);
		if(mouseIn(mx, my) && !world.ui.mouseIn()) {
			switch(orientation) {
				case ORIENTATION_N:
					sprite = DarkRogue.spriteLoader.getSprite(RAT_IDLE_HOV_N);
					break;
				case ORIENTATION_S:
					sprite = DarkRogue.spriteLoader.getSprite(RAT_IDLE_HOV_S);
					break;
				case ORIENTATION_E:
					sprite = DarkRogue.spriteLoader.getSprite(RAT_IDLE_HOV_E);
					break;
				case ORIENTATION_W:
					sprite = DarkRogue.spriteLoader.getSprite(RAT_IDLE_HOV_W);
					break;
			}
			String msg = name + "("+life+"hp)";
			Font.renderRed(msg, (int) (position.x - Font.width(msg) / 2 + 5) - screen.xoff, (int) (position.y - h / 2) - screen.yoff, 0.5f);
		}
		screen.render(sprite, position.x, position.y, w, h, ratio, rotation);
	}
}
