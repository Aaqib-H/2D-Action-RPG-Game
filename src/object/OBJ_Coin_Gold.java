package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin_Gold extends Entity{

	GamePanel gp;
	
	public OBJ_Coin_Gold(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_pickUp;
		name = "Gold Coin";
		value = 1;
		down1 = setup("/objects/coin_gold", gp.tileSize, gp.tileSize);
	}
	public void use(Entity entity) {
		
		gp.playSE(1);
		gp.ui.addMessage("Coins "+gp.player.coin+" + "+value);
		gp.player.coin += value;
	}
}
