package game.view;

import game.controller.SelectionController;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SelectionRenderer {
	
	private static final float CAMERA_WIDTH = 100f;
	private static final float CAMERA_HEIGHT = 100f;
	//private static final float RUNNING_FRAME_DURATION = 0.06f;
	
	private OrthographicCamera cam;
	
	private SpriteBatch spriteBatch;
	
	//Textures
	private TextureRegion background;
	private TextureRegion buttonPlay;
	private TextureRegion buttonPlaySelected;
	private TextureRegion buttonSelect;
	private TextureRegion buttonSelectSelected;
	private TextureRegion buttonBack;
	private TextureRegion buttonBackSelected;
	
	private int width;
    private int height;
    private float ppuX;
    private float ppuY;
    
    public SelectionRenderer() {
    	this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.cam.update();
		spriteBatch = new SpriteBatch();
        loadTextures();
    }

	private void loadTextures() {
		final String path = System.getProperty("user.dir");
		TextureAtlas atlas = new TextureAtlas(path + "/res/img/selection/textures.pack");
		background = atlas.findRegion("menu20");
		buttonPlay = atlas.findRegion("jouer");
		buttonPlaySelected = atlas.findRegion("jouerSelected");
		buttonSelect = atlas.findRegion("select");
		buttonSelectSelected = atlas.findRegion("selectSelected");
		buttonBack = atlas.findRegion("back");
		buttonBackSelected = atlas.findRegion("backSelected");
		
	}

	public void render() {
		spriteBatch.begin();
			drawBackground();			
			if(SelectionController.isPlayButtonSelected()) {
				drawButtonPlaySelected();
			} else {
				drawButtonPlay();
			}
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
		spriteBatch.end();
		
	}
	
	private void drawBackground() {
		spriteBatch.draw(background, 0, 0, CAMERA_WIDTH * ppuX, CAMERA_HEIGHT * ppuY);
	}
	
	private void drawButtonPlay() {
		spriteBatch.draw(buttonPlay, 20 * ppuX, 10 * ppuY, 60f * ppuX, 30f * ppuY);
	}
	
	private void drawButtonPlaySelected() {
		spriteBatch.draw(buttonPlaySelected, 20 * ppuX, 10 * ppuY, 60f * ppuX, 30f * ppuY);
	}
	
	private void drawButtonSelect() {
		spriteBatch.draw(buttonSelect, 20 * ppuX, 70 * ppuY, 60f * ppuX, 30f * ppuY);
	}
	
	private void drawButtonSelectSelected() {
		spriteBatch.draw(buttonSelectSelected, 20 * ppuX, 70 * ppuY, 60f * ppuX, 30f * ppuY);
	}
	
	private void drawButtonBack() {
		spriteBatch.draw(buttonBack, 55 * ppuX, 1 * ppuY, 60f * ppuX, 30f * ppuY);
	}
	
	private void drawButtonBackSelected() {
		spriteBatch.draw(buttonBackSelected, 55 * ppuX, 1 * ppuY, 60f * ppuX, 30f * ppuY);
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
		buttonPlaySelected.getTexture().dispose();
		buttonPlay.getTexture().dispose();
		buttonBackSelected.getTexture().dispose();
		buttonBack.getTexture().dispose();
		
	}

}
