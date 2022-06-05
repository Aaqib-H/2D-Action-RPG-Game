package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {
	
	public OBJ_Axe(GamePanel gp) {
		
		super(gp);
		
		type = type_axe;
		name = "Lumberjack's Axe";
		down1 = setup("/objects/axe", gp.tileSize, gp.tileSize);
		attackVal = 2;
		knockbackPower = 10;
		attackArea.width = 30; // Attack range
		attackArea.height = 30;
		itemDescription = "[ "+name+" ]"+"\nNot only used for chopping \nwood.";
		price = 50;
	}
	
}
