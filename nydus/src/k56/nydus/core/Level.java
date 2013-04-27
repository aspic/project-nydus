package k56.nydus.core;

import javax.swing.plaf.synth.Region;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Level {
	
	private TextureRegion background;
	
	private float height,width;
	
	private int numberOfPixels;
	
	private Color color;
	
	public Level (float width, float height, TextureRegion region){
		this.width = width;
		this.height = height;
		this.color = Color.PINK;
		this.background = region;
	}

	public int getNumberOfPixels() {
		return this.numberOfPixels;
	}
	
	public void draw(SpriteBatch sb){
		sb.setColor(this.color);
		sb.draw(background, 0, 0, width, height);
	}

	public float getWidth() {
		return this.width;
	}
	
	public float getHeight(){
		return this.height;
	}

}
