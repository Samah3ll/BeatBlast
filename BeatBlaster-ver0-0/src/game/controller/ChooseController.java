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
	

	
	public Map<SelectionKeys, Boolean> getKeys() {
		return keys;
	}
	
	
	
	/*
	 * M�thodes
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

	public void scrolled(int amount) {
		// TODO Auto-generated method stub
		
	}

}
