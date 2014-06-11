package game.screens;

import game.controller.PauseController;
import game.controller.PauseController.PauseKeys;
import game.view.PauseRenderer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

/**
 * Ecran de pause, la conception de cet écran est très simpliste : l'écran de jeu est freezé, il faut appuyer sur "p" pour retourner 
 * au jeu ou sur "m" pour retourner au menu.
 * @author SamaHell
 *
 */
public class PauseScreen implements Screen, InputProcessor {
	
	GameScreen oldScreen;
	Game game;
	PauseRenderer renderer;
	PauseController controller;
	
	public PauseScreen(Game game, GameScreen screen) {
		this.game = game;
		this.oldScreen = screen;
	}
	
	/*
	 * Implements Screen
	 */

	@Override
	public void render(float delta) {
		 if(controller.isUnpaused()) {
			controller.getKeys().put(PauseKeys.UNPAUSE, false);
			oldScreen.resume();
		} else if(controller.goToMenu()) {
			controller.getKeys().put(PauseKeys.MENU, false);
			//oldScreen.hide();
			game.setScreen(new MenuScreen(game));
		} else {
			renderer.render();
		}
		

	}

	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);

	}

	@Override
	public void show() {
		renderer = new PauseRenderer();
		controller = new PauseController();
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
	 * implements InputProcessor
	 */

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.P) {
			controller.pPressed();
		}
		if(keycode == Keys.M) {
			controller.mPressed();
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.P) {
			controller.pReleased();
		}
		if(keycode == Keys.M) {
			controller.mReleased();
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
