package game.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class ChooseRenderer {
	
	private static final float CAMERA_WIDTH = 100f;
	private static final float CAMERA_HEIGHT = 100f;
	private static final float BUTTON_WIDTH = 60f;
	private static final float BUTTON_HEIGHT = 30f;
	
	private OrthographicCamera cam;
	
	private SpriteBatch spriteBatch;
	
	//Textures
	private TextureRegion background;
	private TextureRegion okButton;
	private TextureRegion okButtonSelected;
	
	private int width;
    private int height;
    private float ppuX;
    private float ppuY;
    
    /*
     * Constructeur
     */
    
    
    public ChooseRenderer() {
    	this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.cam.update();
		spriteBatch = new SpriteBatch();
        loadTextures();
    }
		
    /*
     * Méthodes
     */	
		

	private void loadTextures() {
		final String path = System.getProperty("user.dir");
		TextureAtlas atlas = new TextureAtlas(path + "/res/img/choose/textures.pack");
		background = atlas.findRegion("menu20");
		okButton = atlas.findRegion("ok");
		okButtonSelected = atlas.findRegion("okSelected");
	}

	public void render() {
		spriteBatch.begin();
			drawBackground();
			drawOkButton();
			drawTextButton();
		spriteBatch.end();
		
	}
	
	private void drawTextButton() {
		TextButtonStyle style = new TextButtonStyle();
		style.font = new BitmapFont();
		style.fontColor = Color.RED;
		TextButton b = new TextButton("Select your music : ", style);
		b.translate(30f * ppuX, 60f * ppuY);
		b.draw(spriteBatch, 1);
	}

	private void drawBackground() {
		spriteBatch.draw(background, 0, 0, CAMERA_WIDTH * ppuX, CAMERA_HEIGHT * ppuY);
		
	}
	
	private void drawOkButton() {
		spriteBatch.draw(okButton, 40 * ppuX, 20 * ppuY, BUTTON_WIDTH * ppuX, BUTTON_HEIGHT * ppuY);		
	}
	
	private void drawOkButtonSelected() {
		spriteBatch.draw(okButtonSelected, 40 * ppuX, 20 * ppuY, BUTTON_WIDTH * ppuX, BUTTON_HEIGHT * ppuY);		
	}

	public void setSize(int width, int height) {
		this.width = width;
        this.height = height;
        ppuX = (float)width / CAMERA_WIDTH;
        ppuY = (float)height / CAMERA_HEIGHT;
		
	}

	public void dispose() {
		background.getTexture().dispose();
		
	}

}
