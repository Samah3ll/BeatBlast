package game.utils;

public class Cursor {
	
	private float SpawnTime;
	private double CursorSpeed;
	private int nbSpawnBlocks;
	
	/**
	 * Constructor
	 * @param d
	 * @param t
	 * @param nbSpawnBlocks
	 */
	public Cursor(double d, double t, int nbSpawnBlocks){
		SpawnTime = System.currentTimeMillis();
		CursorSpeed = Speed(d,t);
		this.nbSpawnBlocks = nbSpawnBlocks;
	}
	
	/**
	 * Calculate Music Speed
	 * @param d
	 * @param t
	 * @return d/t
	 */
	private double Speed(double d, double t){
		return d/t;
	}
}
