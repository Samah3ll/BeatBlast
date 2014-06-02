package game;



import java.io.File;

import com.badlogic.gdx.Game;

import game.screens.ChooseScreen;
import game.screens.GameScreen;
import game.screens.MenuScreen;

public class BeatBlaster extends Game {
	
	final String path = System.getProperty("user.dir");
	String saveDirectory;		//Dossier ou seront stockées les sauvegardes
	
	
	@Override
	public void create() {
		setScreen(new MenuScreen(this));
		//setScreen(new GameScreen(this));
	}
	
	public void dispose () {
		super.dispose();
		getScreen().dispose();
		System.exit(0);
	}
	
	//Crée le dossier de sauvegarde
	private void createSaveRepertory() {
		saveDirectory = path.substring(0, path.length() - 18);
		//newPath += File.separator;
		saveDirectory += "save";
		System.out.println(saveDirectory);
		File folder = new File(saveDirectory);
		if(folder.mkdir()) {
			System.out.println("new folder created");
		}
	}
	
	public String getSaveDirectory() {
		return saveDirectory;
	}
	
}
