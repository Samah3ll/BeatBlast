package game.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * La classe qui définit un monde. Un monde possède un niveau (Level) et un Runner.
 * @author SamaHell
 *
 */
public class World {
	
	private Runner runner;
	private Level level;
	
	/** The collision boxes **/
	Array<Rectangle> collisionRects = new Array<Rectangle>();
	
	public Array<Rectangle> getCollisionRects() {
		    return collisionRects;
		}
	
	public Runner getRunner() {
		return runner;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public World() {
		createDemoWorld();
	}
	
	/**
	 * Crée le monde en initialisant son runner et son level et en ajoutant des block, plateform ou des monstres au level.
	 */
	private void createDemoWorld() {
		runner = new Runner(new Vector2(7, 2));
		level = new Level(80, 14);			//Utiliser depuis WorldRenderer CAMERA_WIDTH et HEIGHT
		BasicBlock b = new BasicBlock(new Vector2(1,1));
		level.addBlock(b);
		BasicBlock b0 = new BasicBlock(new Vector2(1,10));
		level.addBlock(b0);
		BasicPlateform bp = new BasicPlateform(5, 5, 3);
		level.addPlateform(bp);
		BasicPlateform bp1 = new BasicPlateform(9, 9, 5);
		level.addPlateform(bp1);
		
		for(int i = 0; i < 80; i++) {
			BasicBlock b1 = new BasicBlock(new Vector2(i,0));
			level.addBlock(b1);
			BasicBlock b2 = new BasicBlock(new Vector2(i,13));
			level.addBlock(b2);
			if(i > 9 && i < 17) {
				BasicBlock b4 = new BasicBlock(new Vector2(i,4));
				level.addBlock(b4);
			}
			if(i < 14) {
				BasicBlock b3 = new BasicBlock(new Vector2(19,i));
				level.addBlock(b3);
			}
		}
		//System.out.println(level.toString());

	}//end of createDemoWorld
	
	public void moveWorld() {
		level.moveLevel();
	}

}
