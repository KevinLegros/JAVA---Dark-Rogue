package com.gmail.kevinlegros.DarkRogue;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;

import com.gmail.kevinlegros.DarkRogue.Sprite.Font;
import com.gmail.kevinlegros.DarkRogue.Sprite.Screen;

public class Menu {
	List<MenuButton> menuButtons = new ArrayList<MenuButton>();
	public static final int MENUBUTTON_CONTINUE = 0;
	public static final int MENUBUTTON_NEWGAME = 1;
	public static final int MENUBUTTON_EXIT = 2;
	
	private int mx;
	private int my;
	
	public Menu(DarkRogue darkRogue) {
		menuButtons.add(new MenuButton(darkRogue, MENUBUTTON_CONTINUE, DarkRogue.WWIDTH / 2, DarkRogue.WHEIGHT / 2 - 20 * 5));
		menuButtons.add(new MenuButton(darkRogue, MENUBUTTON_NEWGAME, DarkRogue.WWIDTH / 2, DarkRogue.WHEIGHT / 2));
		menuButtons.add(new MenuButton(darkRogue, MENUBUTTON_EXIT, DarkRogue.WWIDTH / 2, DarkRogue.WHEIGHT / 2 + 20 * 5));
	}
	
	public void events() {
		while(Mouse.next()) {
			if(Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) {
				for(int i = 0; i < menuButtons.size(); i++) {
					if(menuButtons.get(i).mouseIn(mx, my)) {
						menuButtons.get(i).isClicked = true;
					}
				}
			}
		}
	}
	
	public void update() {
		mx = Mouse.getX();
		my = DarkRogue.WHEIGHT - Mouse.getY();
		events();
		for(int i = 0; i < menuButtons.size(); i++) {
			menuButtons.get(i).update();
		}
	}
	
	public void render(Screen screen) {
		screen.setOffset(0, 0);
		String msg = "DarkRogue";
		Font.render(msg, screen.w / 2 - Font.width(msg) / 2, screen.h / 8 - Font.height(msg) / 2, 0.5f);
		
		for(int i = 0; i < menuButtons.size(); i++) {
			menuButtons.get(i).render(screen, 0.5f);
		}
	}
}
