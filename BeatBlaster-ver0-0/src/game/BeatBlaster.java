package game;



import java.io.File;

import com.badlogic.gdx.Game;

import game.screens.ChooseScreen;
import game.screens.GameScreen;
import game.screens.MenuScreen;
import game.utils.DataSong;
import game.utils.Reader;

public class BeatBlaster extends Game {	
		
	final String path = System.getProperty("user.dir");
	private String saveDirectory="";		//Dossier ou seront stockées les sauvegardes

	@Override
	public void create() {
		createSaveRepertory();
		setScreen(new MenuScreen(this));
		Reader reader = new Reader();
		DataSong ds = reader.read(saveDirectory, "Delta Goodrem - You Will Only Break My Heart.mp3.WAV.dat");
		System.out.println("blob");
		//setScreen(new GameScreen(this));
	}
	
	public void dispose () {
		super.dispose();
		getScreen().dispose();
		System.exit(0);
	}
	
	//Crée le dossier de sauvegarde
	private void createSaveRepertory() {
		//TODO trouver une meilleure méthode pour définir saveDirectory
		 String[] tmp = path.split("\\\\");
		 for(int i =0; i< tmp.length-1;i++){
			 saveDirectory+=tmp[i];
			 saveDirectory+="\\";
		 }
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
