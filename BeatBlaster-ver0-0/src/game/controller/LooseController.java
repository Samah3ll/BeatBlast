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
	 * Accesseurs
	 */
	
	public boolean isKeyTyped() {
		return keyTyped;
	}
	
	/*
	 * Méthodes
	 */

	public void keyTyped(char character) {
		keyTyped = true;
		
	}

}
