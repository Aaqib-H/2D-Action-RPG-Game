package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity{
	public OBJ_Sword_Normal(GamePanel gp) {
		
		super(gp);
		
		type = type_sword;
		name = "Normal Sword";
		down1 = setup("/objects/sword_normal", gp.tileSize, gp.tileSize);
		attackVal = 1;
		knockbackPower = 2;
		attackArea.width = 36; // Attack range
		attackArea.height = 36;
		itemDescription = "[ "+name+" ]"+"\nAn old but trusty sword.";
		price = 25;
	}

}
