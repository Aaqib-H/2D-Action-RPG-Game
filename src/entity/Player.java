package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity{

	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	int strideTimer;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		super(gp); // calling the constructor and passing GamePanel
		
		this.keyH = keyH;
		
		screenX = gp.screenWidth / 2 - (gp.tileSize/2); // Subtract half a tile size for the actual center of the screen
		screenY = gp.screenHeight / 2 - (gp.tileSize/2);
		
		hitbox = new Rectangle(8, 16, 32, 32); // Create a rectangle inside of a player tile
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 23; // Starting position
		worldY = gp.tileSize * 21;
		speed = 4;
		direction = "right";
		
		// PLAYER STATUS
		maxLife = 8; // 4 hearts
		life = maxLife;
	}
	public void getPlayerImage() {
		
		up1 = setup("/player/boy_up_1");
		up2 = setup("/player/boy_up_2");
		down1 = setup("/player/boy_down_1");
		down2 = setup("/player/boy_down_2");
		left1 = setup("/player/boy_left_1");
		left2 = setup("/player/boy_left_2");
		right1 = setup("/player/boy_right_1");
		right2 = setup("/player/boy_right_2");
	}
	
	public void update() {
		
		if (keyH.upPressed == true || keyH.downPressed == true ||
				keyH.leftPressed == true || keyH.rightPressed == true) {
			
			if (keyH.upPressed == true) {
				direction = "up";
			}
			else if (keyH.downPressed == true) {
				direction = "down";
			}
			else if (keyH.leftPressed == true) {
				direction = "left";
			}
			else if (keyH.rightPressed == true) {
				direction = "right";
			}
			
			// CHECK TILE COLLISION
			collisionOn = false;
			gp.coll.checkTileCollision(this); // Pass player class as Entity oblject
			
			
			// CHECK OBJECT COLLISION
			int objIndex = gp.coll.checkObjectCollision(this, true);
			pickUpObject(objIndex);
			
			
			// CHECK NPC COLLISION
			int npcIndex = gp.coll.checkEntityCollision(this, gp.npc);
			interactNPC(npcIndex);
			
			// CHECK EVENT
			gp.eHandler.checkEvent();
			
			gp.keyH.enterPressed = false;
			
			// IF COLLISION IS FALSE, PLAYER CAN MOVE
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
		else {
			
			strideTimer++;
			if (strideTimer == 20) {
				spriteNum = 1;
				strideTimer++;
			}
			spriteNum = 1;
		}
	}
	
	public void pickUpObject(int i) {
		
		if (i != 999) { // if some object is touched
			
		}		
	}
	public void interactNPC(int i) {
		if (i != 999) { // if a NPC is touched
			if(gp.keyH.enterPressed == true) {
				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
			}
		}	
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
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
		g2.drawImage(image, screenX, screenY, null);
		
	}
}
