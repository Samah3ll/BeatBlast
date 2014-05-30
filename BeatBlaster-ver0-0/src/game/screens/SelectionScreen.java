package game.screens;

import game.controller.SelectionController;
import game.controller.SelectionController.SelectionKeys;
import game.view.SelectionRenderer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;

public class SelectionScreen implements Screen, InputProcessor {
	
	SelectionController controller;
	SelectionRenderer renderer;
	
	Game game;
	
	Music selectedMusic;
	final String path = System.getProperty("user.dir");
	
	public SelectionScreen(Game game) {
		this.game = game;
		renderer = new SelectionRenderer();
		controller = new SelectionController();
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		FileHandle musicFile = new FileHandle(path + "/res/audio/menu/Korobeinki.mp3");
		selectedMusic = Gdx.audio.newMusic(musicFile);
	}
	
	private void keyboardSelection() {
		//Selection par le clavier
			if(controller.getSelectedButton() == 0) {		//If nothing is selected
				if(controller.getKeys().get(SelectionKeys.DOWN)) {		
					controller.setSelectedButton(1);		//Selection button selected
				} else if(controller.getKeys().get(SelectionKeys.UP)) {		
					controller.setSelectedButton(3);		//Back button selected
				}
			} else if(controller.getSelectedButton() == 1) {	//If Selection button is selected
				if(controller.getKeys().get(SelectionKeys.DOWN)) {		
					controller.setSelectedButton(2);		//Play button selected
				} else if(controller.getKeys().get(SelectionKeys.UP)) {		
					controller.setSelectedButton(3);		//Back button selected
				}
			} else if(controller.getSelectedButton() == 2) {	//If Play button is selected
				if(controller.getKeys().get(SelectionKeys.DOWN)) {
					//Back button selected
					controller.setSelectedButton(3);
				} else if(controller.getKeys().get(SelectionKeys.UP)) {
					//Selection button selected
					controller.setSelectedButton(1);
				}
			} else if(controller.getSelectedButton() == 3) {	//If Back button is selected
					if(controller.getKeys().get(SelectionKeys.DOWN)) {					
						controller.setSelectedButton(1);			//Selection button selected
					} else if(controller.getKeys().get(SelectionKeys.UP)) {						
							controller.setSelectedButton(2);		//Play button selected
						}
				} else {
					//Nothing is selected
					controller.setSelectedButton(0);
				}
	}
	
	private void mouseSelection() {
		//TODO : modifier les valeurs pour qu'elles collent � cet �cran.
		//Selection par la sourie
		if(controller.getMousePosition().x > 165 && controller.getMousePosition().x < 555
				&& controller.getMousePosition().y > 10 && controller.getMousePosition().y < 135) {
			controller.setSelectedButton(1);
			if(controller.getMouseState()) {
				//TODO : ouvrir la fenetre de selection de la musique
			}
		}
		
		if(controller.getMousePosition().x > 257 && controller.getMousePosition().x < 460
				&& controller.getMousePosition().y > 310 && controller.getMousePosition().y < 360) {
			controller.setSelectedButton(2);
			if(controller.getMouseState()) {
				game.setScreen(new GameScreen(game, selectedMusic));
			}
		}
			
		if(controller.getMousePosition().x > 525 && controller.getMousePosition().x < 700
				&& controller.getMousePosition().y > 355 && controller.getMousePosition().y < 400) {
			controller.setSelectedButton(3);
			if(controller.getMouseState()) {
				game.setScreen(new MenuScreen(game));
			}
		}
	}
	
	
	/*
	 * Implements Screen
	 */
	
	@Override
	public void render(float delta) {
		renderer.render();
		
		mouseSelection();
		keyboardSelection();
		controller.checkSelection();
		
		if(controller.getKeys().get(SelectionKeys.VALIDATE)) {
			//Si on modifie le switch case il faut aussi modifier la m�thode mouseSelection!
			switch(controller.getSelectedButton()) {
				case (1) : 
					//TODO:selection de la musique
					break;
				case (2) :
					game.setScreen(new GameScreen(game, selectedMusic));
					break;
				case (3) :
					game.setScreen(new MenuScreen(game));
					break;
			}
			
		}		
		
	}

	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);

	}

	@Override
	public void show() {
		
		Gdx.input.setInputProcessor(this);

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

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
		Gdx.input.setInputProcessor(null);
		renderer.dispose();

	}

	/*
	 * Implements InputProcessor
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
		// TODO Auto-generated method stub
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
