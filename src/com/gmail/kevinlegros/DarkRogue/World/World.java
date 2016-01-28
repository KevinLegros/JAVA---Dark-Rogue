package com.gmail.kevinlegros.DarkRogue.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import com.gmail.kevinlegros.DarkRogue.DarkRogue;
import com.gmail.kevinlegros.DarkRogue.Entity.Enemy;
import com.gmail.kevinlegros.DarkRogue.Entity.Entity;
import com.gmail.kevinlegros.DarkRogue.Entity.Player;
import com.gmail.kevinlegros.DarkRogue.Sprite.Screen;
import com.gmail.kevinlegros.DarkRogue.Ui.Ui;

public class World {
	public Tile[] tiles;
	public List<Entity> entities = new ArrayList<Entity>();
	public List<Tile> aggroTiles = new ArrayList<Tile>();
	public List<Tile> skillTiles = new ArrayList<Tile>();
	public List<Entity> entitiesAttacked = new ArrayList<Entity>();
	
	public Tile hoveredTile;
	public int hoveredTileID = -1;
	
	public static int WORLD_WIDTH = 64;
	public static int WORLD_HEIGHT = 64;
	public static int TILE_SIZE = 64;
	
	public DarkRogue darkRogue;
	private Screen screen;
	
	public BattleLog battleLog;
	
	public Player player;
	public Ui ui = null;
	
	private int lastDWheel = 0;
	
	public int xScroll;
	public int yScroll;
	
	public boolean canUpdate = false;
	
	public World(DarkRogue darkRogue, Screen screen) {
		this.darkRogue = darkRogue;
		this.screen = screen;
		tiles = new Tile[WORLD_WIDTH * WORLD_HEIGHT];
		int count = 0;
		for(int y = 0; y < WORLD_HEIGHT; y++) {
			for(int x = 0; x < WORLD_WIDTH; x++) {
				Tile tile = new Tile(this, Tile.TILE_DEFAULT, new Vector2f(x, y));
				tile.setRatio(1.0f);
				tile.index = x + y * TILE_SIZE;
				tiles[x + y * TILE_SIZE] = tile;
				count++;
			}
		}
		System.out.println(count+" tiles added");
		player = new Player(this);
		player.spawn(new Vector2f((float) 2 * TILE_SIZE, (float) 2 * TILE_SIZE));
		Random r = new Random();
		for(int i = 0; i < 200; i++) {
			entities.add(new Enemy(this));
			entities.get(i).spawn(new Vector2f((float) r.nextInt(64) * TILE_SIZE, (float) r.nextInt(64) * TILE_SIZE));	
		}
		
		ui = new Ui(player);
		battleLog = new BattleLog();
	}
	
