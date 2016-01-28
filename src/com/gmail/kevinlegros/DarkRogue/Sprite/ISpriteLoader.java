package com.gmail.kevinlegros.DarkRogue.Sprite;

public interface ISpriteLoader {
	public abstract void init();
	public abstract Sprite getSprite(int spriteId);
	public abstract Sprite getTile(int spriteId);
	public abstract Sprite getSkill(int skillId);
	public abstract Sprite getSkillIcon(int skillIconId);
	
	public abstract Sprite[] getSpriteAnim(int spriteAnimId);
	public abstract Sprite[] getTileAnim(int tileAnimId);
	public abstract Sprite[] getSkillAnim(int skillAnimId);
	
	public abstract Sprite getFont(int index);
}
