package game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

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
	private Runner runner;
	private OrthographicCamera cam;
	
	private SpriteBatch spriteBatch;
    private int width;
    private int height;
    private float ppuX;
    private float ppuY;
    
    //Pour la pause
    private Vector2 positionWhenPaused;
    
    //Block texture
    private TextureRegion blockTexture;
    
    
    //Runner textures
    private TextureRegion runnerIdleLeft;
    private TextureRegion runnerIdleRight;
    private TextureRegion runnerLeft01;
    private TextureRegion runnerLeft02;
    private TextureRegion runnerLeft03;
    private TextureRegion runnerRight01;
    private TextureRegion runnerRight02;
    private TextureRegion runnerRight03;
    private TextureRegion runnerJumpLeft;
    private TextureRegion runnerJumpRight;
    private TextureRegion runnerFrame;
    
    //Runner Animations
    private Animation walkLeftAnimation;
    private Animation walkRightAnimation;
    
	public WorldRenderer(World w) {
		this.world = w;
		this.runner = world.getRunner();
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
	
	public void pause() {
		//spriteBatch.setProjectionMatrix(usualMatrix);
		positionWhenPaused = runner.getPosition();	
	}
	
	public void resume() {
		System.out.println("runner repositionned at " + positionWhenPaused);
		runner.setPosition(positionWhenPaused);
		//render();
	}
	
	private void loadTextures() {
		final String path = System.getProperty("user.dir");
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(path + "/res/img/game/textures.pack"));
		blockTexture = atlas.findRegion("Block");
		runnerIdleLeft = atlas.findRegion("runningRight01");
		runnerIdleRight = atlas.findRegion("runningLeft01");
		TextureRegion[] walkLeftFrames = new TextureRegion[5];
		TextureRegion[] walkRightFrames = new TextureRegion[5];
		for (int i = 0; i < 5; i++) {
			if(i < 3) {
				walkLeftFrames[i] = atlas.findRegion("runningLeft0" + (i + 1));
				walkRightFrames[i] = atlas.findRegion("runningRight0" + (i + 1));
			} else {
				walkLeftFrames[i] = atlas.findRegion("runningLeft0" + (i - 2));
				walkRightFrames[i] = atlas.findRegion("runningRight0" + (i - 2));
			}
		}
		walkLeftAnimation = new Animation(RUNNING_FRAME_DURATION, walkLeftFrames);
		walkRightAnimation = new Animation(RUNNING_FRAME_DURATION, walkRightFrames);
		
		runnerJumpLeft = atlas.findRegion("runningLeft01");
		runnerJumpRight = atlas.findRegion("runningRight01");
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
			runnerFrame = runner.isFacingLeft() ? runnerIdleLeft : runnerIdleLeft ;
			if(runner.getState().equals(State.WALKING)) {
				runnerFrame = runner.isFacingLeft() ? walkLeftAnimation.getKeyFrame(runner.getStateTime(), true) : walkRightAnimation.getKeyFrame(runner.getStateTime(), true);
			} else if (runner.getState().equals(State.JUMPING)) {
				if (runner.getVelocity().y > 0) {
					runnerFrame = runner.isFacingLeft() ? runnerJumpLeft : runnerJumpRight;
				}
			}
			if(runnerFrame == null) {
				System.err.println("pas de frame : " + runner.getState());
				return;
			}
			spriteBatch.draw(runnerFrame, runner.getPosition().x * ppuX, runner.getPosition().y * ppuY, Runner.getSize() * ppuX, Runner.getSize() * ppuY);
		}
	 
	
	 public void moveCamera(){
		 float dx = runner.getVelocity().x;
		 //float dy = runner.getVelocity().y;
		 
		 
		 //Correction pour que la caméra ne dépasse pas le runner ou l'inverse, peut être à modifier
		 float correction = 0.65f;
		 
		 if(runner.getVelocity().x == 0) {
			 dx = 0;
		 }
		 
		 if(runner.getAcceleration().x == 0) {
			 dx = 0;
		 }
		 
		 //Déplacement de la caméra pour suivre le runner
		 if(dx < 0) {
			 cam.translate(dx, 0);
			 if(dx != 0) {
				 cam.translate(-correction, 0);
			 }			 
		 }
		 /*if(up){
			 cam.translate(0, 0);
		 }*/
		 if(dx > 0){
			 cam.translate(dx, 0);
			 if(dx != 0) {
				 cam.translate(correction, 0);
			 }
		 }
		 /*if(down){
			 cam.translate(0, 0);
		 }*/
		 
		 //Pour que la caméra ne sorte pas du niveau
		 if(cam.position.x < 0) {
			 cam.position.x = 0;
		 }		 
		 if(cam.position.x/ppuX > world.getLevel().getWidth() - CAMERA_WIDTH) {
			 cam.position.x = (world.getLevel().getWidth() - CAMERA_WIDTH) * ppuX;
		 }
		 
	}//End of moveCamera


	public void setSize (int w, int h) {
        this.width = w;
        this.height = h;
        ppuX = (float)width / CAMERA_WIDTH;
        ppuY = (float)height / CAMERA_HEIGHT;
    }
	
	public void dispose() {
		//spriteBatch.dispose();
		runnerFrame.getTexture().dispose();
		runnerJumpLeft.getTexture().dispose();
		runnerJumpRight.getTexture().dispose();
	}

	

}
