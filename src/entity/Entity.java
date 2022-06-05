package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity { // Abstract Superclass for players. monsters and NPCs
	
	GamePanel gp;
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2; // Used to store images
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
	public BufferedImage image, image2, image3;
	public Rectangle hitbox = new Rectangle(0, 0, 48, 48);
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
	public int hitboxDefaultX, hitboxDefaultY;
	public boolean collision = false;
	String dialogues[] = new String[20];
	
	// STATE
	public int worldX, worldY; // Character position
	public String direction = "down";
	public int spriteNum = 1;
	int dialogueIndex = 0;
	public boolean collisionOn = false;
	public boolean invincible = false;
	public boolean attacking = false;
	public boolean alive = true;
	public boolean dying = false;
	boolean hpBarOn = false;
	
	// TIMERS
	public int spriteTimer = 0;
	public int actionIntervalTimer = 0;
	public int invincibilityTimer = 0;
	public int projShotTimer = 0;
	int dyingTimer = 0;
	int hpBarTimer;
	
	// CHARACTER ATTRIBUTES
	public String name;
	public int speed; // Character speed
	public int maxLife;
	public int life;
	public int maxMana;
	public int mana; // unit of magic
	public int ammo;
	public int level;
	public int strength; // More strength = more damage dealt
	public int dexterity; // More dexterity = less damage received
	public int defense;
	public int attack;
	public int exp;
	public int nextLvlExp;
	public int coin;
	public Entity currentWeapon;
	public Entity currentShield;
	public Projectile projectile;
	// public Entity currentArmor; maybe
	// public Entity currentSword; maybe
	
	// OBJECT TYPE
	public int type; // 0- player, 1- NPC, 2- monster
	public final int type_player = 0;
	public final int type_npc = 1;
	public final int type_monster = 2;
	public final int type_sword = 3;
	public final int type_axe = 4;
	public final int type_shield = 5;
	public final int type_consumable = 6;
	public final int type_pickUp = 7;
	
	// ITEM ATTRIBUTES
	public int value;
	public int attackVal;
	public int defenseVal;
	public String itemDescription = "";
	public int projectileUseCost;
	
	// ENTITY STATUS
	public Entity(GamePanel gp) {
		
		this.gp = gp;
	}
	
	public void setAction() {}
	public void reaction() {}
	public void speak() {
		
		if(dialogues[dialogueIndex] == null) {
			dialogueIndex = 0;
		}
		gp.ui.currentDialogue = dialogues[dialogueIndex];
		dialogueIndex++;
		
		// Make NPC face player when speaking
		switch(gp.player.direction) {
		
		case "up":
			direction = "down";
			break;
		case "down":
			direction = "up";
			break;
		case "left":
			direction = "right";
			break;
		case "right":
			direction = "left";
			break;
		}
	}
	public void use(Entity entity) {}
	public void checkDrop() {}
	public Color getParticleColor() {
		Color color = null;
		return color;
	}
	public int getParticleSize() {
		int size = 0; // 6 pixels
		return size;
	}
	public int getParticleSpeed() {
		int speed = 0; // How fast particles fly
		return speed;
	}
	public int getParticleMaxLife() {
		int maxLife = 0; // How long particles last
		return maxLife;
	}
	public void generateParticles(Entity generator, Entity target) {
		
		Color color = generator.getParticleColor();
		int size = generator.getParticleSize();
		int speed = generator.getParticleSpeed();
		int maxLife = generator.getParticleMaxLife();
		
		Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1); // -1,-1 particle flies towards top-left
		Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1); // 1,-1 particle flies towards top-right
		Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1); // -1,1 particle flies towards down-left
		Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1); // 1,1 particle flies towards down-right
		gp.particleList.add(p1);
		gp.particleList.add(p2);
		gp.particleList.add(p3);
		gp.particleList.add(p4);
	}
	public void dropItem(Entity droppedItem) {
		
		for(int i = 0; i < gp.obj[1].length; i++) {
			if(gp.obj[gp.currentMap][i] == null) {
				gp.obj[gp.currentMap][i] = droppedItem;
				gp.obj[gp.currentMap][i].worldX = worldX; // Location of dead entity
				gp.obj[gp.currentMap][i].worldY = worldY;
				break;
			}
		}
	}
	public void update() {
		
		setAction(); // if subclass has same method, it will take priority
		
		collisionOn = false;
		gp.coll.checkTileCollision(this);
		gp.coll.checkObjectCollision(this, false);
		gp.coll.checkEntityCollision(this, gp.npc);
		gp.coll.checkEntityCollision(this, gp.monster);
		gp.coll.checkEntityCollision(this, gp.iTile);
		boolean contactPlayer = gp.coll.checkPlayerCollision(this);
		
		if(this.type == type_monster && contactPlayer == true) { // if monster
			damagePlayer(attack);
		}
		// IF COLLISION IS FALSE, ENTITY CAN MOVE
		if (collisionOn == false) {
			switch (direction) {
			case "up": worldY -= speed; break;
				
			case "down": worldY += speed; break;
				
			case "left": worldX -= speed; break;
				
			case "right": worldX += speed; break;
			}
				
		}
		
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
		
		if(invincible == true) {
			invincibilityTimer++;
			if(invincibilityTimer > 30) {
				invincible = false;
				invincibilityTimer = 0;
			}
		}
		if(projShotTimer < 30) {
			projShotTimer++; 
		}
	}
	public void damagePlayer(int attack) {
		
		if(gp.player.invincible == false) {
			gp.playSE(6);
			
			int damage = attack - gp.player.defense; 
			if(damage < 0) {
				damage = 0;
			}
			gp.player.life -= damage;
			gp.player.invincible = true;
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
				if (spriteNum == 1) {image = up1;}
				if (spriteNum == 2) {image = up2;}
				break;
				
			case "down":
				if (spriteNum == 1) {image = down1;}
				if (spriteNum == 2) {image = down2;}
				break;
				
			case "left":
				if (spriteNum == 1) {image = left1;}
				if (spriteNum == 2) {image = left2;}
				break;
				
			case "right":
				if (spriteNum == 1) {image = right1;}
				if (spriteNum == 2) {image = right2;}
				break;
			}
			
			
			// Monster HP bar
			if(type == 2 && hpBarOn == true) {
				
				double oneHealthUnit = (double)gp.tileSize/maxLife; // 1 HP unit = bar length/max lives
				double hpBarValue = oneHealthUnit*life; // current HP value = 1 unit x currentLife 
				
				g2.setColor(new Color(35, 35, 35));
				g2.fillRect(screenX-1, screenY-2, gp.tileSize+2, 5); // Outline
				
				g2.setColor(Color.GREEN);
				g2.fillRect(screenX, screenY-1, (int)hpBarValue, 3); // Bar
				
				hpBarTimer++;
				
				if(hpBarTimer > 600) { // After 10 seconds bar disappears
					hpBarTimer = 0;
					hpBarOn = false;
				}
			}
			
			
			// Player invincibility effect
			if(invincible == true) {
				hpBarOn = true;
				hpBarTimer = 0;
				changeAlpha(g2, 0.4F);
			}
			if(dying == true) {
				dyingAnimation(g2);
			}
			
			g2.drawImage(image, screenX, screenY, null);
			
			// Reset transparency
			changeAlpha(g2, 1F);
		}
	}
	public void dyingAnimation(Graphics2D g2) {
		dyingTimer++;
		
		int i = 5;
		
		if (dyingTimer <= i) {changeAlpha(g2, 0F);} // 100% transparent
		if (dyingTimer > i && dyingTimer <= i*2) {changeAlpha(g2, 1F);}  // 0% transparent
		if (dyingTimer > i*2 && dyingTimer <= i*3) {changeAlpha(g2, 0F);}
		if (dyingTimer > i*3 && dyingTimer <= i*4) {changeAlpha(g2, 1F);}
		if (dyingTimer > i*4 && dyingTimer <= i*5) {changeAlpha(g2, 0F);}
		if (dyingTimer > i*5 && dyingTimer <= i*6) {changeAlpha(g2, 1F);}
		if (dyingTimer > i*6 && dyingTimer <= i*7) {changeAlpha(g2, 0F);}
		if (dyingTimer > i*7 && dyingTimer <= i*8) {changeAlpha(g2, 1F);}
		if(dyingTimer > i*8) {
			alive = false;
		}
	}
	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue)); 

	}
	public BufferedImage setup(String imagePath, int width, int height) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
			image = uTool.scaledImage(image, width, height);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	}
}
