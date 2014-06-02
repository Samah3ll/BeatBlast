package game.screens;

import game.BeatBlaster;
import game.controller.ChooseController;
import game.controller.SelectionController.SelectionKeys;
import game.view.ChooseRenderer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;

public class ChooseScreen implements Screen, InputProcessor {
	
	ChooseController controller;
	ChooseRenderer renderer;
	
	Game game;
	
	final String path = System.getProperty("user.dir");
	String saveDirectory;

	
	/*
	 * Constructeur
	 */	
	
	public ChooseScreen(Game game) {
		this.game = game;
		saveDirectory = ((BeatBlaster) game).getSaveDirectory();
		renderer = new ChooseRenderer(saveDirectory);
		controller = new ChooseController();
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);	
		
	}
	
	
	/*
	 * Méthodes
	 */	
	
	//Selection par le clavier
	private void keyboardSelection() {
		if(controller.getSelectedButton() == 0) {		//If nothing is selected
			if(controller.getKeys().get(SelectionKeys.RIGHT)) {
				controller.setSelectedButton(1);		//Ok button is selected				
			} else if(controller.getKeys().get(SelectionKeys.UP)) {
				controller.setSelectedButton(2);		//Back button is selected
			} else if(controller.getKeys().get(SelectionKeys.LEFT)) {
				controller.setSelectedButton(3);		//MusicList is selected
			} else if(controller.getKeys().get(SelectionKeys.DOWN)) {
				controller.setSelectedButton(1);		//Ok button is selected
			}
		} else if(controller.getSelectedButton() == 1) {		//If Ok is selected
			if(controller.getKeys().get(SelectionKeys.RIGHT)) {
				controller.setSelectedButton(3);		//MusicList is selected				
			} else if(controller.getKeys().get(SelectionKeys.UP)) {
				controller.setSelectedButton(2);		//Back button is selected
			} else if(controller.getKeys().get(SelectionKeys.LEFT)) {
				controller.setSelectedButton(3);		//MusicList is selected
			} else if(controller.getKeys().get(SelectionKeys.DOWN)) {
				controller.setSelectedButton(2);		//Back button is selected
			}
		} else if(controller.getSelectedButton() == 2) {		//If Back is selected
			if(controller.getKeys().get(SelectionKeys.RIGHT)) {
				controller.setSelectedButton(3);		//MusicList is selected				
			} else if(controller.getKeys().get(SelectionKeys.UP)) {
				controller.setSelectedButton(1);		//Ok button is selected
			} else if(controller.getKeys().get(SelectionKeys.LEFT)) {
				controller.setSelectedButton(3);		//MusicList is selected
			} else if(controller.getKeys().get(SelectionKeys.DOWN)) {
				controller.setSelectedButton(1);		//Ok button is selected
			}
		} else if(controller.getSelectedButton() == 3) {		//If MusicList is selected
			if(controller.getKeys().get(SelectionKeys.RIGHT)) {
				controller.setSelectedButton(1);		//Ok button is selected				
			} else if(controller.getKeys().get(SelectionKeys.UP)) {
				//TODO move selection in musicList
			} else if(controller.getKeys().get(SelectionKeys.LEFT)) {
				//TODO move selection in musicList
			} else if(controller.getKeys().get(SelectionKeys.DOWN)) {
				controller.setSelectedButton(1);		//Ok button is selected
			}
		}
	}
	
	//Selection par la sourie
	private void mouseSelection() {
		//La sourie est sur le boutton ok
		if(controller.getMousePosition().x > 160 && controller.getMousePosition().x < 257
				&& controller.getMousePosition().y > 260 && controller.getMousePosition().y < 310) {
			controller.setSelectedButton(1);
		}
		//La sourie est sur le boutton back
		if(controller.getMousePosition().x > 275 && controller.getMousePosition().x < 445
				&& controller.getMousePosition().y > 335 && controller.getMousePosition().y < 385) {
			controller.setSelectedButton(2);
		}
	}
	
	/*
	 * Implements Screen
	 */	

	@Override
	public void render(float delta) {
		renderer.render();
		
		keyboardSelection();
		mouseSelection();
		controller.checkSelection();
		
		if(controller.getKeys().get(SelectionKeys.VALIDATE) || controller.getMouseState()) {
			switch(controller.getSelectedButton()) {
				case (1) : 
					game.setScreen(new SelectionScreen(game));
					break;
				case (2) :
					game.setScreen(new SelectionScreen(game));
					break;
				case (3) :
					
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
        if (keycode == Keys.LEFT)
            controller.leftPressed();
        if (keycode == Keys.RIGHT)
            controller.rightPressed();
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
		if(keycode == Keys.LEFT)
			controller.leftReleased();
		if(keycode == Keys.RIGHT)
			controller.rightReleased();
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
		controller.scrolled(amount);
		return false;
	}
	
	
	

}
