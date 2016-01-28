package com.gmail.kevinlegros.DarkRogue.Sprite;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import com.gmail.kevinlegros.DarkRogue.Entity.IEntity;
import com.gmail.kevinlegros.DarkRogue.Skill.Skill;
import com.gmail.kevinlegros.DarkRogue.Ui.SkillIcon;
import com.gmail.kevinlegros.DarkRogue.World.Tile;

public class SpriteLoader implements ISpriteLoader {
	
	private static Sprite[] sprites = new Sprite[1024];
	private static Sprite[] tiles = new Sprite[64];
	private static Sprite[] skills = new Sprite[1024];
	private static Sprite[] skillIcons = new Sprite[1024];
	private static Sprite[] fonts = new Sprite[512];
	private static Sprite[][] spriteAnims = new Sprite[1024][1024];
	private static Sprite[][] tileAnims = new Sprite[1024][1024];
	private static Sprite[][] skillAnims = new Sprite[1024][1024];
	
	private Hashtable<String, BufferedImage> imageCache = new Hashtable<String, BufferedImage>();
	
	public SpriteLoader() {
	}
	
	public void init() {
		sprites[IEntity.PLAYER] = loadSprite("/garbagecollector01.png", 0, 0, 64, 64);
		sprites[IEntity.ENEMY] = loadSprite("/garbagecollector01.png", 0, 0, 64, 64);
		
		sprites[IEntity.PLAYER_ARMORED_IDLE_BODY_S] = loadSprite("/static.dss", 64 * 0, 64 * 1, 64, 64);
		sprites[IEntity.PLAYER_ARMORED_IDLE_HEAD_S] = loadSprite("/static.dss", 64 * 0, 64 * 0, 64, 64);
		sprites[IEntity.PLAYER_ARMORED_IDLE_BODY_N] = loadSprite("/static.dss", 64 * 1, 64 * 1, 64, 64);
		sprites[IEntity.PLAYER_ARMORED_IDLE_HEAD_N] = loadSprite("/static.dss", 64 * 1, 64 * 0, 64, 64);
		sprites[IEntity.PLAYER_ARMORED_IDLE_BODY_E] = loadSprite("/static.dss", 64 * 2, 64 * 1, 64, 64);
		sprites[IEntity.PLAYER_ARMORED_IDLE_HEAD_E] = loadSprite("/static.dss", 64 * 2, 64 * 0, 64, 64);
		sprites[IEntity.PLAYER_ARMORED_IDLE_BODY_W] = loadSprite("/static.dss", 64 * 3, 64 * 1, 64, 64);
		sprites[IEntity.PLAYER_ARMORED_IDLE_HEAD_W] = loadSprite("/static.dss", 64 * 3, 64 * 0, 64, 64);
		sprites[IEntity.RAT_IDLE_S] = loadSprite("/static.dss", 64 * 0, 64 * 2, 64, 64);
		sprites[IEntity.RAT_IDLE_N] = loadSprite("/static.dss", 64 * 1, 64 * 2, 64, 64);
		sprites[IEntity.RAT_IDLE_E] = loadSprite("/static.dss", 64 * 2, 64 * 2, 64, 64);
		sprites[IEntity.RAT_IDLE_W] = loadSprite("/static.dss", 64 * 3, 64 * 2, 64, 64);
		sprites[IEntity.RAT_IDLE_HOV_S] = loadSprite("/static.dss", 64 * 0, 64 * 3, 64, 64);
		sprites[IEntity.RAT_IDLE_HOV_N] = loadSprite("/static.dss", 64 * 1, 64 * 3, 64, 64);
		sprites[IEntity.RAT_IDLE_HOV_E] = loadSprite("/static.dss", 64 * 2, 64 * 3, 64, 64);
		sprites[IEntity.RAT_IDLE_HOV_W] = loadSprite("/static.dss", 64 * 3, 64 * 3, 64, 64);
		
		tiles[Tile.TILE_DEFAULT] = loadSprite("/default_tile.png", 0, 0, 64, 64);
		tiles[Tile.TILE_DEFAULT_DARK] = loadSprite("/default_tile.png", 64, 0, 64, 64);
		tiles[Tile.TILE_DEFAULT_SELECTED] = loadSprite("/default_tile.png", 128, 0, 64, 64);
		
		tiles[Tile.TILE_AGGRO] = loadSprite("/sights.png", 0, 0, 64, 64);
		tiles[Tile.TILE_AGGRO_C] = loadSprite("/sights.png", 64, 0, 64, 64);
		tiles[Tile.TILE_AGGRO_S] = loadSprite("/sights.png", 64 * 0, 64 * 1, 64, 64);
		tiles[Tile.TILE_AGGRO_N] = loadSprite("/sights.png", 64 * 1, 64 * 1, 64, 64);
		tiles[Tile.TILE_AGGRO_W] = loadSprite("/sights.png", 64 * 2, 64 * 1, 64, 64);
		tiles[Tile.TILE_AGGRO_E] = loadSprite("/sights.png", 64 * 3, 64 * 1, 64, 64);
		tiles[Tile.TILE_AGGRO_NW] = loadSprite("/sights.png", 64 * 0, 64 * 2, 64, 64);
		tiles[Tile.TILE_AGGRO_NE] = loadSprite("/sights.png", 64 * 1, 64 * 2, 64, 64);
		tiles[Tile.TILE_AGGRO_SW] = loadSprite("/sights.png", 64 * 2, 64 * 2, 64, 64);
		tiles[Tile.TILE_AGGRO_SE] = loadSprite("/sights.png", 64 * 3, 64 * 2, 64, 64);
		tiles[Tile.TILE_AGGRO_SF] = loadSprite("/sights.png", 64 * 0, 64 * 3, 64, 64);
		tiles[Tile.TILE_AGGRO_NF] = loadSprite("/sights.png", 64 * 1, 64 * 3, 64, 64);
		tiles[Tile.TILE_AGGRO_WF] = loadSprite("/sights.png", 64 * 2, 64 * 3, 64, 64);
		tiles[Tile.TILE_AGGRO_EF] = loadSprite("/sights.png", 64 * 3, 64 * 3, 64, 64);
		
		tiles[Tile.TILE_SKILL] = loadSprite("/sights.png", 0, 64 * 4, 64, 64);
		tiles[Tile.TILE_SKILL_C] = loadSprite("/sights.png", 64, 64 * 4, 64, 64);
		tiles[Tile.TILE_SKILL_S] = loadSprite("/sights.png", 64 * 0, 64 * 5, 64, 64);
		tiles[Tile.TILE_SKILL_N] = loadSprite("/sights.png", 64 * 1, 64 * 5, 64, 64);
		tiles[Tile.TILE_SKILL_W] = loadSprite("/sights.png", 64 * 2, 64 * 5, 64, 64);
		tiles[Tile.TILE_SKILL_E] = loadSprite("/sights.png", 64 * 3, 64 * 5, 64, 64);
		tiles[Tile.TILE_SKILL_NW] = loadSprite("/sights.png", 64 * 0, 64 * 6, 64, 64);
		tiles[Tile.TILE_SKILL_NE] = loadSprite("/sights.png", 64 * 1, 64 * 6, 64, 64);
		tiles[Tile.TILE_SKILL_SW] = loadSprite("/sights.png", 64 * 2, 64 * 6, 64, 64);
		tiles[Tile.TILE_SKILL_SE] = loadSprite("/sights.png", 64 * 3, 64 * 6, 64, 64);
		tiles[Tile.TILE_SKILL_SF] = loadSprite("/sights.png", 64 * 0, 64 * 7, 64, 64);
		tiles[Tile.TILE_SKILL_NF] = loadSprite("/sights.png", 64 * 1, 64 * 7, 64, 64);
		tiles[Tile.TILE_SKILL_WF] = loadSprite("/sights.png", 64 * 2, 64 * 7, 64, 64);
		tiles[Tile.TILE_SKILL_EF] = loadSprite("/sights.png", 64 * 3, 64 * 7, 64, 64);
		
		skillIcons[SkillIcon.SKILLICON_DEFAULT] = loadSprite("/skillsheet.png", 0, 0, 64, 64);
		skillIcons[SkillIcon.SKILLICON_FLAMESPEAR] = loadSprite("/skillsheet.png", 0, 0, 64, 64);
		skillIcons[SkillIcon.SKILLICON_FLAMESPEAR_HOVER] = loadSprite("/skillsheet.png", 64, 0, 64, 64);
		skillIcons[SkillIcon.SKILLICON_CRESCENTSLASH] = loadSprite("/skillsheet.png", 0, 64 * 1, 64, 64);
		skillIcons[SkillIcon.SKILLICON_CRESCENTSLASH_HOVER] = loadSprite("/skillsheet.png", 64, 64 * 1, 64, 64);
		
		skillAnims[Skill.SKILLANIM_CRESCENTSLASH_S] = loadAnim("/skillsheet.png", 5, 1, 64, 64, 64 * 2, 64 * 1);
		skillAnims[Skill.SKILLANIM_CRESCENTSLASH_N] = loadAnim("/skillsheet.png", 5, 1, 64, 64, 64 * 2, 64 * 2);
		skillAnims[Skill.SKILLANIM_CRESCENTSLASH_W] = loadAnim("/skillsheet.png", 5, 1, 64, 64, 64 * 2, 64 * 3);
		skillAnims[Skill.SKILLANIM_CRESCENTSLASH_E] = loadAnim("/skillsheet.png", 5, 1, 64, 64, 64 * 2, 64 * 4);
		skillAnims[Skill.SKILLANIM_FEROCIOUSCLAW] = loadAnim("/skillsheet.png", 5, 1, 64, 64, 64 * 2, 64 * 5);
		
		loadFont("/font.png", 26, 16, 10, 18);
	}
	
