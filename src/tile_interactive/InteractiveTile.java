package tile_interactive;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;


public class InteractiveTile extends Entity {
	
	GamePanel gp;
	public boolean destructible = false;
	
	public InteractiveTile(GamePanel gp, int col, int row) {
		super(gp);
		this.gp = gp;
	}
	public boolean isRightTool(Entity entity) {
		boolean isRightTool = false;
		return isRightTool;
	}
	public void playSE() {
		
	}
	public InteractiveTile getDestroyedImage() {
		InteractiveTile tile = null;
		return tile;
	}
	public void update() {
		if(invincible == true) {
			invincibilityTimer++;
			if(invincibilityTimer > 20) {
				invincible = false;
				invincibilityTimer = 0;
			}
		}
	}
	public void draw(Graphics2D g2) {
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;		
		
		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			
			g2.drawImage(down1, screenX, screenY, null);
		}
	}
}
