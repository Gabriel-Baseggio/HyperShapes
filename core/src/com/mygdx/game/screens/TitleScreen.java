package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.HyperShapes;
import com.mygdx.game.helper.FontHelper;

public class TitleScreen extends ScreenAdapter {
	private OrthographicCamera camera;
	private SpriteBatch batch;

	public TitleScreen(OrthographicCamera camera) {
		this.camera = camera;
		this.batch = new SpriteBatch();
	}
	
	public void update() {
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
			startGame(1);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
			startGame(2);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
			startGame(4);
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

		FontHelper.write(batch, "Pressione ESC para sair", new Vector2(10, HyperShapes.INSTANCE.getScreenHeight() - 30), 30);

		FontHelper.write(batch, "Hyper Shapes", new Vector2(HyperShapes.INSTANCE.getScreenWidth() / 2 - 120, HyperShapes.INSTANCE.getScreenHeight() / 2), 50);

		FontHelper.write(batch, "Pressione 1 para jogar na dificuldade muito fácil", new Vector2(10, 150), 30);
		FontHelper.write(batch, "Pressione 2 para jogar na dificuldade levemente fácil", new Vector2(10, 90), 30);
		FontHelper.write(batch, "Pressione 3 para jogar na dificuldade fácil", new Vector2(10, 30), 30);

		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	public void startGame(int difficulty){
		HyperShapes.INSTANCE.setDifficulty(difficulty);
		HyperShapes.INSTANCE.setScreen(new GameScreen(this.camera));
	}

}
