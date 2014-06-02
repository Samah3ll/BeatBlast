package game.view;

import java.util.ArrayList;
import java.util.Iterator;

import game.controller.ChooseController;
import game.utils.Reader;

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
	private TextureRegion backButton;
	private TextureRegion backButtonSelected;
	private TextureRegion highlight;
	
	private int width;
    private int height;
    private float ppuX;
    private float ppuY;
    
    private Reader reader = new Reader();
    
    private String saveDirectory;
    private ArrayList<String> savedFiles;
    
    /*
     * Constructeur
     */
    
    
    public ChooseRenderer(String save) {
    	this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.cam.update();
		this.saveDirectory = save;
		savedFiles = reader.getListFiles(save);
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
		backButton = atlas.findRegion("back");
		backButtonSelected = atlas.findRegion("backSelected");
		highlight = atlas.findRegion("highlight");
	}

	public void render() {
		spriteBatch.begin();
			drawBackground();
			if(ChooseController.isBackButtonSelected()) {
				drawBackButtonSelected();
			} else {
				drawBackButton();
			}
			if(ChooseController.isOkButtonSelected()) {
				drawOkButtonSelected();
			} else {
				drawOkButton();
			}
			drawTextButton();
			highlightText(20, 80);
		spriteBatch.end();
		
	}
	
	private void drawTextButton() {
		TextButtonStyle style = new TextButtonStyle();
		style.font = new BitmapFont();
		style.fontColor = Color.RED;
		TextButton b = new TextButton("Select your music : ", style);
		b.translate(20f * ppuX, 80f * ppuY);
		b.draw(spriteBatch, 1);
		
		float f = 0;
		for(Iterator<String> it = savedFiles.iterator(); it.hasNext();) {			
			f += 5f;
			String fileName = it.next();
			fileName = fileName.substring(0, fileName.length() - 4);
			TextButton b1 = new TextButton(fileName, style);
			b1.translate(20f * ppuX, (80f - f) * ppuY);
			b1.draw(spriteBatch, 1);
			
			if(f == 50f) {
				break;
			}
		}
	}
	
	private void highlightText(int x, int y) {
		spriteBatch.draw(highlight, (x + 11) * ppuY, (y - 4) * ppuY, 20 * ppuX, 3 * ppuY);
	}

	private void drawBackground() {
		spriteBatch.draw(background, 0, 0, CAMERA_WIDTH * ppuX, CAMERA_HEIGHT * ppuY);
		
	}
	
	private void drawOkButton() {
		spriteBatch.draw(okButton, 0 * ppuX, 20 * ppuY, BUTTON_WIDTH * ppuX, BUTTON_HEIGHT * ppuY);		
	}
	
	private void drawOkButtonSelected() {
		spriteBatch.draw(okButtonSelected, 0 * ppuX, 20 * ppuY, BUTTON_WIDTH * ppuX, BUTTON_HEIGHT * ppuY);		
	}
	
	private void drawBackButton() {
		spriteBatch.draw(backButton, 20 * ppuX, 5 * ppuY, BUTTON_WIDTH * ppuX, BUTTON_HEIGHT * ppuY);		
	}
	
	private void drawBackButtonSelected() {
		spriteBatch.draw(backButtonSelected, 20 * ppuX, 5 * ppuY, BUTTON_WIDTH * ppuX, BUTTON_HEIGHT * ppuY);		
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
