package game.utils;

import java.util.ArrayList;
import java.util.Iterator;

import game.model.BasicBlock;
import game.model.Level;

public class LevelGenerator {
	
	private Level level;
	private ArrayList<Integer> inputData = new ArrayList<Integer>();
	
	public LevelGenerator(int x) {
		this.level = new Level(x, 16);
		for(int i = 0; i < 40; i++) {
			inputData.add(600*i);
		}
	}
	
	public Level generateLevel(/*inputData*/) {
		for(int i = 0; i < level.getWidth(); i++) {
			BasicBlock b = new BasicBlock(i, 0);
			level.addBlock(b);
			BasicBlock b1 = new BasicBlock(i, level.getHeight() - 1);
			level.addBlock(b1);
		}
		
		for(Iterator<Integer> it = inputData.iterator(); it.hasNext();) {
			float x = it.next()*0.001f*12;
			BasicBlock b2 = new BasicBlock(x, 1);
			level.addBlock(b2);
		}
		
		return level;
	}
}
