package game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PauseRenderer {
	
	private static final float CAMERA_WIDTH = 100f;
	private static final float CAMERA_HEIGHT = 100f;
	
	private OrthographicCamera cam;
	
	private SpriteBatch spriteBatch;
	
	private TextureRegion buttonResume;
	
	private int width;
    private int height;
    private float ppuX;
    private float ppuY;
    
    //TODO:beaucoup de choses à enlever des commentaires ou a supprimer (penser ax imports).
    
    public PauseRenderer() {
    	//this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		//this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		//this.cam.update();
		spriteBatch = new SpriteBatch();
        loadTextures();
    }

    
	private void loadTextures() {
		final String path = System.getProperty("user.dir");
		TextureAtlas atlas = new TextureAtlas(path + "/res/img/pause/textures.pack");
		buttonResume = atlas.findRegion("resume02");
		
	}
     
	public void render() {
		spriteBatch.begin();
		  drawButtonResume();
		spriteBatch.end();
		
	}
	private void drawButtonResume() {
		spriteBatch.draw(buttonResume, 100, 100, 307, 102);
		System.out.println("draw button resume");
		
	}
	
	public void setSize (int w, int h) {
        this.width = w;
        this.height = h;
        ppuX = (float)width / CAMERA_WIDTH;
        ppuY = (float)height / CAMERA_HEIGHT;
        
    }
	
	public void dispose() {
		buttonResume.getTexture().dispose();
	}

}
