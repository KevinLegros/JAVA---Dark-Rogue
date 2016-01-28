package com.gmail.kevinlegros.DarkRogue.Sprite;

import org.lwjgl.opengl.GL11;

import com.gmail.kevinlegros.DarkRogue.DarkRogue;

public class Font {

	private static int w = 10;
	private static int h = 18;
	
	private static String chars = "" +
			"abcdefghijklmnopqrstuvwxyz" +
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
			"0123456789                " +
			".:,; ' \" (!?) +-*/=[]     ";
	
	public static void render(String msg, int x, int y, float ratio) {
		for(int i = 0; i < msg.length(); i++) {
			int pos = chars.indexOf(msg.charAt(i));
			renderChar((int) (x + i * (20 * ratio)), y, (int) pos % 26, (int) pos / 26, ratio, 0);
		}
	}
	
	public static void renderRed(String msg, int x, int y, float ratio) {
		for(int i = 0; i < msg.length(); i++) {
			int pos = chars.indexOf(msg.charAt(i));
			renderChar((int) (x + i * (20 * ratio)) + 1, y + 1, (int) pos % 26, (int) pos / 26, ratio, 26 * 4);
			renderChar((int) (x + i * (20 * ratio)), y, (int) pos % 26, (int) pos / 26, ratio, 26 * 8);
		}
	}
	
	public static void renderBlack(String msg, int x, int y, float ratio) {
		for(int i = 0; i < msg.length(); i++) {
			int pos = chars.indexOf(msg.charAt(i));
			renderChar((int) (x + i * (20 * ratio)), y, (int) pos % 26, (int) pos / 26, ratio, 26 * 4);
		}
	}
	
	public static void renderChar(int x, int y, int xs, int ys, float ratio, int color) {
		GL11.glLoadIdentity();
		GL11.glTranslatef(x, y, 0);

		Sprite sprite = DarkRogue.spriteLoader.getFont((xs + ys * 26) + color);
				
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, sprite.getSpriteId());
		
		GL11.glBegin(GL11.GL_QUADS); {
			// Upper Right
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(w * ratio, -h * ratio);
			// Upper Left
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(-w * ratio, -h * ratio);
			// Lower Left
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(-w * ratio, h * ratio);
			// Lower Right
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(w * ratio, h * ratio);
		}
		GL11.glEnd();
	}
	
	public static int width(String msg) {
		return (int) (msg.length() * w);
	}
	
	public static int height(String msg) {
		return (int) (h);
	}
}
