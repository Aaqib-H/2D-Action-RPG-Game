package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Elixir_Red extends Entity{
	
	GamePanel gp;
	
	public OBJ_Elixir_Red(GamePanel gp) {
		
		super(gp);
		this.gp = gp;
		
		type = type_consumable;
		name = "Red Elixir";
		value = 5;
		down1 = setup("/objects/Elixir_red", gp.tileSize, gp.tileSize);
		itemDescription = "[ "+name+" ]"+"\nSomething magical.";
		price = 25;
	}
	public void use(Entity entity) {
		
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "You drank the "+name+"! You feel amazing.\n"
				+"Healed "+value+" units of life.";
		entity.life += value;
		gp.playSE(2);
	}
}
