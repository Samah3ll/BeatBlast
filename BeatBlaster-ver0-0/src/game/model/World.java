package game.model;

import java.util.ArrayList;

import game.utils.DataSong;
import game.utils.LevelGenerator;

import com.badlogic.gdx.math.Vector2;

/**
 * La classe qui définit un monde. Un monde possède un niveau (Level) et un Runner.
 * @author SamaHell
 *
 */
public class World {
	
	private Runner runner;
	private Level level;
	private LevelGenerator lg;
	
	
	public World(DataSong ds) {
		runner = new Runner(new Vector2(1, 2));
		lg = new LevelGenerator(ds);
		level = lg.generateLevel();
	}
	
	
	public Runner getRunner() {
		return runner;
	}
	public Level getLevel() {
		return level;
	}
	
	public LevelGenerator getLevelGenerator() {
		return lg;
	}
	
	public void setlevel(Level l) {
		this.level = l;
	}
	
	
	
	/**
	 * Supprime tous les blocks qui sont avant la position x.
	 * @param x : position jusqu'à laquel les blocks sont supprimés.
	 */
	public void deleteBlocks(float x) {
		for(int i = 0; i < x; i++) {
			for(int y = 0; y < level.getHeight(); y++) {
				level.removeBlock(i, y);
			}
		}
	}
	
	/**
	 * Supprime toutes les plateformes qui sont avant la position spécifiée.
	 * @param x : position jusqu'à laquelle les plateformes sont supprimées.
	 */
	public void deletPlateforms(float x) {
		for(int i = 0; i < x; i++) {
			for(int y = 0; y < level.getHeight(); y++) {
				level.removePlateform(i, y);
			}
		}
	}
	public void dispose() {
		runner.dispose();
		level.dispose();
		lg.dispose();
		
	}
	public ArrayList<Coin> getCoins() {
		ArrayList<Coin> coins = level.getCoins();
		return coins;
	}
	

}
