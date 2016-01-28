package com.gmail.kevinlegros.DarkRogue;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.gmail.kevinlegros.DarkRogue.Sprite.Font;
import com.gmail.kevinlegros.DarkRogue.Sprite.Screen;

public class Intro {
	public boolean introRunning = true;
	public final int introMaxTime = 60 * 3;
	public int introTime = 0;
	
	private DarkRogue darkRogue;
	
	public Intro(DarkRogue darkRogue) {
		this.darkRogue = darkRogue;
	}
	
	public void events() {
		while(Mouse.next()) ;
		if(Keyboard.next()) {
			darkRogue.mainMenu();
		}
	}
	
	public void update() {
		events();
		introTime++;
		if(introTime > introMaxTime) darkRogue.mainMenu();
	}
	
	public void render(Screen screen) {
		screen.setOffset(0, 0);
		String msg = "Menu in " + ((int) ((introMaxTime - introTime) / 60));
		Font.render(msg, screen.w / 2 - Font.width(msg) / 2, screen.h / 2 - Font.height(msg) / 2, 0.5f);
	}
}
