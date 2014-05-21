package game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import game.model.Block;
import game.model.Plateform;
import game.model.Runner;
import game.model.Runner.State;
import game.model.World;

public class WorldRenderer {
	
	private static final float CAMERA_WIDTH = 20f;
	private static final float CAMERA_HEIGHT = 14f;
	private static final float RUNNING_FRAME_DURATION = 0.06f;
	
	private World world;
	Runner runner;
	private OrthographicCamera cam;
	
	private SpriteBatch spriteBatch;
    private int width;
    private int height;
    private float ppuX;
    private float ppuY;
    
    //Block texture
    private TextureRegion blockTexture;
    
    
    //Runner textures
    private TextureRegion runnerIdleLeft;
    private TextureRegion runnerIdleRight;
    private TextureRegion runnerFrame;
    private TextureRegion runnerJumpLeft;
    private TextureRegion runnerFallLeft;
    private TextureRegion runnerJumpRight;
    private TextureRegion runnerFallRight;
    
    //Runner Animations
    private Animation walkLeftAnimation;
    private Animation walkRightAnimation;
    
	public WorldRenderer(World w) {
		this.world = w;
		this.runner =  world.getRunner();
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.zoom = 72;
		this.cam.update();
		spriteBatch = new SpriteBatch();
		loadTextures();
		
	}


	public void render() {
		GL10 gl = Gdx.graphics.getGL10();
		 gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		 gl.glViewport(-width, -height, width*2, height*2);
		 cam.update();
		 cam.apply(gl);
	     spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();		 
        	drawBlocks();
        	drawplateforms();
        	drawRunner();
        spriteBatch.end();
		
	}
	
	private void loadTextures() {
		final String path = System.getProperty("user.dir");
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(path + "/res/img/game/textures.pack"));
		runnerIdleLeft = atlas.findRegion("Runner01");
		runnerIdleRight = new TextureRegion(runnerIdleLeft);
		runnerIdleRight.flip(true, false);
		blockTexture = atlas.findRegion("Block");
		TextureRegion[] walkLeftFrames = new TextureRegion[5];
		for (int i = 0; i < 5; i++) {
			walkLeftFrames[i] = atlas.findRegion("Runner0" + (i + 2));
		}
		walkLeftAnimation = new Animation(RUNNING_FRAME_DURATION, walkLeftFrames);

		TextureRegion[] walkRightFrames = new TextureRegion[5];

		for (int i = 0; i < 5; i++) {
			walkRightFrames[i] = new TextureRegion(walkLeftFrames[i]);
			walkRightFrames[i].flip(true, false);
		}
		walkRightAnimation = new Animation(RUNNING_FRAME_DURATION, walkRightFrames);
		
		runnerJumpLeft = atlas.findRegion("Runner01");
		runnerJumpRight = new TextureRegion(runnerJumpLeft);
		runnerJumpRight.flip(true, false);
		runnerFallLeft = atlas.findRegion("Runner04");
		runnerFallRight = new TextureRegion(runnerFallLeft);
		runnerFallRight.flip(true, false);
	}

	
	private void drawBlocks() {
		for (Block block : world.getLevel().getBlocks()) {
            spriteBatch.draw(blockTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.getSize() * ppuX, Block.getSize() * ppuY);
        }
		
	}
	
	private void drawplateforms() {
		for(Plateform plateform : world.getLevel().getPlateforms()) {
			for (int i = 0; i < plateform.getSize(); i++) {
				spriteBatch.draw(blockTexture, (plateform.getPosition().x + i) * ppuX, plateform.getPosition().y* ppuY, Block.getSize() * ppuX, Block.getSize() * ppuY);
	        }
		}
		
		
	}
	
	 private void drawRunner() {
			runnerFrame = runner.isFacingLeft() ? runnerIdleLeft : runnerIdleRight;
			if(runner.getState().equals(State.WALKING)) {
				runnerFrame = runner.isFacingLeft() ? walkLeftAnimation.getKeyFrame(runner.getStateTime(), true) : walkRightAnimation.getKeyFrame(runner.getStateTime(), true);
			} else if (runner.getState().equals(State.JUMPING)) {
				if (runner.getVelocity().y > 0) {
					runnerFrame = runner.isFacingLeft() ? runnerJumpLeft : runnerJumpRight;
				} else {
					runnerFrame = runner.isFacingLeft() ? runnerFallLeft : runnerFallRight;
				}
			}
			spriteBatch.draw(runnerFrame, runner.getPosition().x * ppuX, runner.getPosition().y * ppuY, Runner.getSize() * ppuX, Runner.getSize() * ppuY);
		}
	 
	
	 public void moveCamera(boolean left, boolean up, boolean right, boolean down){
		 float dx = runner.getVelocity().x;
		 //float dy = runner.getVelocity().y;
		 float correction = 0.8f;
		 if(left) {
			 cam.translate(dx, 0);
			 if(dx != 0) {
				 cam.translate(-correction, 0);
			 }
			 
		 }
		 if(up){
			 cam.translate(0, 0);
		 }
		 if(right){
			 cam.translate(dx, 0);
			 if(dx != 0) {
				 cam.translate(correction, 0);
			 }
		 }
		 if(down){
			 cam.translate(0, 0);
		 }
		 
		 if(cam.position.x < 0) {
			 cam.position.x = 0;
		 }
		 
		 if(cam.position.x/ppuX > world.getLevel().getWidth() - CAMERA_WIDTH) {
			 cam.position.x = (world.getLevel().getWidth() - CAMERA_WIDTH) * ppuX;
		 }
	}


	public void setSize (int w, int h) {
        this.width = w;
        this.height = h;
        ppuX = (float)width / CAMERA_WIDTH;
        ppuY = (float)height / CAMERA_HEIGHT;
    }
	
	public void dispose() {
		//spriteBatch.dispose();
		runnerIdleLeft.getTexture().dispose();
		runnerIdleRight.getTexture().dispose();
		runnerFallLeft.getTexture().dispose();
		runnerFallRight.getTexture().dispose();
		runnerFrame.getTexture().dispose();
		runnerJumpLeft.getTexture().dispose();
		runnerJumpRight.getTexture().dispose();
	}
	

}
