package game.controller;

import game.controller.RunnerController.Keys;
import game.model.Block;
import game.model.Runner;
import game.model.Runner02;
import game.model.Runner02.State02;
import game.model.World;
import game.model.Runner.State;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class RunnerController02 {
	
	public enum Keys {
		LEFT, RIGHT, JUMP, FIRE, DOWN, PAUSE
	}
	
	private static final long LONG_JUMP_PRESS 	= 100l;
	private static final float ACCELERATION 	= 20f;
	private static final float GRAVITY 			= -20f;
	private static final float MAX_JUMP_SPEED	= 5f;
	private static final float DAMP 			= 0.90f;
	private static final float MAX_VEL 			= 4f;
	
	private float width;
	private float height;
	
	private World 	world;
	private Runner02 	runner;
	
	private boolean jumpingPressed;
	private long	jumpPressedTime = 0;
	private boolean grounded = true;
	
	private Array<Block> collidable = new Array<Block>();
	
	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject() {
			return new Rectangle();
		}
	};
	
	static Map<Keys, Boolean> keys = new HashMap<RunnerController02.Keys, Boolean>();
	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.JUMP, false);
		keys.put(Keys.FIRE, false);
		keys.put(Keys.DOWN, false);
		keys.put(Keys.PAUSE, false);
	};

	public RunnerController02(World w) {
		this.world = w;
		this.runner = new Runner02(new Vector2(2,2));
		this.width = w.getLevel().getWidth();
		this.height = w.getLevel().getHeight();
	}

	public void update(float delta) {
		processInput();	
		
		runner.setFacingLeft(false);
		
		runner.getAcceleration().x = ACCELERATION;
		runner.getVelocity().add(runner.getAcceleration().x, runner.getAcceleration().y);
		
		runner.update(delta);
		
		//Mise à jour de la hitbox du runner
		runner.getBounds().x = runner.getPosition().x;
		runner.getBounds().y = runner.getPosition().y;
		
		checkCollisionWithBlocks(delta);
		
		if(runner.canRun()) {
			runner.getVelocity().x = MAX_VEL;
		}
		
		//Correction de la vitesse
		if (runner.getVelocity().x > MAX_VEL) {
			runner.getVelocity().x = MAX_VEL;
		}
		if (runner.getVelocity().x < -MAX_VEL) {
			runner.getVelocity().x = -MAX_VEL;
		}
		
		//Si le runner sort du niveau en x
		if(runner.getPosition().x < 0) {
			runner.getPosition().x = 0;
		} else if(runner.getPosition().x > width + runner.getBounds().width) {
			runner.getPosition().x = width + runner.getBounds().width;
		}
		
		//Si le runner sort du niveau en y
		if(runner.getPosition().y < 0) {
			runner.getPosition().y = 0f;
			grounded = true;
			runner.setState(State02.RUNNING);
		} else if(runner.getPosition().y > height - runner.getBounds().height) {
			runner.getPosition().y = height- runner.getBounds().height;
			grounded = false;
			runner.setState(State02.JUMPING);
		}
		
	}//end of update
	
	private boolean processInput() {		
			
		//jump pressed
		if(keys.get(Keys.JUMP)) {
			if(runner.getState() == State02.JUMPING) {
				
			}
			if(grounded) {
				runner.setState(State02.JUMPING);
				grounded = false;
				jumpPressedTime = System.currentTimeMillis();
				runner.getAcceleration().y = MAX_JUMP_SPEED;
			} else if(System.currentTimeMillis() - jumpPressedTime < LONG_JUMP_PRESS) {
				runner.getAcceleration().y = MAX_JUMP_SPEED - 0.01f * (System.currentTimeMillis() - jumpPressedTime);
			} else if(System.currentTimeMillis() - jumpPressedTime > LONG_JUMP_PRESS) {
				runner.getAcceleration().y = GRAVITY;
			}
		}
		
		//left pressed
		if(keys.get(Keys.LEFT)) {
			runner.getVelocity().x -= MAX_VEL/2;
			runner.setFacingLeft(false);
			if(grounded) {
				
			} else {
				runner.getAcceleration().y = GRAVITY;
			}
		}
		
		//right pressed
		if(keys.get(Keys.RIGHT)) {
			runner.getVelocity().x += MAX_VEL/2;
			runner.setFacingLeft(true);
			if(grounded) {
				
			} else {
				runner.getAcceleration().y = GRAVITY;
			}
		}
		
		return false;
	}//end of processInput
	
	
	private void checkCollisionWithBlocks(float delta) {
		
		runner.getVelocity().mul(delta);
		
		Rectangle runnerRect = rectPool.obtain();
		runnerRect.set(runner.getBounds().x, runner.getBounds().y, runner.getBounds().width, runner.getBounds().height);
		
		int startX, endX;
		int startY = (int) runner.getBounds().y;
		int endY = (int) (runner.getBounds().y + runner.getBounds().height);	
		
			
		if (runner.getVelocity().x < 0) {
			startX = endX = (int) Math.floor(runner.getBounds().x + runner.getVelocity().x);
		} else {
			startX = endX = (int) Math.floor(runner.getBounds().x + runner.getBounds().width + runner.getVelocity().x);
		}
		
		populateCollidableBlocks(startX, startY, endX, endY);
		runnerRect.x += runner.getVelocity().x;
		runnerRect.y += runner.getVelocity().y;
		
		for(Block block : collidable) {
			if(runner.getState() == State02.RUNNING) {
				if(block.getBounds().overlaps(runnerRect)) {
					runner.setRun(true);
					fixPosition();
				} else if( !block.getBounds().overlaps(runnerRect)) {
					runner.setRun(false);
					grounded = false;
					runner.setState(State02.JUMPING);
				}
			}
			
			if(runner.getState() == State02.JUMPING) {
				if(block.getBounds().overlaps(runnerRect)) {
					runner.setRun(true);
					grounded = true;
					runner.setState(State02.RUNNING);
					fixPosition();
				}
			}
		}
		
		
	}//End of checkCollisionBlock

	private void populateCollidableBlocks(int startX, int startY, int endX, int endY) {
		collidable.clear();
		for (int x = startX; x <= endX; x++) {
			for (int y = startY; y <= endY; y++) {
				if (x >= 0 && x < world.getLevel().getWidth() && y >=0 && y < world.getLevel().getHeight()) {
					collidable.add(world.getLevel().get(x, y));					
				}
			}
		}
		
	}//End of populateCollisionBlock
	/* 
	private void fixRunnerPosition() {
		if(runner.canRun()) {
			runner.getPosition().y = Math.round(runner.getPosition().y);
		}
	}
	*/
	public Map<Keys, Boolean> getKeys() {
		return keys;
	}
	
	public boolean isPaused() {
		return keys.get(Keys.PAUSE);
	}
	
	private void fixPosition() {
		int y; 
		y = (int) Math.rint(runner.getPosition().y);
		if(runner.getPosition().y < y) {
			runner.getPosition().y = y;
		}
	}
	
	// ** Key presses and touches **************** //

		public void leftPressed() {
			keys.get(keys.put(Keys.LEFT, true));
		}

		public void rightPressed() {
			keys.get(keys.put(Keys.RIGHT, true));
		}

		public void jumpPressed() {
			keys.get(keys.put(Keys.JUMP, true));
		}

		public void firePressed() {
			keys.get(keys.put(Keys.FIRE, false));
		}

		public void leftReleased() {
			keys.get(keys.put(Keys.LEFT, false));
		}

		public void rightReleased() {
			keys.get(keys.put(Keys.RIGHT, false));
		}

		public void jumpReleased() {
			keys.get(keys.put(Keys.JUMP, false));
			jumpingPressed = false;
		}

		public void fireReleased() {
			keys.get(keys.put(Keys.FIRE, false));
		}

		public void downPressed() {
			keys.get(keys.put(Keys.DOWN, false));
			
		}

		public void downReleased() {
			keys.get(keys.put(Keys.DOWN, false));
			
		}
		
		public void pPressed() {
			keys.get(keys.put(Keys.PAUSE, true));
			
		}
		
		public void pReleased() {
			keys.get(keys.put(Keys.PAUSE, false));
			
		}
}
