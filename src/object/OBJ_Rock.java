package object;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Rock extends Projectile {

	GamePanel gp;
	
	public OBJ_Rock(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Rock";
		speed = 5;
		maxLife = 80; // Acts as range
		life = maxLife;
		attack = 2;
		projectileUseCost = 1;
		alive = false;
		getImage();
	}
	public void getImage() {
		up1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		down1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		left1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		right1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
	}
	public boolean haveResource(Entity user) {
		
		boolean haveResource = false;
		if(user.ammo >= projectileUseCost) {
			haveResource = true;
		}
		return haveResource;
	}
	public void depleteResource(Entity user) {
		user.ammo -= projectileUseCost;
	}
	public Color getParticleColor() {
		Color color = new Color(40, 50, 0);
		return color;
	}
	public int getParticleSize() {
		int size = 4; // 4 pixels
		return size;
	}
	public int getParticleSpeed() {
		int speed = 1; // How fast particles fly
		return speed;
	}
	public int getParticleMaxLife() {
		int maxLife = 20; // How long particles last
		return maxLife;
	}
}