	private Sprite loadSprite(String path, int x, int y, int w, int h) {
		BufferedImage bi = null;
		try {
			if(imageCache.get(path) != null) bi = (BufferedImage) imageCache.get(path);
			else {
				System.out.println("Loading image " + path);
				bi = ImageIO.read(this.getClass().getResource(path));
				
				byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
				switch(bi.getType()) {
					case BufferedImage.TYPE_4BYTE_ABGR: convertARGBtoBGRA(data); break;
					case BufferedImage.TYPE_3BYTE_BGR: convertBGRtoRGB(data); break;
					default: System.out.println("unknown bufferedimage type");
				}
				imageCache.put(path, bi);
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		int bytesPerPixel = bi.getColorModel().getPixelSize() / 8;
		
		ByteBuffer scratch = ByteBuffer.allocateDirect(w * h * bytesPerPixel).order(ByteOrder.nativeOrder());
		DataBufferByte data = ((DataBufferByte) bi.getRaster().getDataBuffer());
		
		for(int i = 0; i < h; i++) {
			scratch.put(data.getData(), (x + (y + i) * bi.getWidth()) * bytesPerPixel, w * bytesPerPixel);
		}
		scratch.rewind();
		
		IntBuffer buffer = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer();
		GL11.glGenTextures(buffer);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, buffer.get(0));
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		switch(bi.getType()) {
			case BufferedImage.TYPE_4BYTE_ABGR:
				GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, w, h, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, scratch);
				break;
			case BufferedImage.TYPE_3BYTE_BGR:
				GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, w, h, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, scratch);
				break;
			default:
				if(bi.getColorModel().hasAlpha()) {
					GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, w, h, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, scratch);
				} else {
					GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, w, h, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, scratch);
				}
				System.out.println("could not render unknown type sprite "+bi.getType());
		}
		
		Sprite sprite = new Sprite();
		sprite.setSpriteId(buffer.get(0));
		sprite.setSpriteWidth(w);
		sprite.setSpriteHeight(h);
		return sprite;
	}
	
	private Sprite[] loadAnim(String path, int cols, int rows, int w, int h) {
		return loadAnim(path, cols, rows, w, h, 0, 0);
	}
	
	private Sprite[] loadAnim(String path, int cols, int rows, int w, int h, int xo, int yo) {
		Sprite[] ret = new Sprite[cols * rows];
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				ret[i * cols + j] = loadSprite(path, j * w + xo, i * h + yo, w, h);
			}
		}
		return ret;
	}
	
	private void loadFont(String path, int cols, int rows, int w, int h) {
		fonts = new Sprite[cols * rows];
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				fonts[i * cols + j] = loadSprite(path, j * w, i * h, w, h);
			}
		}
	}
	
	private static void convertARGBtoBGRA(final byte[] data) {
		for(int i = 0; i < data.length; i += 4) {
			data[i] ^= data[i + 3];
			data[i + 3] ^= data[i];
			data[i] ^= data[i + 3];
			
			data[i + 1] ^= data[i + 2];
			data[i + 2] ^= data[i + 1];
			data[i + 1] ^= data[i + 2];
		}
		System.out.println("image converted ARGB to BGRA");
	}
	
	private static void convertBGRtoRGB(final byte[] data) {
		for(int i = 0; i < data.length; i += 3) {
			data[i] ^= data[i + 2];
			data[i + 2] ^= data[i];
			data[i] ^= data[i + 2];
		}
		System.out.println("image converted BGR to RGB");
	}
	
	public Sprite getSprite(int spriteId) {
		return sprites[spriteId];
	}
	
	public Sprite getTile(int spriteId) {
		return tiles[spriteId];
	}
	
	public Sprite getSkill(int skillId) {
		return skills[skillId];
	}
	
	public Sprite getSkillIcon(int skillIconId) {
		return skillIcons[skillIconId];
	}
	
	public Sprite[] getSpriteAnim(int spriteAnimId) {
		return spriteAnims[spriteAnimId];
	}
	
	public Sprite[] getTileAnim(int tileAnimId) {
		return tileAnims[tileAnimId];
	}
	
	public Sprite[] getSkillAnim(int skillAnimId) {
		return skillAnims[skillAnimId];
	}
	
	public Sprite getFont(int index) {
		return fonts[index];
	}
}
