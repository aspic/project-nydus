package k56.nydus.screen;

import com.badlogic.gdx.ApplicationListener;

public class Application implements ApplicationListener {
	
	private Screen currentScreen;
	
	@Override
	public void create() {
		currentScreen = new GameScreen(this);
		currentScreen.create();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		currentScreen.render();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		
	}

	public void setScreen(Screen screen) {
		if(currentScreen != null) currentScreen.dispose();
		this.currentScreen = screen;
		this.currentScreen.create();
	}
	
}
