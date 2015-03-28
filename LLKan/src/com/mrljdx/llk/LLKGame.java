package com.mrljdx.llk;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;

public class LLKGame extends Game {
	boolean firstTimeCreate = true; //判断是不是第一次进入游戏
//	private AssetManager assetManager = null;
//	private Scene scene = null;
//	private MainMenuScreen menuscree=null;
	@Override
	public void create() {
		// TODO Auto-generated method stub
//		assetManager = new AssetManager();
//		assetManager.load("audio.png", Texture.class);
//		assetManager.load("bg_black.png", Texture.class);
//		assetManager.load("bg.png", Texture.class);
//		assetManager.load("bglogin.png", Texture.class);
//		assetManager.load("help.png", Texture.class);
//		assetManager.load("help2.png", Texture.class);
//		assetManager.load("helpbg.png", Texture.class);
//		assetManager.load("highscore.png", Texture.class);
//		assetManager.load("myfont.png", Texture.class);
		long starttime=TimeUtils.nanoTime();
		Settings.load();
		Assets.load();
//		scene = new Scene(this);
//		menuscree=new MainMenuScreen(this);
		long loadedtime=TimeUtils.nanoTime()-starttime;
		Gdx.app.log("LLK", "Loading time :"+loadedtime);
		setScreen(new MainMenuScreen(this));
	}
//	@Override
//	public void render() {
//		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
//		assetManager.update();
//		float progress = assetManager.getProgress();
////		if(scene.isloaded()==0)
//			scene.draw(progress);
////		else{
////			Gdx.app.log("LLK", "Enter MainMenuScreen");
////			setScreen(new MainMenuScreen(this));
////		}
//	}
	@Override
	public void dispose(){
		super.dispose();
		getScreen().dispose();
	}
}
