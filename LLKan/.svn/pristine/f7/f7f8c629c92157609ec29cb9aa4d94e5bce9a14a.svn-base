/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.mrljdx.llk;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class HighscoresScreen implements Screen {
	Game game;

	OrthographicCamera guiCam;
	SpriteBatch batcher;
	Rectangle backBounds;
	Vector3 touchPoint;
	String[] highScores;
	Texture highscoreImage;
	TextureRegion highscoreRegion;
	float xOffset = 0;

	public HighscoresScreen (Game game) {
		this.game = game;

		guiCam = new OrthographicCamera(480, 800);
		guiCam.position.set(480 / 2, 800 / 2, 0);
		backBounds = new Rectangle(50,800-750,85,30);
		touchPoint = new Vector3();
		batcher = new SpriteBatch();
		highScores = new String[5];
		highscoreImage = new Texture(Gdx.files.internal("highscore.png"));
		highscoreRegion = new TextureRegion(highscoreImage,0,0,480,800);
		for (int i = 0; i < 5; i++) {
			highScores[i] = i + 1 + ". " + Settings.highscores[i];
			xOffset = Math.max(Assets.font.getBounds(highScores[i]).width, xOffset);
		}
		xOffset = 200 - xOffset / 2 + Assets.font.getSpaceWidth() / 2;
	}

	public void update (float deltaTime) {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (OverlapTester.pointInRectangle(backBounds, touchPoint.x, touchPoint.y)) {
				Assets.playtouchsound(Assets.button);
				game.setScreen(new MainMenuScreen(game));
				return;
			}
		}
	}

	public void draw (float deltaTime) {
		GLCommon gl = Gdx.gl;
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.update();
		batcher.setProjectionMatrix(guiCam.combined);
		batcher.disableBlending();
		batcher.begin();
			batcher.draw(highscoreRegion, 0, 0, 480, 800);
		batcher.end();

		batcher.enableBlending();
		batcher.begin();
//		batcher.draw(highscoreRegion, 10, 360 - 16, 300, 33);

		float y =400;
		for (int i = 4; i >= 0; i--) {
			Assets.font.draw(batcher, highScores[i], xOffset, y);
			Assets.font.setColor(0, 1, 0, 1);
			y += Assets.font.getLineHeight();
		}

//		batcher.draw(Assets.arrow, 0, 0, 64, 64);
		batcher.end();
	}

	public void render (float delta) {
		update(delta);
		draw(delta);
	}

	public void resize (int width, int height) {
	}

	public void show () {
	}

	public void hide () {
	}

	public void pause () {
	}

	public void resume () {
	}

	public void dispose () {
	}
}
