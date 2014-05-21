package game.screens;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;

import game.controller.RunnerController;
import game.model.World;
import game.view.WorldRenderer;

/**
 * Ecran de jeux avec un monde. C'est l'écran ou on joue (déplacer le runner).
 * @author SamaHell
 *
 */
public class GameScreen implements Screen, InputProcessor {
	
	private World world;
	private WorldRenderer renderer;
	private RunnerController controller;
	
	//private int width, height;
	
	Game game;
	
	
	public GameScreen(Game game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Map<RunnerController.Keys, Boolean> m = controller.getKeys();
		renderer.moveCamera(m.get(RunnerController.Keys.LEFT), m.get(RunnerController.Keys.JUMP),
				m.get(RunnerController.Keys.RIGHT), m.get(RunnerController.Keys.DOWN));		
		controller.update(delta);
		renderer.render();
		

	}

	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
		//this.width = width;
		//this.height = height;

	}

	@Override
	public void show() {
		world = new World();
		renderer = new WorldRenderer(world);
		controller = new RunnerController(world);
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

	}

	@Override
	public boolean keyDown(int keycode){
		if (keycode == Keys.LEFT)
	        controller.leftPressed();
	    if (keycode == Keys.RIGHT)
	        controller.rightPressed();
	    if (keycode == Keys.Z || keycode == Keys.UP)
	        controller.jumpPressed();
	    if (keycode == Keys.X)
	        controller.firePressed();
	    if (keycode == Keys.DOWN)
	        controller.downPressed();
	    return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.LEFT)
	           controller.leftReleased();
	        if (keycode == Keys.RIGHT)
	            controller.rightReleased();
	        if (keycode == Keys.Z  || keycode == Keys.UP)
	            controller.jumpReleased();
	        if (keycode == Keys.X)
	            controller.fireReleased();
	        if (keycode == Keys.DOWN)
		        controller.downReleased();
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
