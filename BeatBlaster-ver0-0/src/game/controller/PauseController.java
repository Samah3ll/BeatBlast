package game.controller;

import java.util.HashMap;
import java.util.Map;

public class PauseController {
	
	public enum PauseKeys {
		UNPAUSE
	}
	
	static Map<PauseKeys, Boolean> keys = new HashMap<PauseKeys, Boolean>();
	static {
		keys.put(PauseKeys.UNPAUSE, false);
	};
	
	
	public boolean isUnpaused() {
		return keys.get(PauseKeys.UNPAUSE);
		
		
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
	
	public Map<PauseKeys, Boolean> getKeys() {
		return keys;
	}

}
