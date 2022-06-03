package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Gold;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

public class MON_Slime extends Entity{

	GamePanel gp;
	
	public MON_Slime(GamePanel gp) {
		
		super(gp);
		this.gp = gp;
		
		type = type_monster;
		name = "Slime";
		speed = 1;
		maxLife = 4;
		life = maxLife;
		attack = 2;
		defense = 0;
		exp = 2;
		projectile = new OBJ_Rock(gp);
		
		hitbox.x = 3;
		hitbox.y = 18;
		hitbox.width = 42;
		hitbox.height = 30;
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;

		getImage();
	}

	public void getImage() {
		
		up1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
	}
	
	public void setAction() {
		
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
		int i = new Random().nextInt(100)+1;
		if(i > 99 && projectile.alive == false && projShotTimer == 30) {
			projectile.shoot(worldX, worldY, direction, true, this);
			gp.projectileList.add(projectile);
			projShotTimer = 0;
		}
	}
	public void reaction(){
		
		actionIntervalTimer = 0;
		
		// Slime moves away from player
		direction = gp.player.direction;
	}
	public void checkDrop() {
		
		int i = new Random().nextInt(100)+1;
		
		// SET ITEM DROP
		if(i < 50) { // 50%
			dropItem(new OBJ_Coin_Gold(gp));
		}
		if(i >= 50 && i < 75) { // 25%
			dropItem(new OBJ_Heart(gp));
		}
		if(i >= 75 && i < 100) { // 25%
			dropItem(new OBJ_ManaCrystal(gp));
		}
	}
}
