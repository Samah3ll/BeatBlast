package game;



import java.io.File;

import music.beatroot.BeatRoot;

import com.badlogic.gdx.Game;

import game.screens.MenuScreen;

public class BeatBlaster extends Game {	
		
	final String path = System.getProperty("user.dir");
	private String saveDirectory="";		//Dossier ou seront stockées les sauvegardes
	public static BeatRoot beatroot;
	
	@Override
	public void create() {
		createSaveRepertory();
			beatroot = new BeatRoot(saveDirectory);
			beatroot.getGui().setVisible(beatroot.getVisu());
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
		//TODO trouver une meilleure méthode pour définir saveDirectory
		 String[] tmp = path.split("\\\\");
		 for(int i =0; i< tmp.length-1;i++){
			 saveDirectory+=tmp[i];
			 saveDirectory+="\\";
		 }
		saveDirectory += "save";
		//System.out.println(saveDirectory);
		File folder = new File(saveDirectory);
		if(folder.mkdir()) {
			System.out.println("new folder created");
		}
	}
	
	public String getSaveDirectory() {
		return saveDirectory;
	}

	
}
