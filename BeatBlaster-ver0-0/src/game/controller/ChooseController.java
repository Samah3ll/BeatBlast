package game.controller;

import game.controller.SelectionController.SelectionKeys;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;

public class ChooseController {
	
	public enum chooseKeys {
		LEFT, RIGHT, VALIDATE, DOWN, UP
	}
	
	private final long WAITTIME = 300;
	
	//Désigne le numéro dans la liste de la musique sélectionnée.
	private int selectedMusic = 0;
	
	//Désigne le boutton selectionné : 0 = pas de sélection; 1 = ok; 2 = back; 3 = la liste des musiques.
	private int selectedButton = 0;
	
	static private boolean okButtonSelected = false;
	static private boolean backButtonSelected = false;
	static private boolean musicListSelected = false;
	
	static boolean mouseIsPressed = false;
	
	static Vector2 mousePosition = new Vector2(0, 0);
	
	static Map<SelectionKeys, Boolean> keys = new HashMap<SelectionController.SelectionKeys, Boolean>();
	static {
		keys.put(SelectionKeys.LEFT, false);
		keys.put(SelectionKeys.RIGHT, false);
		keys.put(SelectionKeys.VALIDATE, false);
		keys.put(SelectionKeys.DOWN, false);
		keys.put(SelectionKeys.UP, false);
	};
	
	/*
	 * Accesseurs
	 */
	

	public int getSelectedButton() {
		return selectedButton;
	}
	
	public void setSelectedButton(int s) {
		this.selectedButton = s;
	}
	
	public int getSelectedMusic() {
		return selectedMusic;
	}
	
	public void setSelectedMusic(int s) {
		this.selectedMusic = s;
	}
	
	static public boolean isOkButtonSelected() {
		return okButtonSelected;
	}
	
	public void setOkButtonSelected(boolean b) {
		ChooseController.okButtonSelected = b;
	}
	
	static public boolean isBackButtonSelected() {
		return backButtonSelected;
	}
	
	public void setBackButtonSelected(boolean b) {
		ChooseController.backButtonSelected = b;
	}
	
	static public boolean isMusicListButtonSelected() {
		return musicListSelected;
	}
	
	public void setMusicListButtonSelected(boolean b) {
		ChooseController.musicListSelected = b;
	}
	
	public Map<SelectionKeys, Boolean> getKeys() {
		return keys;
	}
	
	public boolean getMouseState() {
		return mouseIsPressed;
	}	
	
	
	/*
	 * Méthodes
	 */	
	

	public void upPressed() {
		Long start = System.currentTimeMillis();
		while(System.currentTimeMillis() - start < WAITTIME) {
		}
		keys.get(keys.put(SelectionKeys.UP, true));
		
	}

	public void downPressed() {
		Long start = System.currentTimeMillis();
		while(System.currentTimeMillis() - start < WAITTIME) {
		}
		keys.get(keys.put(SelectionKeys.DOWN, true));
		
	}

	public void enterPressed() {
		keys.get(keys.put(SelectionKeys.VALIDATE, true));
		
	}

	public void upReleased() {
		keys.get(keys.put(SelectionKeys.UP, false));
		
	}

	public void downReleased() {
		keys.get(keys.put(SelectionKeys.DOWN, false));
		
		
	}

	public void enterReleased() {
		keys.get(keys.put(SelectionKeys.VALIDATE, false));
		
	}

	public void mouseMouved(int screenX, int screenY) {
		mousePosition.x = screenX;
		mousePosition.y = screenY;
		System.out.println("mouse position : " + mousePosition);
	}
	
	

	public void mouseButtonPressed(int x, int y) {
		mouseIsPressed = true;
		
	}

	public void mouseButtonReleased(int screenX, int screenY) {
		mouseIsPressed = false;
		
	}
	
	public void leftPressed() {
		Long start = System.currentTimeMillis();
		while(System.currentTimeMillis() - start < WAITTIME) {
		}
		keys.get(keys.put(SelectionKeys.LEFT, true));
		
	}

	public void rightPressed() {
		Long start = System.currentTimeMillis();
		while(System.currentTimeMillis() - start < WAITTIME) {
		}
		keys.get(keys.put(SelectionKeys.RIGHT, true));
		
	}

	public void leftReleased() {
		keys.get(keys.put(SelectionKeys.LEFT, false));
		
	}

	public void rightReleased() {
		keys.get(keys.put(SelectionKeys.RIGHT, false));
		
	}

	public void scrolled(int amount) {
		// TODO Auto-generated method stub
		
	}
	
	public void checkSelection() {
		switch (selectedButton) {
			case 1 :
				okButtonSelected = true;
				backButtonSelected = false;
				musicListSelected = false;
				break;
			case 2 :
				okButtonSelected = false;
				backButtonSelected = true;
				musicListSelected = false;
				break;
			case 3 :
				okButtonSelected = false;
				backButtonSelected = false;
				musicListSelected = true;
				break;
			default :
				okButtonSelected = false;
				backButtonSelected = false;
				musicListSelected = false;
				break;
		}
		
	}

	

}
