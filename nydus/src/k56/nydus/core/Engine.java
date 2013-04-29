package k56.nydus.core;

import com.badlogic.gdx.Gdx;
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
	
	private float colorThreshold = 0.1f;
	private float ammo;
	private float spectrumFactor;
	
	public Engine(float width, float height){
		spectrumFactor = 0.1f;
		pixelList = new Array<Pixel>();
		texture = new Texture(Gdx.files.internal("assets/test.png"));
		generateLevel(width, height, 1f);
	}

	private void generateLevel(float width, float height, float dim){
		//Generate a level here
		System.out.println("Generate Level");
		if(this.level != null) {
			pixelList.add(this.level.toPixel());
			
		}
		
		this.level = new Level(width, height, new TextureRegion(texture), dim, new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1f));
		
		int regionDim = (int)(width*0.5f);
		for (int i = 0; i < level.getWidth(); i+=regionDim) {
			for (int j = 0; j < level.getHeight(); j+=regionDim) {
				placePixel(i, j, regionDim);
			}
		}
		this.ammo = calculateAmmo();
	}
	
	
	//Calculates the amount of ammo the player starts with.
	private float calculateAmmo() {
		//TODO: Find appropriate value;
		return 1000;
	}
	
	private void addAmmo(float ammo){
		this.ammo += ammo;
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
				Pixel tempPixel = new Pixel(pixXPos, pixYPos, this.level.getPixelDim(), new TextureRegion(texture));
				tempPixel.setColorSpectrumFactor(this.spectrumFactor);
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
			if (tempPixel.insidePixel(x, y) && ammo>0){
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
				//Remove ammo when shot.
				this.ammo--;
				checkPixel(tempPixel);
				break;
			}
		}
	}
	
	private void checkPixel(Pixel pixel) {
		float r,g,b;
		
		r = Math.abs(pixel.getColor().r - level.getColor().r);
		g = Math.abs(pixel.getColor().g - level.getColor().g);
		b = Math.abs(pixel.getColor().b - level.getColor().b);
		if(r < this.colorThreshold && g < this.colorThreshold && b < this.colorThreshold){
			pixelList.removeValue(pixel, true);
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
		
		//Check logic for complete level.
		if(isLevelDone()){
			//TODO: Insert what to be done when level is complete.
		}
		if(!canStillWin()){
			//TODO: Display game over/failure screen.
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

	public void levelTransition(float zoom) {
		generateLevel(zoom*10, zoom*10, zoom);
	}
	
	public boolean isLevelDone(){
		int counter = 0;
		for (int i = 0; i < pixelList.size; i++) {
			if(pixelList.get(i).getColor() == this.level.getColor()){
				counter++;
			}
		}
		if (counter == pixelList.size){
			System.out.println("Level Done!");
			return true;
		}
		return false;
	}
	
	private boolean canStillWin(){
		//TODO: Check if there is enough ammo to complete the level.
		return true;
	}
}
