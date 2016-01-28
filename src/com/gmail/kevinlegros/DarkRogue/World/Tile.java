package com.gmail.kevinlegros.DarkRogue.World;

import java.util.Random;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import com.gmail.kevinlegros.DarkRogue.DarkRogue;
import com.gmail.kevinlegros.DarkRogue.Sprite.Screen;
import com.gmail.kevinlegros.DarkRogue.Sprite.Sprite;

public class Tile {
	public static int TILE_DEFAULT = 0;
	public static int TILE_DEFAULT_DARK = 1;
	public static int TILE_DEFAULT_SELECTED = 2;
	
	public static int TILE_AGGRO = 3;
	public static int TILE_AGGRO_N = 4;
	public static int TILE_AGGRO_S = 5;
	public static int TILE_AGGRO_E = 6;
	public static int TILE_AGGRO_W = 7;
	public static int TILE_AGGRO_NW = 8;
	public static int TILE_AGGRO_NE = 9;
	public static int TILE_AGGRO_SW = 10;
	public static int TILE_AGGRO_SE = 11;
	public static int TILE_AGGRO_C = 12;
	public static int TILE_AGGRO_NF = 13;
	public static int TILE_AGGRO_SF = 14;
	public static int TILE_AGGRO_EF = 15;
	public static int TILE_AGGRO_WF = 16;
	
	public static int TILE_SKILL = 17;
	public static int TILE_SKILL_N = 18;
	public static int TILE_SKILL_S = 19;
	public static int TILE_SKILL_E = 20;
	public static int TILE_SKILL_W = 21;
	public static int TILE_SKILL_NW = 22;
	public static int TILE_SKILL_NE = 23;
	public static int TILE_SKILL_SW = 24;
	public static int TILE_SKILL_SE = 25;
	public static int TILE_SKILL_C = 26;
	public static int TILE_SKILL_NF = 27;
	public static int TILE_SKILL_SF = 28;
	public static int TILE_SKILL_EF = 29;
	public static int TILE_SKILL_WF = 30;
	
	public Vector2f position;
	public int index;
	public boolean hovered;

	private World world;
	@SuppressWarnings("unused")
	private int id;
	private Vector2f oPos;
	private float w;
	private float h;
	protected Sprite sprite;
	protected Sprite oldSprite;
	protected boolean hasAggro;
	protected Sprite aggroSprite;
	protected boolean isSkillRange;
	protected Sprite skillSprite;
	
	protected int su = 0;
	protected int sd = 1;
	protected int sl = 0;
	protected int sr = 1;
	
	private float ratio = 1.0f;
	
	public Tile(World world, int id, Vector2f position) {
		this.world = world;
		this.id = id;
		this.position = position;
		this.oPos = position;
		this.w = World.TILE_SIZE * ratio + (World.TILE_SIZE * ratio) / 2;
		this.h = World.TILE_SIZE * ratio + (World.TILE_SIZE * ratio) / 2;
		Random random = new Random();
		if(random.nextInt(100) < 20) {
			this.sprite = DarkRogue.spriteLoader.getTile(TILE_DEFAULT_DARK);
		} else {
			this.sprite = DarkRogue.spriteLoader.getTile(TILE_DEFAULT);
		}
		this.oldSprite = this.sprite;
		this.hovered = false;
		aggroSprite = DarkRogue.spriteLoader.getTile(TILE_AGGRO);
		skillSprite = DarkRogue.spriteLoader.getTile(TILE_SKILL);
	}
	
	public void update(Screen screen) {
		int xmt = (screen.xoff + Mouse.getX()) / World.TILE_SIZE;
		int ymt = (screen.yoff + (DarkRogue.WHEIGHT - Mouse.getY())) / World.TILE_SIZE;
		if(hovered) {
			int ind = (int) ((int) (position.x) + ((int) (position.y)) * w);
			int indtm = xmt + ymt * World.TILE_SIZE;
			if(ind != indtm) hovered = false;
		}
		if(hovered) {
			sprite = DarkRogue.spriteLoader.getTile(TILE_DEFAULT_SELECTED);
		} else {
			sprite = oldSprite;
		}
	}
	
