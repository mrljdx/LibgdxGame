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

public class HelpScreen implements Screen {
	Game game;

	OrthographicCamera guiCam;
	SpriteBatch batcher;
	Rectangle backBounds;
	Rectangle nextBounds;
	Vector3 touchPoint;
	Texture helpImage;
	TextureRegion helpRegion;
	private int helppage=1;

	public HelpScreen (Game game) {
		this.game = game;

		guiCam = new OrthographicCamera(480, 800);
		guiCam.position.set(480 / 2, 800 / 2, 0);
		backBounds = new Rectangle(50,800-750,85,30);
		nextBounds = new Rectangle(340, 800-750, 85, 30);
		touchPoint = new Vector3();
		batcher = new SpriteBatch();
		helpImage = new Texture(Gdx.files.internal("helpbg.png"));
		helpRegion = new TextureRegion(helpImage,0,0,480,800);
	}

	public void update (float deltaTime) {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			if (OverlapTester.pointInRectangle(nextBounds, touchPoint.x, touchPoint.y)) {
				Assets.playtouchsound(Assets.button);
//				game.setScreen(new HelpScreen2(game));
				Gdx.app.log("LLK", "the helppage is :"+helppage);
				helppage++;
				if(helppage>3)
					helppage=1;
				return;
			}
			if(OverlapTester.pointInRectangle(backBounds,touchPoint.x, touchPoint.y)){
				Assets.playtouchsound(Assets.button);
				game.setScreen(new MainMenuScreen(game));
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
		batcher.draw(helpRegion,0, 0, 480, 800);
		batcher.end();

		batcher.enableBlending();
		batcher.begin();
			switch(helppage){
				case 1:batcher.draw(Assets.helpscreen[0], 40, 800-680, 400, 600);break;
				case 2:batcher.draw(Assets.helpscreen[1], 40, 800-680, 400, 600);break;
				case 3:batcher.draw(Assets.helpscreen[2], 40, 800-680, 400, 600);break;
			}
		batcher.end();
		gl.glDisable(GL10.GL_BLEND);
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
