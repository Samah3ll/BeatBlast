package game.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Runner02 {
	
	public enum State02 {
		RUNNING, JUMPING
	}
	
	public enum Skin {
		METAL, REGGEA, CLASSICAL, ROCK, BASIC
	}
	
	static final float SPEED = 4f;
	static final float JUMP_VELOCITY = 1f;
	static final float SIZE = 0.5f;
	
	Vector2 	position = new Vector2();
	Vector2 	acceleration = new Vector2();
	Vector2 	velocity = new Vector2();
	Rectangle 	bounds = new Rectangle();
	State02		state = State02.JUMPING;
	boolean		facingLeft = true;
	boolean 	canRun = false;
	float       stateTime = 0;
	
	public Runner02(Vector2 pos) {
		this.position = pos;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
	}
	
	public boolean canRun() {
		
		return canRun;
	}
	
	public void setRun(boolean b) {
		canRun = b;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public Vector2 getPosition() {
		return position;
	}
	

	public void setState(State02 newState) {
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

	public State02 getState() {
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

}