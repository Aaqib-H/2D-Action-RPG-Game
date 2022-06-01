package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
import main.KeyHandler;


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
		
		attackArea.width = 36;
		attackArea.height = 36;
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
	}
	
	
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 23; // Starting position
		worldY = gp.tileSize * 21;
//		worldX = gp.tileSize * 10; // Starting position
//		worldY = gp.tileSize * 13;
		speed = 4;
		direction = "right";
		
		// PLAYER STATUS
		maxLife = 8; // 4 hearts
		life = maxLife;
	}
	public void getPlayerImage() {
		
		up1 = setup("/player/boy_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/boy_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/player/boy_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/player/boy_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/boy_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/player/boy_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/boy_right_2", gp.tileSize, gp.tileSize);
	}
	public void getPlayerAttackImage() {
		
		attackUp1 = setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize*2);
		attackUp2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize*2);
		attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize*2);
		attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize*2);
		attackLeft1 = setup("/player/boy_attack_left_1", gp.tileSize*2, gp.tileSize);
		attackLeft2 = setup("/player/boy_attack_left_2", gp.tileSize*2, gp.tileSize);
		attackRight1 = setup("/player/boy_attack_right_1", gp.tileSize*2, gp.tileSize);
		attackRight2 = setup("/player/boy_attack_right_2", gp.tileSize*2, gp.tileSize);
	}
	
	public void update() {
		
		if(attacking == true) {
			attack();
		}
		
		else if (keyH.upPressed == true || keyH.downPressed == true ||
				keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {
			
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
			
			
			// CHECK MONSTER COLLISION
			int monsterIndex = gp.coll.checkEntityCollision(this, gp.monster);
			contactMonster(monsterIndex);
			
			// CHECK EVENT
			gp.eHandler.checkEvent();
			

			
			// IF COLLISION IS FALSE, PLAYER CAN MOVE
			if (collisionOn == false && keyH.enterPressed == false) {
				switch (direction) {
				case "up": worldY -= speed; break;
					
				case "down": worldY += speed; break;
					
				case "left": worldX -= speed; break;
					
				case "right": worldX += speed; break;
				}
					
			}
			gp.keyH.enterPressed = false;
			
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
		if(invincible == true) {
			invincibleCounter++;
			if(invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
	}
	
	public void attack() {
		
		spriteCounter++;
		
		if(spriteCounter <= 5) { // First 5 frames show image 1
			spriteNum = 1;
		}
		if(spriteCounter > 5 && spriteCounter <= 25) { // Between 6 ~ 25 frames show image 2
			spriteNum = 2;
			
			// Save player position while attacking
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int hitboxWidth = hitbox.width;
			int hitboxHeight = hitbox.height;
			
			// Adjust players position for attackArea
			switch(direction) {
			case "up": worldY -= attackArea.height; break;
			case "down": worldY += attackArea.height; break;
			case "left": worldX -= attackArea.width; break;
			case "right": worldX += attackArea.height; break;
			}
			
			// attackArea becomes hitbox
			hitbox.width = attackArea.width;
			hitbox.height = attackArea.height;
			
			// Check monster collision with updated player position and hitbox
			int monsterIndex = gp.coll.checkEntityCollision(this, gp.monster);
			damageMonster(monsterIndex);
			
			// After checking collision, restore original data
			worldX = currentWorldX;
			worldY = currentWorldY;
			hitbox.width = hitboxWidth;
			hitbox.height = hitboxHeight;
		}
		if(spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}
	public void pickUpObject(int i) {
		
		if (i != 999) { // if some object is touched
			
		}		
	}
	public void interactNPC(int i) {
		if(gp.keyH.enterPressed == true) {
			if (i != 999) { // if a NPC is touched
				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
			}	
			else {
				attacking = true;			
			}
		}
	}
	
	public void contactMonster(int i) {
		
		if(i != 999) {
			if(invincible == false) {
				life -= 1;
				invincible = true;
			}
		}
	}
	public void damageMonster(int i) {
		
		if(i != 999) {
			if(gp.monster[i].invincible == false) {
				gp.monster[i].life -= 1;
				gp.monster[i].invincible = true;
				
				if (gp.monster[i].life <= 0) {
					gp.monster[i] = null;
				}
			}
		}	
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
		switch (direction) {
		case "up":
			if(attacking == false) {
				if (spriteNum == 1) {image = up1;}
				if (spriteNum == 2) {image = up2;}
			}
			if(attacking == true) {
				tempScreenY = screenY - gp.tileSize;
				if (spriteNum == 1) {image = attackUp1;}
				if (spriteNum == 2) {image = attackUp2;}
			}
			break;
			
		case "down":
			if(attacking == false) {
				if (spriteNum == 1) {image = down1;}
				if (spriteNum == 2) {image = down2;}
			}
			if(attacking == true) {
				if (spriteNum == 1) {image = attackDown1;}
				if (spriteNum == 2) {image = attackDown2;}
			}
			break;
			
		case "left":
			if(attacking == false) {
				if (spriteNum == 1) {image = left1;}
				if (spriteNum == 2) {image = left2;}
			}
			if(attacking == true) {
				tempScreenX = screenX - gp.tileSize;
				if (spriteNum == 1) {image = attackLeft1;}
				if (spriteNum == 2) {image = attackLeft2;}
			}
			break;
			
		case "right":
			if(attacking == false) {
				if (spriteNum == 1) {image = right1;}
				if (spriteNum == 2) {image = right2;}
			}
			if(attacking == true) {
				if (spriteNum == 1) {image = attackRight1;}
				if (spriteNum == 2) {image = attackRight2;}
			}
			break;
		}
		
		// Invincibility effect
		if(invincible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5F)); // 50% transparent
		}
		
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		
		// Reset transparency
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F)); // 0% transparent

		
		// DEBUG
//		g2.setFont(new Font("Arial", Font.PLAIN, 26));
//		g2.setColor(Color.WHITE);
//		g2.drawString("Invincible: " + invincibleCounter, 10, 400);
	}
}