	public void setRatio(float ratio) {
		this.ratio = ratio;
		this.w = World.TILE_SIZE * this.ratio;
		this.h = World.TILE_SIZE * this.ratio;
		this.position.x = oPos.x * ratio;
		this.position.y = oPos.y * ratio;
	}
	
	public void checkAggro() {
		boolean tileAggroN = false;
		boolean tileAggroS = false;
		boolean tileAggroE = false;
		boolean tileAggroW = false;
		if(world.getTile(index - World.WORLD_WIDTH) != null) tileAggroN = world.getTile(index - World.WORLD_WIDTH).hasAggro;
		if(world.getTile(index - 1) != null) tileAggroW = world.getTile(index - 1).hasAggro;
		if(world.getTile(index + 1) != null) tileAggroE = world.getTile(index + 1).hasAggro;
		if(world.getTile(index + World.WORLD_WIDTH) != null) tileAggroS = world.getTile(index + World.WORLD_WIDTH).hasAggro;
		if(!tileAggroN && !tileAggroE && !tileAggroW && tileAggroS) {
			// N
			aggroSprite = DarkRogue.spriteLoader.getTile(TILE_AGGRO_N);
		} else if(tileAggroN && !tileAggroE && !tileAggroW && !tileAggroS) {
			// S
			aggroSprite = DarkRogue.spriteLoader.getTile(TILE_AGGRO_S);
		} else if(!tileAggroN && !tileAggroE && tileAggroW && !tileAggroS) {
			// E
			aggroSprite = DarkRogue.spriteLoader.getTile(TILE_AGGRO_E);
		} else if(!tileAggroN && tileAggroE && !tileAggroW && !tileAggroS) {
			// W
			aggroSprite = DarkRogue.spriteLoader.getTile(TILE_AGGRO_W);
		} else if(!tileAggroN && tileAggroE && tileAggroW && tileAggroS) {
			// N flat
			aggroSprite = DarkRogue.spriteLoader.getTile(TILE_AGGRO_NF);
		} else if(tileAggroN && tileAggroE && tileAggroW && !tileAggroS) {
			// S flat
			aggroSprite = DarkRogue.spriteLoader.getTile(TILE_AGGRO_SF);
		} else if(tileAggroN && !tileAggroE && tileAggroW && tileAggroS) {
			// E flat
			aggroSprite = DarkRogue.spriteLoader.getTile(TILE_AGGRO_EF);
		} else if(tileAggroN && tileAggroE && !tileAggroW && tileAggroS) {
			// W flat
			aggroSprite = DarkRogue.spriteLoader.getTile(TILE_AGGRO_WF);
		} else if(!tileAggroN && !tileAggroE && tileAggroW && tileAggroS) {
			// NE
			aggroSprite = DarkRogue.spriteLoader.getTile(TILE_AGGRO_NE);
		} else if(!tileAggroN && tileAggroE && !tileAggroW && tileAggroS) {
			// NW
			aggroSprite = DarkRogue.spriteLoader.getTile(TILE_AGGRO_NW);
		} else if(tileAggroN && !tileAggroE && tileAggroW && !tileAggroS) {
			// SE
			aggroSprite = DarkRogue.spriteLoader.getTile(TILE_AGGRO_SE);
		} else if(tileAggroN && tileAggroE && !tileAggroW && !tileAggroS) {
			// SW
			aggroSprite = DarkRogue.spriteLoader.getTile(TILE_AGGRO_SW);
		} else {
			aggroSprite = DarkRogue.spriteLoader.getTile(TILE_AGGRO_C);
		}
	}
	
