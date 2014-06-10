package game.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * 
 * @author SamaHell
 * La classe coin qui permet de conter des points avec des pieces.
 * 3 constructeurs : un avec 2 int, un avec 2 float, un avec un Vector2.
 */
public class Coin {
	
	private static final float SIZE = 0.5f;
	
	private Vector2 position = new Vector2();
	private Rectangle bounds = new Rectangle();
	
	public Coin(int x, int y) {
		Vector2 p = new Vector2(x, y);
		this.position = p;
		this.bounds.setX(p.x);
		this.bounds.setY(p.y);
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
	}
	
	public Coin(float x, float y) {
		Vector2 p = new Vector2(x, y);
		this.position = p;
		this.bounds.setX(p.x);
		this.bounds.setY(p.y);
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
	}
	
	public Coin(Vector2 pos) {
		this.position = pos;
		this.bounds.setX(pos.x);
		this.bounds.setY(pos.y);
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
	}

	public Vector2 getPosition() {
		return position;
	}

	public static float getSize() {
		return SIZE;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	/**
	 * Supprime la piece.
	 */
	public void remove() {
		this.position = null;
		this.bounds = null;
	}

}
