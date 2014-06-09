package game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
    
    private TextureRegion background;
    
    
    public LooseRenderer() {
    	this.cam = new OrthographicCamera(CAMERA_WIDTH * 2, CAMERA_HEIGHT * 2);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.cam.update();
    	spriteBatch = new SpriteBatch();
    	loadTextures();
    }
    
    public void render() {
    	spriteBatch.begin();
    		drawBackground();
    	spriteBatch.end();
    }
    
    private void loadTextures() {
    	TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(path + "/res/img/loose/textures.pack"));
    	background = atlas.findRegion("background");
    }
    
    private void drawBackground() {
    	spriteBatch.draw(background, 0, 0, CAMERA_WIDTH * ppuX, CAMERA_HEIGHT * ppuY);
    }

	public void setSize(int width, int height) {
		this.width = width;
        this.height = height;
        ppuX = (float)width / CAMERA_WIDTH;
        ppuY = (float)height / CAMERA_HEIGHT;
		
	}

}
