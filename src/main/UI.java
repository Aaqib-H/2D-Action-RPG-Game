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
import object.OBJ_Heart;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	Font maruMonica;
	BufferedImage heart_full, heart_half, heart_blank;
	public boolean gameFinished = false;
	public boolean messageOn = false;
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageTimer = new ArrayList<>();
	public String currentDialogue = "";
	public int commandNum = 0;
	public int slotCol = 0;
	public int slotRow = 0;
	
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
			drawInventoryScreen();
		}
	}
	public void drawPlayerLife() {
		
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		int i = 0;
		
		// MAX LIFE
		while(i < gp.player.maxLife/2) { // 1 heart 2 lives
			g2.drawImage(heart_blank, x, y, null);
			i++;
			x += gp.tileSize;
		}
		
		// Reset
		x = gp.tileSize/2;
		y = gp.tileSize/2;
		i = 0;
		
		// CURRENT LIFE
		while(i < gp.player.life) { // 1 heart 2 lives
			g2.drawImage(heart_half, x, y, null);
			i++;
			if(i < gp.player.life) {
				g2.drawImage(heart_full, x, y, null);
			}
			i++;
			x += gp.tileSize;
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
				
				if(messageTimer.get(i) > 120) {
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
		int x = gp.tileSize * 2; // 2 tiles from the left
		int y = gp.tileSize / 2; // half tile from the top
		int width = gp.screenWidth - (gp.tileSize*4);
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
		final int frameX = gp.tileSize;
		final int frameY = gp.tileSize;
		final int frameWidth = gp.tileSize*5;
		final int frameHeight = gp.tileSize*10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		//  TEXT
		g2.setColor(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(30F));
		
		int textX = frameX + 20;
		int textY = frameY + gp.tileSize;
		final int lineHeight = 35;
		
		// STAT NAMES
		g2.drawString("Life", textX, textY);
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
	public void drawInventoryScreen() {
		// INVENTORY WINDOW
		int frameX = gp.tileSize*9;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize*6;
		int frameHeight = gp.tileSize*5;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		// SLOTS
		final int slotXStart = frameX + 20;
		final int slotYStart = frameY + 20;
		int slotX = slotXStart;
		int slotY = slotYStart;
		int slotSize = gp.tileSize+3;
		
		// CURSOR
		int cursorX = slotXStart + (slotSize * slotCol);
		int cursorY = slotYStart + (slotSize * slotRow);
		int cursorWidth = gp.tileSize;
		int cursorHeight = gp.tileSize;
		
		// DRAW INVENTORY ITEMS
		for(int i =0; i < gp.player.inventory.size(); i++) {
			
			// EQUIPPED ITEM CURSOR
			if(gp.player.inventory.get(i) == gp.player.currentWeapon ||
					gp.player.inventory.get(i) == gp.player.currentShield) {
				
				g2.setColor(Color.GRAY);
				g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
				
				g2.setColor(Color.YELLOW);
				g2.setStroke(new BasicStroke(2)); //2  pixel outline
				g2.drawRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
			}
			
			g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
			slotX += slotSize;
			
			if(i == 4 || i == 9 || i == 14) { // if iteration reaches end of row, move down to next row
				slotX = slotXStart;
				slotY += slotSize;
			}
		}
		
		// DRAW CURSOR
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
		
		int itemIndex = getInventoryItemIndex();
		
		if(itemIndex < gp.player.inventory.size()) {
			
			drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight); // Draw description window if there is an item.
			
			for(String line: gp.player.inventory.get(itemIndex).itemDescription.split("\n")) {
				g2.drawString(line, textX, textY);
				textY += 30;
			}
		}
	}
	public int getInventoryItemIndex() {
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









