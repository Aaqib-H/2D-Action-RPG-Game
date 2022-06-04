package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile_interactive.InteractiveTile;

public class GamePanel extends JPanel implements Runnable{ // Make a JPanel class with extra functionalities
	
	// SCREEN SETTINGS
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 3; // Enlarges tile scale
	public final int tileSize = originalTileSize * scale; // 48x48 tile
	public final int maxScreenCol = 20;
	public final int maxScreenRow = 12; // 4:3 Ratio
	public final int screenWidth = tileSize * maxScreenCol; // 960 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	
	
	// WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	
	
	// FULL SCREEN SETTINGS
	int screenWidth2 = screenWidth;
	int screenHeight2 = screenHeight;
	BufferedImage tempScreen;
	Graphics2D g2;
	public boolean fullScreenOn = false; // Default value
	
	// FPS
	int FPS = 60; 
	
	
	//SYSTEM
	TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionSystem coll = new CollisionSystem(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	public EventHandler eHandler = new EventHandler(this);
	Config config = new Config(this);
	Thread gameThread;
	
	
	// ENTITY AND OBJECT
	public Player player = new Player(this, keyH);
	public Entity obj[]= new Entity[20]; // 20 slots for objects
	public Entity npc[] = new Entity[10];
	public Entity monster[] = new Entity[20];
	public InteractiveTile iTile[] = new InteractiveTile[50];
	public ArrayList<Entity> projectileList = new ArrayList<>();
	public ArrayList<Entity> particleList = new ArrayList<>();
	ArrayList<Entity> entityList = new ArrayList<>();
	
	
	// GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int characterState = 4;
	public final int optionState = 5;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void setupGame() {
		
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonster();
		aSetter.setInteractiveTile();
		//playMusic(0); // Theme Music
		gameState = titleState;
		
		// FULL SCREEN SETTINGS
		tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB); //
		g2 = (Graphics2D)tempScreen.getGraphics();
		
		if(fullScreenOn == true) {
			setFullScreen(); // Full Screen
		}
	}
	public void setFullScreen() {
		
		// GET LOCAL SCREEN DEVICE
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(Main.window);
		
		// GET FULLSCREEN SCREEN WIDTH AND HEIGHT
		screenWidth2 = Main.window.getWidth();
		screenHeight2 = Main.window.getHeight();
	}
	public void startGameThread() {
		gameThread = new Thread(this); // Instantiate a thread and pass this class to Thread object
		gameThread.start();
	}
	@Override
	public void run() { // -> Game Loop = update >  repaint > repeat | Sleep Method
		
		double drawInterval = 1000000000/FPS; // Frames per second | 0.01666 seconds | Make an interval for the game loop
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		while (gameThread != null) { // run while gameThread exists
			
			// 1 Update information such as character positions
			update();
			
			// 2 Draw the screen with the updated information
			//repaint(); // Call paint component method
			
			drawToTempScreen(); // Draw everything to bufferedImage
			drawToScreen(); // Draw bufferedImage to screen
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime(); // Calculate remaining time after performing a loop
				remainingTime = remainingTime/1000000; // convert to milliseconds for Thread method
				
				if (remainingTime < 0) { // If game loop takes longer than one interval, thread doesn't need to sleep
					remainingTime = 0;
				}
				Thread.sleep((long) remainingTime); // Sleep for remaining time to maintain FPS
				
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void update() {
		
	if(gameState == playState) {
		// PLAYER
		player.update();
		
		// NPC
		for(int i = 0; i < npc.length; i++) {
			if(npc[i] != null) {
				npc[i].update();
			}
		}
		for(int i = 0; i < monster.length; i++) {
			if(monster[i] != null) {
				if(monster[i].alive == true || monster[i].dying == true) {
					monster[i].update();

				}
				if(monster[i].alive == false) {
					monster[i].checkDrop();
					monster[i] = null;

				}
			}
		}
		for(int i = 0; i < projectileList.size(); i++) {
			if(projectileList.get(i) != null) {
				if(projectileList.get(i).alive == true) {
					projectileList.get(i).update();
				}
				if(projectileList.get(i).alive == false) {
					projectileList.remove(i);
				}
			}
		}
		for(int i = 0; i < particleList.size(); i++) {
			if(particleList.get(i) != null) {
				if(particleList.get(i).alive == true) {
					particleList.get(i).update();
				}
				if(particleList.get(i).alive == false) {
					particleList.remove(i);
				}
			}
		}
		for(int i = 0; i < iTile.length; i++) {
			if(iTile[i] != null) {
				iTile[i].update();
			}
		}
	}
	if (gameState == pauseState){
		
	}
		
	}
	public void drawToTempScreen() {
	
		// ANTI-ALIASING
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// DEBUG
		long drawStart = 0;
		if(keyH.showDebug == true) {
			drawStart = System.nanoTime();
		}
		
		// TITLE SCREEN
		if(gameState == titleState) {
			ui.draw(g2);
		}

		else {
			// TILE
			tileM.draw(g2);
			
			// INTERACTIVE TILE
			for(int i = 0; i < iTile.length; i++) {
				if(iTile[i] != null) {
					iTile[i].draw(g2);
				}
			}
			
			// ADD ENTITIES TO THE LIST
			entityList.add(player);
			for(int i = 0; i < npc.length; i++) {
				if(npc[i] != null) {
					entityList.add(npc[i]);
				}
			}
			
			for(int i = 0; i < monster.length; i++) {
				if(monster[i] != null) {
					entityList.add(monster[i]);
				}
			}
			
			for(int i = 0; i < obj.length; i++) {
				if(obj[i] != null) {
					entityList.add(obj[i]);
				}
			}
			
			for(int i = 0; i < projectileList.size(); i++) {
				if(projectileList.get(i) != null) {
					entityList.add(projectileList.get(i));
				}
			}
			for(int i = 0; i < particleList.size(); i++) {
				if(particleList.get(i) != null) {
					entityList.add(particleList.get(i));
				}
			}
			
			// SORT
			Collections.sort(entityList, new Comparator<Entity>() {

				@Override
				public int compare(Entity e1, Entity e2) {
					
					int result = Integer.compare(e1.worldY, e2.worldY);
					return result;
				}
				
			});
			
			// ENTITIES
			for(int i = 0; i < entityList.size(); i++) {
				entityList.get(i).draw(g2);
			}
			entityList.clear();
			
			// UI
			ui.draw(g2);
		}
		
		
		
		//DEBUG
		if(keyH.showDebug == true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			
			g2.setFont(new Font("Arial", Font.PLAIN, 20));
			g2.setColor(Color.WHITE);
			int x = 10;
			int y = 400;
			int lineHeight = 25;
			
			g2.drawString("WorldX: " + player.worldX, x, y); y+=lineHeight;
			g2.drawString("WorldY: " + player.worldY, x, y); y+=lineHeight;
			g2.drawString("Col: " + (player.worldX + player.hitbox.x)/tileSize, x, y); y+=lineHeight;
			g2.drawString("Row: " + (player.worldY + player.hitbox.y)/tileSize, x, y); y+=lineHeight;
			
			g2.drawString("Draw Time: " + passed, x, y);
		}
	}
	public void drawToScreen() {
		
		Graphics g = getGraphics();
		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
		g.dispose();
	}
	
	// SOUND 
	public void playMusic(int i) {
		
		music.setFile(i);
		music.play();
		music.loop();
	}
	public void stopMusic() {
		
		music.stop();
	
	}
	public void playSE(int i) { // Sound Effects
		
		se.setFile(i);
		se.play();
	}
}






