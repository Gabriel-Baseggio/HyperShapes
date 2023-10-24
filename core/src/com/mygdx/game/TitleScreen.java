package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class TitleScreen extends ScreenAdapter {
	private OrthographicCamera camera;
	private SpriteBatch batch;

	private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont bitmap;
	
	public TitleScreen(OrthographicCamera camera) {
		this.camera = camera;
		this.batch = new SpriteBatch();

		this.generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        this.parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.parameter.size = 30;
        this.parameter.borderWidth = 1;
        this.parameter.borderColor = Color.BLACK;
        this.parameter.color = Color.WHITE;
        this.bitmap = generator.generateFont(parameter);
		
	}
	
	public void update() {
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
			HyperShapes.INSTANCE.setScreen(new GameScreen(this.camera));
			HyperShapes.INSTANCE.setDifficulty(1);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
			HyperShapes.INSTANCE.setScreen(new GameScreen(this.camera));
			HyperShapes.INSTANCE.setDifficulty(2);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
			HyperShapes.INSTANCE.setScreen(new GameScreen(this.camera));
			HyperShapes.INSTANCE.setDifficulty(4);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}

	@Override
	public void render(float delta) {
		update();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        
        batch.draw(new Texture("patinho.png"), HyperShapes.INSTANCE.getScreenWidth() / 2, HyperShapes.INSTANCE.getScreenHeight() / 2, 64 , 64);
        batch.draw(new Texture("patinho.png"), HyperShapes.INSTANCE.getScreenWidth() / 2 - 96, HyperShapes.INSTANCE.getScreenHeight() / 2, 64 , 64);
        batch.draw(new Texture("patinho.png"), HyperShapes.INSTANCE.getScreenWidth() / 2 - 80 , HyperShapes.INSTANCE.getScreenHeight() / 2 - 200, 128 , 128);
        
        bitmap.draw(batch, "Hyper Shapes", HyperShapes.INSTANCE.getScreenWidth() / 2 - ("Hyper Shapes").length() / 2 * 20, HyperShapes.INSTANCE.getScreenHeight() / 2);

		batch.end();
	}

	@Override
	public void dispose() {
		bitmap.dispose();
		generator.dispose();
		batch.dispose();
	}
}
