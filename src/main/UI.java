package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {
	
	GamePanel gp;
	Font arial_30, arial_80;
	BufferedImage keyImage;
	public boolean gameFinished = false;
	public boolean messageOn = false;
	public String message = "";
	int messageTimer = 0;
	
	double playTime;
	DecimalFormat df = new DecimalFormat("#0.00");
	
	
	public UI (GamePanel gp) {
		
		this.gp = gp;
		arial_30 = new Font("Arial", Font.BOLD, 30);
		arial_80 = new Font("Arial", Font.BOLD, 80);
		OBJ_Key key = new OBJ_Key(gp);
		keyImage = key.image;
	}
	
	public void showMessage(String text) {
		
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		
		if (gameFinished == true) {
			
			g2.setFont(g2.getFont().deriveFont(40F));
			g2.setColor(Color.WHITE);
			
			String text;
			int textLength;
			int x, y;
			
			text = "You found the treasure!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.ScreenWidth/2 - textLength/2;
			y = gp.ScreenHeight/2 - (gp.tileSize*3);
			g2.drawString(text, x, y);
			
			
			text = "Your time: " + df.format(playTime) + "!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.ScreenWidth/2 - textLength/2;
			y = gp.ScreenHeight/2 + (gp.tileSize*4);
			g2.drawString(text, x, y);
			
			
			g2.setFont(arial_80);
			g2.setColor(Color.YELLOW);
			text = "Congratulations!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.ScreenWidth/2 - textLength/2;
			y = gp.ScreenHeight/2 + (gp.tileSize*2);
			g2.drawString(text, x, y);
			
			gp.gameThread = null; // Stop the gameThread
		}
		else {
			g2.setFont(arial_30);
			g2.setColor(Color.WHITE);
			g2.drawImage(keyImage, gp.tileSize/3, gp.tileSize/3, 40, 40, null);
			g2.drawString("" + gp.player.hasKey, 55, 50);
			
			// TIME
			playTime += (double)1/60;
			g2.drawString("Time: " + df.format(playTime), gp.tileSize*11, 50);
			
			// MESSAGE
			if (messageOn == true) {
				g2.setFont(g2.getFont().deriveFont(30F));
				g2.drawString(message, gp.tileSize/3, gp.tileSize * 5);
				
				messageTimer++;
				
				if (messageTimer > 120) { // 120 frames
					messageTimer = 0;
					messageOn = false;
				}
			}
		}
		
	}
	
	
}
