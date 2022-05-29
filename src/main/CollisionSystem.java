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
			hitboxPoint1 = gp.tileM.mapTileNum[hitboxLeftCol][hitboxTopRow]; // Top-left corner of hitbox
			hitboxPoint2 = gp.tileM.mapTileNum[hitboxRightCol][hitboxTopRow]; // Top-right corner of hitbox
			if (gp.tileM.tile[hitboxPoint1].collision == true || gp.tileM.tile[hitboxPoint2].collision == true) {
				entity.collisionOn = true;
			}
			break;
			
		case "down":
			hitboxBottomRow = (hitboxBottomWorldY + entity.speed) / gp.tileSize;
			hitboxPoint1 = gp.tileM.mapTileNum[hitboxLeftCol][hitboxBottomRow]; // Top-left corner of hitbox
			hitboxPoint2 = gp.tileM.mapTileNum[hitboxRightCol][hitboxBottomRow]; // Top-right corner of hitbox
			if (gp.tileM.tile[hitboxPoint1].collision == true || gp.tileM.tile[hitboxPoint2].collision == true) {
				entity.collisionOn = true;
			}
			break;
			
		case "left":
			hitboxLeftCol = (hitboxLeftWorldX - entity.speed) / gp.tileSize;
			hitboxPoint1 = gp.tileM.mapTileNum[hitboxLeftCol][hitboxTopRow]; // Top-left corner of hitbox
			hitboxPoint2 = gp.tileM.mapTileNum[hitboxLeftCol][hitboxBottomRow]; // Top-right corner of hitbox
			if (gp.tileM.tile[hitboxPoint1].collision == true || gp.tileM.tile[hitboxPoint2].collision == true) {
				entity.collisionOn = true;
			}
			break;
			
		case "right":
			hitboxRightCol = (hitboxRightWorldX + entity.speed) / gp.tileSize;
			hitboxPoint1 = gp.tileM.mapTileNum[hitboxRightCol][hitboxTopRow]; // Top-left corner of hitbox
			hitboxPoint2 = gp.tileM.mapTileNum[hitboxRightCol][hitboxBottomRow]; // Top-right corner of hitbox
			if (gp.tileM.tile[hitboxPoint1].collision == true || gp.tileM.tile[hitboxPoint2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		}
	}
	
	public int checkObjectCollision (Entity entity, boolean player) {
		
		int index = 999; // Variable to store index of the object
		
		for (int i = 0; i < gp.obj.length; i++) { // Iterate through objects
			
			if (gp.obj[i] != null) {
				
				// Get entity's hitbox position on the map
				entity.hitbox.x = entity.worldX + entity.hitbox.x;
				entity.hitbox.y = entity.worldY + entity.hitbox.y;
				
				// Get the object's hitbox position on the map
				gp.obj[i].hitbox.x = gp.obj[i].worldX + gp.obj[i].hitbox.x;
				gp.obj[i].hitbox.y = gp.obj[i].worldY + gp.obj[i].hitbox.y;
				
				switch (entity.direction) {
				case "up":
					entity.hitbox.y -= entity.speed;
					
					if (entity.hitbox.intersects(gp.obj[i].hitbox)) { // Check if entity and player hitboxes are intersecting
						if (gp.obj[i].collision == true) { // if object is solid
							entity.collisionOn = true;
						}
						if (player == true) { // Check if it is player who is colliding with object
							index = i;
						}
					}
					break;
					
				case "down":
					entity.hitbox.y += entity.speed;
					
					if (entity.hitbox.intersects(gp.obj[i].hitbox)) { // Check if entity and player hitboxes are intersecting
						if (gp.obj[i].collision == true) { 
							entity.collisionOn = true;
						}
						if (player == true) { 
							index = i;
						}
					}
					break;
					
				case "left":
					entity.hitbox.x -= entity.speed;
					
					if (entity.hitbox.intersects(gp.obj[i].hitbox)) { // Check if entity and player hitboxes are intersecting
						if (gp.obj[i].collision == true) { 
							entity.collisionOn = true;
						}
						if (player == true) { 
							index = i;
						}
					}
					break;
					
				case "right":
					entity.hitbox.x += entity.speed;
					
					if (entity.hitbox.intersects(gp.obj[i].hitbox)) { // Check if entity and player hitboxes are intersecting
						if (gp.obj[i].collision == true) { 
							entity.collisionOn = true;
						}
						if (player == true) { 
							index = i;
						}
					}
					break;
				}
				// Reset before next iteration
				entity.hitbox.x = entity.hitboxDefaultX; 
				entity.hitbox.y = entity.hitboxDefaultY;
				gp.obj[i].hitbox.x = gp.obj[i].hitboxDefaultX;
				gp.obj[i].hitbox.y = gp.obj[i].hitboxDefaultY;
			}
		}
		
		return index; // Return the index of the object
	}
}
