package entity;

import main.GamePanel;

public class Projectile extends Entity{
	
	Entity user;
	
	public Projectile(GamePanel gp) {
		super(gp);
	}
	public void shoot(int worldX, int worldY, String direction, boolean alive, Entity user) {
		this.worldX = worldX;
		this.worldY = worldY;
		this.direction = direction;
		this.alive = alive;
		this.user = user;
		this.life = this.maxLife; // Reset everytime you shoot
	}
	public void update() {
		
		if(user == gp.player) {
			int monsterIndex = gp.coll.checkEntityCollision(this, gp.monster);
			if(monsterIndex !=999) {
				gp.player.damageMonster(monsterIndex, attack);
				generateParticles(user.projectile, gp.monster[monsterIndex]);
				alive = false; // Projectile disappears after collision;
			}
		}
		if(user != gp.player) {
		boolean contactPlayer = gp.coll.checkPlayerCollision(this);
			if(gp.player.invincible == false && contactPlayer == true) {
				damagePlayer(attack);
				generateParticles(user.projectile, gp.player);
				alive = false;
			}

		}
		
		switch(direction) {
		case "up": worldY -= speed; break;
		case "down": worldY += speed; break;
		case "left": worldX -= speed; break;
		case "right": worldX += speed; break;
		}
		
		life--; // Projectile will gradually lose it's life every frame for life = 80 ie. 80 frames;
		if(life <= 0) {
			alive = false;
		}
		
		spriteTimer++;
		if(spriteTimer > 12) {
			if(spriteNum == 1) {
				spriteNum = 2;
			}
			else if(spriteNum == 2) {
				spriteNum = 1;
			}
			spriteTimer = 0;
		}
	}
	public boolean haveResource(Entity user) {
		boolean haveResource = false;
		return haveResource;
	}
	public void depleteResource(Entity user) {}
}
