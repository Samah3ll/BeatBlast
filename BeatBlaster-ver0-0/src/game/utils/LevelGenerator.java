package game.utils;

import game.model.BasicBlock;
import game.model.Level;

public class LevelGenerator {
	
	private Level level;
	DataSong dataSong;
	
	public LevelGenerator(DataSong ds) {
		this.dataSong = ds;
		this.level = new Level(((int)ds.getMaxTimeSong() ) * 6, 16);
	}
	
	/**Création des blocks de contour */
	private void generateBounds() {
		//bas et haut
		for(int i = 0; i < level.getWidth(); i++) {
			BasicBlock b = new BasicBlock(i, 0);
			level.addBlock(b);
			BasicBlock b1 = new BasicBlock(i, level.getHeight() - 1);
			level.addBlock(b1);
		}

		//cotés
		for(int i = 0; i < level.getHeight(); i++) {
			BasicBlock b = new BasicBlock(0, i);
			level.addBlock(b);
			BasicBlock b1 = new BasicBlock(level.getWidth() - 1, i);
			level.addBlock(b1);
		}
	}
	
	public void generateBeatBlocks(DataSong ds) {
		for(int i = 0; i < ds.getBeats().size(); i++){
			BasicBlock b = new BasicBlock(((float) ds.getBeats().get(i)) * 6, 1f);
			level.addBlock(b);
		}
	}
	
	public void generateSpectroBlocks(DataSong ds) {
		//TODO
	}
	
	public Level generateLevel() {
		generateBounds();
		generateBeatBlocks(dataSong);
		generateSpectroBlocks(dataSong);
		return level;
	}
	
	
}
