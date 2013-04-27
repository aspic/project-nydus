package k56.nydus.core;

import com.badlogic.gdx.graphics.Color;

public enum ShootingValue {
	
	RED(Color.RED), GREEN(Color.GREEN), BLUE(Color.BLUE);
	
	Color color;
	private ShootingValue(Color color) {
		this.color = color;
	}
	public Color getColor() {
		return this.color;
	}
}
