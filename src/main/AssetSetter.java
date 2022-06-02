package main;

import entity.NPC_OldMan;
import monster.MON_Slime;
import object.OBJ_Axe;
import object.OBJ_Door;
import object.OBJ_Elixir_Red;
import object.OBJ_Key;
import object.OBJ_Shield_New;


public class AssetSetter {
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		int i = 0;
		gp.obj[i] = new OBJ_Key(gp);
		gp.obj[i].worldX = gp.tileSize*25;
		gp.obj[i].worldY = gp.tileSize*19;
		i++;
		gp.obj[i] = new OBJ_Key(gp);
		gp.obj[i].worldX = gp.tileSize*21;
		gp.obj[i].worldY = gp.tileSize*19;
		i++;
		gp.obj[i] = new OBJ_Key(gp);
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
		
		
//		gp.monster[0] = new MON_Slime(gp);
//		gp.monster[0].worldX = gp.tileSize*11;
//		gp.monster[0].worldY = gp.tileSize*10;
//		
//		gp.monster[1] = new MON_Slime(gp);
//		gp.monster[1].worldX = gp.tileSize*11;
//		gp.monster[1].worldY = gp.tileSize*11;
	}
}
