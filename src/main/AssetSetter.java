package main;

import entity.NPC_OldMan;
import monster.MON_Slime;
import object.OBJ_Axe;
import object.OBJ_Coin_Gold;
import object.OBJ_Door;
import object.OBJ_Elixir_Red;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_ManaCrystal;
import object.OBJ_Shield_New;
import tile_interactive.IT_DryTree;


public class AssetSetter {
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		int i = 0;
		gp.obj[i] = new OBJ_Coin_Gold(gp);
		gp.obj[i].worldX = gp.tileSize*25;
		gp.obj[i].worldY = gp.tileSize*19;
		i++;
		gp.obj[i] = new OBJ_Coin_Gold(gp);
		gp.obj[i].worldX = gp.tileSize*21;
		gp.obj[i].worldY = gp.tileSize*19;
		i++;
		gp.obj[i] = new OBJ_Coin_Gold(gp);
		gp.obj[i].worldX = gp.tileSize*21;
		gp.obj[i].worldY = gp.tileSize*21;
		i++;
		gp.obj[i] = new OBJ_Axe(gp);
		gp.obj[i].worldX = gp.tileSize*33;
		gp.obj[i].worldY = gp.tileSize*21;
		i++;
		gp.obj[i] = new OBJ_Shield_New(gp);
		gp.obj[i].worldX = gp.tileSize*34;
		gp.obj[i].worldY = gp.tileSize*21;
		i++;
		gp.obj[i] = new OBJ_Elixir_Red(gp);
		gp.obj[i].worldX = gp.tileSize*30;
		gp.obj[i].worldY = gp.tileSize*21;
		i++;
		gp.obj[i] = new OBJ_Elixir_Red(gp);
		gp.obj[i].worldX = gp.tileSize*31;
		gp.obj[i].worldY = gp.tileSize*21;
		i++;
		gp.obj[i] = new OBJ_Heart(gp);
		gp.obj[i].worldX = gp.tileSize*29;
		gp.obj[i].worldY = gp.tileSize*21;
		i++;
		gp.obj[i] = new OBJ_Heart(gp);
		gp.obj[i].worldX = gp.tileSize*28;
		gp.obj[i].worldY = gp.tileSize*21;
		i++;
		gp.obj[i] = new OBJ_ManaCrystal(gp);
		gp.obj[i].worldX = gp.tileSize*27;
		gp.obj[i].worldY = gp.tileSize*21;
		i++;
		gp.obj[i] = new OBJ_ManaCrystal(gp);
		gp.obj[i].worldX = gp.tileSize*26;
		gp.obj[i].worldY = gp.tileSize*21;
	}
	
	public void setNPC() {
		
		gp.npc[0] = new NPC_OldMan(gp);
		gp.npc[0].worldX = gp.tileSize*21; // Spawn location
		gp.npc[0].worldY = gp.tileSize*21;
		
//		gp.npc[0] = new NPC_OldMan(gp);
//		gp.npc[0].worldX = gp.tileSize*9; // Spawn location
//		gp.npc[0].worldY = gp.tileSize*10;


	}
	
	public void setMonster() {
		
		int i = 0;
		gp.monster[i] = new MON_Slime(gp);
		gp.monster[i].worldX = gp.tileSize*23;
		gp.monster[i].worldY = gp.tileSize*36;
		i++;
		gp.monster[i] = new MON_Slime(gp);
		gp.monster[i].worldX = gp.tileSize*23;
		gp.monster[i].worldY = gp.tileSize*37;
		i++;
		gp.monster[i] = new MON_Slime(gp);
		gp.monster[i].worldX = gp.tileSize*23;
		gp.monster[i].worldY = gp.tileSize*38;
		i++;
		gp.monster[i] = new MON_Slime(gp);
		gp.monster[i].worldX = gp.tileSize*23;
		gp.monster[i].worldY = gp.tileSize*39;
		i++;
		gp.monster[i] = new MON_Slime(gp);
		gp.monster[i].worldX = gp.tileSize*23;
		gp.monster[i].worldY = gp.tileSize*38;
		i++;
		gp.monster[i] = new MON_Slime(gp);
		gp.monster[i].worldX = gp.tileSize*35;
		gp.monster[i].worldY = gp.tileSize*39;
		i++;
		gp.monster[i] = new MON_Slime(gp);
		gp.monster[i].worldX = gp.tileSize*36;
		gp.monster[i].worldY = gp.tileSize*38;
		i++;
		gp.monster[i] = new MON_Slime(gp);
		gp.monster[i].worldX = gp.tileSize*35;
		gp.monster[i].worldY = gp.tileSize*38;
		
		
//		gp.monster[0] = new MON_Slime(gp);
//		gp.monster[0].worldX = gp.tileSize*11;
//		gp.monster[0].worldY = gp.tileSize*10;
//		
//		gp.monster[1] = new MON_Slime(gp);
//		gp.monster[1].worldX = gp.tileSize*11;
//		gp.monster[1].worldY = gp.tileSize*11;
	}
	public void setInteractiveTile() {
		
		int i = 0;
		
		gp.iTile[i] = new IT_DryTree(gp, 27, 12);i++;
		gp.iTile[i] = new IT_DryTree(gp, 28, 12);i++;
		gp.iTile[i] = new IT_DryTree(gp, 29, 12);i++;
		gp.iTile[i] = new IT_DryTree(gp, 30, 12);i++;
		gp.iTile[i] = new IT_DryTree(gp, 31, 12);i++;
		gp.iTile[i] = new IT_DryTree(gp, 32, 12);i++;
		gp.iTile[i] = new IT_DryTree(gp, 33, 12);i++;
	}
}
