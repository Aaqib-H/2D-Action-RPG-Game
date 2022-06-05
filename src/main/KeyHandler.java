package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, projKeyPressed;
	
	// DEBUG
	boolean showDebug = false;
	
	GamePanel gp;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	
	

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode(); // Returns keyCode associated with the Key
		
		// TITLE STATE
		if(gp.gameState == gp.titleState) {
			titleState(code);
		}
		
		// PLAY STATE
		else if(gp.gameState == gp.playState){
			playState(code);
			}
		
		// PAUSE STATE
		else if(gp.gameState == gp.pauseState){
			pauseState(code);
		}
		
		// DIALOGUE STATE
		else if(gp.gameState == gp.dialogueState){
			dialogueState(code);
		}
		
		// CHARACTER STATE
		else if(gp.gameState == gp.characterState) {
			characterState(code);
		}
		
		// OPTION STATE
		else if(gp.gameState == gp.optionState) {
			optionState(code);
		}
		// GAME OVER STATE
		else if(gp.gameState == gp.gameOverState) {
			gameOverState(code);
		}
	}
	
	public void titleState(int code) {
		
		if(code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			gp.playSE(9);
			if(gp.ui.commandNum < 0) {
				gp.ui.commandNum = 2;
			}
		}
		if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			gp.playSE(9);
			if(gp.ui.commandNum > 2) {
				gp.ui.commandNum = 0;
			}
		}
		if(code == KeyEvent.VK_ENTER) {
			if(gp.ui.commandNum == 0) {
				gp.gameState = gp.playState;
				gp.playMusic(0);
			}
			else if(gp.ui.commandNum == 1) {
				// gp.gameState = gp.load;
			}
			else {
				System.exit(0);
			}
		}
			
	}
	public void playState(int code) {
		
		if(code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if(code == KeyEvent.VK_P) {
			gp.gameState = gp.pauseState;
		}
		if(code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.optionState;
		}
		if(code == KeyEvent.VK_C) {
			gp.gameState = gp.characterState;
		}
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		if(code == KeyEvent.VK_SHIFT) {
			projKeyPressed = true;
		}
		
		// DEBUG
		if(code == KeyEvent.VK_T) {
			if(showDebug == false) {
				showDebug = true;
			}
			else if (showDebug == true) {
				showDebug = false;
			}
		}
		if(code == KeyEvent.VK_R) { 
			switch(gp.currentMap) {
			case 0: gp.tileM.loadMap("/maps/worldV2.txt", 0); break;
			case 1: gp.tileM.loadMap("/maps/interior01.txt", 1); break;
			}
			
		}
	}
	public void pauseState(int code) {
		
		if(code == KeyEvent.VK_P) {
			gp.gameState = gp.playState;
		}
	}
	public void dialogueState(int code) {
		
		if(code == KeyEvent.VK_ENTER) {
			gp.gameState = gp.playState;
		}
	}
	public void characterState(int code) {
		
		if(code == KeyEvent.VK_C) {
			gp.gameState = gp.playState;
		}
		if(code == KeyEvent.VK_W) {
			if(gp.ui.slotRow != 0) {
				gp.ui.slotRow--;
				gp.playSE(9);
			}
		}
		if(code == KeyEvent.VK_A) {
			if(gp.ui.slotCol != 0) {
				gp.ui.slotCol--;
				gp.playSE(9);
			}
		}
		if(code == KeyEvent.VK_S) {
			if(gp.ui.slotRow != 3) {
				gp.ui.slotRow++;
				gp.playSE(9);
			}
		}
		if(code == KeyEvent.VK_D) {
			if(gp.ui.slotCol != 4) {
				gp.ui.slotCol++;
				gp.playSE(9);
			}
		}	
		if(code == KeyEvent.VK_ENTER) {
			gp.player.equipItem();
		}	
	}
	public void optionState(int code) {
		
		if(code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
		}
		if(code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		
		int maxCommandNum = 0;
		switch(gp.ui.subState) {
		case 0: maxCommandNum = 5; break;
		case 3: maxCommandNum = 1; break;
		}
		
		if(code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			gp.playSE(9);
			if(gp.ui.commandNum < 0) {
				gp.ui.commandNum = maxCommandNum;
			}
		}
		if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			gp.playSE(9);
			if(gp.ui.commandNum > maxCommandNum) {
				gp.ui.commandNum = 0;
			}
		}
		
		
		// SOUND SLIDERS | MUSIC and SE
		if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			
			// Music
			if(gp.ui.subState == 0) {
				if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
					gp.music.volumeScale--;
					gp.music.checkVolume();
					gp.playSE(10);
				}
			}
			
			// SE
			if(gp.ui.subState == 0) {
				if(gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
					gp.se.volumeScale--;
					gp.playSE(10);
				}
			}
		}
		if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			
			// Music
			if(gp.ui.subState == 0) {
				if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
					gp.music.volumeScale++;
					gp.music.checkVolume();
					gp.playSE(10);
				}
			}
			
			// SE
			if(gp.ui.subState == 0) {
				if(gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
					gp.se.volumeScale++;
					gp.playSE(10);
				}
			}
		}	
	}
	public void gameOverState(int code) {
		
		if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			gp.ui.commandNum--;
			gp.playSE(9);
			if(gp.ui.commandNum < 0) {
				gp.ui.commandNum = 1;
			}
		}
		if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			gp.ui.commandNum++;
			gp.playSE(9);
			if(gp.ui.commandNum > 1) {
				gp.ui.commandNum = 0;
			}
		}
		
		if(code == KeyEvent.VK_ENTER) {
			// RETRY
			if(gp.ui.commandNum == 0) {
				gp.gameState = gp.playState;
				gp.retryGame();
				gp.playMusic(0);
			}
			// RESTART
			else if (gp.ui.commandNum == 1) {
				gp.gameState = gp.titleState;
				gp.restartGame();
				gp.stopMusic();
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if(code == KeyEvent.VK_SHIFT) {
			projKeyPressed = false;
		}
	}

	

}
