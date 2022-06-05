package main;

import entity.Entity;

public class CollisionSystem {
	GamePanel gp;
	
	public CollisionSystem (GamePanel gp) {
		this.gp = gp;
	}
	
	public void checkTileCollision(Entity entity) {
		
		// Coordinates of the hitbox in the world
		int hitboxLeftWorldX = entity.worldX + entity.hitbox.x; // left side of hitbox
		int hitboxRightWorldX = entity.worldX + entity.hitbox.x + entity.hitbox.width; // right side of hitbox
		int hitboxTopWorldY = entity.worldY + entity.hitbox.y; // top side of hitbox
		int hitboxBottomWorldY = entity.worldY + entity.hitbox.y + entity.hitbox.height; // bottom side of hitbox
		//
		
		
		int hitboxLeftCol = hitboxLeftWorldX / gp.tileSize;
		int hitboxRightCol = hitboxRightWorldX / gp.tileSize;
		int hitboxTopRow = hitboxTopWorldY / gp.tileSize;
		int hitboxBottomRow = hitboxBottomWorldY / gp.tileSize;
		
		int hitboxPoint1, hitboxPoint2; // Collision will be checked on two corners of hitbox when moving, in each direction
		
		switch (entity.direction) {
		
		case "up":
			hitboxTopRow = (hitboxTopWorldY - entity.speed) / gp.tileSize;
			hitboxPoint1 = gp.tileM.mapTileNum[gp.currentMap][hitboxLeftCol][hitboxTopRow]; // Top-left corner of hitbox
			hitboxPoint2 = gp.tileM.mapTileNum[gp.currentMap][hitboxRightCol][hitboxTopRow]; // Top-right corner of hitbox
			if (gp.tileM.tile[hitboxPoint1].collision == true || gp.tileM.tile[hitboxPoint2].collision == true) {
				entity.collisionOn = true;
			}
			break;
			
		case "down":
			hitboxBottomRow = (hitboxBottomWorldY + entity.speed) / gp.tileSize;
			hitboxPoint1 = gp.tileM.mapTileNum[gp.currentMap][hitboxLeftCol][hitboxBottomRow]; // Top-left corner of hitbox
			hitboxPoint2 = gp.tileM.mapTileNum[gp.currentMap][hitboxRightCol][hitboxBottomRow]; // Top-right corner of hitbox
			if (gp.tileM.tile[hitboxPoint1].collision == true || gp.tileM.tile[hitboxPoint2].collision == true) {
				entity.collisionOn = true;
			}
			break;
			
		case "left":
			hitboxLeftCol = (hitboxLeftWorldX - entity.speed) / gp.tileSize;
			hitboxPoint1 = gp.tileM.mapTileNum[gp.currentMap][hitboxLeftCol][hitboxTopRow]; // Top-left corner of hitbox
			hitboxPoint2 = gp.tileM.mapTileNum[gp.currentMap][hitboxLeftCol][hitboxBottomRow]; // Top-right corner of hitbox
			if (gp.tileM.tile[hitboxPoint1].collision == true || gp.tileM.tile[hitboxPoint2].collision == true) {
				entity.collisionOn = true;
			}
			break;
			
		case "right":
			hitboxRightCol = (hitboxRightWorldX + entity.speed) / gp.tileSize;
			hitboxPoint1 = gp.tileM.mapTileNum[gp.currentMap][hitboxRightCol][hitboxTopRow]; // Top-left corner of hitbox
			hitboxPoint2 = gp.tileM.mapTileNum[gp.currentMap][hitboxRightCol][hitboxBottomRow]; // Top-right corner of hitbox
			if (gp.tileM.tile[hitboxPoint1].collision == true || gp.tileM.tile[hitboxPoint2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		}
	}
	
	public int checkObjectCollision (Entity entity, boolean player) {
		
		int index = 999; // Variable to store index of the object
		
		for (int i = 0; i < gp.obj[1].length; i++) { // Iterate through objects
			
			if (gp.obj[gp.currentMap][i] != null) {
				
				// Get entity's hitbox position on the map
				entity.hitbox.x = entity.worldX + entity.hitbox.x;
				entity.hitbox.y = entity.worldY + entity.hitbox.y;
				
				// Get the object's hitbox position on the map
				gp.obj[gp.currentMap][i].hitbox.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].hitbox.x;
				gp.obj[gp.currentMap][i].hitbox.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].hitbox.y;
				
				switch (entity.direction) {
				case "up": entity.hitbox.y -= entity.speed; break;
				case "down": entity.hitbox.y += entity.speed; break;
				case "left": entity.hitbox.x -= entity.speed; break;
				case "right": entity.hitbox.x += entity.speed; break;
				}
				if (entity.hitbox.intersects(gp.obj[gp.currentMap][i].hitbox)) { 
					if (gp.obj[gp.currentMap][i].collision == true) { 
						entity.collisionOn = true;
					}
					if (player == true) { 
						index = i;
					}
				}
				
				// Reset before next iteration
				entity.hitbox.x = entity.hitboxDefaultX; 
				entity.hitbox.y = entity.hitboxDefaultY;
				gp.obj[gp.currentMap][i].hitbox.x = gp.obj[gp.currentMap][i].hitboxDefaultX;
				gp.obj[gp.currentMap][i].hitbox.y = gp.obj[gp.currentMap][i].hitboxDefaultY;
			}
		}
		
		return index; // Return the index of the object
	}
	
