package game.model;

import com.badlogic.gdx.math.Vector2;

/**
 * Block de base, première classe implémentable du type block.
 * @author SamaHell
 *
 */
public class BasicBlock extends Block {
	

	public BasicBlock(Vector2 pos) {
		super(pos);
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
	}
	
	public BasicBlock(int x, int y) {
		super(x, y);
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
	}

	public BasicBlock(float x, float y) {
		super(x, y);
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
	}

}
