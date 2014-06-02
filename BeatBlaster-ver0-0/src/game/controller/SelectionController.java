package game.controller;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;

public class SelectionController {
	
	public enum SelectionKeys {
		LEFT, RIGHT, VALIDATE, DOWN, UP
	}

	private final long WAITTIME = 300;
	
	static boolean mouseIsPressed = false;
	
	static boolean playButtonSelected = false;
	static boolean selectButtonSelected = false;
	static boolean backButtonSelected = false;
	static boolean chooseButtonSelected = false;
	
	//Désigne le boutton selectionné : 0 = pas de sélection; 1 = select; 2 = choose; 3 = play; 4 = back.
	private int selectedButton = 0;

	
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
		SelectionController.playButtonSelected = b;
	}
	
	static public boolean isBackButtonSelected() {
		return backButtonSelected;
	}
	
	public void setBackButtonSelected(boolean b) {
		SelectionController.backButtonSelected = b;
	}
	
	static public boolean isSelectButtonSelected() {
		return selectButtonSelected;
	}
	
	public void setSelectButtonSelected(boolean b) {
		SelectionController.selectButtonSelected = b;
	}
	
	static public boolean isChooseButtonSelected() {
		return chooseButtonSelected;
	}
	
	public void setChooseButtonSelected(boolean b) {
		SelectionController.chooseButtonSelected = b;
	}
	
	public int getSelectedButton() {
		return selectedButton;
	}
	
	public void setSelectedButton(int s) {
		this.selectedButton = s;
	}
	
	public Map<SelectionKeys, Boolean> getKeys() {
		return keys;
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
	
	
	public void checkSelection() {
		switch (selectedButton) {
			case 1 :
				selectButtonSelected = true;
				chooseButtonSelected = false;
				playButtonSelected = false;				
				backButtonSelected = false;
				break;
			case 2 :
				selectButtonSelected = false;
				chooseButtonSelected = true;
				playButtonSelected = false;				
				backButtonSelected = false;
				break;
			case 3 :
				selectButtonSelected = false;
				chooseButtonSelected = false;
				playButtonSelected = true;				
				backButtonSelected = false;
				break;
			case 4 :
				selectButtonSelected = false;
				chooseButtonSelected = false;
				playButtonSelected = false;				
				backButtonSelected = true;
				break;
			default :
				selectButtonSelected = false;
				chooseButtonSelected = false;
				playButtonSelected = false;				
				backButtonSelected = false;
				break;
		}
		
	}

}
