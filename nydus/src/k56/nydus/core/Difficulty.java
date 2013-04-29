package k56.nydus.core;

public class Difficulty {
	
	private float colorThreshold = 0.1f;
	private float ammoBonus = 50;
	private float spectrumFactor = 0.2f;
	private float changeVal = 0.01f;
	private int ammoDiff = 2;
	

	private float time = 1000;
	private boolean timed = false;
	private boolean lockOnCorrectPartial = true;
	
	public Difficulty(){
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

	public float getChangeVal() {
		return changeVal;
	}
	
	public void setChangeVal(float changeVal) {
		this.changeVal = changeVal;
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
	
	public boolean isLockOnCorrectPartial() {
		return lockOnCorrectPartial;
	}
	
	public void setLockOnCorrectPartial(boolean lockOnCorrectPartial) {
		this.lockOnCorrectPartial = lockOnCorrectPartial;
	}
	
	

}
