package game.controller;

/**
 * Controlleur de l'�cran de d�faite.
 * @author SamaHell
 *
 */
public class LooseController {	
	
	/*
	 * Donn�es membres
	 */
		
	private boolean keyTyped;
	private boolean mousePressed;	
	
	
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
	 * M�thodes
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
