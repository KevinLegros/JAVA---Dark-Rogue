package com.gmail.kevinlegros.DarkRogue;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

public class InputHandler {
	private DarkRogue dr;
	
	private class Key {
		public boolean down;
		
		public Key() {
			keys.add(this);
			down = false;
		}
	}
	
	private List<Key> keys = new ArrayList<Key>();
	
	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	
	public InputHandler(DarkRogue dr) {
		this.dr = dr;
	}
	
	public void update() {
		while(Keyboard.next()) {
			switch(Keyboard.getEventKey()) {
			case Keyboard.KEY_ESCAPE:
				dr.setRunning(false);
				break;
			case Keyboard.KEY_RIGHT:
				if(right.down) right.down = false;
				else right.down = true;
				break;
			}
		}
	}
}
