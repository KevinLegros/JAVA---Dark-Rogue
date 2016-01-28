package com.gmail.kevinlegros.DarkRogue;

import com.gmail.kevinlegros.DarkRogue.Sprite.Font;
import com.gmail.kevinlegros.DarkRogue.Sprite.Screen;

public class MenuButton {
	public int type;
	public boolean isClicked = false;
	
	private int x;
	private int y;
	private int w;
	private int h;
	private String msg;
	private DarkRogue darkRogue;
	
	public MenuButton(DarkRogue darkRogue, int type, int x, int y) {
		this.darkRogue = darkRogue;
		this.type = type;
		
		switch(type) {
			case Menu.MENUBUTTON_CONTINUE:
				msg = "Continue";
				break;
			case Menu.MENUBUTTON_NEWGAME:
				msg = "New Game";
				break;
			case Menu.MENUBUTTON_EXIT:
				msg = "Exit";
				break;
			default:
				break;
		}
		
		this.x = x - Font.width(msg);
		this.y = y - Font.height(msg);
		this.w = Font.width(msg) * 2;
		this.h = Font.height(msg) * 2;
	}
	
	public void update() {
		if(!isClicked) return;
		switch(type) {
			case Menu.MENUBUTTON_CONTINUE:
				darkRogue.menu = null;
				darkRogue.startGame();
				break;
			case Menu.MENUBUTTON_NEWGAME:
				darkRogue.menu = null;
				darkRogue.startGame();
				break;
			case Menu.MENUBUTTON_EXIT:
				darkRogue.setRunning(false);
				break;
			default:
				break;
		}
	}
	
	public boolean mouseIn(int mx, int my) {
		return !(mx < x || my < y || mx > x + w || my > y + h);
	}
	
	public void render(Screen screen, float ratio) {
		Font.render(msg, x + Font.width(msg) / 2, y + Font.height(msg) / 2, ratio);
	}
}
