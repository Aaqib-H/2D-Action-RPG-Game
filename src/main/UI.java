package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import entity.Entity;
import object.OBJ_Coin_Gold;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	Font maruMonica;
	BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank, coin;
	public boolean gameFinished = false;
	public boolean messageOn = false;
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageTimer = new ArrayList<>();
	public String currentDialogue = "";
	public int commandNum = 0;
	public int playerSlotCol = 0;
	public int playerSlotRow = 0;
	public int npcSlotCol = 0;
	public int npcSlotRow = 0;
	int subState = 0;
	int timer = 0;
	public Entity npc;
	
	public UI (GamePanel gp) {
		
		this.gp = gp;
		
		InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
		try {
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// HUD
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		
		Entity crystal = new OBJ_ManaCrystal(gp);
		crystal_full = crystal.image;
		crystal_blank = crystal.image2;
		
		Entity GoldCoin = new OBJ_Coin_Gold(gp);
		coin = GoldCoin.down1;
	}
	public void addMessage(String text) {
		
		message.add(text);
		messageTimer.add(0);
	}
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(maruMonica);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.WHITE);
		
		// TITLE STATE
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		
		
		// PLAY STATE
		if(gp.gameState == gp.playState) {
			drawPlayerLife();
			drawMessage();
		}
		// PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPauseScreen();
		}
		
		// DIALOGUE STATE
		if(gp.gameState == gp.dialogueState) {
			drawPlayerLife();
			drawDialogueScreen();
		}
		
		// CHARACTER STATE
		if(gp.gameState == gp.characterState) {
			drawCharacterScreen();
			drawInventoryScreen(gp.player, true);
		}
		
		// OPTION STATE
		if(gp.gameState == gp.optionState) {
			drawOptionScreen();
		}
		
		// GAME OVER STATE
		if(gp.gameState == gp.gameOverState) {
			drawGameOverScreen();
		}
		// TRANSITION STATE
		if(gp.gameState == gp.transitionState) {
			drawTransition();
		}
		// TRADE STATE
		if(gp.gameState == gp.tradeState) {
			drawTradeScreen();
		}
	}
	public void drawPlayerLife() {
		
		int x = gp.tileSize/2;
		int y = gp.tileSize/3;
		int i = 0;
		
		// DRAW MAX LIFE
		while(i < gp.player.maxLife/2) { // 1 heart 2 lives
			g2.drawImage(heart_blank, x, y, null);
			i++;
			x += gp.tileSize;
		}
		
		// Reset
		x = gp.tileSize/2;
		y = gp.tileSize/3;
		i = 0;
		
		// DRAW CURRENT LIFE
		while(i < gp.player.life) { // 1 heart 2 lives
			g2.drawImage(heart_half, x, y, null);
			i++;
			if(i < gp.player.life) {
				g2.drawImage(heart_full, x, y, null);
			}
			i++;
			x += gp.tileSize;
		}
		
		
		
		// DRAW MAX MANA
		x = gp.tileSize/2;
		y = (int) (gp.tileSize*1.5);
		i = 0;
		while(i < gp.player.maxMana) {
			g2.drawImage(crystal_blank, x, y, null);
			i++;
			x += 35;
			
		}
		
		// DRAW CURRENT MANA
		x = gp.tileSize/2;
		y = (int) (gp.tileSize*1.5);
		i = 0;
		
		while(i < gp.player.mana) {
			g2.drawImage(crystal_full, x, y, null);
			i++;
			x += 35;
		}
	}
	public void drawMessage() {
		
		int messageX = gp.tileSize/2;
		int messageY = gp.tileSize*3;
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20F));
		
		for(int i = 0; i < message.size(); i++) {
			
			if(message.get(i) != null) {
				
				g2.setColor(Color.GRAY);
				g2.drawString(message.get(i), messageX+2, messageY+2);
				
				g2.setColor(Color.WHITE);
				g2.drawString(message.get(i), messageX, messageY);
				
				int counter = messageTimer.get(i) + 1; // messageTimer++
				messageTimer.set(i, counter);
				messageY += 40;
				
				if(messageTimer.get(i) > 180) {
					message.remove(i);
					messageTimer.remove(i);
				}
			}
		}
	}
	public void drawTitleScreen() {
	
		// BACKGROUND COLOR
		g2.setColor(Color.DARK_GRAY);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		// TITLE NAME
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
		String text = "Red's Adventure";
		int x = getXForCenteredText(text);
		int y = gp.tileSize * 3;
		
		// SHADOW
		Color c = new Color(0, 0, 0, 210);
		g2.setColor(c);
		g2.drawString(text, x+5, y+5);
		
		// TITLE COLOR
		g2.setColor(Color.RED);
		g2.drawString(text, x, y);
		
		// TITLE IMAGE
		x = gp.screenWidth/2 - (gp.tileSize*3)/2;
		y += gp.tileSize * 2;
		g2.drawImage(gp.player.down1, x, y, gp.tileSize*3, gp.tileSize*3, null);
		
		// MENU
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
		text = "NEW GAME";
		x = getXForCenteredText(text);
		y += gp.tileSize*4;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-gp.tileSize/2, y);
		}
		text = "LOAD GAME";
		x = getXForCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-gp.tileSize/2, y);
		}
		
		text = "QUIT";
		x = getXForCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if(commandNum == 2) {
			g2.drawString(">", x-gp.tileSize/2, y);
		}
		
	}
	public void drawPauseScreen() {
		
		String text = "PAUSED";
		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = getXForCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
	}
	public void drawDialogueScreen() {
		
		// Window
		int x = gp.tileSize * 3; // 3 tiles from the left
		int y = gp.tileSize / 2; // half tile from the top
		int width = gp.screenWidth - (gp.tileSize*6);
		int height = gp.tileSize * 4;
		drawSubWindow(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 26F));
		x += gp.tileSize/2;
		y += gp.tileSize;
		
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
	}
	public void drawSubWindow(int x, int y, int width, int height) {

		
		Color c = new Color(0, 0, 0, 220); // Create RGB black (R, G, B, opacity 0-255)
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5)); // 5 pixel outline
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
	}
	public void drawCharacterScreen() {
		
		// CREATE A FRAME
		final int frameX = gp.tileSize*2;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize*5;
		final int frameHeight = (int) (gp.tileSize*10.5);
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		//  TEXT
		g2.setColor(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(25F));
		
		int textX = frameX + 20;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 35;
		
		// STAT NAMES
		g2.drawString("Life", textX, textY);
		textY += lineHeight;
		g2.drawString("Mana", textX, textY);
		textY += lineHeight;
		g2.drawString("Level", textX, textY);
		textY += lineHeight;
		g2.drawString("Strength", textX, textY);
		textY += lineHeight;
		g2.drawString("Dexterity", textX, textY);
		textY += lineHeight;
		g2.drawString("Attack", textX, textY);
		textY += lineHeight;
		g2.drawString("Defense", textX, textY);
		textY += lineHeight;
		g2.drawString("Current XP", textX, textY);
		textY += lineHeight;
		g2.drawString("XP to Next Lvl", textX, textY);
		textY += lineHeight;
		g2.drawString("Coins", textX, textY);
		textY += lineHeight+25;
		g2.drawString("Weapon", textX, textY);
		textY += lineHeight+13;
		g2.drawString("Shield", textX, textY);
		textY += lineHeight;
		
		// STAT VALUES
		int tailX = (frameX + frameWidth) - 30;
		textY = frameY + gp.tileSize;// Reset textY
		String val;
		
		val = String.valueOf(gp.player.life+"/"+gp.player.maxLife);
		textX = getXForAlignRightText(val, tailX);
		g2.drawString(val, textX, textY);
		textY += lineHeight;

		val = String.valueOf(gp.player.mana+"/"+gp.player.maxMana);
		textX = getXForAlignRightText(val, tailX);
		g2.drawString(val, textX, textY);
		textY += lineHeight;
		
		val = String.valueOf(gp.player.level);
		textX = getXForAlignRightText(val, tailX);
		g2.drawString(val, textX, textY);
		textY += lineHeight;

		val = String.valueOf(gp.player.strength);
		textX = getXForAlignRightText(val, tailX);
		g2.drawString(val, textX, textY);
		textY += lineHeight;

		val = String.valueOf(gp.player.dexterity);
		textX = getXForAlignRightText(val, tailX);
		g2.drawString(val, textX, textY);
		textY += lineHeight;

		val = String.valueOf(gp.player.attack);
		textX = getXForAlignRightText(val, tailX);
		g2.drawString(val, textX, textY);
		textY += lineHeight;

		val = String.valueOf(gp.player.defense);
		textX = getXForAlignRightText(val, tailX);
		g2.drawString(val, textX, textY);
		textY += lineHeight;

		val = String.valueOf(gp.player.exp);
		textX = getXForAlignRightText(val, tailX);
		g2.drawString(val, textX, textY);
		textY += lineHeight;

		val = String.valueOf(gp.player.nextLvlExp);
		textX = getXForAlignRightText(val, tailX);
		g2.drawString(val, textX, textY);
		textY += lineHeight;

		val = String.valueOf(gp.player.coin);
		textX = getXForAlignRightText(val, tailX);
		g2.drawString(val, textX, textY);
		textY += lineHeight;

		g2.drawImage(gp.player.currentWeapon.down1, tailX-gp.tileSize, textY-13, null);
		textY += gp.tileSize;
		g2.drawImage(gp.player.currentShield.down1, tailX-gp.tileSize, textY-10, null);
		
	}
	public void drawInventoryScreen(Entity entity, boolean cursor) {
		
		int frameX;
		int frameY;
		int frameWidth;
		int frameHeight;
		int slotCol;
		int slotRow;
		
		if(entity == gp.player) {
			frameX = gp.tileSize*12;
			frameY = gp.tileSize;
			frameWidth = gp.tileSize*6;
			frameHeight = gp.tileSize*5;
			slotCol = playerSlotCol;
			slotRow = playerSlotRow;
		}
		else {
			frameX = gp.tileSize*2;
			frameY = gp.tileSize;
			frameWidth = gp.tileSize*6;
			frameHeight = gp.tileSize*5;
			slotCol = npcSlotCol;
			slotRow = npcSlotRow;
		}
		
		// INVENTORY WINDOW
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// SLOTS
		final int slotXStart = frameX + 20;
		final int slotYStart = frameY + 20;
		int slotX = slotXStart;
		int slotY = slotYStart;
		int slotSize = gp.tileSize+3;
		
		// DRAW INVENTORY ITEMS
		for(int i =0; i < entity.inventory.size(); i++) {
			
			// EQUIPPED ITEM CURSOR
			if(entity.inventory.get(i) == entity.currentWeapon ||
					entity.inventory.get(i) == entity.currentShield) {
				
				g2.setColor(Color.GRAY);
				g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
				
				g2.setColor(Color.YELLOW);
				g2.setStroke(new BasicStroke(2)); //2  pixel outline
				g2.drawRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
			}
			
			g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);
			slotX += slotSize;
			
			if(i == 4 || i == 9 || i == 14) { // if iteration reaches end of row, move down to next row
				slotX = slotXStart;
				slotY += slotSize;
			}
		}
		
		// CURSOR
		int cursorX = slotXStart + (slotSize * slotCol);
		int cursorY = slotYStart + (slotSize * slotRow);
		int cursorWidth = gp.tileSize;
		int cursorHeight = gp.tileSize;
		
		// DRAW CURSOR
		if(cursor == true) {
			g2.setColor(Color.WHITE);
			g2.setStroke(new BasicStroke(3));
			g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
				
			// DESCRIPTION WINDOW
			int dFrameX = frameX;
			int dFrameY = frameY + frameHeight;
			int dFrameWidth = frameWidth;
			int dFrameHeight = gp.tileSize*3;
			
			
			// DESCRIPTION TEXT
			int textX = dFrameX + 20;
			int textY = dFrameY + gp.tileSize-10;
			g2.setFont(g2.getFont().deriveFont(20F));
			
			int itemIndex = getInventoryItemIndex(slotCol, slotRow);
			
			if(itemIndex < entity.inventory.size()) {
				
				drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight); // Draw description window if there is an item.
				
				for(String line: entity.inventory.get(itemIndex).itemDescription.split("\n")) {
					g2.drawString(line, textX, textY);
					textY += 30;
				}
			}
		}
	}
	public void drawOptionScreen() {
		
		g2.setColor(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		// SUB WINDOW
		int frameX = gp.tileSize*6;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize*8;
		int frameHeight = gp.tileSize*10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		switch(subState) {
		case 0: option_titles(frameX, frameY); break;
		case 1: options_fullScreenNotif(frameX, frameY); break;
		case 2: options_control(frameX, frameY); break;	
		case 3: options_endGameConfirmation(frameX, frameY); break;
		}
		
		gp.keyH.enterPressed = false;
	}
	public void drawGameOverScreen() {
		
		g2.setColor(new Color(0, 0, 0, 150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		int x;
		int y;
		String text;
		
		g2.setFont((g2.getFont().deriveFont(Font.BOLD, 110f)));
		
		text = "Game Over";
		
		// Shadow
		g2.setColor(Color.GRAY);
		x = getXForCenteredText(text);
		y = gp.tileSize*4;
		g2.drawString(text, x, y);
		
		// Title
		g2.setColor(Color.WHITE);
		g2.drawString(text, x-4, y-4);
		
		// Retry
		g2.setFont((g2.getFont().deriveFont(50f)));
		text = "Retry";
		x = getXForCenteredText(text);
		y += gp.tileSize*4;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-40, y);
		}

		// Back to Title Screen
		text = "End Game";
		x = getXForCenteredText(text);
		y += 55;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-40, y);
		}
	}
	public void option_titles(int frameX, int frameY) {
		
		int textX;
		int textY;
		
		g2.setFont(g2.getFont().deriveFont(34F));
		// TITLE
		String text = "-- OPTIONS -- ";
		textX = getXForCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		
		g2.setFont(g2.getFont().deriveFont(30F));
		// FULL SCREEN OPTION
		textX = frameX + gp.tileSize;
		textY = (int) (frameY + gp.tileSize*2.5);
		g2.drawString("Full Screen", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			
			if(gp.keyH.enterPressed == true) {
				gp.playSE(10);
				if(gp.fullScreenOn == false) {
					gp.fullScreenOn = true;
				}
				else if(gp.fullScreenOn == true) {
					gp.fullScreenOn = false;
				}
				subState = 1;
			}
		}
		
		// MUSIC OPTION
		textY += gp.tileSize;
		g2.drawString("Music", textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX-25, textY);
		}
		
		// SOUND EFFECT OPTION
		textY += gp.tileSize;
		g2.drawString("SE", textX, textY);
		if(commandNum == 2) {
			g2.drawString(">", textX-25, textY);
		}
		
		// CONTROLS
		textY += gp.tileSize;
		g2.drawString("Controls", textX, textY);
		if(commandNum == 3) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 2;
				commandNum = 0;
			}
		}
		
		// END GAME OPTION
		textY += gp.tileSize;
		g2.drawString("End Game", textX, textY);
		if(commandNum == 4) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 3;
				commandNum = 0;
			}
		}
		
		// GO BACK
		textY = frameY + gp.tileSize*9;
		g2.drawString("Back", textX, textY);
		if(commandNum == 5) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				gp.gameState = gp.playState;
			}
		}
		
		// FULL SCREEN CHECKBOX
		textX = (int) (frameX + gp.tileSize*6.5);
		textY = frameY + gp.tileSize*2;
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(textX, textY, 24, 24);
		if(gp.fullScreenOn == true) {
			g2.fillRect(textX, textY, 24, 24);
		}
		
		// MUSIC VOLUME SLIDER
		textX = (int) (frameX + gp.tileSize*4.5);
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, 24); // 120/5 = 24 px
		int volumeWidth = 24 * gp.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		// SE VOLUME SLIDER
		textY += gp.tileSize;
		g2.drawRect(textX, textY, 120, 24);
		volumeWidth = 24 * gp.se.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		gp.config.saveConfig(); // Save settings to config file
	}
	public void options_fullScreenNotif(int frameX, int frameY) {
		
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize*3;
		
		currentDialogue = "The change will take \neffect after restarting \nthe game.";
		
		for(String line: currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}
		
		// BACK
		textY = frameY + gp.tileSize*9;
		g2.setFont(g2.getFont().deriveFont(30F));
		g2.drawString("Back", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
			}
		}
	}
	public void options_control(int frameX, int frameY) {
		
		int textX;
		int textY;
		
		// TITLE
		g2.setFont(g2.getFont().deriveFont(34F));
		String text = "-- CONTROLS --";
		textX = getXForCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		
		
		// CONTROLS
		g2.setFont(g2.getFont().deriveFont(26F));
		textX = frameX + gp.tileSize;
		textY = frameY + gp.tileSize*2;
		
		g2.drawString("Move", textX, textY); textY += gp.tileSize*0.75;
		g2.drawString("Attack/Interact", textX, textY); textY += gp.tileSize*0.75;
		g2.drawString("Shoot", textX, textY); textY += gp.tileSize*0.75;
		g2.drawString("Character Screen", textX, textY); textY += gp.tileSize*0.75;
		g2.drawString("Pause", textX, textY); textY += gp.tileSize*0.75;
		g2.drawString("Debug", textX, textY); textY += gp.tileSize*0.75;
		g2.drawString("Options", textX, textY); textY += gp.tileSize*0.75;
		
		textX = (int) (frameX + gp.tileSize*5.5);
		textY = frameY + gp.tileSize*2;
		
		g2.drawString("[WASD]", textX, textY); textY += gp.tileSize*0.75;
		g2.drawString("[Enter]", textX, textY); textY += gp.tileSize*0.75;
		g2.drawString("[Shift]", textX, textY); textY += gp.tileSize*0.75;
		g2.drawString("[C]", textX, textY); textY += gp.tileSize*0.75;
		g2.drawString("[P]", textX, textY); textY += gp.tileSize*0.75;
		g2.drawString("[T]", textX, textY); textY += gp.tileSize*0.75;
		g2.drawString("[Esc]", textX, textY); textY += gp.tileSize*0.75;
		
		// BACK
		textX = frameX + gp.tileSize;
		textY = frameY + gp.tileSize*9;
		g2.setFont(g2.getFont().deriveFont(30F));
		g2.drawString("Back", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 3;
			}
		}
	}
	public void options_endGameConfirmation(int frameX, int frameY) {
		
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize*3;
		
		currentDialogue = "End game and return \nto Main Menu?";
		
		for(String line: currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}
		
		// YES
		String text = "Yes";
		textX = getXForCenteredText(text);
		textY += gp.tileSize*3;
		g2.drawString(text, textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
				gp.gameState = gp.titleState;
				gp.stopMusic();
			}
		}
		
		// NO
		text = "No";
		textX = getXForCenteredText(text);
		textY += gp.tileSize;
		g2.drawString(text, textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 4;
			}
		}
	}
	public void drawTransition(){
		
		timer++;
		g2.setColor(new Color(0, 0, 0, timer*5));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		if(timer == 50) {
			timer = 0;
			gp.gameState = gp.playState;
			gp.currentMap = gp.eHandler.tempMap;
			gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
			gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
			gp.eHandler.previousEventX = gp.player.worldX;
			gp.eHandler.previousEventY = gp.player.worldY;
		}
	}
	public void drawTradeScreen() {
		
		switch(subState) {
		case 0: trade_select(); break;
		case 1: trade_buy(); break;
		case 2: trade_sell(); break;
		}
		gp.keyH.enterPressed = false;
	}
	public void trade_select() {
		
		drawDialogueScreen();
		
		// DRAW TRADE WINDOW
		int x = gp.tileSize * 15;
		int y = gp.tileSize * 4;
		int width = gp.tileSize * 3;
		int height = (int) (gp.tileSize * 3.5);
		drawSubWindow(x, y, width, height);
		
		// TEXT
		x += gp.tileSize;
		y += gp.tileSize;
		g2.drawString("Buy", x, y);
		if(commandNum == 0) {
			g2.drawString(">", x-24, y);
			if(gp.keyH.enterPressed == true) {
				subState = 1;
			}
		}

		y += gp.tileSize;
		g2.drawString("Sell", x, y);
		if(commandNum == 1) {
			g2.drawString(">", x-24, y);
			if(gp.keyH.enterPressed == true) {
				subState = 2;
			}
		}
		
		y += gp.tileSize;
		g2.drawString("Leave", x, y);
		if(commandNum == 2) {
			g2.drawString(">", x-24, y);
			if(gp.keyH.enterPressed == true) {
				commandNum = 0;
				gp.gameState = gp.dialogueState;
				currentDialogue = "I'll see you later... for sure.";
			}
		}
		
	}
	public void trade_buy() {
		
		// DRAW PLAYER INVENTORY
		drawInventoryScreen(gp.player, false);
		
		// DRAW NPC INVENTORY
		drawInventoryScreen(npc, true);
		
		// DRAW HINT WINDOW
		int x = gp.tileSize*2;
		int y = gp.tileSize*9;
		int width = gp.tileSize*6;
		int height = gp.tileSize*2;
		drawSubWindow(x, y, width, height);
		g2.drawString("[Esc] Back", x+24, y+60);
		
		// DRAW PLAYER COIN WINDOW
		x = gp.tileSize*12;
		y = gp.tileSize*9;
		width = gp.tileSize*6;
		height = gp.tileSize*2;
		drawSubWindow(x, y, width, height);
		g2.drawImage(coin, x+24, y+32, 32, 32, null);
		g2.drawString("Your Coins: "+gp.player.coin, x+60, y+56);
		
		// DRAW PRICE WINDOW
		int itemIndex = getInventoryItemIndex(npcSlotCol, npcSlotRow);
		if(itemIndex < npc.inventory.size()) {
			
			x = (int) (gp.tileSize*5.5);
			y = (int) (gp.tileSize*5.5);
			width = (int) (gp.tileSize*2.5);
			height = gp.tileSize;
			drawSubWindow(x, y , width, height);
			g2.drawImage(coin, x+8, y+7, 32, 32, null);
			
			int price = npc.inventory.get(itemIndex).price;
			String text = ""+price;
			x = getXForAlignRightText(text, gp.tileSize*8-20);
			g2.drawString(text, x, y+31);
			
			
			// BUY AN ITEM
			if(gp.keyH.enterPressed == true) {
				if(npc.inventory.get(itemIndex).price > gp.player.coin) {
					subState = 0;
					gp.gameState = gp.dialogueState;
					currentDialogue = "Insufficient amount. You need more coins to buy that!";
					drawDialogueScreen();
				}
				else if(gp.player.inventory.size() == gp.player.maxInventorySize) {
					subState = 0;
					gp.gameState = gp.dialogueState;
					currentDialogue = "Inventory full. You need more space!";
					drawDialogueScreen();
				}
				else {
					gp.playSE(10);
					gp.player.coin -= npc.inventory.get(itemIndex).price;
					gp.player.inventory.add(npc.inventory.get(itemIndex));
				}
			}
		}
	}
	public void trade_sell() {
		
		// DRAW PLAYER INVENTORY
		drawInventoryScreen(gp.player, true);
		
		// DRAW HINT WINDOW
		int x = gp.tileSize*2;
		int y = gp.tileSize*9;
		int width = gp.tileSize*6;
		int height = gp.tileSize*2;
		drawSubWindow(x, y, width, height);
		g2.drawString("[Esc] Back", x+24, y+60);
		
		// DRAW PLAYER COIN WINDOW
		x = gp.tileSize*12;
		y = gp.tileSize*9;
		width = gp.tileSize*6;
		height = gp.tileSize*2;
		drawSubWindow(x, y, width, height);
		g2.drawImage(coin, x+24, y+32, 32, 32, null);
		g2.drawString("Your Coins: "+gp.player.coin, x+60, y+56);
		
		// DRAW PRICE WINDOW
		int itemIndex = getInventoryItemIndex(playerSlotCol, playerSlotRow);
		if(itemIndex < gp.player.inventory.size()) {
			
			x = (int) (gp.tileSize*15.5);
			y = (int) (gp.tileSize*5.5);
			width = (int) (gp.tileSize*2.5);
			height = gp.tileSize;
			drawSubWindow(x, y , width, height);
			g2.drawImage(coin, x+8, y+7, 32, 32, null);
			
			int price = gp.player.inventory.get(itemIndex).price/2;
			String text = ""+price;
			x = getXForAlignRightText(text, gp.tileSize*18-20);
			g2.drawString(text, x, y+31);
			
			
			// SELL AN ITEM
			if(gp.keyH.enterPressed == true) {
				
				if(gp.player.inventory.get(itemIndex) == gp.player.currentWeapon ||
						gp.player.inventory.get(itemIndex) == gp.player.currentShield) {
					subState = 0;
					commandNum = 0;
					gp.gameState = gp.dialogueState;
					currentDialogue = "You're trying to sell an equipped item!";
				}
				else {
					gp.playSE(10);
					gp.player.inventory.remove(itemIndex);
					gp.player.coin += price;
				}
			}
		}
	}
	public int getInventoryItemIndex(int slotCol, int slotRow) {
		int itemIndex = slotCol + (slotRow*5);
		return itemIndex;
	}
	public int getXForCenteredText(String text) {
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
	public int getXForAlignRightText(String text, int tailX) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;
		return x;
	}
}









