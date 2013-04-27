package k56.nydus.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Engine {
	
	private Level level;
	private Array<Pixel> pixelList;
	
	private Camera camera;
	
	//Value that color is changes when hit by the player.
	private float changeVal;
	private Color deltaColor; //Will be used to add to the pixel color.
	
	private Texture texture;

	
	public Engine( Camera camera ){
		pixelList = new Array<Pixel>();
		texture = new Texture(Gdx.files.internal("assets/test.png"));
		generateLevel();
	}

	private void generateLevel(){
		//Generate a level here.
		this.level = new Level(10, 10, new TextureRegion(texture));
		for (int i = 0; i < level.getWidth()-5; i+=5) {
			for (int j = 0; j < level.getHeight(); j++) {
				placePixel(i, j);
			}
		}
		
//		double generatePixelThreshold = 1 - (double)level.getNumberOfPixels()/(double)(level.getHeight()*level.getWidth());
//		for (int i = 0; i < level.getNumberOfPixels(); i++) {
//			double generate = MathUtils.random(0, 1);
//			if(generate > generatePixelThreshold){
//				for()
//				float tempXPos, tempYPos;
//				tempXPos = (float) (Math.random() % (float) level.getWidth());
//				tempYPos = (float) (Math.random() % (float) level.getHeight());
//				this.pixelList.add(new Pixel(tempXPos, tempYPos));
//			}
//		}
	}
	
	private void placePixel(float x, float y){
		float threshold = 0.5f;
		for (int i = 0; i < 5; i++) {
			float generate = MathUtils.random();
			if(generate> threshold){
				pixelList.add(new Pixel(x+i,y+i, new TextureRegion(texture)));				
			}
		}
	}
	
	/**
	 * Check if the coordinate is in a pixel and change pixel accordingly.
	 */
	public void clicked(float x, float y, Action action){
		//TODO: Check if in pixel. Switch case on action to see what method to call from Pixel.
		for (int i = 0; i < pixelList.size; i++) {
			Pixel tempPixel = pixelList.get(i);
			if (tempPixel.insidePixel(x, y)){
				switch(action){
				case ADD:
					pixelList.get(i).addColor(this.deltaColor);
					break;
				case SUB:
					pixelList.get(i).subColor(this.deltaColor);
					break;
				}
			}
		}
	}
	
	public void setColor(ShootingValue color){
		switch(color){
		case RED:
			this.deltaColor = new Color(changeVal, 0, 0, 1);
			break;
		case GREEN:
			this.deltaColor = new Color( 0, changeVal, 0, 1);
			break;
		case BLUE:
			this.deltaColor = new Color(0, 0, changeVal, 1);
			break;
		}
	}
	
	public void render(SpriteBatch sb){
		level.draw(sb);
		for (int i = 0; i < pixelList.size; i++) {
			pixelList.get(i).draw(sb);
		}
	}
	
	public float getWidth() {
		return level.getWidth();
	}
	
	public float getHeight() {
		return level.getHeight();
	}
}
