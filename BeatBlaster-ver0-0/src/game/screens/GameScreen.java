package game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;

import game.controller.RunnerController;
import game.model.World;
import game.utils.DataSong;
import game.view.WorldRenderer;

/**
 * Ecran de jeux avec un monde. C'est l'�cran ou on joue (d�placer le runner).
 * On utilise les fl�ches pour d�placer le Runner. L'objectif est de r�cuperer un maximum de pi�ces en passant dessus.
 * @author SamaHell
 *
 */
public class GameScreen implements Screen, InputProcessor {
	
	private World world;
	private WorldRenderer renderer;
	private RunnerController controller;
	private double currentTime;
	private boolean isMusicPlaid = false;
	
	Game game;
	
	Music selectedMusic;
	long musicLength;
			
	
	
	public GameScreen(Game game, DataSong ds, Music selectedMusic) {
		this.game = game;
		this.selectedMusic = selectedMusic;
		this.world = new World(ds);
		this.musicLength = (long) ds.getMaxTimeSong();
		//System.out.println("music length : " + musicLength);
		this.renderer = new WorldRenderer(world);
		this.controller = new RunnerController(world);
		this.selectedMusic.setVolume(0.6f);
		currentTime = System.currentTimeMillis();
	}

	@Override
	public void render(float delta) {		
		
		//D�cide de quand la musique d�marre
		if(!isMusicPlaid && System.currentTimeMillis() - currentTime > world.getLevelGenerator().getTimeBeforeMusicBegin()){
			selectedMusic.play();
			isMusicPlaid=true;
		}
		
		//Correspond � la position de la musique
		float whereShouldBeRunnerWithMusic = selectedMusic.getPosition()*world.getLevelGenerator().getCoeff()+world.getLevelGenerator().getnbBlocksBefore();
		float nbBlocksBeforeRunner = 5; //5 blocks avant l'endroit o� le runner devrait �tre
		
		renderer.setMusicPosition(selectedMusic.getPosition(), musicLength);
		//Permet au renderer de savoir jusqu'o� effacer les blocks
		if(whereShouldBeRunnerWithMusic > world.getLevelGenerator().getnbBlocksBefore()){
			renderer.setX(whereShouldBeRunnerWithMusic -nbBlocksBeforeRunner);
			controller.setX(whereShouldBeRunnerWithMusic -nbBlocksBeforeRunner);
			
			//Supprime les blocks du monde
			world.deleteBlocks(whereShouldBeRunnerWithMusic -nbBlocksBeforeRunner);
		}
		
		if(controller.isDead()) {
			this.pause();
			renderer.hide();
			game.setScreen(new LooseScreen(game, world.getPoint()));
		} else if(controller.isPaused()) {					
			this.pause();
			game.setScreen(new PauseScreen(game, this));
		} else if(selectedMusic.getPosition() >= musicLength)  {
			this.pause();
			renderer.hide();			
			game.setScreen(new WinScreen(game, world.getPoint()));
		} else {
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
		Gdx.input.setInputProcessor(this);

	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
		renderer.pause();
		selectedMusic.pause();
		world.getRunner().getAcceleration().x = 0;
		world.getRunner().getAcceleration().y = 0;
		world.getRunner().getVelocity().x = 0;
		world.getRunner().getVelocity().y = 0;

	}

	@Override
	public void resume() {
		renderer.resume();
		game.setScreen(this);
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		selectedMusic.play();
		controller.getKeys().put(RunnerController.Keys.PAUSE, false);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
		renderer.dispose();
		selectedMusic.stop();
		selectedMusic.dispose();

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
