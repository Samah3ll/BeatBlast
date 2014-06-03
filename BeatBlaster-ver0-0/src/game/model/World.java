package game.model;

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
	//private Runner02 runner02;
	private Level level;
	private LevelGenerator lg;
	
	public Runner getRunner() {
		return runner;
	}
	/*
	public Runner02 getRunner02() {
		return runner02;
	}
	*/
	public Level getLevel() {
		return level;
	}
	
	public void setlevel(Level l) {
		this.level = l;
	}
	
	public World(DataSong ds) {
		runner = new Runner(new Vector2(7, 2));
		//runner02 = new Runner02(new Vector2(7,1));
		lg = new LevelGenerator(ds);
		level = lg.generateLevel();
		//createDemoWorld();
	}
	
	/**
	 * Crée le monde en initialisant son runner et son level et en ajoutant des block, plateform ou des monstres au level.
	 */
	private void createDemoWorld() {
		
		//runner = new Runner(new Vector2(7, 2));
		level = new Level(80, 16);			//Utiliser depuis WorldRenderer CAMERA_WIDTH et HEIGHT
		BasicBlock b = new BasicBlock(new Vector2(1,1));
		level.addBlock(b);
		BasicBlock b0 = new BasicBlock(new Vector2(1,10));
		level.addBlock(b0);
		BasicBlock b2 = new BasicBlock(new Vector2(1,13));
		level.addBlock(b2);
		BasicPlateform bp = new BasicPlateform(5, 5, 3);
		level.addPlateform(bp);
		BasicPlateform bp1 = new BasicPlateform(9, 9, 5);
		level.addPlateform(bp1);
		BasicPlateform bp2 = new BasicPlateform(2, 7, 3);
		level.addPlateform(bp2);
		BasicPlateform bp3 = new BasicPlateform(20, 5, 10);
		level.addPlateform(bp3);
		
		for(int i = 0; i < 80; i++) {
			BasicBlock b1 = new BasicBlock(new Vector2(i,0));
			level.addBlock(b1);
			BasicBlock b3 = new BasicBlock(new Vector2(i,level.getHeight() - 1));
			level.addBlock(b3);
			if(i > 9 && i < 17) {
				BasicBlock b4 = new BasicBlock(new Vector2(i,4));
				level.addBlock(b4);
			}
			
		}

	}//end of createDemoWorld
	

}
