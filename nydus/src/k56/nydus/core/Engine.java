package k56.nydus.core;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Engine {
	
	private Level level;
	private ArrayList<Pixel> pixelList;
	
	private Camera camera;
	
	//Value that color is changes when hit by the player.
	private int changeVal = 5;
	
	
	public Engine( Camera camera ){
		pixelList = new ArrayList<Pixel>();
		generateLevel();
	}

	private void generateLevel(){
		//Generate a level here.
	}
	
	/**
	 * Check if the coordinate is in a pixel and change pixel accordingly.
	 */
	public void clicked(float x, float y){
		
	}
	
	public void render(SpriteBatch sb){
		for (int i = 0; i < pixelList.size(); i++) {
			pixelList.get(i).draw();
		}
		
	}

}