	// Check if Player is colliding with ENTITY
	public int checkEntityCollision(Entity entity, Entity[][] target) {
		
		int index = 999; // Variable to store index of the object
		
		for (int i = 0; i < target[1].length; i++) { // Iterate through objects
			
			if (target[gp.currentMap][i] != null) {
				
				// Get entity's hitbox position on the map
				entity.hitbox.x = entity.worldX + entity.hitbox.x;
				entity.hitbox.y = entity.worldY + entity.hitbox.y;
				
				// Get the object's hitbox position on the map
				target[gp.currentMap][i].hitbox.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].hitbox.x;
				target[gp.currentMap][i].hitbox.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].hitbox.y;
				
				switch (entity.direction) {
				case "up":entity.hitbox.y -= entity.speed; break;				
				case "down": entity.hitbox.y += entity.speed; break;
				case "left": entity.hitbox.x -= entity.speed; break;			
				case "right": entity.hitbox.x += entity.speed; break;
				}
				
				if (entity.hitbox.intersects(target[gp.currentMap][i].hitbox)) { 
					if(target[gp.currentMap][i] != entity) {
						entity.collisionOn = true;
						index = i;
					}
				}
				
				// Reset before next iteration
				entity.hitbox.x = entity.hitboxDefaultX; 
				entity.hitbox.y = entity.hitboxDefaultY;
				target[gp.currentMap][i].hitbox.x = target[gp.currentMap][i].hitboxDefaultX;
				target[gp.currentMap][i].hitbox.y = target[gp.currentMap][i].hitboxDefaultY;
			}
		}
		
		return index; // Return the index of the object
	}
	
	// Check if Entity is colliding with PLAYER
	public boolean checkPlayerCollision(Entity entity) {
		
		boolean contactPlayer = false;
		
		// Get entity's hitbox position on the map
		entity.hitbox.x = entity.worldX + entity.hitbox.x;
		entity.hitbox.y = entity.worldY + entity.hitbox.y;
		
		// Get the object's hitbox position on the map
		gp.player.hitbox.x = gp.player.worldX + gp.player.hitbox.x;
		gp.player.hitbox.y = gp.player.worldY + gp.player.hitbox.y;
		
		switch (entity.direction) {
		case "up": entity.hitbox.y -= entity.speed; break;			
		case "down": entity.hitbox.y += entity.speed; break;	
		case "left": entity.hitbox.x -= entity.speed; break;
		case "right": entity.hitbox.x += entity.speed; break;
		}
		
		if (entity.hitbox.intersects(gp.player.hitbox)) { // Check if entity and player hitboxes are intersecting
			entity.collisionOn = true;
			contactPlayer = true;
		}
		
		// Reset before next iteration
		entity.hitbox.x = entity.hitboxDefaultX; 
		entity.hitbox.y = entity.hitboxDefaultY;
		gp.player.hitbox.x = gp.player.hitboxDefaultX;
		gp.player.hitbox.y = gp.player.hitboxDefaultY;
		
		return contactPlayer;
	}
}