	public void events() {
		if(Mouse.isInsideWindow()) {
			int xmt = (screen.xoff + Mouse.getX()) / TILE_SIZE;
			int ymt = (screen.yoff + (DarkRogue.WHEIGHT - Mouse.getY())) / TILE_SIZE;
			int ind = (int) ((int) (player.position.x / player.w) + ((int) (player.position.y / player.h)) * player.w);
			int indtm = xmt + ymt * TILE_SIZE;
			if(ind > 0) {
				if(ind - 1 == indtm) {
					tiles[ind - 1].hovered = true;
					hoveredTile = tiles[ind - 1];
					hoveredTileID = ind - 1;
				} else if(ind + 1 == indtm) {
					tiles[ind + 1].hovered = true;
					hoveredTile = tiles[ind + 1];
					hoveredTileID = ind + 1;
				} else if(ind - WORLD_WIDTH == indtm) {
					tiles[ind - WORLD_WIDTH].hovered = true;
					hoveredTileID = ind - WORLD_WIDTH;
					hoveredTile = tiles[ind - WORLD_WIDTH];
				} else if(ind + WORLD_WIDTH == indtm) {
					tiles[ind + WORLD_WIDTH].hovered = true;
					hoveredTile = tiles[ind + WORLD_WIDTH];
					hoveredTileID = ind + WORLD_WIDTH;
				}
			}
			while(Mouse.next()) {
				if(Mouse.getEventButton() == 0 && !Mouse.getEventButtonState()) {
					if(ui.mouseIn()) {
						ui.events();
						if(ui.selectedIndex < 0) {
							player.currentActiveSkill = null;
						}
					} else {
						if(ind > 0) {
							if(ui.selectedIndex > -1) {
								Tile tile = tiles[indtm];
								if(skillTiles.contains(tile)) {
									for(int i = 0; i < entities.size(); i++) {
										if(entities.get(i).curTile == tile) {
											player.currentSkillUsed = true;
											// damage
											entitiesAttacked.add(entities.get(i));
											if(player.position.y / TILE_SIZE > tile.position.y) player.turnUp();
											else if(player.position.y / TILE_SIZE < tile.position.y) player.turnDown();
											if(player.position.x / TILE_SIZE > tile.position.x) player.turnLeft();
											else if(player.position.x / TILE_SIZE < tile.position.x) player.turnRight();
										}
									}
								}
							} else {
								Vector2f newpos = new Vector2f();
								if(ind - 1 == indtm) {
									newpos = new Vector2f(player.position.x - TILE_SIZE, player.position.y);
									if(canMoveEntity(player, newpos)) player.move(newpos);
									hoveredTile = null;
								} else if(ind + 1 == indtm) {
									newpos = new Vector2f(player.position.x + TILE_SIZE, player.position.y);
									if(canMoveEntity(player, newpos)) player.move(newpos);
									hoveredTile = null;
								} else if(ind - WORLD_WIDTH == indtm) {
									newpos = new Vector2f(player.position.x, player.position.y - TILE_SIZE);
									if(canMoveEntity(player, newpos)) player.move(newpos);
									hoveredTile = null;
								} else if(ind + WORLD_WIDTH == indtm) {
									newpos = new Vector2f(player.position.x, player.position.y + TILE_SIZE);
									if(canMoveEntity(player, newpos)) player.move(newpos);
									hoveredTile = null;
								}
							}
						}
					}
				}

				if(Mouse.getEventButton() == 1 && !Mouse.getEventButtonState()) {
					if(player.currentActiveSkill != null) {
						player.currentActiveSkill = null;
						ui.unselectSkill();
					}
				}
			}
			int nowWheel = Mouse.getDWheel();
			if(lastDWheel != nowWheel) {
				System.out.println(nowWheel);
			}
		}
		
		while(Keyboard.next()) {
			if(!Keyboard.getEventKeyState()) continue;
			Vector2f newpos;
			switch(Keyboard.getEventKey()) {
				case Keyboard.KEY_ESCAPE:
					darkRogue.mainMenu();
					break;
				case Keyboard.KEY_UP:
					newpos = new Vector2f(player.position.x, player.position.y - TILE_SIZE);
					if(canMoveEntity(player, newpos)) player.move(newpos);
					break;
				case Keyboard.KEY_DOWN:
					newpos = new Vector2f(player.position.x, player.position.y + TILE_SIZE);
					if(canMoveEntity(player, newpos)) player.move(newpos);
					break;
				case Keyboard.KEY_LEFT:
					newpos = new Vector2f(player.position.x - TILE_SIZE, player.position.y);
					if(canMoveEntity(player, newpos)) player.move(newpos);
					break;
				case Keyboard.KEY_RIGHT:
					newpos = new Vector2f(player.position.x + TILE_SIZE, player.position.y);
					if(canMoveEntity(player, newpos)) player.move(newpos);
					break;
				case Keyboard.KEY_1:
					if(player.currentActiveSkill != null && player.currentSkillUsed && !player.currentActiveSkill.animOver) {} else {
						ui.selectSkill(0);
						ui.activateSkill();
					}
					break;
				case Keyboard.KEY_2:
					if(player.currentActiveSkill != null && player.currentSkillUsed && !player.currentActiveSkill.animOver) {} else {
						ui.selectSkill(1);
						ui.activateSkill();
					}
					break;
				case Keyboard.KEY_3:
					if(player.currentActiveSkill != null && player.currentSkillUsed && !player.currentActiveSkill.animOver) {} else {
						ui.selectSkill(2);
						ui.activateSkill();
					}
					break;
				case Keyboard.KEY_4:
					if(player.currentActiveSkill != null && player.currentSkillUsed && !player.currentActiveSkill.animOver) {} else {
						ui.selectSkill(3);
						ui.activateSkill();
					}
					break;
				case Keyboard.KEY_SPACE:
					canUpdate = true;
					break;
			}
		}
	}
	
