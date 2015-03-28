package com.mrljdx.llk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {	
//	public static float ScreenWidth=Gdx.graphics.getWidth();
//	public static float ScreenHeight=Gdx.graphics.getHeight();
	//定义图片资源
	public static float picwidth=45; //定义图片的长宽
	public static float picheight=45;
	public static TextureRegion splashbg;
	public static TextureRegion menubg;
	public static TextureRegion gamebg;
	public static Texture bgTexture;
	public static TextureRegion bgblack;
	public static Texture selectorTur;
	public static TextureRegion selector;
	public static TextureRegion[] pt;
	public static TextureRegion[] audiobt;
	public static TextureRegion[] helpscreen;
	public static TextureRegion pausebg;
	public static TextureRegion nextlevelbg;
	public static TextureRegion continuebt;
	public static TextureRegion returnbt;
	public static TextureRegion toolbt1;
	public static TextureRegion toolbt2;
	public static TextureRegion toolbt3;
	public static TextureRegion toolbt4;
	//Animation
	public static Animation ptAnim;
	//定义字体
	public static BitmapFont font;
	public static BitmapFont scorefont;
	public static BitmapFont gamefont;
	//定义音频文件
	public static Music bgmusic;
	public static Sound bomb;
	public static Sound button;
	public static Sound click;
	public static Sound failed;
	public static Sound tools1;
	public static Sound tools2;
	public static Sound tools3;
	public static Sound tools4;
	public static Sound success;
	public static Sound upgrade;
	public static Sound dominating;
	public static Sound doublekill;
	public static Sound firstblood;
	public static Sound godlike;
	public static Sound killingspree;
	public static Sound holyshit;
	public static Sound megakill;
	public static Sound monsterkill;
	public static Sound unstoppable;
	public static Sound flashsound;
	public static void load(){
		//捕捉返回键
		Gdx.input.setCatchBackKey(true);
		//图片资源加载
		TextureAtlas bg=new TextureAtlas(Gdx.files.internal("bg.pack"));
		menubg=bg.findRegion("menubg");		
		gamebg=bg.findRegion("gamebg");
		bgTexture=new Texture(Gdx.files.internal("bg_black.png"));
		bgblack=new TextureRegion(bgTexture, 0, 0, 480, 480);
		selectorTur=new Texture(Gdx.files.internal("selector.png"));
		selector=new TextureRegion(selectorTur);
		TextureAtlas pic=new TextureAtlas(Gdx.files.internal("pic.pack"));
		pt=new TextureRegion[14];
		pt[0]=pic.findRegion("p0");
		pt[1]=pic.findRegion("p1");
		pt[2]=pic.findRegion("p2");
		pt[3]=pic.findRegion("p3");
		pt[4]=pic.findRegion("p4");
		pt[5]=pic.findRegion("p5");
		pt[6]=pic.findRegion("p6");
		pt[7]=pic.findRegion("p7");
		pt[8]=pic.findRegion("p8");
		pt[9]=pic.findRegion("p9");
		pt[10]=pic.findRegion("p10");
		pt[11]=pic.findRegion("p11");
		pt[12]=pic.findRegion("p12");
		pt[13]=pic.findRegion("p13");
		
		TextureAtlas audioalt=new TextureAtlas(Gdx.files.internal("audio.pack"));
		audiobt=new TextureRegion[4];
		audiobt[0]=audioalt.findRegion("1");
		audiobt[1]=audioalt.findRegion("2");
		audiobt[2]=audioalt.findRegion("3");
		audiobt[3]=audioalt.findRegion("4");
		
		TextureAtlas helpalt=new TextureAtlas(Gdx.files.internal("help.pack"));
		helpscreen=new TextureRegion[3];
		helpscreen[0]=helpalt.findRegion("tips1");
		helpscreen[1]=helpalt.findRegion("tips2");
		helpscreen[2]=helpalt.findRegion("tips3");
		
		TextureAtlas pausealt=new TextureAtlas(Gdx.files.internal("pas.pack"));
		nextlevelbg=pausealt.findRegion("complete");
		pausebg=pausealt.findRegion("pause");
		continuebt=pausealt.findRegion("continue");
		returnbt=pausealt.findRegion("return");
		toolbt1=pausealt.findRegion("tool1");
		toolbt2=pausealt.findRegion("tool2");
		toolbt3=pausealt.findRegion("tool3");
		toolbt4=pausealt.findRegion("tool4");
		
		//定义游戏中的字体
		font=new BitmapFont(Gdx.files.internal("myfont.fnt"),Gdx.files.internal("myfont.png"),false);
		scorefont=new BitmapFont(Gdx.files.internal("timefont.fnt"),Gdx.files.internal("timefont.png"),false);
		gamefont=new BitmapFont(Gdx.files.internal("gamefont.fnt"),Gdx.files.internal("gamefont.png"),false);
		//加载游戏音频
		bgmusic=Gdx.audio.newMusic(Gdx.files.internal("audio/bgmusic.mp3"));
		bgmusic.setLooping(true);
		bgmusic.setVolume(0.5f);
		if(Settings.soundEnable) 
			bgmusic.play();
		click=Gdx.audio.newSound(Gdx.files.internal("audio/selected.wav"));
		button=Gdx.audio.newSound(Gdx.files.internal("audio/playbutton.mp3"));
		dominating=Gdx.audio.newSound(Gdx.files.internal("audio/dominating.wav"));
		doublekill=Gdx.audio.newSound(Gdx.files.internal("audio/doublekill.wav"));
		firstblood=Gdx.audio.newSound(Gdx.files.internal("audio/firstblood.mp3"));
		godlike=Gdx.audio.newSound(Gdx.files.internal("audio/godlike.wav"));
		holyshit=Gdx.audio.newSound(Gdx.files.internal("audio/holyshit.wav"));
		killingspree=Gdx.audio.newSound(Gdx.files.internal("audio/killingspree.wav"));
		megakill=Gdx.audio.newSound(Gdx.files.internal("audio/megakill.wav"));
		monsterkill=Gdx.audio.newSound(Gdx.files.internal("audio/monsterkill.wav"));
		unstoppable=Gdx.audio.newSound(Gdx.files.internal("audio/unstoppable.wav"));
		
		tools1=Gdx.audio.newSound(Gdx.files.internal("audio/tools1.ogg"));
		tools2=Gdx.audio.newSound(Gdx.files.internal("audio/tools2.ogg"));
		tools3=Gdx.audio.newSound(Gdx.files.internal("audio/tools3.ogg"));
		tools4=Gdx.audio.newSound(Gdx.files.internal("audio/tools4.ogg"));	
		
	}
	public static void playtouchsound(Sound sound){
		if(Settings.touchEnable)
			sound.play(1);			
	}	
	public static void playsound(Sound sound){
		if(Settings.soundEnable)
			sound.play(1);
		
	}

}
