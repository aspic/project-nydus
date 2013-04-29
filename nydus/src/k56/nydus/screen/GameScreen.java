package k56.nydus.screen;

import k56.nydus.core.Action;
import k56.nydus.core.Engine;
import k56.nydus.core.ShootingValue;
import k56.nydus.ui.ColorTable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class GameScreen extends Screen implements InputProcessor {
	
	private int viewWidth = 10;
	private int viewHeight = 10;
	
	private int startWidth = 10;
	private int startHeight = 10;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private TextureRegion region;
	
	private Vector3 mouse = new Vector3();
	private float panY, panX = 0;
	private Engine engine;
	
	private boolean touching;
	private Action action = Action.ADD;
	private float lastZoom;
	private float zoom = 1;
	private float duration = 1f;
	private float runtime;
	
	private ParticleEffect effect;
	
	// User interface
	private Stage stage;
	private ColorTable[] pallette;
	private int selectedColor;
	private Label actionLabel;
	
	public GameScreen(Application application) {
		super(application);
	}
	
	@Override
	public void create() {
		float aspect = (float)Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
		camera = new OrthographicCamera(viewWidth*aspect, viewHeight);
		batch = new SpriteBatch();
		
		region = new TextureRegion(new Texture(Gdx.files.internal("assets/test.png")));

		engine = new Engine(startWidth, startHeight);

		stage = new Stage(200, 200, true);
		loadUI();
		selectColor(0);
		Gdx.input.setInputProcessor(new InputMultiplexer(this));
		
		camera.position.x = engine.getWidth()*0.5f;
		camera.position.y = engine.getHeight()*0.5f;
		
		effect = new ParticleEffect();
		effect.load(Gdx.files.internal("assets/particle/filler"), Gdx.files.internal("assets/"));
		effect.setDuration(0);
		effect.getEmitters().get(0).getTint().getColors()[0] = 0f;
	}
	
	/** Load some minimalistic user interface */
	private void loadUI() {
		
		pallette = new ColorTable[3];
		float dimension = 15f;
		
		// Load UI
		pallette[0] = new ColorTable(dimension, dimension, region, ShootingValue.RED);
		pallette[1] = new ColorTable(dimension, dimension, region, ShootingValue.GREEN);
		pallette[2] = new ColorTable(dimension, dimension, region, ShootingValue.BLUE);
		
		Table table = new Table();
		table.defaults().pad(5).width(dimension).height(dimension).expandX();
		table.setSize(stage.getWidth()*0.2f, stage.getHeight());
		table.setPosition(stage.getWidth() - table.getWidth(), 0);
		
		LabelStyle style = new LabelStyle(new BitmapFont(), Color.BLACK);
		Label label = new Label("Color", style);
		actionLabel = new Label("Action", style);
		
		// Do layout
		table.add(label).row();
		table.add(pallette[0]).row();
		table.add(pallette[1]).row();
		table.add(pallette[2]).row();
		table.add(actionLabel).row();
		
		// Add to stage
		stage.addActor(table);
	}

	@Override
	public void render() {
		float delta = Gdx.graphics.getDeltaTime();
		
		// Clear screen
		Gdx.graphics.getGL20().glClearColor(0, 1, 1, 1);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		engine.render(batch);
		batch.setColor(Color.BLUE);
		effect.draw(batch, delta);
		batch.setColor(Color.WHITE);

		batch.end();
		
		
		stage.draw();
		
		panCamera();
		zoomCamera(delta);
		
		camera.position.add(panX*delta, panY*delta, 0);
		camera.update();
		
		panY *= 0.9f;
		panX *= 0.9f;
		
		if(touching) {
			engine.clicked(mouse.x, mouse.y, action);
		}
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.NUM_1:
			selectColor(0);
			break;
		case Keys.NUM_2:
			selectColor(1);
			break;
		case Keys.NUM_3:
			selectColor(2);
			break;
		case Keys.SPACE:
			lastZoom = zoom;
			zoom *= 10f;
			runtime = 0;
			engine.levelTransition(zoom);
			effect.getEmitters().get(0).getScale().getScaling()[0] = zoom;
			break;
		}
		return false;
	}
	
	private void selectColor(int i) {
		if(i >= 0 && i <= pallette.length) {
			selectedColor = i;
			
			for (int j = 0; j < pallette.length; j++) {
				if(j == selectedColor) pallette[j].setSelected();
				else pallette[j].deselect();
			}
			engine.setColor(pallette[selectedColor].getShootingValue());
			setEffectColor(pallette[selectedColor].getShootingValue().getColor());
		}
	}

	@Override
	public boolean keyUp(int keycode) {
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
		effect.start();
		touching = true;
		
		if(button == 0) {
			action = Action.ADD;
			actionLabel.setText("Add");
		} else if(button == 1) {
			action = Action.SUB;
			actionLabel.setText("Subtract");
		}
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		touching = false;
		effect.setDuration(0);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		mouse.set(screenX, screenY, 0);
		camera.unproject(mouse);
		effect.setPosition(mouse.x, mouse.y);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		mouse.set(screenX, screenY, 0);
		camera.unproject(mouse);
		effect.setPosition(mouse.x, mouse.y);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void zoomCamera(float delta) {
		
		if(camera.zoom < zoom) {
			runtime += delta;
			float deltaZoom = Interpolation.bounceOut.apply(lastZoom, zoom, runtime/duration);
			camera.zoom = deltaZoom;
			
		} else {
			camera.zoom = zoom;
		}
		
	}
	
	private void panCamera() {
		
		float dirX = 0, dirY = 0;
		if(Gdx.input.isKeyPressed(Keys.W)) dirY = 1;
		if(Gdx.input.isKeyPressed(Keys.S)) dirY = -1;
		if(Gdx.input.isKeyPressed(Keys.A)) dirX = -1;
		if(Gdx.input.isKeyPressed(Keys.D)) dirX = 1;
		
		float diffX = camera.position.x + dirX*zoom;
		float diffY = camera.position.y + dirY*zoom;
		
		if(dirX < 0 && diffX > (viewWidth*zoom - engine.getWidth())*0.5f) panX += dirX*zoom;
		else if(dirX > 0 && diffX < (viewWidth*zoom + engine.getWidth())*0.5f) panX += dirX*zoom;
		
		if((dirY < 0 && diffY > (viewHeight*zoom - engine.getHeight())*0.5f)) panY += dirY*zoom;
		else if(dirY > 0 && diffY < (viewHeight*zoom + engine.getHeight())*0.5f) panY += dirY*zoom;
	}
	
	/** Le hack */
	private void setEffectColor(Color color) {
		if(effect == null) return;
		effect.getEmitters().get(0).getTint().getColors()[0] = color.r;
		effect.getEmitters().get(0).getTint().getColors()[1] = color.g;
		effect.getEmitters().get(0).getTint().getColors()[2] = color.b;
	}
}
