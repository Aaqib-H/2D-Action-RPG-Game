package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_Slime extends Entity{

	public MON_Slime(GamePanel gp) {
		
		super(gp);
		
		type = 2;
		name = "Slime";
		speed = 1;
		maxLife = 4;
		life = maxLife;
		
		hitbox.x = 3;
		hitbox.y = 18;
		hitbox.width = 42;
		hitbox.height = 30;
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;

		getImage();
	}

	public void getImage() {
		
		up1 = setup("/monster/greenslime_down_1");
		up2 = setup("/monster/greenslime_down_2");
		down1 = setup("/monster/greenslime_down_1");
		down2 = setup("/monster/greenslime_down_2");
		left1 = setup("/monster/greenslime_down_1");
		left2 = setup("/monster/greenslime_down_2");
		right1 = setup("/monster/greenslime_down_1");
		right2 = setup("/monster/greenslime_down_2");
	}
	
	public void setAction() {
		
		actionIntervalCounter++;
		
		if(actionIntervalCounter == 120) { // Interval is 120 frames or 2 seconds
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
			actionIntervalCounter = 0;
		}
	}
}
