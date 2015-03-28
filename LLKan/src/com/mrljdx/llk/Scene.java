package com.mrljdx.llk;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Scene implements Screen {
	Game game;
	private BitmapFont font;
	private SpriteBatch batch=new SpriteBatch();
	private int loaded=0;
	public Scene(Game game) {
		this.game=game;
		font = new BitmapFont(Gdx.files.internal("myfont.fnt"), false);
		font.setScale(0.6f);
	}

	public void draw(float t) {
		batch.begin();
		if (t < 1.0f) {
			font.draw(batch, "Loading " + (int) (t * 100) + "%", 20f,
					320 / 2f);
		} else {
			Gdx.app.log("LLK", "Loaded");
			font.draw(batch, "Loading " + (int) (t * 100) + "%", 20f, 320 / 2f);
			loaded=1;
		}
		batch.end();
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	public int isloaded() {
		// TODO Auto-generated method stub
		return loaded;
	}
}
