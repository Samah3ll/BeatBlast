package game.utils;

import com.badlogic.gdx.math.Vector2;
import game.model.Block;
import game.model.Level;
import game.model.Monster;

public class LevelGenerator {
	
	private Level level;
	private Block[][] blocks;
	private Monster[][] monsters;
	
	public LevelGenerator(int x, int y) {
		this.level = new Level(x, y);
	}
	
	private void generateBlocks() {
		//TODO:Methode to generate blocks from inputdata
	}
	
	private void generateMonsters() {
		//TODO:Methode to generate monsters from inputdata
	}
}
