package k56.nydus.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Level {
	
	private TextureRegion background;
	
	private float height,width;
	
	private int numberOfPixels;
	private float pixelHeight;
	
	private Color color;
	private float ratio;
	
	/** Standard level, ratio of one */
	public Level(float width, float height, TextureRegion region) {
		this(width, height, region, 1);
	}
	
	public Level (float width, float height, TextureRegion region, float ratio){
		this.width = width * ratio;
		this.height = height * ratio;
		this.color = Color.PINK;
		this.background = region;
		this.ratio = ratio;
	}

	public int getNumberOfPixels() {
		return this.numberOfPixels;
	}
	
	public void draw(SpriteBatch sb){
		sb.setColor(this.color);
		sb.draw(background, 0, 0, width * ratio, height * ratio);
		sb.setColor(Color.WHITE);
	}

	public float getWidth() {
		return this.width * ratio;
	}
	
	public float getHeight(){
		return this.height * ratio;
	}
	
	public float getRatio() {
		return this.ratio;
	}

	public void setRatio(float zoom) {
		this.ratio = zoom;
	}
	
	public void setColor(Color color){
		this.color = color;
	}

	public float getPixelDim() {
		return pixelHeight;
	}

	public void setPixelHeight(float pixelHeight) {
		this.pixelHeight = pixelHeight;
	}

	public Color getColor() {
		// TODO Auto-generated method stub
		return this.color;
	}
	
}
