package game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class Main {
	public static void main(String[] args) {
		new LwjglApplication(new BeatBlaster(), "Beat Blaster", 720, 450, true);
	}
}
