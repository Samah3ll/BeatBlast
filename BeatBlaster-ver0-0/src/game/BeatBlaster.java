package game;



import com.badlogic.gdx.Game;

import game.screens.GameScreen;
import game.screens.MenuScreen;
import game.screens.PauseScreen;

public class BeatBlaster extends Game {
	
	
	@Override
	public void create() {
		//setScreen(new MenuScreen(this));
		setScreen(new GameScreen(this));
		//setScreen(new PauseScreen(this));
	}
	
	public void dispose () {
		super.dispose();
		getScreen().dispose();
		System.exit(0);
	}
	
}
