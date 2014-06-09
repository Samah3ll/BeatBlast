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
	
	static private boolean musicListSelected;
	
	static boolean mouseIsPressed;
	static boolean isBackButtonSelected;
	
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
	 * Constructeur
	 */
	
	public ChooseController() {
		musicListSelected = false;
		mouseIsPressed = false;
		isBackButtonSelected = false;
		mousePosition.set(0, 0);
	}
	
	/*
	 * Accesseurs
	 */
	
	
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
	
	public Vector2 getMousePosition() {
		return mousePosition;
	}
	
	static public boolean isBackButtonSelected() {
		return isBackButtonSelected;
	}
	
	public void setBackButtonSelected(boolean b) {
		ChooseController.isBackButtonSelected = b;
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
		//System.out.println("mouse position : " + mousePosition);
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

		
	

}
