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
	private Color deltaColor; //Will be used to add to the pixel color.
	private ShootingValue selectedColor;
	private float ammo,maxAmmo;
	private boolean isRedLocked = false;
	private boolean isBlueLocked = false;
	private boolean isGreenLocked = false;

	private Texture texture;

	private Difficulty difficulty;
	private Score score;
	
//	private float colorThreshold = 0.1f;
//	private float ammoBonus;
//	private float spectrumFactor;
//	private int ammoDiff = 2;
	
	public Engine(float width, float height){
		this.difficulty = new Difficulty();
		this.score = new Score();
		this.difficulty.setSpectrumFactor(0.1f);
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
		this.level.printColor();
		
		int regionDim = (int)(width*0.5f);
		for (int i = 0; i < level.getWidth(); i+=regionDim) {
			for (int j = 0; j < level.getHeight(); j+=regionDim) {
				placePixel(i, j, regionDim);
			}
		}
		this.ammo = calculateRequiredAmmo();
		this.maxAmmo = this.ammo;
	}
	
	
	//Calculates the amount of ammo the player starts with.
	private float calculateRequiredAmmo() {
		float changeRequired = 0;
		float r,g,b;
		
		for (int i = 0; i < this.pixelList.size; i++) {
			Pixel tmpPixel = this.pixelList.get(i);
			r = Math.abs(tmpPixel.getColor().r - level.getColor().r);
			g = Math.abs(tmpPixel.getColor().g - level.getColor().g);
			b = Math.abs(tmpPixel.getColor().b - level.getColor().b);
			changeRequired += (r+g+b);
			System.out.println(changeRequired);
		}
		float ammoToUse = (changeRequired/this.difficulty.getChangeVal())*this.difficulty.getAmmoDiff();
		System.out.println("Ammo: " + ammoToUse);
		return ammoToUse;
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
				tempPixel.setColorSpectrumFactor(this.difficulty.getSpectrumFactor());
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
					tempPixel.printColor();
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
			if (tempPixel.insidePixel(x, y) && ammo>0 && !isColorLocked(selectedColor)){
				switch(action){
				case ADD:
					pixelList.get(i).addColor(this.deltaColor);
					break;
				case SUB:
					pixelList.get(i).subColor(this.deltaColor);
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
		
		if(difficulty.isLockOnCorrectPartial()){
			if (r < this.difficulty.getColorThreshold()) {
				isRedLocked = true;
			}
			if (g < this.difficulty.getColorThreshold()) {
				isGreenLocked = true;
			}
			if (b < this.difficulty.getColorThreshold()) {
				isBlueLocked = true;
			}
		}
		
		System.out.println(r + " " + g + " " + b);
		if(r < this.difficulty.getColorThreshold() && g < this.difficulty.getColorThreshold() && b < this.difficulty.getColorThreshold()){
			pixelList.removeValue(pixel, true);
			addAmmo();
		}
	}

	public void setColor(ShootingValue color){
		switch(color){
		case RED:
			this.deltaColor = new Color(difficulty.getChangeVal(), 0, 0, 1);
			this.selectedColor = ShootingValue.RED;
			System.out.println("Set Red!");
			break;
		case GREEN:
			this.deltaColor = new Color( 0, difficulty.getChangeVal(), 0, 1);
			this.selectedColor = ShootingValue.GREEN;
			System.out.println("Set Green!");
			break;
		case BLUE:
			this.deltaColor = new Color(0, 0, difficulty.getChangeVal(), 1);
			this.selectedColor = ShootingValue.BLUE;
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
			System.out.println("You won!");
			//TODO: Insert what to be done when level is complete.
		}
		if(!canStillWin()){
			System.out.println("Fail much!?");
			score.calculateScore(ammo, difficulty);
			System.out.println(score.getScore());
			//TODO: Display game over/failure screen.
		}
	}
	
	public float getWidth() {
		return level.getWidth();
	}
	
	public float getHeight() {
		return level.getHeight();
	}

	public void levelTransition(float zoom) {
		generateLevel(zoom*10, zoom*10, zoom);
	}
	
	public boolean isLevelDone(){
		if(pixelList.size<= 0){
			return true;
		}
		return false;
	}
	
	private boolean canStillWin(){
		//TODO: Check if there is enough ammo to complete the level.
		return true;
	}

	public float getAmmoRatio() {
		return ammo/this.maxAmmo;
	}
	
	private void addAmmo(){
		this.ammo += this.difficulty.getAmmoBonus();
	}
	
	public float getAmmo() {
		return ammo;
	}
	
	public void setAmmo(float ammo) {
		this.ammo = ammo;
	}
	
	public void setDifficulty(Difficulty difficulty){
		this.difficulty = difficulty;
	}
	
	public boolean isColorLocked(ShootingValue color){
		switch(color){
		case RED:
			if(isRedLocked)
				return true;
			break;
		case GREEN:
			if(isGreenLocked)
				return true;
			break;
		case BLUE:
			if(isBlueLocked)
				return true;
			break;
		}
		return false;
	}
}
