package com.mrljdx.llk;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen implements Screen ,InputProcessor{
	
	Game game;
//	private Mesh lineMesh;
	OrthographicCamera guiCam;
	SpriteBatch batcher;
	Vector3 touchPoint;
	//定义按键有效范围
	Rectangle startgame;
	Rectangle help;
	Rectangle highscore;
	Rectangle finishgame;
	Rectangle bt_audio1;
	Rectangle bt_audio2;
	Graphics graphics;
	public MainMenuScreen(Game game){
		this.game=game;
		//捕捉所有触摸事件
		Gdx.input.setInputProcessor(this);
		//定义相机
		guiCam=new OrthographicCamera(480,800);
		guiCam.position.set(480/2,800/2, 0);
		batcher=new SpriteBatch();
		touchPoint=new Vector3();
		//规定有效范围
		startgame=new Rectangle(130,800-450,200,50);
		help=new Rectangle(130,800-515,200,50);
		highscore=new Rectangle(130,800-585,200,50);
		finishgame=new Rectangle(130,800-655,200,50);
		bt_audio1=new Rectangle(330,700,64,64);
		bt_audio2=new Rectangle(390,700,64,64);
	}
	public void draw(float deltaTime){
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(1, 0, 0,1);
		guiCam.update();
		batcher.setProjectionMatrix(guiCam.combined);
		batcher.disableBlending();
		batcher.begin();
			batcher.draw(Assets.menubg, 0,0,480,800);
		batcher.end();
		batcher.enableBlending();
		batcher.begin();
			batcher.draw(Settings.soundEnable?Assets.audiobt[0]:Assets.audiobt[1],330,700,64,64);
			batcher.draw(Settings.touchEnable?Assets.audiobt[2]:Assets.audiobt[3],390,700,64,64);
		batcher.end();
	}
	
	public void update(float deltaTime){
		if(Gdx.input.justTouched()){
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),0));
			if(OverlapTester.pointInRectangle(startgame, touchPoint.x,touchPoint.y)){
				Assets.playtouchsound( Assets.button);
				Gdx.app.log("LLK", "Enter Game Screen!");
				game.setScreen(new GameScreen(game));
			}
			if(OverlapTester.pointInRectangle(help, touchPoint.x,touchPoint.y)){
				Assets.playtouchsound(Assets.button);
				Gdx.app.log("LLK", "Enter help Screen!");
				game.setScreen(new HelpScreen(game));
			}
			if(OverlapTester.pointInRectangle(highscore, touchPoint.x,touchPoint.y)){
				Assets.playtouchsound(Assets.button);
				Gdx.app.log("LLK", "Enter highScore Screen!");
				game.setScreen(new HighscoresScreen(game));
			}
			if(OverlapTester.pointInRectangle(finishgame, touchPoint.x,touchPoint.y)){
				Assets.playtouchsound( Assets.button);
				Gdx.app.log("LLK", "Finish Game Screen!");
				Gdx.app.exit();
			}
			if(OverlapTester.pointInRectangle(bt_audio1, touchPoint.x,touchPoint.y)){
				Assets.playtouchsound(Assets.button);
				Settings.soundEnable=!Settings.soundEnable;
				if(Settings.soundEnable){
					Assets.bgmusic.play();
				}else{
					Assets.bgmusic.pause();
				}
			}
			if(OverlapTester.pointInRectangle(bt_audio2, touchPoint.x,touchPoint.y)){
				Assets.playtouchsound(Assets.button);
				Settings.touchEnable=!Settings.touchEnable;
			}
		}
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		Settings.save();
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		draw(delta);
		update(delta);
//		lineMesh.render(GL11.GL_LINES,0,2);
		batcher.begin();
			Assets.font.draw(batcher, "fps: " + Gdx.graphics.getFramesPerSecond(), 10, 30);
		batcher.end();
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean keyDown(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

}
