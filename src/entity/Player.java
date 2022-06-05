package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Axe;
import object.OBJ_Fireball;
import object.OBJ_Key;
import object.OBJ_Rock;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;


public class Player extends Entity{

	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	int strideTimer;
	public boolean attackCancel = false;
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int maxInventorySize = 20;
	
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
		getPlayerAttackImage();
		setInventoryItem();
	}
	
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 23; // Starting position
		worldY = gp.tileSize * 21;
//		worldX = gp.tileSize * 12; // Starting position
//		worldY = gp.tileSize * 13;
		speed = 4;
		direction = "right";
		
		// PLAYER STATS
		level = 1;
		maxLife = 6; // 3 hearts
		life = maxLife;
		maxMana = 4;
		mana = maxMana;
		ammo = 10;
		strength = 1;
		dexterity = 1;
		exp = 0;
		nextLvlExp = 5;
		coin = 0;
		currentWeapon = new OBJ_Sword_Normal(gp);
		
		currentShield = new OBJ_Shield_Wood(gp);
		projectile = new OBJ_Fireball(gp);
		//projectile = new OBJ_Rock(gp);
		attack = getAttackVal(); // Total attack value is determined by strength and weapon
		defense = getDefenseVal(); // Total defense value is determined by dexterity and shield
	}
	public void resetPosition() {
		
		worldX = gp.tileSize * 23; // Starting position
		worldY = gp.tileSize * 21;
		direction = "right";
	}
	public void resetLifeAndMana() {
		
		life = maxLife;
		mana = maxMana;
		invincible = false;
	}
	public void setInventoryItem() {
		
		inventory.clear();
		inventory.add(currentWeapon);
		inventory.add(currentShield);
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Axe(gp));
	}
	public int getAttackVal() {
		attackArea = currentWeapon.attackArea;
		return attack = strength * currentWeapon.attackVal;
	}
	public int getDefenseVal() {
		return defense = dexterity * currentShield.defenseVal;
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
		
		if(currentWeapon.type == type_sword) {
			attackUp1 = setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize*2);
			attackUp2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize*2);
			attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize*2);
			attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize*2);
			attackLeft1 = setup("/player/boy_attack_left_1", gp.tileSize*2, gp.tileSize);
			attackLeft2 = setup("/player/boy_attack_left_2", gp.tileSize*2, gp.tileSize);
			attackRight1 = setup("/player/boy_attack_right_1", gp.tileSize*2, gp.tileSize);
			attackRight2 = setup("/player/boy_attack_right_2", gp.tileSize*2, gp.tileSize);
		}
		if(currentWeapon.type == type_axe) {
			attackUp1 = setup("/player/boy_axe_up_1", gp.tileSize, gp.tileSize*2);
			attackUp2 = setup("/player/boy_axe_up_2", gp.tileSize, gp.tileSize*2);
			attackDown1 = setup("/player/boy_axe_down_1", gp.tileSize, gp.tileSize*2);
			attackDown2 = setup("/player/boy_axe_down_2", gp.tileSize, gp.tileSize*2);
			attackLeft1 = setup("/player/boy_axe_left_1", gp.tileSize*2, gp.tileSize);
			attackLeft2 = setup("/player/boy_axe_left_2", gp.tileSize*2, gp.tileSize);
			attackRight1 = setup("/player/boy_axe_right_1", gp.tileSize*2, gp.tileSize);
			attackRight2 = setup("/player/boy_axe_right_2", gp.tileSize*2, gp.tileSize);
		}
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
			
			// CHECK INTERACTIVE TILE COLLISION
			gp.coll.checkEntityCollision(this, gp.iTile);
			
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
			if(keyH.enterPressed == true && attackCancel == false) {
				gp.playSE(7);
				attacking = true;
				spriteTimer = 0;
			}
			
			attackCancel = false;
			gp.keyH.enterPressed = false;
			
			spriteTimer++; // Making the sprite walk
			if (spriteTimer > 12) { // Change player image every 12 frames
				if (spriteNum == 1) {
					spriteNum = 2;
				}
				else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteTimer = 0;
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
		if(gp.keyH.projKeyPressed == true && projectile.alive == false &&
				projShotTimer == 30 && projectile.haveResource(this) == true) {
			
			projectile.shoot(worldX, worldY, direction, true, this);
			
			projectile.depleteResource(this);
			
			gp.projectileList.add(projectile);
			
			projShotTimer = 0;
			
			gp.playSE(11);
		}
		
		if(invincible == true) {
			invincibilityTimer++;
			if(invincibilityTimer > 60) {
				invincible = false;
				invincibilityTimer = 0;
			}
		}
		if(projShotTimer < 30) {
			projShotTimer++;
		}
		if(life > maxLife) {
			life = maxLife;
		}
		if(mana > maxMana) {
			mana = maxMana;
		}
		if(life <= 0) {
			gp.gameState = gp.gameOverState;
			gp.ui.commandNum = -1; // Avoid game over screen wrong input
			gp.stopMusic();
			gp.playSE(13);
		}
	}
	public void attack() {
		
		spriteTimer++;
		
		if(spriteTimer <= 5) { // First 5 frames show image 1
			spriteNum = 1;
		}
		if(spriteTimer > 5 && spriteTimer <= 25) { // Between 6 ~ 25 frames show image 2
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
			damageMonster(monsterIndex, attack);
			
			// Check if player is trying to destroy interactive tile
			int iTileIndex = gp.coll.checkEntityCollision(this, gp.iTile);
			destroyInteractiveTile(iTileIndex);
			
			// After checking collision, restore original data
			worldX = currentWorldX;
			worldY = currentWorldY;
			hitbox.width = hitboxWidth;
			hitbox.height = hitboxHeight;
		}
		if(spriteTimer > 25) {
			spriteNum = 1;
			spriteTimer = 0;
			attacking = false;
		}
	}
	public void pickUpObject(int i) {
		
		String text;
		
		if (i != 999) { // if some object is touched
			
			// PICK UP TYPE ITEMS
			if(gp.obj[gp.currentMap][i].type == type_pickUp) {
				
				gp.obj[gp.currentMap][i].use(this);
				gp.obj[gp.currentMap][i] = null;
			}
			// INVENTORY ITEMS
			else {
				if(inventory.size() != maxInventorySize) { // if inventory not full
					inventory.add(gp.obj[gp.currentMap][i]);
					gp.playSE(1);
					text = gp.obj[gp.currentMap][i].name+" added to inventory!";
				}
				else {
					text = "Inventory full!";
				}
				gp.ui.addMessage(text);
				gp.obj[gp.currentMap][i] = null;
			}
		}		
	}
	public void equipItem() {
		int itemIndex = gp.ui.getInventoryItemIndex();
		
		if(itemIndex < inventory.size()) {
			
			Entity selectedItem = inventory.get(itemIndex);
			
			if(selectedItem.type == type_sword || selectedItem.type == type_axe) {
				gp.playSE(10);
				currentWeapon = selectedItem;
				attack = getAttackVal();
				getPlayerAttackImage(); // Sword or Axe
			}
			if(selectedItem.type == type_shield) {
				gp.playSE(10);
				currentShield = selectedItem;
				defense = getDefenseVal();
			}
			if(selectedItem.type == type_consumable) {
				gp.playSE(10);
				selectedItem.use(this);
				inventory.remove(itemIndex);
			}
		}
	}
	public void interactNPC(int i) {
		if(gp.keyH.enterPressed == true) {
			if (i != 999) { // if a NPC is touched
				attackCancel = true;
				gp.gameState = gp.dialogueState;
				gp.npc[gp.currentMap][i].speak();
			}	
		}
	}
	public void contactMonster(int i) {
		
		if(i != 999) {
			if(invincible == false && gp.monster[gp.currentMap][i].dying == false) {
				gp.playSE(6);
				
				int damage = gp.monster[gp.currentMap][i].attack - defense;
				if(damage < 0) {
					damage = 0;
				}
				life -= damage;
				invincible = true;
			}
		}
	}
	public void damageMonster(int i, int attack) {
		
		if(i != 999) {
			if(gp.monster[gp.currentMap][i].invincible == false) {
				
				gp.playSE(5);
				
				int damage = attack - gp.monster[gp.currentMap][i].defense;
				if(damage < 0) {
					damage = 0;
				}
				gp.monster[gp.currentMap][i].life -= damage;
				gp.ui.addMessage(damage+" damage!");
				gp.monster[gp.currentMap][i].invincible = true;
				gp.monster[gp.currentMap][i].reaction();
				
				if (gp.monster[gp.currentMap][i].life <= 0) {
					gp.monster[gp.currentMap][i].dying = true;
					gp.ui.addMessage("Killed a "+gp.monster[gp.currentMap][i].name+"!");
					gp.ui.addMessage("+"+gp.monster[gp.currentMap][i].exp+" XP!");
					exp += gp.monster[gp.currentMap][i].exp;
					checkLvlUp();
				}
			}
		}	
	}
	public void destroyInteractiveTile(int i) {
		if(i != 999 && gp.iTile[gp.currentMap][i].destructible == true &&
				gp.iTile[gp.currentMap][i].isRightTool(this) == true && gp.iTile[gp.currentMap][i].invincible == false) {
			
			gp.iTile[gp.currentMap][i].playSE();
			gp.iTile[gp.currentMap][i].life--;
			gp.iTile[gp.currentMap][i].invincible = true;
			
			generateParticles(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);
			
			if(gp.iTile[gp.currentMap][i].life == 0) {
				gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedImage();
			}
		}
	}
	public void checkLvlUp() {
		
		if(exp >= nextLvlExp) {
			
			level++;
			exp = exp - nextLvlExp;
			nextLvlExp = nextLvlExp*2;
			maxLife += 1;
			strength++;
			dexterity++;
			attack = getAttackVal();
			defense = getDefenseVal();
			
			gp.playSE(8);
			gp.gameState = gp.dialogueState;
			gp.ui.currentDialogue = "Level Up! \nYou are level "+level+" now! \nYou feel stronger than ever!";
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
