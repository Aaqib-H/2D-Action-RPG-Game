package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_New extends Entity{
	public OBJ_Shield_New(GamePanel gp) {
		
		super(gp);
		
		type = type_shield;
		name = "New Wooden Shield";
		down1 = setup("/objects/shield_new", gp.tileSize, gp.tileSize);
		defenseVal = 2;
		itemDescription = "[ "+name+" ]"+"\nA shiny new shield.";
		price = 50;
	}
}
