package game.model;

import com.badlogic.gdx.math.Vector2;

public abstract class Monster {
	
	private Vector2 position;
	
	public Monster(int x, int y) {
		this.position = new Vector2(x, y);
	}
	
	public Monster(Vector2 pos) {
		this.position = pos;
	}

}
