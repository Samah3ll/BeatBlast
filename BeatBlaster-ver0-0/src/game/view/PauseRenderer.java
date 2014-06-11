package game.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Renderer du PauseScreen.
 * @author SamaHell
 *
 */
public class PauseRenderer {
	
	private static final float CAMERA_WIDTH = 100f;
	private static final float CAMERA_HEIGHT = 100f;
	
	private OrthographicCamera cam;
	
	private SpriteBatch spriteBatch;
	
	
	private int width;
    private int height;
    private float ppuX;
    private float ppuY;
    
    final String path = System.getProperty("user.dir");
    
    
    /*
     * Constructeur
     */
    
    public PauseRenderer() {
    	this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.cam.update();
		spriteBatch = new SpriteBatch();
        loadTextures();
    }
    
    /*
     * Méthodes
     */    
	
     
	public void render() {
		spriteBatch.begin();
		spriteBatch.end();
		
	}
	
	private void loadTextures() {
		
		
	}
	
	public void setSize (int w, int h) {
        this.width = w;
        this.height = h;
        ppuX = (float)width / CAMERA_WIDTH;
        ppuY = (float)height / CAMERA_HEIGHT;
        
    }
	
	public void dispose() {
	}

}
