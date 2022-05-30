package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity { // Abstract Superclass for players. monsters and NPCs
	
	GamePanel gp;
	public int worldX, worldY; // Character position
	public int speed; // Character speed
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2; // Used to store images
	public String direction;
	public int spriteCounter = 0;
	public int spriteNum = 1;
	// Variables for collision
	public Rectangle hitbox = new Rectangle(0, 0, 48, 48);
	public int hitboxDefaultX, hitboxDefaultY;
	public boolean collisionOn = false;
	public int actionIntervalCounter = 0;
	
	public Entity(GamePanel gp) {
		
		this.gp = gp;
	}
	
	public void setAction() {}
	
	public void update() {
		
		setAction(); // if subclass has same method, it will take priority
		
		collisionOn = false;
		gp.coll.checkTileCollision(this);
		gp.coll.checkObjectCollision(this, false);
		gp.coll.checkPlayerCollision(this);
		
		// IF COLLISION IS FALSE, ENTITY CAN MOVE
		if (collisionOn == false) {
			switch (direction) {
			case "up": worldY -= speed; break;
				
			case "down": worldY += speed; break;
				
			case "left": worldX -= speed; break;
				
			case "right": worldX += speed; break;
			}
				
		}
		
		spriteCounter++; // Making the sprite walk
		if (spriteCounter > 12) { // Change player image every 12 frames
			if (spriteNum == 1) {
				spriteNum = 2;
			}
			else if (spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	}
	
	public void draw(Graphics2D g2) {
		
		
		BufferedImage image = null;
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;		
		
		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			
			switch (direction) {
			case "up":
				if (spriteNum == 1) {
					image = up1;
				}
				if (spriteNum == 2) {
					image = up2;
				}
				break;
				
			case "down":
				if (spriteNum == 1) {
					image = down1;
				}
				if (spriteNum == 2) {
					image = down2;
				}
				break;
				
			case "left":
				if (spriteNum == 1) {
					image = left1;
				}
				if (spriteNum == 2) {
					image = left2;
				}
				break;
				
			case "right":
				if (spriteNum == 1) {
					image = right1;
				}
				if (spriteNum == 2) {
					image = right2;
				}
				break;
			}
			
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
	}
	
	public BufferedImage setup(String imagePath) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
			image = uTool.scaledImage(image, gp.tileSize, gp.tileSize);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
