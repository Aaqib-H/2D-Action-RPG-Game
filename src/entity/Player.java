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
	
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	public int hasKey = 0; // Key inventory
	int strideTimer;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = gp.ScreenWidth / 2 - (gp.tileSize/2); // Subtract half a tile size for the actual center of the screen
		screenY = gp.ScreenHeight / 2 - (gp.tileSize/2);
		
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
	}
	public void getPlayerImage() {
		
		up1 = setup("boy_up_1");
		up2 = setup("boy_up_2");
		down1 = setup("boy_down_1");
		down2 = setup("boy_down_2");
		left1 = setup("boy_left_1");
		left2 = setup("boy_left_2");
		right1 = setup("boy_right_1");
		right2 = setup("boy_right_2");
	}
	
	public BufferedImage setup(String imageName) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/"+imageName+".png"));
			image = uTool.scaledImage(image, gp.tileSize, gp.tileSize);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return image;
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
			
			String objName = gp.obj[i].name;
			
			switch (objName) {
			case "Key":
				gp.playSE(1);
				hasKey++;
				gp.obj[i] = null; // Delete object from map
				gp.ui.showMessage("You found a key!");
				break;
				
			case "Door":
				if (hasKey > 0) {
					gp.playSE(3);
					gp.obj[i] = null;
					gp.ui.showMessage("Door unlocked!");
					hasKey--;
				}
				else {
					gp.ui.showMessage("You need a key!");
				}
				break;
			
			case "Boots":
				gp.playSE(2);
				speed += 2;
				gp.obj[i] = null;
				gp.ui.showMessage("Speed Up!");
				break;
			
			case "Chest":
				gp.ui.gameFinished = true;
				gp.stopMusic();
				gp.playSE(4);
				break;
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
