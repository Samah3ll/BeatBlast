package game.screens;

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
 * Ecran de jeux avec un monde. C'est l'�cran ou on joue (d�placer le runner).
 * @author SamaHell
 *
 */
public class GameScreen implements Screen, InputProcessor {
	
	private World world;
	private WorldRenderer renderer;
	private RunnerController controller;
	
	Game game;
	
	
	public GameScreen(Game game) {
		this.game = game;
		world = new World(/*inputData*/);
		renderer = new WorldRenderer(world);
		controller = new RunnerController(world);
		
	}

	@Override
	public void render(float delta) {
		
		if(controller.isPaused()) {					
			game.pause();
			this.hide();
			game.setScreen(new PauseScreen(game, this));
		}else if(!controller.isPaused()) {
			renderer.moveCamera();		
			controller.update(delta);
			renderer.render();
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
		
		//world = new World(/*inputData*/);
		//renderer = new WorldRenderer(world);
		//controller = new RunnerController(world);
		
		Gdx.input.setInputProcessor(this);

	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);

	}

	@Override
	public void pause() {
		renderer.pause();
		world.getRunner().getAcceleration().x = 0;
		world.getRunner().getAcceleration().y = 0;
		world.getRunner().getVelocity().x = 0;
		world.getRunner().getVelocity().y = 0;

	}

	@Override
	public void resume() {
		game.resume();
		game.setScreen(this);
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		controller.getKeys().put(RunnerController.Keys.PAUSE, false);
		Gdx.input.setInputProcessor(this);
		//renderer.resume();
	}
	
	public void resume(float delta) {
		game.setScreen(this);
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		controller.getKeys().put(RunnerController.Keys.PAUSE, false);
		Gdx.input.setInputProcessor(this);
		//renderer.resume();
		//render(delta);

	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
		renderer.dispose();

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
	    if (keycode == Keys.P)
	        controller.pPressed();
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
	        if (keycode == Keys.P)
		        controller.pReleased();
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
