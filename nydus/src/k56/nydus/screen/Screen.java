package k56.nydus.screen;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class Screen implements ApplicationListener, InputProcessor {
	private int viewWidth = 10;
	private int viewHeight = 10;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private TextureRegion region;
	
	private Vector3 mouse = new Vector3();
	private float panY, panX = 0;
	private Direction direction;

	@Override
	public void create() {
		float aspect = (float)Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
		camera = new OrthographicCamera(viewWidth*aspect, viewHeight);
		batch = new SpriteBatch();
		
		region = new TextureRegion(new Texture(Gdx.files.internal("assets/test.png")));
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void render() {
		float delta = Gdx.graphics.getDeltaTime();
		
		// Clear screen
		Gdx.graphics.getGL20().glClearColor(0, 1, 1, 1);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(region, 0, 0);
		// engine.render()
		batch.end();
		
		
		if(direction != null) panCamera();
		
		camera.position.add(panX*delta, panY*delta, 0);
		camera.update();
		
		panY *= 0.9f;
		panX *= 0.9f;
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.Q:
			// engine() switch color?
			break;
		case Keys.E:
			// engine() switch color?
			break;
		case Keys.W:
			direction = Direction.NORTH;
			break;
		case Keys.A:
			direction = Direction.EAST;
			break;
		case Keys.D:
			direction = Direction.WEST;
			break;
		case Keys.S:
			direction = Direction.SOUTH;
			break;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		direction = null;
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		mouse.set(screenX, screenY, 0);
		camera.unproject(mouse);
		// engine.clicked();
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		mouse.set(screenX, screenY, 0);
		camera.unproject(mouse);

		System.out.println("Pointer at: " + mouse);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void panCamera() {
		panX += direction.x;
		panY += direction.y;
	}
	
	private enum Direction {
		NORTH(0, -1), EAST(1, 0), WEST(-1, 0), SOUTH(0, 1);
		int x,y;
		Direction(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

}
