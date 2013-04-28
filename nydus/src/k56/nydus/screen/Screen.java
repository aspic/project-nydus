package k56.nydus.screen;

public abstract class Screen {
	
	protected Application application;
	
	public Screen(Application application) {
		this.application = application;
	}
	
	public abstract void create();
	public abstract void render();
	public abstract void dispose();
}