	public void update() {
		events();
		screen.xoff = (int) (player.position.x) - screen.w / 2;
		screen.yoff = (int) (player.position.y) - screen.h / 2;
		if(screen.xoff < 0) screen.xoff = 0;
		if(screen.yoff < 0) screen.yoff = 0;
		if(screen.xoff + screen.w > WORLD_WIDTH * TILE_SIZE) screen.xoff = WORLD_WIDTH * TILE_SIZE - screen.w;
		if(screen.yoff + screen.h > WORLD_HEIGHT * TILE_SIZE) screen.yoff = WORLD_HEIGHT * TILE_SIZE - screen.h;
		lastDWheel = Mouse.getDWheel();
		player.update();
		for(int y = 0; y < WORLD_HEIGHT; y++) {
			for(int x = 0; x < WORLD_WIDTH; x++) {
				tiles[x + y * TILE_SIZE].update(screen);
			}
		}
		if(hoveredTile != null && !hoveredTile.hovered) hoveredTile = null;
		if(hoveredTile == null && hoveredTileID > -1) {
			tiles[hoveredTileID].hovered = false;
			hoveredTileID = -1;
		}
		
		for(int i = 0; i < aggroTiles.size(); i++) {
			aggroTiles.get(i).hasAggro = false;
		}
		aggroTiles.clear();
		List<Vector2f> vectors;
		int index;
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.update();
			if(e.position.x < screen.xoff || e.position.y < screen.yoff || e.position.x > screen.xoff + screen.w || e.position.y > screen.yoff + screen.h) continue;
			vectors = e.getRangeGrid();
			if(vectors == null) continue;
			for(int j = 0; j < vectors.size(); j++) {
				index = (int) vectors.get(j).x + (int) vectors.get(j).y * TILE_SIZE;
				if(index > 0 && index < WORLD_WIDTH * WORLD_HEIGHT) {
					Tile tile = tiles[index];
					if(!aggroTiles.contains(tile)) aggroTiles.add(tile);
				}
			}
		}
		for(int i = 0; i < aggroTiles.size(); i++) {
			aggroTiles.get(i).hasAggro = true;
		}
		for(int i = 0; i < skillTiles.size(); i++) {
			skillTiles.get(i).isSkillRange = false;
		}
		skillTiles.clear();
		if(player.currentActiveSkill != null) {
			vectors = player.getRangeGrid();
			if(vectors != null) {
				for(int i = 0; i < vectors.size(); i++) {
					index = (int) vectors.get(i).x + (int) vectors.get(i).y * TILE_SIZE;
					if(index > 0 && index < WORLD_WIDTH * WORLD_HEIGHT) {
						Tile tile = tiles[index];
						if(!skillTiles.contains(tile)) skillTiles.add(tile);
					}
				}
			}
			for(int i = 0; i < skillTiles.size(); i++) {
				skillTiles.get(i).isSkillRange = true;
			}
		}
		
		canUpdate = false;
		
		if(player.currentActiveSkill != null) {
			if(player.currentActiveSkill.animOver) {
				for(int i = 0; i < entitiesAttacked.size(); i++) {
					entitiesAttacked.get(i).hurt(player.currentActiveSkill.damage);
				}
				entitiesAttacked.clear();
			}
		}
		
		ui.update();
	}
	
	public void removeEntity(Entity e) {
		entities.remove(e);
	}
	
	public boolean canMoveEntity(Enemy e, Vector2f newpos) {
		return canMoveEntity((Entity) e, newpos);
	}
	
	public boolean canMoveEntity(Entity e, Vector2f newpos) {
		if(!(e instanceof Player)) if(newpos.x == player.position.x && newpos.y == player.position.y) return false;
		for(int i = 0; i < entities.size(); i++) {
			Entity e2 = entities.get(i);
			if(newpos.x == e2.position.x && newpos.y == e2.position.y) return false;
		}
		return true;
	}
	
	public Tile[] getTiles() {
		return tiles;
	}
	
	public Tile getTile(int index) {
		if(index < 0 || index > WORLD_WIDTH * WORLD_HEIGHT - 1) return null;
		return tiles[index];
	}
	
	public void render() {
		xScroll = (int) (player.position.x) - screen.w / 2;
		yScroll = (int) (player.position.y) - screen.h / 2;
		if(xScroll < 0) xScroll = 0;
		if(yScroll < 0) yScroll = 0;
		if(xScroll > WORLD_WIDTH * TILE_SIZE - screen.w) xScroll = WORLD_WIDTH * TILE_SIZE - screen.w;
		if(yScroll > WORLD_HEIGHT * TILE_SIZE - screen.h) yScroll = WORLD_HEIGHT * TILE_SIZE - screen.h;
		int x0 = xScroll / TILE_SIZE;
		int y0 = yScroll / TILE_SIZE;
		int x1 = (xScroll + DarkRogue.WWIDTH) / TILE_SIZE + 1;
		int y1 = (yScroll + DarkRogue.WHEIGHT) / TILE_SIZE + 1;
		if(x1 > WORLD_WIDTH) x1 = WORLD_WIDTH;
		if(y1 > WORLD_HEIGHT) y1 = WORLD_HEIGHT;
		for(int y = y0; y < y1; y++) {
			for(int x = x0; x < x1; x++) {
				tiles[x + y * TILE_SIZE].render(screen);
			}
		}
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if(e.position.x < screen.xoff || e.position.y < screen.yoff || e.position.x > screen.xoff + screen.w || e.position.y > screen.yoff + screen.h) continue;
			e.render(screen);
		}
		
		player.render(screen);
		
		ui.render(screen);
		battleLog.render(screen);
	}
}
