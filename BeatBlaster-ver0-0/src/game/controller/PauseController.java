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

}
