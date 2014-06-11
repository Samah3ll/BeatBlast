package game.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlleur de l'écran de pause.
 * @author SamaHell
 *
 */
public class PauseController {
	
	public enum PauseKeys {
		UNPAUSE, MENU
	}
	
	static Map<PauseKeys, Boolean> keys = new HashMap<PauseKeys, Boolean>();
	static {
		keys.put(PauseKeys.UNPAUSE, false);
		keys.put(PauseKeys.MENU, false);
	};
	
	
	public PauseController() {
		keys.put(PauseKeys.UNPAUSE, false);
		keys.put(PauseKeys.MENU, false);
	}
	
	
	
	public boolean isUnpaused() {
		return keys.get(PauseKeys.UNPAUSE);
		
		
	}
	
	public boolean goToMenu() {
		return keys.get(PauseKeys.MENU);
	}
	
	public Map<PauseKeys, Boolean> getKeys() {
		return keys;
	}
	
	
	
	public void pPressed() {
		Long start = System.currentTimeMillis();
		while(System.currentTimeMillis() - start < 400) {
		}
		keys.get(keys.put(PauseKeys.UNPAUSE, true));
		
	}

	public void pReleased() {
		Long start = System.currentTimeMillis();
		while(System.currentTimeMillis() - start < 400) {
		}
		keys.get(keys.put(PauseKeys.UNPAUSE, false));	
		
	}	

	public void mPressed() {
		Long start = System.currentTimeMillis();
		while(System.currentTimeMillis() - start < 400) {
		}
		keys.get(keys.put(PauseKeys.MENU, true));
		
	}

	public void mReleased() {
		Long start = System.currentTimeMillis();
		while(System.currentTimeMillis() - start < 400) {
		}
		keys.get(keys.put(PauseKeys.MENU, false));
		
	}

}
