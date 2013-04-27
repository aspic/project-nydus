package k56.nydus.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ColorTable extends Table {
	
	private Color selectedColor;
	
	public ColorTable(int width, int height, TextureRegion region, Color color) {
		Image image = new Image(new TextureRegionDrawable(region));
		image.setColor(color);
		
		this.setBackground(new TextureRegionDrawable(region));
		
		selectedColor = new Color(Color.ORANGE);
		this.setColor(selectedColor);
		
		this.size(width, height);
		add(image).expand().fill().pad(1);
	}
}
