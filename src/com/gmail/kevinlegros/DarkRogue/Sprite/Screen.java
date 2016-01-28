package com.gmail.kevinlegros.DarkRogue.Sprite;

import org.lwjgl.opengl.GL11;

public class Screen {
	public int w;
	public int h;
	
	public int xoff;
	public int yoff;
	
	private int su = 0;
	private int sd = 1;
	private int sl = 0;
	private int sr = 1;
	
	public Screen(int w, int h) {
		this.w = w;
		this.h = h;
		xoff = 0;
		yoff = 0;
	}
	
	public void setOffset(int xoff, int yoff) {
		this.xoff = xoff;
		this.yoff = yoff;
	}
	
	public void render(Sprite sprite, float x, float y, float w, float h, float ratio) {
		render(sprite, x, y, w, h, ratio, 0.0f);
	}
	
	public void render(Sprite sprite, float x, float y, float w, float h, float ratio, float rotation) {
		GL11.glLoadIdentity();
		GL11.glTranslatef(x - xoff, y - yoff, 0);
		GL11.glRotatef(rotation, .0f, .0f, 1.0f);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, sprite.getSpriteId());
		
		GL11.glBegin(GL11.GL_QUADS); {
			// Upper Right
			GL11.glTexCoord2f(sr, su);
			GL11.glVertex2f((w * ratio) / 2, (-h * ratio) / 2);
			// Upper Left
			GL11.glTexCoord2f(sl, su);
			GL11.glVertex2f((-w * ratio) / 2, (-h * ratio) /2);
			// Lower Left
			GL11.glTexCoord2f(sl, sd);
			GL11.glVertex2f((-w * ratio) / 2, (h * ratio) / 2);
			// Lower Right
			GL11.glTexCoord2f(sr, sd);
			GL11.glVertex2f((w * ratio) / 2, (h * ratio) / 2);
		}
		GL11.glEnd();
	}
}
