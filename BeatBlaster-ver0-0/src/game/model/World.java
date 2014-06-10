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
	
	//Compte les points du joueur.
	private long points;
	
	
	public World(DataSong ds) {
		runner = new Runner(new Vector2(1, 2));
		lg = new LevelGenerator(ds);
		level = lg.generateLevel();
		this.points = 0;
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
	
	/**
	 * 
	 * @return une ArrayList qui contient les pièces du niveau.
	 */
	public ArrayList<Coin> getCoins() {
		ArrayList<Coin> coins = level.getCoins();
		return coins;
	}
	
	/**
	 * Supprime la pièce à la position spécifiée, appel la méthode du même nom de la classe Level.
	 * @param x abscisse de la pièce à supprimer.
	 * @param y ordonnée de la pièce à supprimer.
	 */
	public void deleteCoin(int x, int y) {
		level.deleteCoin(x, y);
	}
	
	/**
	 * Ajoute le nombre de point spécifiée au compteur.
	 * @param point : le nombre de points à ajouter au compteur.
	 */
	public void addPoint(int point) {
		this.points += point;
	}
	
	/**
	 * 
	 * @return les points du joueur.
	 */
	public long getPoint() {
		return points;
	}
	
	
	public void dispose() {
		runner.dispose();
		level.dispose();
		lg.dispose();
		
	}
	
	

}
