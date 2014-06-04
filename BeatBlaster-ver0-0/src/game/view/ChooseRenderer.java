package game.view;

import java.util.ArrayList;
import java.util.Iterator;

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
	
	private OrthographicCamera cam;
	
	private SpriteBatch spriteBatch;
	
	//Textures
	private TextureRegion background;
	private TextureRegion highlight;
	
	private int width;
    private int height;
    private float ppuX;
    private float ppuY;
        
    //Musiques sauvegardées
    private ArrayList<String> savedFiles;
    
    //Index de début et de fin des musiques à afficher dans la liste des musiques sauvegardées
    private int lastStart;
    private int lastEnd;
    
    //Liste des musiques à afficher
    private ArrayList<String> printable = new ArrayList<String>(8);
    
    
    /*
     * Constructeur
     */
    
    
    public ChooseRenderer(ArrayList<String> savedFiles) {
    	this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.cam.update();
		this.savedFiles = savedFiles;
		spriteBatch = new SpriteBatch();
        loadTextures();
        lastStart = 0;
        if(savedFiles.size() <= 7) {
        	lastEnd = savedFiles.size();
        } else {
        	lastEnd = 7;
        }
        for(Iterator<String> it = savedFiles.iterator(); it.hasNext();) {
        	String current = it.next();
        	if(!current.contains(".dat")) {
        		it.remove();;
        	}
        }
        for(int i = 0; i < 8; i++) {
        	
        	printable.add(i, savedFiles.get(i));
        }
        
    }
    
    /*
     * Accesseur
     */	
    
    public boolean isLastSong(int num) {
    	if(num > savedFiles.size() - 1) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public boolean isFirstSong(int num) {
    	if(num == lastStart) {
    		return true;
    	} else {
    		return false;
    	}
    }
		
    /*
     * Méthodes
     */	
		

	private void loadTextures() {
		final String path = System.getProperty("user.dir");
		TextureAtlas atlas = new TextureAtlas(path + "/res/img/choose/textures.pack");
		background = atlas.findRegion("menu20");
		highlight = atlas.findRegion("highlight");
	}

	public void render() {
		spriteBatch.begin();
			drawBackground();
			//drawTextButton();
			printPrintable();
		spriteBatch.end();
		
	}
	
	private void drawTextButton() {
		TextButtonStyle style = new TextButtonStyle();
		style.font = new BitmapFont();
		style.fontColor = Color.GREEN;
		TextButton b = new TextButton("Select your music : ", style);
		b.translate(20f * ppuX, 80f * ppuY);
		b.draw(spriteBatch, 1);
	}
	
	public void higlightButtonN(int num) {
		//System.out.println("musique sélectionnée : " + num);	
		
		if(num > savedFiles.size() - 1) {
			num = savedFiles.size() - 1;
		}
			
		int x = 20;
		int y = 80 - 5 * num;
		
		spriteBatch.begin();
		spriteBatch.draw(highlight, (x + 11) * ppuY, (y - 4.5f) * ppuY, 60 * ppuX, 3 * ppuY);
		spriteBatch.end();
	}
	
	public void printPrintable() {
		TextButtonStyle style = new TextButtonStyle();
		style.font = new BitmapFont();
		style.fontColor = Color.GREEN;
		
		float f = 0;
		for(int i = 0; i < 8; i++) {			
			f += 5f;
			String fileName = printable.get(i);
			fileName = fileName.substring(0, fileName.length() - 4);
			TextButton b1 = new TextButton(fileName, style);
			b1.translate(20f * ppuX, (80f - f) * ppuY);
			b1.draw(spriteBatch, 1);
			
		}
	}
	
	public void scrollSelection(int dx) {
		if(lastStart + dx < 0 && dx < 0) {
			return;
		}
		if(lastEnd > savedFiles.size() - 2 && dx > 0) {
			return;
		}
		int pos = 0;
		for(int i = lastStart + dx; i <= lastEnd + dx; i++) {
			printable.set(pos, savedFiles.get(i));
			pos++;
		}
		lastStart += dx;
		lastEnd += dx;
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

	public void dispose() {
		background.getTexture().dispose();
		
	}

}
