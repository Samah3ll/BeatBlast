package game.controller;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import game.model.Block;
import game.model.Coin;
import game.model.Runner;
import game.model.Runner.State;
import game.model.World;

public class RunnerController {
	
	public enum Keys {
		LEFT, RIGHT, JUMP, FIRE, DOWN, PAUSE
	}
	
	private static final long LONG_JUMP_PRESS 	= 200l;
	private static final float GRAVITY 			= -100f;
	private static final float MAX_JUMP_SPEED	= 12f;
	private static final float DAMP 			= 0.90f;
	private static final float MAX_VEL 			= 10f;
	private float newAcceleration = 0;
	
	private float width;
	private float height;
	private float ppuX;
	private float ppuY;
	
	private World 	world;
	private Runner 	runner;
	
	private boolean isDead = false;
	
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
	
	static Map<Keys, Boolean> keys = new HashMap<RunnerController.Keys, Boolean>();
	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.JUMP, false);
		keys.put(Keys.FIRE, false);
		keys.put(Keys.DOWN, false);
		keys.put(Keys.PAUSE, false);
	};

	public RunnerController(World w) {
		this.world = w;
		this.runner = world.getRunner();
		this.width = w.getLevel().getWidth();
		this.height = w.getLevel().getHeight();
		grounded = true;
		jumpPressedTime = 0;
		jumpingPressed = false;
		isDead = false;
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.JUMP, false);
		keys.put(Keys.PAUSE, false);
	}

	private void newAcceleration(double d, double t, float delta) {
		newAcceleration = (float) (d/(t*DAMP*delta*20)); //pk 20 ? 20=10*2, 2 c'est pour d qui est deux fois gros et 10 c'est pour DAMP il me semble..
	}

	public boolean isDead() {
		return isDead;
	}
	
	public void update(float delta) {
		processInput(delta);
		if (grounded && runner.getState().equals(State.JUMPING)) {
			runner.setState(State.IDLE);
		}
		runner.getAcceleration().y = GRAVITY;
		runner.getAcceleration().mul(delta);
		runner.getVelocity().add(runner.getAcceleration().x, runner.getAcceleration().y);
		checkCollisionWithBlocks(delta);
		checkCollisionWithCoin();
		
		runner.getVelocity().x *= DAMP;
		
		if (runner.getVelocity().x > MAX_VEL) {
			runner.getVelocity().x = MAX_VEL;
		}
		if (runner.getVelocity().x < -MAX_VEL) {
			runner.getVelocity().x = -MAX_VEL;
		}
		if(runner.getAcceleration().x > newAcceleration + newAcceleration/2) {
			runner.getAcceleration().x = newAcceleration;
		}
		runner.update(delta);
		//Ajouté depuis les coms du tuto
		runner.getBounds().x = runner.getPosition().x;
		runner.getBounds().y = runner.getPosition().y;
		if (runner.getPosition().y < 0) {
			runner.getPosition().y = 0f;
			runner.setState(State.DYING);
			grounded = true;
			runner.setPosition(runner.getPosition());
			if (runner.getState().equals(State.JUMPING)) {
				runner.setState(State.IDLE);
			}
		}
		if (runner.getPosition().x < 0) {
			runner.getPosition().x = 0;
			runner.setPosition(runner.getPosition());
			if (!runner.getState().equals(State.JUMPING)) {
				runner.setState(State.IDLE);
			}
		}
		if (runner.getPosition().x > width - runner.getBounds().width ) {
			runner.getPosition().x = width - runner.getBounds().width;
			runner.setPosition(runner.getPosition());
			if (!runner.getState().equals(State.JUMPING)) {
				runner.setState(State.IDLE);
			}
		}
	}//end of update
	
	private void processInput(float delta) {
		newAcceleration(world.getLevelGenerator().getDataSong().getMaxTimeSong()*world.getLevelGenerator().getCoeff(), world.getLevelGenerator().getDataSong().getMaxTimeSong(),delta);
		runner.getAcceleration().x = newAcceleration;
		runner.setFacingLeft(false);
		
		if(runner.getState().equals(State.DYING)) {			
			isDead = true;
		}
		
		//Key jump pressed
		if (keys.get(Keys.JUMP)) {
			if (!runner.getState().equals(State.JUMPING)) {
				jumpingPressed = true;
				grounded = false;
				jumpPressedTime = System.currentTimeMillis();
				runner.setState(State.JUMPING);
				runner.getVelocity().y = MAX_JUMP_SPEED;
		} else {
			if (jumpingPressed && ((System.currentTimeMillis() - jumpPressedTime) >= LONG_JUMP_PRESS)) {
				jumpingPressed = false;
			}
			if (jumpingPressed){
				runner.getVelocity().y = MAX_JUMP_SPEED; //la vitesse reste constante plutôt qu'elle diminue ==> saute + haut
				} 
		   	}
		}
		
		if(keys.get(Keys.LEFT)) {
			runner.setFacingLeft(false);
			if (!runner.getState().equals(State.JUMPING)) {
				runner.setState(State.WALKING);
				grounded = true;
			}
			runner.getAcceleration().x -= newAcceleration/2;
		} else if(keys.get(Keys.RIGHT)) {
			runner.setFacingLeft(false);
			if (!runner.getState().equals(State.JUMPING)) {
				runner.setState(State.WALKING);
				grounded = true;
			}
			runner.getAcceleration().x += newAcceleration/2;
		} else if(!runner.getState().equals(State.JUMPING)) {
			runner.setState(State.WALKING);
		}
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
		
		for (Block block : collidable) {
			if (block == null) {
				continue;
			}
			
			if (runnerRect.overlaps(block.getBounds()) && !runner.getBounds().overlaps(block.getBounds())) {
				runner.getVelocity().x = 0;
				runner.getAcceleration().x = 0;
				break;
			} else if (runnerRect.overlaps(block.getBounds()) && runner.getBounds().overlaps(block.getBounds())) {
				
				if(runner.getVelocity().x > 0) {
					runner.getPosition().x -= 0.05f;
				} else if(runner.getVelocity().x < 0) {
					runner.getPosition().x += 0.05f;
				}
				runner.getVelocity().x = 0;
				runner.getAcceleration().x = 0;
				break;
			}
		}
		
		
		runnerRect.x = runner.getPosition().x;
		startX = (int) runner.getBounds().x;
		endX = (int) (runner.getBounds().x + runner.getBounds().width);
		
		if (runner.getVelocity().y < 0) {
			startY = endY = (int) Math.floor(runner.getBounds().y + runner.getVelocity().y * 2);
		} else {
			startY = endY = (int) Math.floor(runner.getBounds().y + runner.getBounds().height + runner.getVelocity().y * 2);
		}
		
		if(startY < 0) {
			startY = 0;
		}
		if(endY < 0) {
			endY = 0;
		}
		
		populateCollidableBlocks(startX, startY, endX, endY);
		
		runnerRect.y += runner.getVelocity().y * 2;
		//runnerRect.y += 0.1;
		
		if(runnerRect.y < 0) {
			runnerRect.y = 0;
		}
			
		for (Block block : collidable) {
			if (block == null) {
				continue;
			}
			
			if (runnerRect.overlaps(block.getBounds()) && !runner.getBounds().overlaps(block.getBounds())) {
				if (runner.getVelocity().y < 0) {
					grounded = true;
					runner.getVelocity().y = 0;
					
				}
				
				runner.getVelocity().y = 0;
				runner.getAcceleration().y = 0;
				break;
			} else if(runnerRect.overlaps(block.getBounds()) && runner.getBounds().overlaps(block.getBounds())) {
				runner.getVelocity().y = 0;
				runner.getAcceleration().y = 0;
				//Paramètres à adapter en fonction de la vitesse du runner
				runner.getPosition().y += 0.05f;
				runner.getPosition().x += 0.1f;
				//fixPosition();
				break;
			}
		}
		
		runnerRect.y = runner.getPosition().y;
		
		runner.getPosition().add(runner.getVelocity());
		runner.getBounds().x = runner.getPosition().x;
		runner.getBounds().y = runner.getPosition().y;
		runner.getVelocity().mul(1 / delta);
		
		//System.out.println("Runner position : " + runner.getPosition());
		
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
	
	/**
	 * Vérifie si le runner touche une pièce.
	 */
	private void checkCollisionWithCoin() {
		
		int x = (int) runner.getPosition().x;
		int y = (int) runner.getPosition().y;
		
		Rectangle runnerRect = rectPool.obtain();
		runnerRect.set(x, y, runner.getBounds().width, runner.getBounds().height);
		
		for(Coin coin : world.getCoins()) {
			if(runnerRect.overlaps(coin.getBounds())) {
				world.deleteCoin(x, y);
			}
		}
	}
		
	public Map<Keys, Boolean> getKeys() {
		return keys;
	}
	
	public boolean isPaused() {
		return keys.get(Keys.PAUSE);
	}
	
	public void setSize(float ppuX, float ppuY) {
		this.ppuX = ppuX;
		this.ppuY = ppuY;
		
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
			keys.get(keys.put(Keys.DOWN, true));
			
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
