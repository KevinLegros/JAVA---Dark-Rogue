package com.gmail.kevinlegros.DarkRogue.World;

import com.gmail.kevinlegros.DarkRogue.DarkRogue;
import com.gmail.kevinlegros.DarkRogue.Sprite.Font;
import com.gmail.kevinlegros.DarkRogue.Sprite.Screen;

public class BattleLog {
	
	public String[] messages = new String[10];
	public int x = DarkRogue.WWIDTH - 300;
	public int y = DarkRogue.WHEIGHT - 250;
	
	public BattleLog() {
		for(int i = 0; i < messages.length; i++) {
			messages[i] = "";
		}
	}
	
	public void update() {
		
	}
	
	public void addMessage(String msg) {
		for(int i = 9; i > 0; i--) {
			messages[i] = messages[i-1];
		}
		
		messages[0] = msg;
	}
	
	public void render(Screen screen) {
		for(int i = 0; i < messages.length; i++) {
			if(messages[i] != "") Font.render(messages[i], x, y + 200 - (i * 20), 0.5f);
		}
	}
}
