package game.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import game.model.BasicBlock;
import game.model.Level;

public class LevelGenerator {
	
	private Level level;
	private ArrayList<Integer> inputData = new ArrayList<Integer>();
	DataSong dataSong;
	
	public LevelGenerator(DataSong ds) {
		this.dataSong = ds;
		this.level = new Level((int)ds.getMaxTimeSong() +20 , 16);
	}
	
	/**Cr�ation des blocks de contour */
	private void generateBounds() {
		//bas et haut
		for(int i = 0; i < level.getWidth(); i++) {
			BasicBlock b = new BasicBlock(i, 0);
			level.addBlock(b);
			BasicBlock b1 = new BasicBlock(i, level.getHeight() - 1);
			level.addBlock(b1);
		}
		//cot�s
		for(int i = 0; i < level.getHeight(); i++) {
			BasicBlock b = new BasicBlock(0, i);
			level.addBlock(b);
			BasicBlock b1 = new BasicBlock(level.getWidth() - 1, i);
			level.addBlock(b1);
		}
	}
	
	public void generateSpectroBlocks(DataSong ds) {
		for(int i=0;i<ds.getBeats().size(); i++){
			BasicBlock b = new BasicBlock(i, 1);
			level.addBlock(b);
		}
		
	}

	public void generateBeatBlocks(DataSong ds) {
		// TODO Auto-generated method stub
		
	}

	public Level generateLevel() {
		generateBounds();
		generateBeatBlocks(dataSong);
		generateSpectroBlocks(dataSong);
		return level;
	}
	
	
}
