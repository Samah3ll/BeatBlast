package game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;

import game.controller.MenuController;
import game.controller.MenuController.MenuKeys;
import game.view.MenuRenderer;

/**
 * Ecran du menu avec les bouttons play et quit pour passer � l'�cran suivant ou quitter le jeu.
 * @author SamaHell
 *
 */
public class MenuScreen implements Screen, InputProcessor {
	
	
	private static final float CAMERA_WIDTH = 100f;
	private static final float CAMERA_HEIGHT = 100f;
	
    private float ppuX;
    private float ppuY;
	
	private MenuController controller;
	private MenuRenderer renderer;
	
	private Music music;
	
	Game game;
	
	final String path = System.getProperty("user.dir");
	
	
	/*
	 * Constructeur
	 */
	
	public MenuScreen(Game game) {
		this.game = game;
		renderer = new MenuRenderer();
		controller = new MenuController();
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		FileHandle musicFile = new FileHandle(path + "/res/audio/menu/Korobeinki.mp3");
		music = Gdx.audio.newMusic(musicFile);
		//music.play();
		
	}
	
	/*
	 * M�thodes
	 */
	
	
	private void mouseSelection() {
		//Selection par la sourie
		if(controller.getMousePosition().x > 38f*ppuX && controller.getMousePosition().x < 61f*ppuX
				&& controller.getMousePosition().y > 60f*ppuY && controller.getMousePosition().y < 70f*ppuY) {
			controller.setSelectedButton(1);
		} else if(controller.getMousePosition().x > 35f*ppuX && controller.getMousePosition().x < 66f * ppuX
				&& controller.getMousePosition().y > 80f * ppuY && controller.getMousePosition().y < 90f * ppuY) {
			controller.setSelectedButton(3);
		} else {
			controller.setSelectedButton(0);
		}
	}
	
	/*
	 * implements Screen
	 */
	
	@Override
	public void render(float delta) {
		
		renderer.render();
		 		
		mouseSelection();
		controller.checkSelection();	
		
		if(controller.getKeys().get(MenuKeys.VALIDATE) || controller.getMouseState()) {
			//Si on modifie le switch case il faut aussi modifier la m�thode mouseSelection!
			switch(controller.getSelectedButton()) {
				case (1) : 
					music.stop();
					game.setScreen(new SelectionScreen(game));
					break;
				case (2) :
					//TODO :game.setScreen(new OptionScreen());
					break;
				case (3) :
					game.dispose();
					break;
			}
		}
		

	}
	


	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
        ppuX = (float)width / CAMERA_WIDTH;
        ppuY = (float)height / CAMERA_HEIGHT;

	}

	@Override
	public void show() {
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
		music.pause();

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(null);
		renderer.dispose();
		music.dispose();
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
