package object;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Fireball extends Projectile{
	
	GamePanel gp;
	
	public OBJ_Fireball(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Fireball";
		speed = 5;
		maxLife = 80; // Acts as range
		life = maxLife;
		attack = 2;
		projectileUseCost = 1;
		alive = false;
		getImage();
	}
	public void getImage() {
		up1 = setup("/projectile/fireball_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/projectile/fireball_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/projectile/fireball_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/projectile/fireball_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/projectile/fireball_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/projectile/fireball_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/projectile/fireball_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/projectile/fireball_right_2", gp.tileSize, gp.tileSize);
	}
	public boolean haveResource(Entity user) {
		
		boolean haveResource = false;
		if(user.mana >= projectileUseCost) {
			haveResource = true;
		}
		return haveResource;
	}
	public void depleteResource(Entity user) {
		user.mana -= projectileUseCost;
	}
	public Color getParticleColor() {
		Color color = new Color(240, 50, 0);
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
