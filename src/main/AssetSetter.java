package main;

import entity.NPC_Merchant;
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
		
		int mapNum = 0;
		int i = 0;
		gp.obj[mapNum][i] = new OBJ_Coin_Gold(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*25;
		gp.obj[mapNum][i].worldY = gp.tileSize*19;
		i++;
		gp.obj[mapNum][i] = new OBJ_Coin_Gold(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*21;
		gp.obj[mapNum][i].worldY = gp.tileSize*19;
		i++;
		gp.obj[mapNum][i] = new OBJ_Coin_Gold(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*21;
		gp.obj[mapNum][i].worldY = gp.tileSize*21;
		i++;
		gp.obj[mapNum][i] = new OBJ_Axe(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*33;
		gp.obj[mapNum][i].worldY = gp.tileSize*21;
		i++;
		gp.obj[mapNum][i] = new OBJ_Shield_New(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*34;
		gp.obj[mapNum][i].worldY = gp.tileSize*21;
		i++;
		gp.obj[mapNum][i] = new OBJ_Elixir_Red(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*30;
		gp.obj[mapNum][i].worldY = gp.tileSize*21;
		i++;
		gp.obj[mapNum][i] = new OBJ_Elixir_Red(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*31;
		gp.obj[mapNum][i].worldY = gp.tileSize*21;
		i++;
		gp.obj[mapNum][i] = new OBJ_Heart(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*29;
		gp.obj[mapNum][i].worldY = gp.tileSize*21;
		i++;
		gp.obj[mapNum][i] = new OBJ_Heart(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*28;
		gp.obj[mapNum][i].worldY = gp.tileSize*21;
		i++;
		gp.obj[mapNum][i] = new OBJ_ManaCrystal(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*27;
		gp.obj[mapNum][i].worldY = gp.tileSize*21;
		i++;
		gp.obj[mapNum][i] = new OBJ_ManaCrystal(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*26;
		gp.obj[mapNum][i].worldY = gp.tileSize*21;
	}
	public void setNPC() {
		
		// GREEN FIELDS
		int mapNum = 0;
		int i = 0;
		gp.npc[mapNum][i] = new NPC_OldMan(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize*21; // Spawn location
		gp.npc[mapNum][i].worldY = gp.tileSize*21;
		i++;
		
		// HUT
		mapNum = 1;
		i = 0;
		gp.npc[mapNum][i] = new NPC_Merchant(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize*12; // Spawn location
		gp.npc[mapNum][i].worldY = gp.tileSize*7;
		i++;

	}
	public void setMonster() {
		
		int mapNum = 0;
		int i = 0;
		gp.monster[mapNum][i] = new MON_Slime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*23;
		gp.monster[mapNum][i].worldY = gp.tileSize*36;
		i++;
		gp.monster[mapNum][i] = new MON_Slime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*23;
		gp.monster[mapNum][i].worldY = gp.tileSize*37;
		i++;
		gp.monster[mapNum][i] = new MON_Slime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*23;
		gp.monster[mapNum][i].worldY = gp.tileSize*38;
		i++;
		gp.monster[mapNum][i] = new MON_Slime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*23;
		gp.monster[mapNum][i].worldY = gp.tileSize*39;
		i++;
		gp.monster[mapNum][i] = new MON_Slime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*23;
		gp.monster[mapNum][i].worldY = gp.tileSize*38;
		i++;
		gp.monster[mapNum][i] = new MON_Slime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*35;
		gp.monster[mapNum][i].worldY = gp.tileSize*39;
		i++;
		gp.monster[mapNum][i] = new MON_Slime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*36;
		gp.monster[mapNum][i].worldY = gp.tileSize*38;
		i++;
		gp.monster[mapNum][i] = new MON_Slime(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*35;
		gp.monster[mapNum][i].worldY = gp.tileSize*38;
		
		
//		gp.monster[0] = new MON_Slime(gp);
//		gp.monster[0].worldX = gp.tileSize*11;
//		gp.monster[0].worldY = gp.tileSize*10;
//		
//		gp.monster[1] = new MON_Slime(gp);
//		gp.monster[1].worldX = gp.tileSize*11;
//		gp.monster[1].worldY = gp.tileSize*11;
	}
	public void setInteractiveTile() {
		
		int mapNum = 0;
		int i = 0;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 27, 12);i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 28, 12);i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 29, 12);i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 30, 12);i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 31, 12);i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 32, 12);i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 33, 12);i++;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 31, 21);i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 18, 40);i++;
//		gp.iTile[mapNum][i] = new IT_DryTree(gp, 17, 40);i++;
//		gp.iTile[mapNum][i] = new IT_DryTree(gp, 16, 40);i++;
//		gp.iTile[mapNum][i] = new IT_DryTree(gp, 15, 40);i++;
//		gp.iTile[mapNum][i] = new IT_DryTree(gp, 14, 40);i++;
//		gp.iTile[mapNum][i] = new IT_DryTree(gp, 13, 40);i++;
//		gp.iTile[mapNum][i] = new IT_DryTree(gp, 13, 41);i++;
//		gp.iTile[mapNum][i] = new IT_DryTree(gp, 12, 41);i++;
//		gp.iTile[mapNum][i] = new IT_DryTree(gp, 11, 41);i++;
//		gp.iTile[mapNum][i] = new IT_DryTree(gp, 10, 41);i++;
//		gp.iTile[mapNum][i] = new IT_DryTree(gp, 10, 40);i++;
		
	}
}
