package k56.nydus.core;

public class Score {
	
	private float ammoFactor = 20;
	private float ammoBonusFactor = 500;
	private float timeFactor = 100;
	private float spectrumFactor = 0.5f;
	

	private long score;
	
	public Score(){
		this.score = 0;
	}
	
	public void calculateScore(float ammoRest, Difficulty difficulty){
		if(!difficulty.isTimed())
			this.timeFactor = 0; //If run was not using time. Timefactor is 0.
		
		this.score = (long) (this.ammoFactor*(1.0f/difficulty.getAmmoDiff())*ammoRest //Ammo-portion 
				+ this.ammoBonusFactor*(1.0f/difficulty.getAmmoBonus()) //AmmoBonus
				+ this.timeFactor*difficulty.getTime() //Time-used
				+ this.spectrumFactor*(1.0f/difficulty.getSpectrumFactor())); //Colorspectrum
	}

	public float getAmmoFactor() {
		return ammoFactor;
	}
	
	public void setAmmoFactor(float ammoFactor) {
		this.ammoFactor = ammoFactor;
	}
	
	public float getAmmoBonusFactor() {
		return ammoBonusFactor;
	}
	
	public void setAmmoBonusFactor(float ammoBonusFactor) {
		this.ammoBonusFactor = ammoBonusFactor;
	}
	
	public float getTimeFactor() {
		return timeFactor;
	}
	
	public void setTimeFactor(float timeFactor) {
		this.timeFactor = timeFactor;
	}
	
	public float getSpectrumFactor() {
		return spectrumFactor;
	}
	
	public void setSpectrumFactor(float spectrumFactor) {
		this.spectrumFactor = spectrumFactor;
	}
	
	public long getScore() {
		return score;
	}
}