	public void checkSkill() {
		boolean tileSkillN = false;
		boolean tileSkillS = false;
		boolean tileSkillE = false;
		boolean tileSkillW = false;
		if(world.getTile(index - World.WORLD_WIDTH) != null) tileSkillN = world.getTile(index - World.WORLD_WIDTH).isSkillRange;
		if(world.getTile(index - 1) != null) tileSkillW = world.getTile(index - 1).isSkillRange;
		if(world.getTile(index + 1) != null) tileSkillE = world.getTile(index + 1).isSkillRange;
		if(world.getTile(index + World.WORLD_WIDTH) != null) tileSkillS = world.getTile(index + World.WORLD_WIDTH).isSkillRange;
		if(!tileSkillN && !tileSkillE && !tileSkillW && tileSkillS) {
			// N
			skillSprite = DarkRogue.spriteLoader.getTile(TILE_SKILL_N);
		} else if(tileSkillN && !tileSkillE && !tileSkillW && !tileSkillS) {
			// S
			skillSprite = DarkRogue.spriteLoader.getTile(TILE_SKILL_S);
		} else if(!tileSkillN && !tileSkillE && tileSkillW && !tileSkillS) {
			// E
			skillSprite = DarkRogue.spriteLoader.getTile(TILE_SKILL_E);
		} else if(!tileSkillN && tileSkillE && !tileSkillW && !tileSkillS) {
			// W
			skillSprite = DarkRogue.spriteLoader.getTile(TILE_SKILL_W);
		} else if(!tileSkillN && tileSkillE && tileSkillW && tileSkillS) {
			// N flat
			skillSprite = DarkRogue.spriteLoader.getTile(TILE_SKILL_NF);
		} else if(tileSkillN && tileSkillE && tileSkillW && !tileSkillS) {
			// S flat
			skillSprite = DarkRogue.spriteLoader.getTile(TILE_SKILL_SF);
		} else if(tileSkillN && !tileSkillE && tileSkillW && tileSkillS) {
			// E flat
			skillSprite = DarkRogue.spriteLoader.getTile(TILE_SKILL_EF);
		} else if(tileSkillN && tileSkillE && !tileSkillW && tileSkillS) {
			// W flat
			skillSprite = DarkRogue.spriteLoader.getTile(TILE_SKILL_WF);
		} else if(!tileSkillN && !tileSkillE && tileSkillW && tileSkillS) {
			// NE
			skillSprite = DarkRogue.spriteLoader.getTile(TILE_SKILL_NE);
		} else if(!tileSkillN && tileSkillE && !tileSkillW && tileSkillS) {
			// NW
			skillSprite = DarkRogue.spriteLoader.getTile(TILE_SKILL_NW);
		} else if(tileSkillN && !tileSkillE && tileSkillW && !tileSkillS) {
			// SE
			skillSprite = DarkRogue.spriteLoader.getTile(TILE_SKILL_SE);
		} else if(tileSkillN && tileSkillE && !tileSkillW && !tileSkillS) {
			// SW
			skillSprite = DarkRogue.spriteLoader.getTile(TILE_SKILL_SW);
		} else {
			skillSprite = DarkRogue.spriteLoader.getTile(TILE_SKILL_C);
		}
	}
	
	public void render(Screen screen) {
		screen.render(sprite, position.x * (World.TILE_SIZE * ratio) + w / 2, position.y * (World.TILE_SIZE * ratio) + h / 2, w, h, ratio);
		if(hasAggro) {
			checkAggro();
			screen.render(aggroSprite, position.x * (World.TILE_SIZE * ratio) + w / 2, position.y * (World.TILE_SIZE * ratio) + h / 2, w, h, ratio);
		}
		if(isSkillRange) {
			checkSkill();
			screen.render(skillSprite, position.x * (World.TILE_SIZE * ratio) + w / 2, position.y * (World.TILE_SIZE * ratio) + h / 2, w, h, ratio);
		}
	}
}
