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
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	Font maruMonica;
	public boolean gameFinished = false;
	public boolean messageOn = false;
	public String message = "";
	int messageTimer = 0;
	public String currentDialogue = "";
	
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
	}
	
	public void showMessage(String text) {
		
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(maruMonica);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.WHITE);
		
		// PLAY STATE
		if(gp.gameState == gp.playState) {
			
		}
		// PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		
		// DIALOGUE STATE
		if(gp.gameState == gp.dialogueState) {
			drawDialogueScreen();
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
	public int getXForCenteredText(String text) {
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
}
