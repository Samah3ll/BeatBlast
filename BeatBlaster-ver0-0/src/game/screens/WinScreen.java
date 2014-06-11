package game.screens;

import game.controller.WinController;
import game.view.WinRenderer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;

public class WinScreen implements InputProcessor, Screen {
	
	Game game;
	WinController controller;
	WinRenderer renderer;	
	
	
	public WinScreen(Game game, long score) {
		this.game = game;
		this.controller = new WinController();
		this.renderer = new WinRenderer(score);
	}

	/*
	 * Implements Screen
	 */
	
	@Override
	public void render(float delta) {
		renderer.render();
		if(controller.isKeyTyped() || controller.isMousePressed()) {
			game.setScreen(new MenuScreen(game));
		}

	}

	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);

	}

	@Override
	public void show() {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);		
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
		controller = null;
		renderer.dispose();
		renderer = null;

	}

	/*
	 * Implements InputProcessor
	 */
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		controller.keyTyped(character);
		return true;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		controller.keyPressed(screenX, screenY);
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		controller.keyReleased(screenX, screenY);
		return true;
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
