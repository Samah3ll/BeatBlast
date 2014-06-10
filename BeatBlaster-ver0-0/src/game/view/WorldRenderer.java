package game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import game.model.Block;
import game.model.Coin;
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
	
	GL10 gl = Gdx.graphics.getGL10();
	
	private SpriteBatch spriteBatch;
    private int width;
    private int height;
    private float ppuX;
    private float ppuY;
    
    final String path = System.getProperty("user.dir");
    
    //Pour la pause
    private Vector2 positionWhenPaused;
    private boolean paused = false;
    
    //Block texture
    private TextureRegion blockTexture;
    
    //Particles
    //private ParticleEffect particleEffect;
    
    //Runner textures
    private TextureRegion runnerIdleLeft;
    private TextureRegion runnerIdleRight;
    private TextureRegion runnerJumpLeft;
    private TextureRegion runnerJumpRight;
    private TextureRegion runnerFrame;
    private TextureRegion coinTexture;
    
    //pour le HUD
    private TextButtonStyle style;
    private String position;
    private String velocity;
    
    //Runner Animations
    private Animation walkLeftAnimation;
    private Animation walkRightAnimation;
    
  //Position dans le niveau jusqu'a laquelle les blocks disparraissent
  	private float x = 0;
  	private float musicPosition = 0;
  	    
    /*
     * Constructeur
     */
  	
	public WorldRenderer(World w) {
		this.world = w;
		this.runner = world.getRunner();
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.zoom = 72;
		this.cam.update();
		spriteBatch = new SpriteBatch();
		loadTextures();
		/*
		//initialize particles
		particleEffect = new ParticleEffect();
		FileHandle effectFile = new FileHandle(path + "/res/particules/test.p");
		particleEffect.loadEmitters(effectFile);
		*/
	}
	
	/*
	 * Accesseurs
	 */
	
	
	public void setX(float x){
		this.x = x;
	}
	
	public void setMusicPosition(float musicPosition) {
		this.musicPosition = musicPosition;
	}
	
	/*
	 * Méthodes
	 */


	public void render() {
		
		 gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		 gl.glViewport(-width, -height, width*2, height*2);
		 cam.update();
		 cam.apply(gl);
		 if(!paused) {
			 spriteBatch.setProjectionMatrix(cam.combined);
				spriteBatch.begin();
		        	drawBlocks();
		        	drawplateforms();
		        	drawRunner();
		        	drawHUD();
		        	drawCoin();
		        spriteBatch.end();
		 }    
        
        //particleEffect.setPosition(5, 5);
        //particleEffect.draw(spriteBatch);
		
	}
	
	public void pause() {
		positionWhenPaused = runner.getPosition();
		paused = true;	
		gl.glViewport(0, 0, 720, 450);
		cam.apply(gl);
	}
	
	public void resume() {
		runner.setPosition(positionWhenPaused);
		paused = false;
	}
	
	public void hide() {
		cam = null;
	}
	
	private void loadTextures() {		
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(path + "/res/img/game/textures.pack"));
		blockTexture = atlas.findRegion("Block");
		runnerIdleLeft = atlas.findRegion("Runner04");
		runnerIdleRight = runnerIdleLeft;
		runnerIdleRight.flip(true, false);
		TextureRegion[] walkLeftFrames = new TextureRegion[5];
		TextureRegion[] walkRightFrames = new TextureRegion[5];
		for (int i = 0; i < 5; i++) {			
			walkLeftFrames[i] = atlas.findRegion("Runnert0" + (i + 1));
			walkRightFrames[i] = atlas.findRegion("Runner0" + (i + 1));
			walkRightFrames[i].flip(true, false);
			
		}
		walkLeftAnimation = new Animation(RUNNING_FRAME_DURATION, walkLeftFrames);
		walkRightAnimation = new Animation(RUNNING_FRAME_DURATION, walkRightFrames);
		
		runnerJumpLeft = atlas.findRegion("Runner04");
		runnerJumpRight = runnerJumpLeft;
		runnerJumpRight.flip(true, false);
		
		coinTexture = atlas.findRegion("coin");
		
		style = new TextButtonStyle();
		style.font = new BitmapFont();
		style.fontColor = Color.WHITE;
	}
	
	private void drawCoin() {
		for(Coin coin : world.getCoins()) {
			spriteBatch.draw(coinTexture, coin.getPosition().x * ppuX, coin.getPosition().y * ppuY, Coin.getSize() * ppuX, Coin.getSize() * ppuY);		
		}
	}
	
	private void drawBlocks() {
		for (Block block : world.getLevel().getBlocks()) {
            spriteBatch.draw(blockTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.getSize() * ppuX, Block.getSize() * ppuY);
        }		
	}
	
	private void drawplateforms() {
		for(Plateform plateform : world.getLevel().getPlateforms()) {
			for (int i = 0; i < plateform.getSize(); i++) {
				if(plateform.getPosition().x > x) {
					spriteBatch.draw(blockTexture, (plateform.getPosition().x + i) * ppuX, plateform.getPosition().y* ppuY, Block.getSize() * ppuX, Block.getSize() * ppuY);
				}				
	        }
		}		
	}
	
	 private void drawRunner() {
			runnerFrame = runner.isFacingLeft() ? runnerIdleLeft : runnerIdleLeft ;
			if(runner.getState().equals(State.WALKING)) {
				runnerFrame = runner.isFacingLeft() ? walkLeftAnimation.getKeyFrame(runner.getStateTime(), true) : walkRightAnimation.getKeyFrame(runner.getStateTime(), true);
			} else if (runner.getState().equals(State.JUMPING)) {
				if (runner.getVelocity().y > 0) {
					runnerFrame = runnerJumpRight;
				}
			}
			if(runnerFrame == null) {
				System.err.println("pas de frame : " + runner.getState());
				return;
			}
			spriteBatch.draw(runnerFrame, runner.getPosition().x * ppuX, runner.getPosition().y * ppuY, Runner.getSize() * ppuX, Runner.getSize() * ppuY);
		}
	 
	 private void drawHUD() {
		 position = runner.getPosition().toString();
		 velocity = runner.getVelocity().toString();
		 
		 TextButton buttonP = new TextButton("Position : " + position, style);
		 TextButton buttonV = new TextButton("Velocity : " + velocity, style);
		 TextButton musicP = new TextButton("Music position : " + musicPosition , style);
		 
		 if(runner.getPosition().x - 9 < 0) {
			 buttonP.translate(0 * ppuX, 14 * ppuY);
			 buttonV.translate(0 * ppuX, 14.5f * ppuY);
			 musicP.translate(0 * ppuX, 15 * ppuY);
		 } else {
			 buttonP.translate((runner.getPosition().x - 9) * ppuX, 14 * ppuY);
			 buttonV.translate((runner.getPosition().x - 9) * ppuX, 14.5f * ppuY);
			 musicP.translate((runner.getPosition().x - 9) * ppuX, 15 * ppuY);
		 }
		 
		 buttonP.draw(spriteBatch, 1);		 
		 buttonV.draw(spriteBatch, 1);		 
		 musicP.draw(spriteBatch, 1);
	 }
	 
	
	 public void moveCamera(){
		 	 
		 cam.position.x = runner.getPosition().x * ppuX - 9 * ppuX;		
		 
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
		runnerFrame.getTexture().dispose();
		runnerJumpLeft.getTexture().dispose();
		runnerJumpRight.getTexture().dispose();
		runnerIdleLeft.getTexture().dispose();
		runnerIdleRight.getTexture().dispose();
		this.cam = null;
		world.dispose();
		world = null;
	}

	

}
