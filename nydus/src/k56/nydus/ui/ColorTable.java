package k56.nydus.ui;

import k56.nydus.core.ShootingValue;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ColorTable extends Table {
	
	private ShootingValue value;
	
	public ColorTable(int width, int height, TextureRegion region, ShootingValue value) {
		this.value = value;
		
		Image image = new Image(new TextureRegionDrawable(region));
		image.setColor(value.getColor());
		
		this.setBackground(new TextureRegionDrawable(region));
		
		this.size(width, height);
		add(image).expand().fill().pad(1);
	}
	
	public void setSelected() {
		this.setColor(Color.WHITE);
	}

	public void deselect() {
		this.setColor(Color.BLACK);
	}
	
	public ShootingValue getShootingValue() {
		return this.value;
	}
}
