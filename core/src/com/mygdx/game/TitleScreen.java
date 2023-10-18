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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class TitleScreen extends ScreenAdapter {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	
	private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont bitmap;
	
	public TitleScreen(OrthographicCamera camera) {
		this.camera = camera;
		this.batch = new SpriteBatch();
		this.shapeRenderer = new ShapeRenderer();
		
		this.generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        this.parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.parameter.size = 30;
        this.parameter.borderWidth = 1;
        this.parameter.borderColor = Color.BLACK;
        this.parameter.color = Color.WHITE;
        this.bitmap = generator.generateFont(parameter);
		
	}
	
	public void update() {
		if (Gdx.input.isKeyPressed(Input.Keys.ENTER) || Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			HyperShapes.INSTANCE.setScreen(new GameScreen(this.camera));
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
	
}
