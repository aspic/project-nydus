package k56.nydus.core;

public class Difficulty {
	
	private float colorThreshold = 0.03f;
	private float ammoBonus;
	private float spectrumFactor;
	private int ammoDiff = 2;
	private float time;
	private boolean timed = false;
	
	public Difficulty(){
		this.colorThreshold = 0.03f;
		this.ammoDiff = 5;
		this.ammoBonus = 10*ammoDiff;
		this.spectrumFactor = 0.2f;
		this.time = 1000;
	}
	
	public Difficulty(float colorThreshold, float ammoBonus, float spectrumFactro, float time, int ammo){
		this.colorThreshold = colorThreshold;
		this.ammoDiff = ammo;
		this.ammoBonus = ammoBonus;
		this.spectrumFactor = spectrumFactro;
		this.time = time;
	}

	public float getColorThreshold() {
		return colorThreshold;
	}

	public void setColorThreshold(float colorThreshold) {
		this.colorThreshold = colorThreshold;
	}

	public float getAmmoBonus() {
		return ammoBonus;
	}

	public void setAmmoBonus(float ammoBonus) {
		this.ammoBonus = ammoBonus;
	}

	public float getSpectrumFactor() {
		return spectrumFactor;
	}

	public void setSpectrumFactor(float spectrumFactor) {
		this.spectrumFactor = spectrumFactor;
	}

	public int getAmmoDiff() {
		return ammoDiff;
	}

	public void setAmmoDiff(int ammoDiff) {
		this.ammoDiff = ammoDiff;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	public boolean isTimed() {
		return timed;
	}

	public void setTimed(boolean timed) {
		this.timed = timed;
	}
	
	

}
