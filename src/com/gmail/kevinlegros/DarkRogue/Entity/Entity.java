package com.gmail.kevinlegros.DarkRogue.Entity;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import com.gmail.kevinlegros.DarkRogue.DarkRogue;
import com.gmail.kevinlegros.DarkRogue.Sprite.*;
import com.gmail.kevinlegros.DarkRogue.World.Tile;
import com.gmail.kevinlegros.DarkRogue.World.World;

public abstract class Entity implements IEntity {

	public Vector2f position = new Vector2f();
	
	public int type;
	public int ow;
	public int oh;
	public float w;
	public float h;
	public Tile curTile;
	public List<Vector2f> inRange = new ArrayList<Vector2f>();
	public int aggroRange;
	public int range;
	
	protected Sprite sprite;
	protected World world;
	protected float su = 0;
	protected float sd = 1;
	protected float sl = 0;
	protected float sr = 1;
	protected float rotation = 0;
	
	protected float ratio = 1.0f;
	
	public int orientation;
	public static final int ORIENTATION_N = 0;
	public static final int ORIENTATION_S = 1;
	public static final int ORIENTATION_E = 2;
	public static final int ORIENTATION_W = 3;
	
	public EntityStats stats;
	
	public String name;
	
	protected void init(World world) {
		this.world = world;
		this.sprite = DarkRogue.spriteLoader.getSprite(this.type);
		this.ow = sprite.getSpriteWidth();
		this.oh = sprite.getSpriteHeight();
		this.w = ow * ratio;
		this.h = oh * ratio;
	}
	
	public void setRatio(float ratio) {
		this.ratio = ratio;
		this.w = ow * this.ratio;
		this.h = oh * this.ratio;
	}
	
	public float getRatio() {
		return ratio;
	}
	
	public void spawn(Vector2f position) {
		this.position.x = position.x + w / 2;
		this.position.y = position.y + h / 2;
		curTile = world.tiles[((int) position.x / World.TILE_SIZE) + ((int) position.y / World.TILE_SIZE) * World.TILE_SIZE];
	}
	
	public int hurt(int damage) {
		return 0;
	}
	
	public void update() {
	}
	public void render(Screen screen) {
	}
	
	public void updateRangeGrid() {
		inRange.clear();
		int minRangeY = (int) (position.y / World.TILE_SIZE) - aggroRange;
		int maxRangeY = (int) (position.y / World.TILE_SIZE) + aggroRange;
		int xr = -aggroRange;
		for(int y = minRangeY; y <= maxRangeY; y++) {
			int minRangeX = (int) (position.x / World.TILE_SIZE) - (aggroRange - Math.abs(xr));
			int maxRangeX = (int) (position.x / World.TILE_SIZE) + (aggroRange - Math.abs(xr));
			for(int x = minRangeX; x <= maxRangeX; x++) {
				if(x < 0 || y < 0) continue;
				inRange.add(new Vector2f(x, y));
			}
			xr++;
		}
	}
	
	public List<Vector2f> getRangeGrid() {
		return inRange;
	}
}
