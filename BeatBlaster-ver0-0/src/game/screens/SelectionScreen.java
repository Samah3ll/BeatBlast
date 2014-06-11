package game.screens;

import music.beatroot.BeatRoot;
import game.controller.SelectionController;
import game.controller.SelectionController.SelectionKeys;
import game.view.SelectionRenderer;
import game.BeatBlaster;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;

/**
 * Ecran de sélection.
 * @author SamaHell
 *
 */
public class SelectionScreen implements Screen, InputProcessor {
	
	private static final float BUTTON_WIDTH = 60f;
	private static final float BUTTON_HEIGHT = 30f;
	private static final float CAMERA_WIDTH = 100f;
	private static final float CAMERA_HEIGHT = 100f;
	
    private float ppuX;
    private float ppuY;
	
	SelectionController controller;
	SelectionRenderer renderer;
	
	Game game;
	
	//Reader reader = new Reader();
	
	Music selectedMusic;
	final String path = System.getProperty("user.dir");

	
	/*
	 * Constructeur
	 */
	
	public SelectionScreen(Game game) {
		this.game = game;
		renderer = new SelectionRenderer();
		controller = new SelectionController();
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		FileHandle musicFile = new FileHandle( path + "/res/audio/menu/Korobeinki.mp3");
		selectedMusic = Gdx.audio.newMusic(musicFile);
	}
	
	/*
	 * Méthodes
	 */
	
		
	//Selection par la sourie
	private void mouseSelection() {
		//TODO modifier les valeurs pour qu'elles collent à cet écran.
		
		//La sourie est sur le boutton select
		if(controller.getMousePosition().x > 20 * ppuX && controller.getMousePosition().x < 20 * ppuX + BUTTON_WIDTH * ppuX
				&& controller.getMousePosition().y > 20 * ppuY && controller.getMousePosition().y < 5 * ppuY + BUTTON_HEIGHT * ppuY) {
			controller.setSelectedButton(1);
		} else if(controller.getMousePosition().x > 25 * ppuX && controller.getMousePosition().x < 15 * ppuX + BUTTON_WIDTH * ppuX	//La sourie est sur le boutton choose
				&& controller.getMousePosition().y > 40 * ppuY && controller.getMousePosition().y < 25 * ppuY + BUTTON_HEIGHT * ppuY) {
			controller.setSelectedButton(2);
		} else if(controller.getMousePosition().x > 36 * ppuX && controller.getMousePosition().x < 5 * ppuX + BUTTON_WIDTH * ppuX //La sourie est sur le boutton back
				&& controller.getMousePosition().y > 79 * ppuY && controller.getMousePosition().y < 60 * ppuY + BUTTON_HEIGHT * ppuY) {
			controller.setSelectedButton(3);
		} else {
			controller.setSelectedButton(0);
		}
	}
	
	/*
	 * Implements Screen
	 */
	
	@Override
	public void render(float delta) {
		renderer.render();
		
		mouseSelection();
		//keyboardSelection();
		controller.checkSelection();
		
		if(controller.getKeys().get(SelectionKeys.VALIDATE) || controller.getMouseState()) {
			switch(controller.getSelectedButton()) {
				case (1) : 
					BeatBlaster.beatroot.getGui().loadAudioData();
					if(BeatBlaster.beatroot.getVisu()){
						BeatBlaster.beatroot.gui.getDisplayPanel().resizeSpectroForVisu(12);
						BeatBlaster.beatroot.gui.getDisplayPanel().repaintImage();
					}
					game.setScreen(new SelectionScreen(game));
					break;
				case (2) :
					game.setScreen(new ChooseScreen(game));
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
        ppuX = (float)width / CAMERA_WIDTH;
        ppuY = (float)height / CAMERA_HEIGHT;

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
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		controller.mouseMouved(screenX, screenY);
		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	
}
