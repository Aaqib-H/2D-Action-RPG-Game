package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity {
	
	public NPC_OldMan(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		
		hitbox.x = 8;
		hitbox.y = 16;
		hitbox.width = 32;
		hitbox.height = 32;
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
		
		getImage();
		setDialogue();
	}
	
	
	public void getImage() {
		
		up1 = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/oldman_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/oldman_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/oldman_right_2", gp.tileSize, gp.tileSize);
	}
	
	public void setDialogue() {
		
		dialogues[0] = "Ayyy boy!";
		dialogues[1] = "So you've come to this island to find the treasure?";
		dialogues[2] = "I used to be a great wizard but now... I'm a bit too old \nfor an adventure.";
		dialogues[3] = "Well, good luck to you.";

	}
	
	public void setAction() { // Set character behavior / AI
		
		// Go to location
		if(onPath == true) {
			
			
			// Go to specific location
//			int goalCol = 12;
//			int goalRow = 9;
			
			//Follow player
			int goalCol = (gp.player.worldX + gp.player.hitbox.x) / gp.tileSize;
			int goalRow = (gp.player.worldY + gp.player.hitbox.y) / gp.tileSize;
			
			searchPath(goalCol, goalRow);
		}
		else {
			actionIntervalTimer++;
			
			if(actionIntervalTimer == 120) { // Interval is 120 frames or 2 seconds
				Random rnd = new Random();
				
				int i = rnd.nextInt(100)+1; // Get random number from 1-100
				
				// Randomize movement
				if(i <= 25) {
					direction = "up";
				}
				if(i > 25 && i <= 50) {
					direction = "down";
				}
				if(i > 50 && i <= 75) {
					direction = "left";
				}
				if(i > 75 && i <= 100 ) {
					direction = "right";
				}
				actionIntervalTimer = 0;
			}
		}
	}
	
	public void speak() {
		
		super.speak();
		
		onPath = true;
	}
}
