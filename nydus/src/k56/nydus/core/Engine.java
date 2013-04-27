package k56.nydus.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Engine {
	
	private Level level;
	private Array<Pixel> pixelList;
	
	private Camera camera;
	
	//Value that color is changes when hit by the player.
	private float changeVal;
	private Color color; //Will be used to add to the pixel color.
	
	private Texture texture;

	
	public Engine( Camera camera ){
		pixelList = new Array<Pixel>();
		generateLevel();
		texture = new Texture(Gdx.files.internal("assets/test.png"));
	}

	private void generateLevel(){
		//Generate a level here.
		for (int i = 0; i < level.getNumberOfPixels(); i++) {
			pixelList.add(new Pixel());
		}
	}
	
	/**
	 * Check if the coordinate is in a pixel and change pixel accordingly.
	 */
	public void clicked(float x, float y, Action action){
		//TODO: Check if in pixel. Switch case on action to see what method to call from Pixel.
	}
	
	public void setColor(ShootingValue color){
		switch(color){
		case RED:
			this.color = new Color(changeVal, 0, 0, 1);
			break;
		case GREEN:
			this.color = new Color( 0, changeVal, 0, 1);
			break;
		case BLUE:
			this.color = new Color(0, 0, changeVal, 1);
			break;
		}
	}
	
	public void render(SpriteBatch sb){
		level.draw(sb);
		for (int i = 0; i < pixelList.size; i++) {
			pixelList.get(i).draw(sb);
		}
	}

}
