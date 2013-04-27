package k56.nydus.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;



public class Pixel {
	
	private float x,y,height,width;
	
	private Color color;
	private TextureRegion region;
	
	public Pixel(float x, float y){
		this.height = 1;
		this.width = 1;
		this.x = x;
		this.y = y;
	}

	public void draw(SpriteBatch sb){
		sb.setColor(color);
		sb.draw(region, x, y, height, width); //TODO: HOW TO CHOSE POSITION HERE!?!?!
		sb.setColor(Color.WHITE);
	}
	
	public void addColor(Color color){
		this.color.add(color);
	}
	
	public void subColor(Color color){
		this.color.sub(color);
	}
	
}
