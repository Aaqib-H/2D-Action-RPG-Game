package tile_interactive;

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
}
