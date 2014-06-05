package game.view;

import game.controller.SelectionController;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SelectionRenderer {
	
	private static final float CAMERA_WIDTH = 100f;
	private static final float CAMERA_HEIGHT = 100f;
	private static final float BUTTON_WIDTH = 60f;
	private static final float BUTTON_HEIGHT = 30f;
	//private static final float RUNNING_FRAME_DURATION = 0.06f;
	
	private OrthographicCamera cam;
	
	private SpriteBatch spriteBatch;
	
	
	//Textures
	private TextureRegion background;
	private TextureRegion buttonSelect;
	private TextureRegion buttonSelectSelected;
	private TextureRegion buttonBack;
	private TextureRegion buttonBackSelected;
	private TextureRegion buttonChoose;
	private TextureRegion buttonChooseSelected;
	
	private int width;
    private int height;
    private float ppuX;
    private float ppuY;
    
    
    /*
     * Constructeur
     */	
    
    public SelectionRenderer() {
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
		TextureAtlas atlas = new TextureAtlas(path + "/res/img/selection/textures.pack");
		background = atlas.findRegion("menu20");
		buttonSelect = atlas.findRegion("select");
		buttonSelectSelected = atlas.findRegion("selectSelected");
		buttonBack = atlas.findRegion("back");
		buttonBackSelected = atlas.findRegion("backSelected");
		buttonChoose = atlas.findRegion("choose");
		buttonChooseSelected = atlas.findRegion("chooseSelected");
		
	}

	public void render() {
		spriteBatch.begin();
			drawBackground();
			if(SelectionController.isBackButtonSelected()) {
				drawButtonBackSelected();
			} else {
				drawButtonBack();
			}
			if(SelectionController.isSelectButtonSelected()) {
				drawButtonSelectSelected();
			} else {
				drawButtonSelect();
			}
			if(SelectionController.isChooseButtonSelected()) {
				drawButtonChooseSelected();
			} else {
				drawButtonChoose();
			}
		spriteBatch.end();
		
	}
	
		
	private void drawBackground() {
		spriteBatch.draw(background, 0, 0, CAMERA_WIDTH * ppuX, CAMERA_HEIGHT * ppuY);
	}
	
	private void drawButtonSelect() {
		spriteBatch.draw(buttonSelect, 20 * ppuX, 60 * ppuY, BUTTON_WIDTH * ppuX, (BUTTON_HEIGHT - 5) * ppuY);
	}
	
	private void drawButtonSelectSelected() {
		spriteBatch.draw(buttonSelectSelected, 20 * ppuX, 60 * ppuY, BUTTON_WIDTH * ppuX, (BUTTON_HEIGHT - 5) * ppuY);
	}
	
	private void drawButtonBack() {
		spriteBatch.draw(buttonBack, 30 * ppuX, 5 * ppuY, (BUTTON_WIDTH - 20f) * ppuX, (BUTTON_HEIGHT - 10f) * ppuY);
	}
	
	private void drawButtonBackSelected() {
		spriteBatch.draw(buttonBackSelected, 30 * ppuX, 5 * ppuY, (BUTTON_WIDTH - 20f) * ppuX, (BUTTON_HEIGHT - 10f) * ppuY);
	}
	
	private void drawButtonChoose() {
		spriteBatch.draw(buttonChoose, 20 * ppuX, 40 * ppuY, BUTTON_WIDTH * ppuX, (BUTTON_HEIGHT - 5) * ppuY);
	}
	
	private void drawButtonChooseSelected() {
		spriteBatch.draw(buttonChooseSelected, 20 * ppuX, 40 * ppuY, BUTTON_WIDTH * ppuX, (BUTTON_HEIGHT - 5) * ppuY);
	}

	public void setSize(int width, int height) {
		this.width = width;
        this.height = height;
        ppuX = (float)width / CAMERA_WIDTH;
        ppuY = (float)height / CAMERA_HEIGHT;
		
	}

	public void dispose() {
		background.getTexture().dispose();
		buttonSelectSelected.getTexture().dispose();
		buttonSelect.getTexture().dispose();
		buttonBackSelected.getTexture().dispose();
		buttonBack.getTexture().dispose();
		
	}

}
