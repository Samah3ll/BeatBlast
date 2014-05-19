package game.screens;

import game.BeatBlaster;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import game.controller.MenuController;
import game.controller.MenuController.MenuKeys;
import game.view.MenuRenderer;

/**
 * Ecran du menu avec les bouttons play et quit.
 * @author SamaHell
 *
 */
public class MenuScreen implements Screen, InputProcessor {
	
	 private static final float BUTTON_WIDTH = 60f;
	 private static final float BUTTON_HEIGHT = 20f;
	 private static final float BUTTON_SPACING = 10f;
	
	private MenuController controller;
	private MenuRenderer renderer;
	
	private int width, height;
	
	BeatBlaster game;
	
	
	public MenuScreen(Game game) {
		this.game = (BeatBlaster) game;
	}
	
	/*
	 * implements Screen
	 */
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		renderer.render();
		
		
		mouseSelection();
		keyboardSelection();
		controller.checkSelection();	
		
		if(controller.getKeys().get(MenuKeys.VALIDATE)) {
			switch(controller.getSelectedButton()) {
				case (1) : 
					game.setScreen(new GameScreen(game));
					break;
				case (2) :
					//TODO :game.setScreen(new OptionScreen());
					break;
				case (3) :
					game.dispose();
			}
		}
		

	}
	
	private void keyboardSelection() {
	//Selection par le clavier
	//TODO : modififer les valeur une fois le bouton option ajouté
		if(controller.getSelectedButton() == 0) {		//If nothing is selected
			if(controller.getKeys().get(MenuKeys.DOWN)) {		
				controller.setSelectedButton(1);		//Play button selected
			} else if(controller.getKeys().get(MenuKeys.UP)) {		
				controller.setSelectedButton(3);		//Quit button selected
			}
		} else if(controller.getSelectedButton() == 1) {	//If Play button is selected
			if(controller.getKeys().get(MenuKeys.DOWN)) {		
				controller.setSelectedButton(3);		//Option button selected
			} else if(controller.getKeys().get(MenuKeys.UP)) {		
				controller.setSelectedButton(3);		//Quit button selected
			}
		}/* else if(controller.getSelectedButton() == 2) {	//If Option button is selected
			if(controller.getKeys().get(MenuKeys.DOWN)) {
				//Quit button selected
				controller.setSelectedButton(3);
			} else if(controller.getKeys().get(MenuKeys.UP)) {
				//Play button selected
				controller.setSelectedButton(1);
			}
		}*/ else if(controller.getSelectedButton() == 3) {	//If Quit button is selected
				if(controller.getKeys().get(MenuKeys.DOWN)) {					
					controller.setSelectedButton(1);			//Play button selected
				} else if(controller.getKeys().get(MenuKeys.UP)) {						
						controller.setSelectedButton(1);		//Option button selected
					}
			} else {
				//Nothing is selected
				controller.setSelectedButton(0);
			}
	}
	
	private void mouseSelection() {
		//Selection par la sourie
		if(controller.getMousePosition().x > 255 && controller.getMousePosition().x < 465
				&& controller.getMousePosition().y > 65 && controller.getMousePosition().y < 112) {
			controller.setSelectedButton(1);
			if(controller.getMouseState()) {
				game.setScreen(new GameScreen(game));
			}
		}
			
		if(controller.getMousePosition().x > 276 && controller.getMousePosition().x < 440
				&& controller.getMousePosition().y > 335 && controller.getMousePosition().y < 380) {
			controller.setSelectedButton(3);
			if(controller.getMouseState()) {
				game.dispose();
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
		this.width = width;
		this.height = height;

	}

	@Override
	public void show() {
		renderer = new MenuRenderer();
		controller = new MenuController();
		Gdx.input.setInputProcessor(this);

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(null);

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(null);
		//System.out.println("MenuSceen -> dispose");
	}
	
	/*
	 * implements InputProcessor
	 */

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.UP)
	        controller.upPressed();
        if (keycode == Keys.DOWN)
            controller.downPressed();
        if (keycode == Keys.ENTER)
            controller.enterPressed();
        return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.UP)
			controller.upReleased();
		if(keycode == Keys.DOWN)
			controller.downReleased();
		if(keycode == Keys.ENTER)
			controller.enterReleased();
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		controller.mouseButtonPressed(screenX, screenY);
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		controller.mouseButtonReleased(screenX, screenY);
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		controller.mouseMouved(screenX, screenY);
		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	

}
