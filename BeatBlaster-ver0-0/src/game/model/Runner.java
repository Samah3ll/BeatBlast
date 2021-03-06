package game.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Le personnage qui court sur l'�cran.
 * Il poss�de une position (Vector2), une vitesse et une acc�leration (Vector2) une limite (bounds) pour d�tecter les collisions et un �tat.
 * 
 * @author SamaHell
 *
 */
public class Runner {
	
	public enum State {
		IDLE, WALKING, JUMPING, DYING
	}
	
	static final float SPEED = 4f;
	static final float JUMP_VELOCITY = 1f;
	static final float SIZE = 0.9f;
	
	Vector2 	position = new Vector2();
	Vector2 	acceleration = new Vector2();
	Vector2 	velocity = new Vector2();
	Rectangle 	bounds = new Rectangle();
	State		state = State.IDLE;
	boolean		facingLeft = true;
	float       stateTime = 0;
	
	public Runner(Vector2 pos) {
		this.position = pos;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public Vector2 getPosition() {
		return position;
	}
	

	public void setState(State newState) {
		this.state = newState;
	}
	
	public void update(float delta) {
		stateTime += delta;
		position.add(velocity.cpy().mul(delta));
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public Vector2 getAcceleration() {
		return acceleration;
	}
	
	public void setFacingLeft(boolean b) {
		this.facingLeft = b;
		
	}

	public boolean isFacingLeft() {
		return facingLeft;
	}

	public State getState() {
		return state;
	}

	public float getStateTime() {
		return stateTime;
	}
	
	public static float getSize() {
		return SIZE;
	}

	public void setPosition(Vector2 position2) {
		this.position = position2;
		
	}

	public void dispose() {
		velocity = null;
		acceleration = null;
		position = null;
		bounds = null;
		state = null;
	}

}
