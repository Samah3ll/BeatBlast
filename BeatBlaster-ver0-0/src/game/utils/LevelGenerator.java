package game.utils;

import game.model.BasicBlock;
import game.model.BasicPlateform;
import game.model.Level;

public class LevelGenerator {
	
	private Level level;
	private DataSong dataSong;
	private final int coeff = 7;
	
	public LevelGenerator(DataSong ds) {
		this.dataSong = ds;
		this.level = new Level(((int)ds.getMaxTimeSong() ) * coeff, 16);
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
			BasicBlock b = new BasicBlock(((float) ds.getBeats().get(i)) * coeff, 1f);
			level.addBlock(b);
		}
	}
	
	public void generateSpectroBlocks(DataSong ds) {
		for(int i = 0; i < ds.getBeats().size()-1; i++){
			if( i<ds.getSpectro().length){
				BasicPlateform p = new BasicPlateform(ds.getBeats().get(i)*coeff,ds.bestSpectro(i), ((int)(ds.getBeats().get(i+1)-ds.getBeats().get(i))+1)+1);
				level.addPlateform(p);
			}
		}
	}
	
	public Level generateLevel() {
		generateBounds();
		//generateBeatBlocks(dataSong);
		generateSpectroBlocks(dataSong);
		return level;
	}
	
	
}
