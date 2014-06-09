package game.controller;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;

public class MenuController {
	
	public enum MenuKeys {
		LEFT, RIGHT, VALIDATE, DOWN, UP
	}
	
	private final long WAITTIME = 300;
	
	static boolean mouseIsPressed;
	
	static boolean playButtonSelected;
	static boolean optionButtonSelected;
	static boolean quitButtonSelected;
	
	//Désigne le boutton selectionné : 0 = pas de sélection; 1 = play; 2 = option; 3 = quit.
	private int selectedButton;
	
	static Vector2 mousePosition = new Vector2(0, 0);
	
	static Map<MenuKeys, Boolean> keys = new HashMap<MenuController.MenuKeys, Boolean>();
	static {
		keys.put(MenuKeys.LEFT, false);
		keys.put(MenuKeys.RIGHT, false);
		keys.put(MenuKeys.VALIDATE, false);
		keys.put(MenuKeys.DOWN, false);
		keys.put(MenuKeys.UP, false);
	};
	
	/*
	 * Constructeur
	 */
	
	public MenuController() {
		playButtonSelected = false;
		optionButtonSelected = false;
		quitButtonSelected = false;
		mouseIsPressed = false;
		selectedButton = 0;
		mousePosition.set(0, 0);
	}
	
	/*
	 * Accesseurs
	 */
	
	public Vector2 getMousePosition() {
		return mousePosition;
	}
	
	public boolean getMouseState() {
		return mouseIsPressed;
	}
	
	static public boolean isPlayButtonSelected() {
		return playButtonSelected;
	}
	
	public void setPlayButtonSelected(boolean b) {
		MenuController.playButtonSelected = b;
	}
	
	static public boolean isOptionButtonSelected() {
		return optionButtonSelected;
	}
	
	public void setOptionButtonSelected(boolean b) {
		MenuController.optionButtonSelected = b;
	}
	
	static public boolean isQuitButtonSelected() {
		return quitButtonSelected;
	}
	
	public void setQuitButtonSelected(boolean b) {
		MenuController.quitButtonSelected = b;
	}
	
	public int getSelectedButton() {
		return selectedButton;
	}
	
	public void setSelectedButton(int s) {
		this.selectedButton = s;
	}
	
	/*
	 * Méthodes
	 */

	public void upPressed() {
		Long start = System.currentTimeMillis();
		while(System.currentTimeMillis() - start < WAITTIME ) {
		}
		keys.get(keys.put(MenuKeys.UP, true));
		
	}

	public void downPressed() {
		Long start = System.currentTimeMillis();
		while(System.currentTimeMillis() - start < WAITTIME) {
		}
		keys.get(keys.put(MenuKeys.DOWN, true));
	}

	public void enterPressed() {
		keys.get(keys.put(MenuKeys.VALIDATE, true));
		
	}

	public void upReleased() {
		keys.get(keys.put(MenuKeys.UP, false));
		
	}

	public void downReleased() {
		keys.get(keys.put(MenuKeys.DOWN, false));
		
		
	}

	public void enterReleased() {
		keys.get(keys.put(MenuKeys.VALIDATE, false));
		
	}
	
	public Map<MenuKeys, Boolean> getKeys() {
		return keys;
	}

	public void mouseMouved(int screenX, int screenY) {
		mousePosition.x = screenX;
		mousePosition.y = screenY;
		//System.out.println(mousePosition);
	}
	
	

	public void mouseButtonPressed(int x, int y) {
		mouseIsPressed = true;
		
	}

	public void mouseButtonReleased(int screenX, int screenY) {
		mouseIsPressed = false;
		
	}

	public void checkSelection() {
		switch (selectedButton) {
			case 1 :
				playButtonSelected = true;
				optionButtonSelected = false;
				quitButtonSelected = false;
				break;
			case 2 :
				playButtonSelected = false;
				optionButtonSelected = true;
				quitButtonSelected = false;
				break;
			case 3 :
				playButtonSelected = false;
				optionButtonSelected = false;
				quitButtonSelected = true;
				break;
			default :
				playButtonSelected = false;
				optionButtonSelected = false;
				quitButtonSelected = false;
				break;
		}
		
	}
	

}
