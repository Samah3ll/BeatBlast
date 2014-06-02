package game.screens;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

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
import music.beatroot.BeatRoot;

public class SelectionScreen implements Screen, InputProcessor {
	
	SelectionController controller;
	SelectionRenderer renderer;
	
	Game game;
	
	Music selectedMusic;
	final String path = System.getProperty("user.dir");
	String saveDirectory =  ((BeatBlaster) game).getSaveDirectory();
	
	HashMap<String, FileHandle> savedMusic = new HashMap<String, FileHandle>();
	HashMap<String, String> savedMusicData = new HashMap<String, String>();
	
	/*
	 * Constructeur
	 */
	
	public SelectionScreen(Game game) {
		this.game = game;
		renderer = new SelectionRenderer();
		controller = new SelectionController();
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		FileHandle musicFile = new FileHandle(path + "/res/audio/menu/Korobeinki.mp3");
		selectedMusic = Gdx.audio.newMusic(musicFile);
		
		saveMusic("music");
	}
	
	/*
	 * M�thodes
	 */
	
	private void keyboardSelection() {
		//Selection par le clavier
			if(controller.getSelectedButton() == 0) {		//If nothing is selected
				if(controller.getKeys().get(SelectionKeys.DOWN)) {		
					controller.setSelectedButton(1);		//Selection button selected
				} else if(controller.getKeys().get(SelectionKeys.UP)) {		
					controller.setSelectedButton(4);		//Back button selected
				}
			} else if(controller.getSelectedButton() == 1) {	//If Selection button is selected
				if(controller.getKeys().get(SelectionKeys.DOWN)) {		
					controller.setSelectedButton(2);		//Choose button selected
				} else if(controller.getKeys().get(SelectionKeys.UP)) {		
					controller.setSelectedButton(4);		//Back button selected
				}
			} else if(controller.getSelectedButton() == 2) {	//If Choose button is selected
				if(controller.getKeys().get(SelectionKeys.DOWN)) {
					controller.setSelectedButton(3);		//PLay button selected
				} else if(controller.getKeys().get(SelectionKeys.UP)) {					
					controller.setSelectedButton(1);		//Selection button selected
				}
			} else if(controller.getSelectedButton() == 3) {	//If PLay button is selected
					if(controller.getKeys().get(SelectionKeys.DOWN)) {					
						controller.setSelectedButton(4);			//Back button selected
					} else if(controller.getKeys().get(SelectionKeys.UP)) {						
							controller.setSelectedButton(2);		//Choose button selected
					}
				}  else if(controller.getSelectedButton() == 4) {	//If Back button is selected
					if(controller.getKeys().get(SelectionKeys.DOWN)) {					
						controller.setSelectedButton(1);			//Selection button selected
					} else if(controller.getKeys().get(SelectionKeys.UP)) {						
							controller.setSelectedButton(3);		//Play button selected
					}
				} else {
					//Nothing is selected
					controller.setSelectedButton(0);
				}
			
	}
	
	//Selection par la sourie
	private void mouseSelection() {
		//TODO modifier les valeurs pour qu'elles collent � cet �cran.
		
		//La sourie est sur le boutton select
		if(controller.getMousePosition().x > 165 && controller.getMousePosition().x < 555
				&& controller.getMousePosition().y > 10 && controller.getMousePosition().y < 135) {
			controller.setSelectedButton(1);
		}
		
		//La sourie est sur le boutton choose
		if(controller.getMousePosition().x > 153 && controller.getMousePosition().x < 565
				&& controller.getMousePosition().y > 175 && controller.getMousePosition().y < 215) {
			controller.setSelectedButton(2);
		}
		
		//La sourie est sur le boutton play
		if(controller.getMousePosition().x > 257 && controller.getMousePosition().x < 460
				&& controller.getMousePosition().y > 310 && controller.getMousePosition().y < 360) {
			controller.setSelectedButton(3);
		}
		
		//La sourie est sur le boutton back
		if(controller.getMousePosition().x > 525 && controller.getMousePosition().x < 700
				&& controller.getMousePosition().y > 355 && controller.getMousePosition().y < 400) {
			controller.setSelectedButton(4);
		}
	}
	
	//Ajoute le ficher au dossier et charge la musique
	private void loadMusic(String fileName) {
		saveMusic(fileName);
		FileHandle musicFile = new FileHandle(path + "/res/audio/menu/" + fileName);
		selectedMusic = Gdx.audio.newMusic(musicFile);
	}
	
	//Charge la musique (elle a d�j� �t�e sauvegard�e)
	private void reloadMusic(String name) {
		//TODO modifer le dossier
		FileHandle musicFile = new FileHandle(path + "/res/audio/menu/" + name);
		selectedMusic = Gdx.audio.newMusic(musicFile);
	}
	
	//Enregistre la musique dans le dossier (avec le fichier trait�)
	private void saveMusic(String fileName) {
		//TODO copier le ficher dans le bon dossier
		File save = new File(saveDirectory + "\\" + fileName + ".DAT");
		try {
			if(save.createNewFile()) {
				System.out.println("save created");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//savedMusic.put(fileName, );
		//TODO traiter le fichier
		String musicData = "� faire";
		savedMusicData.put(fileName, musicData);
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
		
		if(controller.getKeys().get(SelectionKeys.VALIDATE) || controller.getMouseState()) {
			switch(controller.getSelectedButton()) {
				case (1) : 
					try{
						new BeatRoot();
					}catch(NullPointerException e){
						System.err.println("Aucune musique s�lectionn�e : " + e);
						game.setScreen(new SelectionScreen(game));
					}
					break;
				case (2) :
					game.setScreen(new ChooseScreen(game));
					break;
				case (3) :
					game.setScreen(new GameScreen(game, selectedMusic));
					break;
				case (4) : 
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
