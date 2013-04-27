package k56.nydus.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;



public class Pixel {
	
	private float x,y,height,width;
	
	private Color color;
	
	/***
	 * Value that is used in pixel generation. Decides the available colors. A higher value the more contrast there will be in the color spectrum.
	 * Is used by the Pixel constructor (at the moment) to set it's tint color.
	 * TODO: USE IT!
	 */
	private float colorSpectrumFactor = 0.05f;
	
	private boolean lock = false; //If true color can not change.
	
	
	private TextureRegion region;
	
	public Pixel(float x, float y, TextureRegion textureRegion){
		this.color = new Color(MathUtils.random((int)1f/colorSpectrumFactor)/(1f/colorSpectrumFactor), MathUtils.random((int)1f/colorSpectrumFactor)/(1f/colorSpectrumFactor), MathUtils.random((int)1f/colorSpectrumFactor)/(1f/colorSpectrumFactor), 1);
		this.height = 1;
		this.width = 1;
		this.x = x;
		this.y = y;
		this.region = textureRegion;
	}

	public void draw(SpriteBatch sb){
		sb.setColor(color);
		sb.draw(region, x, y, height, width);
		sb.setColor(Color.WHITE);
	}
	
	public void addColor(Color color){
		if(!lock)
			this.color.add(color);
	}
	
	public void subColor(Color color){
		if(!lock)
			this.color.sub(color);
	}
	
	/***
	 * Check to see if coordinates are inside the pixel.
	 * @param x2
	 * @param y2
	 * @return
	 */
	public boolean insidePixel(float x2, float y2) {
		if(x2 > this.x && x2 < this.x+this.width && y2 > this.y && y2 < this.y+this.height)
			return true;
		return false;
	}
	
	public boolean intersects(Pixel pixel){
		if(this.insidePixel(pixel.getX(), pixel.getY()) || 
				this.insidePixel(pixel.getX()+pixel.getWidth(), pixel.getY()) ||
				this.insidePixel(pixel.getX(), pixel.getY()+pixel.getHeight()) ||
				this.insidePixel(pixel.getX()+ pixel.getWidth(), pixel.getY()+pixel.getHeight())){
			return true;
		}
		return false;
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getHeight() {
		return height;
	}

	public float getWidth() {
		return width;
	}

	public float getColorSpectrumFactor() {
		return colorSpectrumFactor;
	}
	
	public void setColorSpectrumFactor(float colorSpectrumFactor) {
		this.colorSpectrumFactor = colorSpectrumFactor;
	}
	
	public void setColorandLock(Color color){
		this.color = color;
		this.lock = true;
	}

	public Color getColor() {
		// TODO Auto-generated method stub
		return this.color;
	}
	
}
