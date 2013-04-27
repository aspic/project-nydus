package k56.nudys;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import k56.nydus.screen.Screen;

public class Game {
	
	public static void main(String[] args) {
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Unpixelation the game";
		config.useGL20 = true;
		config.width = 800;
		config.height = 480;
		config.vSyncEnabled = true;
		
		new LwjglApplication(new Screen(), config);
	}
}