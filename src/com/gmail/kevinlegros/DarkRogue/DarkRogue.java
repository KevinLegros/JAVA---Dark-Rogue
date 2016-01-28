package com.gmail.kevinlegros.DarkRogue;


import java.io.File;

import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.gmail.kevinlegros.DarkRogue.Sprite.ISpriteLoader;
import com.gmail.kevinlegros.DarkRogue.Sprite.Screen;
import com.gmail.kevinlegros.DarkRogue.Sprite.SpriteLoader;
import com.gmail.kevinlegros.DarkRogue.World.World;

/**
 * TODO LIST
 * @author klegros
 * PRIORITY
 * X LWJGL
 * X PLAYER
 * - BASIC TERRAIN TILES
 * - BOSS DRAGON
 * - BASIC ENEMIES
 * SECONDARY
 * - MAGIC SCHOOLS
 * - SKILLS
 * - ACHIEVEMENTS
 * - LOOTS
 * - SKILLPOINTS
 * - EXP
 */

public class DarkRogue implements Runnable {
	private static final double FRAMERATE = 60.0;
	public static int WWIDTH = 1024;
	public static int WHEIGHT = 768;
	
	private boolean running = false;
	@SuppressWarnings("unused")
	private int fps = 0;
	private String title = "DarkRogue";
	
	public static ISpriteLoader spriteLoader = null;
	
	public Intro intro = null;
	public Menu menu = null;

	private Screen screen;
	private World world;
	
	public DarkRogue() {
		System.setProperty("org.lwjgl.librarypath", new File(new File(System.getProperty("user.dir"), "native"), LWJGLUtil.getPlatformName()).getAbsolutePath());
		System.setProperty("net.java.games.input.librarypath", System.getProperty("org.lwjgl.librarypath"));
		
		running = true;
		init();
	}

	@Override
	public void run() {
		long last = System.nanoTime();
		double unproc = 0;
		double nanoPerTick = 1000000000 / FRAMERATE;
		int frames = 0;
		int ticks = 0;
		long timer = Sys.getTime();
		
		while(running && !Display.isCloseRequested()) {
			long now = System.nanoTime();
			unproc += (now - last) / nanoPerTick;
			last = now;
			boolean shouldRender = false;
			
			while(unproc >= 1) {
				ticks++;
				update();
				render();
				Display.update();
				unproc -= 1;
				shouldRender = true;
			}
			
			Display.sync((int) FRAMERATE);
			
			if(shouldRender) frames++;
			
			if(Sys.getTime() - timer > 1000) {
				timer += 1000;
				Display.setTitle(title + " FPS: " + frames + " Updates: " + ticks);
				fps = frames;
				frames = 0;
				ticks = 0;
			}
		}
		Display.destroy();
	}
	
	public void update() {
		if(intro != null) {
			intro.update();
		} else if(menu != null) {
			menu.update();
		} else if(world != null){
			world.update();
		}
	}
	
	public void render() {
		// Clear screen and depth buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		
		if(intro != null) {
			intro.render(screen);
		} else if(menu != null) {
			menu.render(screen);
		} else if(world != null){
			world.render();
		}
	}
	
	public void init() {
		try {
			Display.setDisplayMode(new DisplayMode(WWIDTH, WHEIGHT));
			Display.setTitle(title);
			Display.setFullscreen(false);
			Display.create();
			Display.setVSyncEnabled(false);
		} catch(LWJGLException e) {
			e.printStackTrace();
		}
		initGL();
		spriteLoader = new SpriteLoader();
		spriteLoader.init();
		screen = new Screen(WWIDTH, WHEIGHT);

		intro();
	}
	
	public void initGL() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		GL11.glViewport(0, 0, WWIDTH, WHEIGHT);
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, WWIDTH, WHEIGHT, 0, -1, 1);
	}
	
	public void startGame() {
		intro = null;
		menu = null;
		world = new World(this, screen);
	}
	
	public void intro() {
		menu = null;
		world = null;
		intro = new Intro(this);
	}
	
	public void mainMenu() {
		intro = null;
		world = null;
		menu = new Menu(this);
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DarkRogue dr = new DarkRogue();
		dr.run();
	}

}
