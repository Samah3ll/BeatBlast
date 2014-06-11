package game.controller;

/**
 * Controlleur de l'�cran de victoire. Comme l'�cran de d�faite il suffit d'appuyer sur une touche pour revenir au menu principal.
 * @author SamaHell
 *
 */
public class WinController {
	
	private boolean keyTyped;
	private boolean mousePressed;
	
	
	/*
	 * Constructeur
	 */
	
	public WinController() {
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

	public void keyPressed(int screenX, int screenY) {
		mousePressed = true;
		
	}

	public void keyReleased(int screenX, int screenY) {
		mousePressed = false;
		
	}
	

}
