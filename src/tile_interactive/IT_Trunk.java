package tile_interactive;

import main.GamePanel;

public class IT_Trunk extends InteractiveTile {
	
	GamePanel gp;

	public IT_Trunk(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		this.worldX = gp.tileSize*col;
		this.worldY = gp.tileSize*row;
		
		down1 = setup("/tiles_interactive/trunk", gp.tileSize, gp.tileSize);
		
		// Disable hitbox
		hitbox.x = 0;
		hitbox.y = 0;
		hitbox.width = 0;
		hitbox.height = 0;
		hitboxDefaultX = hitbox.x;
		hitboxDefaultY = hitbox.y;
	}
}
