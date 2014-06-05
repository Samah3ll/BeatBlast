package game.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import game.controller.MenuController;

public class MenuRenderer {
	
	//TODO:modifier les tailles de la caméra (penser aux autres écrans)
	private static final float BUTTON_WIDTH = 40f;
	private static final float BUTTON_HEIGHT = 20f;
	private static final float CAMERA_WIDTH = 100f;
	private static final float CAMERA_HEIGHT = 100f;
	//private static final float RUNNING_FRAME_DURATION = 0.06f;
	
	private OrthographicCamera cam;
	
	private SpriteBatch spriteBatch;
	
	private TextureRegion background;
	private TextureRegion buttonGame;
	private TextureRegion buttonGameSelected;
	private TextureRegion buttonQuit;
	private TextureRegion buttonQuitSelected;
	
	private int width;
    private int height;
    private float ppuX;
    private float ppuY;
    
    public MenuRenderer() {
    	this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.cam.update();
		spriteBatch = new SpriteBatch();
        loadTextures();
    }
	
	
	public void setSize (int w, int h) {
        this.width = w;
        this.height = h;
        ppuX = (float)width / CAMERA_WIDTH;
        ppuY = (float)height / CAMERA_HEIGHT;
        
    }
	
	private void loadTextures() {
		final String path = System.getProperty("user.dir");
		TextureAtlas atlas = new TextureAtlas(path + "/res/img/menu/textures.pack");
		background = atlas.findRegion("menu20");
		buttonGame = atlas.findRegion("jouer");
		buttonGameSelected = atlas.findRegion("jouerSelected");
		buttonQuit = atlas.findRegion("Quit");
		buttonQuitSelected = atlas.findRegion("QuitSelected");
		if(background == null) {
			System.err.println("no background");
		}
	}
	
	public void render() {
		spriteBatch.begin();
            drawBackground();           
            if(MenuController.isPlayButtonSelected()) {
            	drawButtonGameSelected();
            } else {
            	drawButtonGame();
            }
            if(MenuController.isQuitButtonSelected()) {
            	drawButtonQuitSelected();
            } else {
            	drawButtonQuit();
            }
            	
        spriteBatch.end();
    }


	private void drawBackground() {
		spriteBatch.draw(background, 0, 0, CAMERA_WIDTH * ppuX, CAMERA_HEIGHT * ppuY);
	}
	
	private void drawButtonGame() {
		spriteBatch.draw(buttonGame, 30f * ppuX, 25f * ppuY, BUTTON_WIDTH * ppuX, BUTTON_HEIGHT * ppuY);
	}
	
	private void drawButtonGameSelected() {
		spriteBatch.draw(buttonGameSelected, 30f * ppuX, 25f * ppuY, BUTTON_WIDTH * ppuX, BUTTON_HEIGHT * ppuY);
	}
	
	private void drawButtonQuit() {
		spriteBatch.draw(buttonQuit, 30f * ppuX, 5f * ppuY, BUTTON_WIDTH * ppuX, BUTTON_HEIGHT * ppuY);
	}
	
	private void drawButtonQuitSelected() {
		spriteBatch.draw(buttonQuitSelected, 30f * ppuX, 5f * ppuY, BUTTON_WIDTH * ppuX, BUTTON_HEIGHT * ppuY);
	}


	public void dispose() {
		background.getTexture().dispose();
		buttonGame.getTexture().dispose();
		buttonGameSelected.getTexture().dispose();
		buttonQuit.getTexture().dispose();
		buttonQuitSelected.getTexture().dispose();
	}


}
