package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class TitleScreen extends ScreenAdapter {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	
	public TitleScreen(OrthographicCamera camera) {
		this.camera = camera;
		this.batch = new SpriteBatch();
		this.shapeRenderer = new ShapeRenderer();
		
	}
	
	public void update() {
		if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
			HyperShapes.INSTANCE.setScreen(new GameScreen(this.camera));
		}
	}
	
	public void render(float delta) {
		update();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        
        batch.draw(new Texture("patinho.png"), 50, 50, 64, 64);
        batch.end();
	}
	
}
