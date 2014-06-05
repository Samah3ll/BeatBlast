package game.screens;

import java.util.ArrayList;

import game.BeatBlaster;
import game.controller.ChooseController;
import game.controller.SelectionController.SelectionKeys;
import game.utils.DataSong;
import game.utils.Reader;
import game.view.ChooseRenderer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;

public class ChooseScreen implements Screen, InputProcessor {
	
	private static final float BUTTON_WIDTH = 60f;
	private static final float BUTTON_HEIGHT = 30f;
	private static final float CAMERA_WIDTH = 100f;
	private static final float CAMERA_HEIGHT = 100f;
	
	private int width;
    private int height;
    private float ppuX;
    private float ppuY;
	
	ChooseController controller;
	ChooseRenderer renderer;
	
	Game game;
	
	Reader reader = new Reader();
	
	final String path = System.getProperty("user.dir");
	String saveDirectory;
	ArrayList<String> savedFiles;
	

    //La musique sélectionnée
    private int selectedSong = 0;
    
    private int scrolled = 0;

	
	/*
	 * Constructeur
	 */	
	
	public ChooseScreen(Game game) {
		this.game = game;
		saveDirectory = ((BeatBlaster) game).getSaveDirectory();
		savedFiles = reader.getListFiles(saveDirectory);
		renderer = new ChooseRenderer(savedFiles);
		controller = new ChooseController();
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);	
		
	}
	
	/*
	 * Accesseurs
	 */	
	
	public int getSelectedSong() {
		return selectedSong;
	}
	
	
	/*
	 * Méthodes
	 */	
	
	public void nextSong() {
		selectedSong++;
	}
	
	public void previousSong() {
		selectedSong--;
	}
	
	//Selection par le clavier
	private void keyboardSelection() {
		
		if(controller.getKeys().get(SelectionKeys.UP)) {
			if(selectedSong >= 0) {
				previousSong();
				if(renderer.isFirstSong(selectedSong)) {
					renderer.scrollSelection(-1);
				}
			}
		} else if(controller.getKeys().get(SelectionKeys.DOWN)) {
			if(!renderer.isLastSong(selectedSong)) {
				nextSong();
				if(selectedSong > 7) {
					previousSong();
					renderer.scrollSelection(1);
				}
			}				
			
		}
		
	}
	
	//Selection par la sourie
	private void mouseSelection() {
		if(controller.getMousePosition().x > 37f*ppuX && controller.getMousePosition().x < 5f * ppuX + BUTTON_WIDTH * ppuX
				&& controller.getMousePosition().y > 80f * ppuY && controller.getMousePosition().y < 60f * ppuY + BUTTON_HEIGHT * ppuY) {
			controller.setBackButtonSelected(true);
		} else {
			controller.setBackButtonSelected(false);
		}
	}
	
		
	
	/*
	 * Implements Screen
	 */	

	@Override
	public void render(float delta) {
		renderer.render();
		renderer.higlightButtonN(selectedSong);
		
		keyboardSelection();
		mouseSelection();
		
		if(controller.getKeys().get(SelectionKeys.VALIDATE) || controller.getMouseState()) {
			if(ChooseController.isBackButtonSelected()) {
				game.setScreen(new SelectionScreen(game));
			} else {
				DataSong ds = reader.read(saveDirectory, savedFiles.get(selectedSong + scrolled));
				String musicName = "\\" + savedFiles.get(selectedSong + scrolled);
				musicName = (String) musicName.subSequence(0, musicName.length() - 4);
				System.out.println(musicName);
				FileHandle musicFile = new FileHandle(saveDirectory + musicName);
				Music music = Gdx.audio.newMusic(musicFile);
				game.setScreen(new GameScreen(game, ds, music));
			}
			
			
			
		}

	}

	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
		this.width = width;
        this.height = height;
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
		if(selectedSong + amount < 0) {
			selectedSong = 0;
			renderer.scrollSelection(-1);
			scrolled--;
		} else if(selectedSong + amount > 7) {
			selectedSong = 7;
			renderer.scrollSelection(1);
			scrolled++;
		}else if(selectedSong >= 0 || selectedSong < 7) {
			selectedSong += amount;
		} else {
			return false;
		}
		
		if(scrolled < 0) {
			scrolled = 0;
		} else if(selectedSong + scrolled > savedFiles.size()) {
			scrolled = savedFiles.size() - selectedSong - 1;
		}
		
		return true;
		
	}
	
	
	

}
