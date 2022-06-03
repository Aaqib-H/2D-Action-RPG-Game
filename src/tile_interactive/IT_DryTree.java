package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class IT_DryTree extends InteractiveTile {
	
	GamePanel gp;

	public IT_DryTree(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		this.worldX = gp.tileSize*col;
		this.worldY = gp.tileSize*row;
		
		down1 = setup("/tiles_interactive/drytree", gp.tileSize, gp.tileSize);
		destructible = true;
		life = 3; // hits
	}
	public boolean isRightTool(Entity entity) {
		boolean isRightTool = false;
		
		if(entity.currentWeapon.type == type_axe) {
			isRightTool = true;
		}
		
		return isRightTool;
	}
	public void playSE() {
		gp.playSE(12);
	}
	public InteractiveTile getDestroyedImage() {
		InteractiveTile tile = new IT_Trunk(gp, worldX/gp.tileSize, worldY/gp.tileSize);
		return tile;
	}
}
