package game.controller;

import java.util.HashMap;
import java.util.Map;

public class LooseController {	
	/*
	 * Données membres
	 */
	public enum LooseKeys {
		LEFT, RIGHT, JUMP, FIRE, DOWN, PAUSE
	}
	
	private boolean keyTyped = false;
	private boolean mousePressed = false;
	
	static Map<LooseKeys, Boolean> keys = new HashMap<LooseController.LooseKeys, Boolean>();
	static {
		keys.put(LooseKeys.LEFT, false);
		keys.put(LooseKeys.RIGHT, false);
		keys.put(LooseKeys.JUMP, false);
		keys.put(LooseKeys.FIRE, false);
		keys.put(LooseKeys.DOWN, false);
		keys.put(LooseKeys.PAUSE, false);
	};
	
	/*
	 * Constructeur
	 */
	
	public LooseController() {
		keyTyped = false;
		mousePressed = false;
	}
	
	/*
	 * Accesseurs
	 */
	
	public boolean isKeyTyped() {
		return keyTyped;
	}
	
	public boolean isMousePressed() {
		return mousePressed;
	}
	
	/*
	 * Méthodes
	 */

	public void keyTyped(char character) {
		keyTyped = true;
		
	}

	public void mouseButtonPressed(int screenX, int screenY) {
		mousePressed = true;
		
	}

	public void mouseButtonReleased(int screenX, int screenY) {
		mousePressed = false;
		
	}

}
