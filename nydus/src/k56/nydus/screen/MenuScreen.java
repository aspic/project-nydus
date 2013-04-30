package k56.nydus.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MenuScreen extends Screen {
	
	private Stage stage;
	
	public MenuScreen(Application application) {
		super(application);
		
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void create() {
		// Create components
		
		TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("assets/test.png"))));
		TextButtonStyle style = new TextButtonStyle(drawable, drawable, drawable, new BitmapFont());
		final TextButton playButton = new TextButton("Play", style);
		
		ClickListener listener = new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(event.getListenerActor().equals(playButton)) {
					application.setScreen(new GameScreen(application));
				}
				
			}
		};
		playButton.addListener(listener);
		
		LabelStyle labelStyle = new LabelStyle(new BitmapFont(), Color.BLUE);
		
		
		Table table = new Table();
		table.setSize(stage.getWidth(), stage.getHeight());
		table.defaults().expandX().center().pad(5);
		
		// Do layout
		table.add(new Label("Unpixelation", labelStyle)).row();
		table.add(playButton).fill();

		stage.addActor(table);
	}

	@Override
	public void render() {
		
		stage.draw();
	}

	@Override
	public void dispose() {
		
	}

}
