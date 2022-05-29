package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity { // Superclass for players. monsters and NPCs
	
	public int worldX, worldY; // Character position
	public int speed; // Character speed
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2; // Used to store images
	public String direction;
	public int spriteCounter = 0;
	public int spriteNum = 1;
	// Variables for collision
	public Rectangle hitbox;
	public int hitboxDefaultX, hitboxDefaultY;
	public boolean collisionOn = false;
}
