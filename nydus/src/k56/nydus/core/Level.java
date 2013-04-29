package k56.nydus.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Level {
	
	private TextureRegion background;
	
	private float height,width;
	
	private int numberOfPixels;
	private float pixelDim;
	
	private Color color;
	private float dimension;
	
	private float startingAmmo;
	
	public Level (float width, float height, TextureRegion region, float dimension, Color color){
		this.width = width;
		this.height = height;
		this.background = region;
		this.pixelDim = dimension;
		this.color = color;
	}

	public int getNumberOfPixels() {
		return this.numberOfPixels;
	}
	
	public void draw(SpriteBatch sb){
		sb.setColor(this.color);
		sb.draw(background, 0, 0, width, height);
		sb.setColor(Color.WHITE);
	}

	public float getWidth() {
		return this.width;
	}
	
	public float getHeight(){
		return this.height;
	}
	
	public float getPixelDim() {
		return pixelDim;
	}

	public void setPixelHeight(float pixelHeight) {
		this.pixelDim = pixelHeight;
	}

	public Color getColor() {
		return this.color;
	}

	public float getStartAmmo() {
		return this.startingAmmo;
	}
	
	/** Returns this level as a new pixel */
	public Pixel toPixel() {
		return new Pixel(0, 0, width, background, this.color);
	}
	
	public void printColor(){
		System.out.println(this.color.r + " " + this.color.g + " " + this.color.b);
	}
	
}
