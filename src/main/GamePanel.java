package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{ // Make a JPanel class with extra functionalities
	
	// SCREEN SETTINGS
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 3; // Enlarges tile scale
	public final int tileSize = originalTileSize * scale; // 48x48 tile
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12; // 4:3 Ratio
	public final int ScreenWidth = tileSize * maxScreenCol; // 768 pixels
	public final int ScreenHeight = tileSize * maxScreenRow; // 576 pixels
	
	
	// WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	
	
	// FPS
	int FPS = 60; 
	
	
	//SYSTEM
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionSystem coll = new CollisionSystem(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	Thread gameThread;
	
	
	// ENTITY AND OBJECT
	public Player player = new Player(this, keyH);
	public SuperObject obj[]= new SuperObject[10]; // 10 slots for objects
	
	
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void setupGame() {
		
		aSetter.setObject();
		playMusic(0); // Theme Music
		
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
			repaint(); // Call paint component method
			
			
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
		
		player.update();
		
	}
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g; // Gives more control over graphics and makes it 2D
		
		// DEBUG
		long drawStart = 0;
		if(keyH.checkDrawTime == true) {
			drawStart = System.nanoTime();
		}
		
		
		
		
		// TILE
		tileM.draw(g2);
		
		// OBJECT
		for (int i = 0; i < obj.length; i++) { // Iterate through objects
			
			if (obj[i] != null) { // Check if slot is empty, to avoid NullPointer error
				obj[i].draw(g2, this);
			}
		}
		 
		// PLAYER
		player.draw(g2);
		
		// UI
		ui.draw(g2);
		
		
		
		
		
		//DEBUG
		if(keyH.checkDrawTime == true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.WHITE);
			g2.drawString("Draw Time: " + passed, 10, 400);
			System.out.println("Draw time: " + passed);
		}
		
		g2.dispose();
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





