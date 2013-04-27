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

	public int getNumberOfPixels() {
		// TODO Auto-generated method stub
		return this.numberOfPixels;
	}
	
	public void draw(SpriteBatch sb){
		sb.setColor(color);
		sb.draw(background, 0, 0);
		sb.setColor(Color.WHITE);	
	}

}
