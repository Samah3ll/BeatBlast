package game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

/**
 * Renderer du LooseScreen.
 * @author SamaHell
 *
 */
public class LooseRenderer {
	
	private static final float CAMERA_WIDTH = 20f;
	private static final float CAMERA_HEIGHT = 14f;
	
	private OrthographicCamera cam;
	
	private int width;
    private int height;
    private float ppuX;
    private float ppuY;
    
    private SpriteBatch spriteBatch;
    
    final String path = System.getProperty("user.dir");
    
    //Textures
    private TextureRegion background;
    
    private TextButtonStyle style;
    
    private long score;
    
    
    /*
     * Constructeur
     */
    
    public LooseRenderer(long score) {
    	this.cam = new OrthographicCamera(CAMERA_WIDTH * 2, CAMERA_HEIGHT * 2);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.cam.update();
    	spriteBatch = new SpriteBatch();
    	loadTextures();
    	this.score = score;
    }
    
    /*
     * Méthodes
     */
    
    public void render() {
    	spriteBatch.begin();
    		drawBackground();
    		drawScore();
    	spriteBatch.end();
    }
    
    private void loadTextures() {
    	TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(path + "/res/img/loose/textures.pack"));
    	background = atlas.findRegion("background");
    	
    	style = new TextButtonStyle();
		style.font = new BitmapFont();
		style.fontColor = Color.GREEN;
    }
    
    private void drawBackground() {
    	spriteBatch.draw(background, 0, 0, CAMERA_WIDTH * ppuX, CAMERA_HEIGHT * ppuY);
    }
    
    private void drawScore() {
    	TextButton buttonS = new TextButton("Score : " + score, style);
    	buttonS.translate(9f * ppuX, 1f * ppuY);
    	buttonS.draw(spriteBatch, 1);
    }

	public void setSize(int w, int h) {
		this.width = w;
        this.height = h;
        ppuX = (float)width / CAMERA_WIDTH;
        ppuY = (float)height / CAMERA_HEIGHT;
		
	}

	public void dispose() {
		background.getTexture().dispose();
		cam = null;
		
	}

}
