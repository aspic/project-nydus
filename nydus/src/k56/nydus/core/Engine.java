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
	
	//Value that color is changes when hit by the player.
	private float changeVal = 0.01f;
	private Color deltaColor; //Will be used to add to the pixel color.

	private Texture texture;
	
	private float colorThreshold = 0.05f;

	
	public Engine( Camera camera ){
		pixelList = new Array<Pixel>();
		texture = new Texture(Gdx.files.internal("assets/test.png"));
		generateLevel();
	}

	private void generateLevel(){
		//Generate a level here
		System.out.println("Generate Level");
		int regionDim = 5;
		this.level = new Level(10, 10, new TextureRegion(texture));
		level.setColor(Color.DARK_GRAY);
		level.setPixelHeight(1f);
		for (int i = 0; i < level.getWidth(); i+=regionDim) {
			for (int j = 0; j < level.getHeight(); j+=regionDim) {
				placePixel(i, j, regionDim);
			}
		}
	}
	
	private void placePixel(float x, float y, int regionDim){
		float threshold = 0.3f;
		System.out.println("Start Pixel Placement");
		Array<Pixel> occupied = new Array<Pixel>();
		for (int i = 0; i < regionDim; i++) {
			float generate = MathUtils.random();
			if(generate> threshold){
				float pixXPos, pixYPos;
				pixXPos = MathUtils.random(x, x+regionDim-this.level.getPixelDim());
				pixYPos = MathUtils.random(y, y+regionDim-this.level.getPixelDim());
				Pixel tempPixel = new Pixel(pixXPos,pixYPos, new TextureRegion(texture));
				boolean insert = true;
				for (int j = 0; j < occupied.size; j++) {
					System.out.println("Test intersect.");
					if(occupied.get(j).intersects(tempPixel)){
						insert = false;
						System.out.println("INTERSECTS!");
						break;
					}
				}
				if(insert){
					occupied.add(tempPixel);
					pixelList.add(tempPixel);
				}
			}
		}
	}
	
	/**
	 * Check if the coordinate is in a pixel and change pixel accordingly.
	 */
	public void clicked(float x, float y, Action action){
		for (int i = 0; i < pixelList.size; i++) {
			Pixel tempPixel = pixelList.get(i);
			if (tempPixel.insidePixel(x, y)){
				switch(action){
				case ADD:
					pixelList.get(i).addColor(this.deltaColor);
					System.out.println("Add Color!");
					break;
				case SUB:
					pixelList.get(i).subColor(this.deltaColor);
					System.out.println("Sub Color!");
					break;
				}
			}
			//Check to see if pixel is completed.
			isCorrectColor(pixelList.get(i));
		}
	}
	
	private void isCorrectColor(Pixel pixel) {
		float r,g,b;
		r = pixel.getColor().r - level.getColor().r;
		g = pixel.getColor().g - level.getColor().g;
		b = pixel.getColor().b - level.getColor().b;
		if(r < this.colorThreshold && g < this.colorThreshold && b < this.colorThreshold){
			pixel.setColorandLock(level.getColor());
		}
	}

	public void setColor(ShootingValue color){
		switch(color){
		case RED:
			this.deltaColor = new Color(changeVal, 0, 0, 1);
			System.out.println("Set Red!");
			break;
		case GREEN:
			this.deltaColor = new Color( 0, changeVal, 0, 1);
			System.out.println("Set Green!");
			break;
		case BLUE:
			this.deltaColor = new Color(0, 0, changeVal, 1);
			System.out.println("Set Blue!");
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
	
	public void setChangeVal(float value){
		this.changeVal = value;
	}
	

}
