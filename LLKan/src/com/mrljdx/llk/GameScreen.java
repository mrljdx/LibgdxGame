package com.mrljdx.llk;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class GameScreen implements Screen, InputProcessor {
	// ������Ϸ�еĸ���״̬��׼��0����Ϸ����1����Ϸ��ͣ2��ͨ��3����Ϸover 4
	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;
	Game game;
	int state;
	int startflag=0;
	boolean win=false;
	int score;
	int lastscore; //��¼���÷�
	String scorestr;
	String timestr;
	String usedtimestr;
	String levelstr;
	int picflag = 0;
	int posx,posy;
	int killcount=0;
	int[] pos1 = new int[2];
	int[] pos2 =new int[2];
	private List<int[]> selected = new ArrayList<int[]>();
	int[][] pointpath =null; 
	//����һ��9��8�еľ������ڴ��ֵ
	OrthographicCamera guiCam;
	Vector3 touchPoint;
	SpriteBatch batcher;
	BitmapFont bf;
	protected  static final int xCount =10;
	protected static final int  yCount =11;
	TextureRegion[][] map = new TextureRegion[xCount][yCount];
	// Bounds
	Rectangle pauseBounds;
	Rectangle resumeBounds;
	Rectangle quitBounds;
	Rectangle bt_audio1;
	Rectangle bt_audio2;
	Rectangle tool1Bounds;
	Rectangle tool2Bounds;
	Rectangle tool3Bounds;
	Rectangle tool4Bounds;
	private int tool1;
	private int tool2;
	private int tool3;
	private int tool4;
	
	private double totalTime = 120;
	private double leftTime;
	private int level=1;
	
	public GameScreen(Game game) {
		this.game = game;
		Gdx.input.setInputProcessor(this);
		Assets.font.setColor(0,1,0,1);
		guiCam = new OrthographicCamera(480, 800);
		guiCam.position.set(480 / 2, 800 / 2, 0);
		touchPoint = new Vector3();
		batcher = new SpriteBatch();
		pauseBounds = new Rectangle(400, 720, 50, 50);
		resumeBounds = new Rectangle(160, 460, 170, 50);
		quitBounds = new Rectangle(160, 400, 170, 50);
		tool1Bounds = new Rectangle(250,800-660,90,90);
		tool2Bounds = new Rectangle(350,800-660,90,90);
		tool3Bounds = new Rectangle(250,800-760,90,90);
		tool4Bounds = new Rectangle(350,800-760,90,90);
		bt_audio1=new Rectangle(60,350,64,64);
		bt_audio2=new Rectangle(390,350,64,64);
		leftTime=totalTime;
		initMap();
		score=0;
		lastscore=0;
		tool1=3;
		tool2=3;
		tool3=3;
		tool4=3;
		levelstr=new String("Level:");
		scorestr=new String("");
		timestr=new String("");
	}
	public void startPlay(){
		state=GAME_RUNNING;
		tool1=3;
		tool2=3;
		tool3=3;
		tool4=3;
		leftTime = totalTime;
		initMap();
	}
	
	public void initMap(){  //��չ˼·����X��ֵ��Ϊһ����ά���飬��¼1-13��ֵ�������Լ����ĵ�ͼ
		int x=1; //��¼ͼƬID
		int y=0; //���
		for(int i=0;i<xCount;i++){
			for(int j=0;j<yCount;j++){
				if(i==0||j==0||i==xCount-1||j==yCount-1){
					map[i][j] = Assets.pt[0];
				}else{
					map[i][j] = Assets.pt[x];
					if(y==1){
						x++;
						y=0;
					if(x==13){ //13��ͼƬһ��ѭ��
						x=1;
					}
					}else{
						y=1;
					}
				}
			}
		}
		ChangeMap();	
	}
	private void ChangeMap(){
		//��������ͼƬ
		Random random = new Random();
		int tmpX,tmpY;
		TextureRegion tg;
		for (int x = 1; x <xCount-1; x++) {
			for (int y = 1; y <yCount-1; y++) {
				tmpX = 1 + random.nextInt(xCount - 2);
				tmpY = 1 + random.nextInt(yCount - 2);
				tg = map[x][y];
				map[x][y] = map[tmpX][tmpY];
				map[tmpX][tmpY] = tg;
				}
		}
		if(die()){ //����������ӣ���ô�Զ�ˢ��Map
			ChangeMap();
		}
	}
	//�ж��Ƿ��޿���ͼƬ
	private boolean die(){ 
		for (int y = 1; y < yCount - 1; y++) {
			for (int x = 1; x < xCount - 1; x++) {
				if (map[x][y] != Assets.pt[0]) {
					for (int j = y; j < yCount - 1; j++) {
						if (j == y) {
							for (int i = x + 1; i < xCount - 1; i++) {
								int[] m = new int[2];
								int[] n = new int[2];
								m[0]=x;
								m[1]=y;
								n[0]=i;
								n[1]=j;
								if ((map[i][j] == map[x][y])&& link(m,n)) {
									return false;
								}
							}
						} else {
							for (int i = 1; i < xCount - 1; i++) {
								int[] m = new int[2];
								int[] n = new int[2];
								m[0]=x;
								m[1]=y;
								n[0]=i;
								n[1]=j;
								if ((map[i][j] == map[x][y])&& link(m,n)) {
									return false;
								}
							}
						}
					}
				}
			}
		}
		return true;
	}
	private void drawmap(TextureRegion[][] map){//��ͼ
		for(int i=1;i<xCount-1;i++){
			for(int j=1;j<yCount-1;j++){
				batcher.draw(map[i][j], 56 + (i-1) * 45, 295 + (j-1) * 45, Assets.picwidth, Assets.picheight);
			}
		}		
	}
	//Mrljdx
	private void drawline(int[][] path){
		if (path != null &&path.length >= 2) {
			for (int i = 0; i < path.length - 1; i++) {
				Gdx.app.log("LLk", "����һ������");	
//				Pixmap pixmap = new Pixmap(64,64,Format.RGBA8888);
//				pixmap.setColor(1, 0, 0, 1);
//				Texture tex=new Texture(pixmap);
//				Bitmap bitmap = Bitmap.createBitmap(49,49,Bitmap.Config.ARGB_8888);
//				Canvas canvas = new Canvas(bitmap);
//				Paint paint = new Paint();
//				paint.setColor(Color.CYAN);
//				paint.setStyle(Paint.Style.STROKE);
//				paint.setStrokeWidth(3);
//				canvas.drawLine(pos1[0] + 49 / 2, pos1[1] + 49 / 2,
//						pos2[0] + 49 / 2, pos2[1] + 49 / 2, paint);
			}
			int[] p=path[0];
			map[p[0]][p[1]]=Assets.pt[0];
			p=path[path.length-1];
			map[p[0]][p[1]]=Assets.pt[0];
			selected.clear();
			path=null;
			score+=10;
		}
	}
	
	List<int[]> p1E = new ArrayList<int[]>();
	List<int[]> p2E = new ArrayList<int[]>();	
	private boolean link(int[] p1, int[] p2) {
		if (p1[0]==p2[0]&&p1[1]==p2[1]) {
			Gdx.app.log("LLK", "�����ͬһ��ͼƬ");
			return false;
		}
		selected.clear();
		if (map[p1[0]][p1[1]] == map[p2[0]][p2[1]]) {
			if (linkD(p1, p2)) {//���ڵ�һ����ͼƬ��ȥ��
//				Gdx.app.log("LLK", "������ͼƬ���ڣ���һ����������ȥ��");
				selected.add(p1);
				selected.add(p2);
				return true;
			}
			int[] p = new int[2];
			p[0] = p1[0]; // p1�ĺ�����
			p[1] = p2[1]; // p2��������
			if (map[p[0]][p[1]] == Assets.pt[0]) {  //��һ��ת�۵�
				if (linkD(p1, p) && linkD(p, p2)) {
					selected.add(p1);
					selected.add(p);
					selected.add(p2);
					Gdx.app.log("LLK","===ͼƬ��ˮƽ����֮����һ��ת�۵㣬������ȥ===");
					return true;
				}
			}
			p[0]=p2[0];  // p2�ĺ�����
			p[1]=p1[1];  // p1��������
			if (map[p[0]][p[1]] == Assets.pt[0]) {  //��һ��ת�۵� ͬ�� ���ڶ��������
				if (linkD(p1, p) && linkD(p, p2)) {
					selected.add(p1);
					selected.add(p);
					selected.add(p2);
					Gdx.app.log("LLK","===ͼƬ����ֱ����֮����һ��ת�۵㣬������ȥ===");
					return true;
				}
			}
			
			//��� List�е�ֵ
			p1E.clear();
			p2E.clear();
			Gdx.app.log("LLK", "---->p1-X<-----");
			expandX(p1,p1E);
			Gdx.app.log("LLK", "---->p2-X<-----");
			expandX(p2,p2E);
			/*-------------------�ж���2��ת�۵�����----------------------*/
			for (int[] pt1 : p1E) {
				for (int[] pt2 : p2E) {
					if (pt1[0] == pt2[0]) {
						Gdx.app.log("LLK","===����ת�۵�����1===");
						if (linkD(pt1, pt2)) {							
							selected.add(p1);
							selected.add(pt1);
							selected.add(pt2);
							selected.add(p2);
							return true;
						}
					}
					Gdx.app.log("LLK","===����ת�۵�����1 �ж�ʧ��===");
				}
			}
			Gdx.app.log("LLK", "---->p1-Y<-----");
			expandY(p1,p1E);
			Gdx.app.log("LLK", "---->p2-Y<-----");
			expandY(p2,p2E);
			//�ж�3��ת�۵�����2
			for (int[] pt1 : p1E) {
				for (int[] pt2 : p2E) {	
//					Gdx.app.log("LLK", "����p1E�е�ֵ��"+pt1);
					if (pt1[1] == pt2[1]) {
						Gdx.app.log("LLK","===����ת�۵�����2===");
						if (linkD(pt1, pt2)) {							
							selected.add(p1);
							selected.add(pt1);
							selected.add(pt2);
							selected.add(p2);
							return true;
						}
					}
					Gdx.app.log("LLK","===����ת�۵�����2 �ж�ʧ��===");
				}
			}
			return false;
		}
		
		return false;
	}
	private boolean linkD(int[] p1, int[] p2) {
		/*--------------------�ж�һ��ת�۵�����------------------------*/
		//�������ͼƬ��ͬһ����
		if (p1[0] == p2[0]) {  
			Gdx.app.log("LLK", "���뵽LinkD����");			
			int y1 = Math.min(p1[1], p2[1]);//y1Ϊp1[1]��p2[1]�Ƚϵ���Сֵ
			int y2 = Math.max(p1[1], p2[1]);//y2Ϊp1[1]��p2[1]�Ƚϵ����ֵ
			boolean flag = true;
			for (int y = y1 + 1; y < y2; y++) { //�ж���һ���ϣ�����ͼƬ֮����û�谭
				if (map[p1[0]][y] != Assets.pt[0]) {
					Gdx.app.log("LLK", "����������ͬһ�е�ͼƬ֮��������ͼƬ���Բ���ֱ�����ӣ�");
					flag = false;
					break;
				}
			}
			if (flag) {
				return true; 
			}
		}
		//��� ����ͼƬ��ͬһ����
		if (p1[1] == p2[1]) { 
			int x1 = Math.min(p1[0], p2[0]);
			int x2 = Math.max(p1[0], p2[0]);
			boolean flag = true;
			for (int x = x1 + 1; x < x2; x++) { //�ж���һ���ϣ�����ͼƬ֮����û�谭
				if (map[x][p1[1]] != Assets.pt[0]) {
					Gdx.app.log("LLK", "������վ��ͬһ�е�ͼƬ֮��������ͼƬ���Բ���ֱ�����ӣ�");
					flag = false;
					break;
				}
			}
			if (flag) {
				return true;
			}
		}
		return false;
	}
	/*ͬ���ж�ͼƬ���ܵĿհ׵�*/
	private void expandX(int[] p,List<int[]> l) {
		for (int x = p[0] + 1; x < xCount; x++) {
			if (map[x][p[1]] != Assets.pt[0]) {
				Gdx.app.log("LLK", "��ͼƬͬ�У����ұ߱���������ͼƬ--->"+p[1]);
				break;
			}
			int[] m=new int[2];
			m[0]=x;
			m[1]=p[1];
			Gdx.app.log("LLK", "m��ֵ��"+m[0]+" "+m[1]);
			l.add(m);			
			Gdx.app.log("LLK", "expandX������ֵX+"+x);
		}
		for (int x = p[0] - 1; x >=0; x--) {
			if (map[x][p[1]] != Assets.pt[0]) {
				Gdx.app.log("LLK", "��ͼƬͬ�У�����߱���������ͼƬ--->"+p[1]);
				break;
			}
			int[] m=new int[2];
			m[0]=x;
			m[1]=p[1];
			l.add(m);
		}
	}
	/*ͬ���ж�ͼƬ���ܵĿհ׵�*/
	private void expandY(int[] p,List<int[]> l) {
		for (int y = p[1] + 1; y <yCount; y++) {
			if (map[p[0]][y] != Assets.pt[0]) {
				Gdx.app.log("LLK", "��ͼƬͬ�У����������������ͼƬ--->"+p[0]);
				break;
			}
			int[] m=new int[2];
			m[0]=p[0];
			m[1]=y;
			l.add(m);
		}
		for (int y = p[1] - 1; y >=0; y--) {
			if (map[p[0]][y] != Assets.pt[0]) {
				Gdx.app.log("LLK", "��ͼƬͬ�У����±���������ͼƬ--->"+p[0]);
				break;
			}
			int[] m=new int[2];
			m[0]=p[0];
			m[1]=y;
			l.add(m);
		}
	}

	private boolean win() {
		for (int x = 0; x <xCount; x++) {
			for (int y = 0; y <yCount; y++) {
				if (map[x][y] != Assets.pt[0]) {
					return false;
				}
			}
		}
		return true;
	}

	private void draw(float deltaTime) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
		guiCam.update();
		batcher.setProjectionMatrix(guiCam.combined);
		batcher.disableBlending();
		batcher.begin();
			batcher.draw(Assets.gamebg, 0, 0, 480, 800);
		batcher.end();
		batcher.enableBlending();
		batcher.begin(); //���߼���
			Assets.gamefont.draw(batcher, ""+tool1,315 ,800-640 );
			Assets.gamefont.draw(batcher, ""+tool2,420 ,800-640 );
			Assets.gamefont.draw(batcher, ""+tool3,315 ,800-740 );
			Assets.gamefont.draw(batcher, ""+tool4,420 ,800-740 );
		
		switch (state) {
		case GAME_READY:
			presentReady();
			break;
		case GAME_RUNNING:
			presentRunning(deltaTime);
			break;
		case GAME_PAUSED:
			presentPaused();
			break;
		case GAME_LEVEL_END:
			presentLevelEnd();
			break;
		case GAME_OVER:
			presentGameOver();
			break;
		}
		batcher.end();
	}

	private void presentReady() {
		// TODO Auto-generated method stub
		drawmap(map);
		int minutes = (int)(leftTime/60.0);
		int seconds = (int)(leftTime-minutes*60);
		timestr=" "+minutes;
		if (seconds < 10) {
			timestr += ":0" + seconds;
		}
		else {
			timestr += ":" + seconds;
		}
		Assets.gamefont.draw(batcher, "level:", 50, 752);
		Assets.gamefont.draw(batcher,""+Settings.level,150,752);
		Assets.gamefont.draw(batcher, "time:", 220, 752);
		Assets.scorefont.draw(batcher, timestr, 280, 752);
		batcher.draw(Assets.bgblack, 0, 0, 480, 800);
		Assets.font.draw(batcher, "READY??", 200, 450);
	}
		
	private void presentRunning(float deltaTime) {
		// TODO Auto-generated method stub
		drawmap(map);
		Assets.gamefont.draw(batcher, "level:", 50, 752);
		Assets.gamefont.draw(batcher,""+Settings.level,150,752);
		Assets.gamefont.draw(batcher, "time:", 220, 752);
		Assets.scorefont.draw(batcher, timestr, 280, 752);
	}

	private void presentPaused() {
		// TODO Auto-generated method stub
		drawmap(map);
		Assets.gamefont.draw(batcher, "level:", 50, 752);
		Assets.gamefont.draw(batcher,""+Settings.level,150,752);
		Assets.gamefont.draw(batcher, "time:", 220, 752);
		Assets.scorefont.draw(batcher, timestr, 280, 752);
		batcher.draw(Assets.bgblack, 0, 0, 480, 800);
		batcher.draw(Assets.pausebg, 40, 350);
		batcher.draw(Assets.continuebt, 160, 460);
		batcher.draw(Assets.returnbt, 160, 400);
		batcher.draw(Settings.soundEnable?Assets.audiobt[0]:Assets.audiobt[1],60,350,64,64);
		batcher.draw(Settings.touchEnable?Assets.audiobt[2]:Assets.audiobt[3],370,350,64,64);
	}

	private void presentLevelEnd() {
		// TODO Auto-generated method stub
		Assets.gamefont.draw(batcher, "level:", 50, 752);
		Assets.gamefont.draw(batcher,""+Settings.level,150,752);
		Assets.gamefont.draw(batcher, "time:", 220, 752);
		Assets.scorefont.draw(batcher, timestr, 300, 752);
		batcher.draw(Assets.bgblack, 0, 0, 480, 800);
		Assets.font.draw(batcher, "Used time:"+usedtimestr, 120, 540);
		Assets.font.draw(batcher, "Level is :"+level, 150, 510);
		Assets.font.draw(batcher,scorestr+lastscore, 80, 480);
		Assets.font.draw(batcher, "Touch to Next Level!!", 80, 450);
	}

	private void presentGameOver() {
		// TODO Auto-generated method stub
		drawmap(map);
		Assets.gamefont.draw(batcher, "level:", 50, 752);
		Assets.gamefont.draw(batcher,""+Settings.level,150,752);
		Assets.gamefont.draw(batcher, "time:", 220, 752);
		Assets.scorefont.draw(batcher, timestr, 300, 752);
		batcher.draw(Assets.bgblack, 0, 0, 480, 800);
		
		Assets.font.draw(batcher, "Level is :"+level, 150, 510);
		Assets.font.draw(batcher,scorestr+lastscore, 80, 480);
		Assets.font.draw(batcher, "GAME OVER!!", 150, 450);
	}

	public void update(float deltaTime) {
		if (deltaTime > 0.1f)
			deltaTime = 0.1f;
		switch (state) {
		case GAME_READY:
			updateReady();
			break;
		case GAME_RUNNING:
			updateRunning(deltaTime);
			break;
		case GAME_PAUSED:
			updatePaused();
			break;
		case GAME_LEVEL_END:
			updateLevelEnd();
			break;
		case GAME_OVER:
			updateGameOver();
			break;
		}
	}

	private void updateReady() {
		// TODO Auto-generated method stub
		if (Gdx.input.justTouched()) {
			state = GAME_RUNNING;
		}
	}
	/*=====================�ĸ���Ϸ���ߵ�ʹ��==========================*/
	private void crakepic(){
		drawline(selected.toArray(new int[][]{}));
	}
	private void autoLink(){//�Զ���
		drawline(selected.toArray(new int[][]{}));
	}
	private void refreshMap(){
		ChangeMap();
	}
	
	
	private void updateRunning(float deltaTime) {
		// TODO Auto-generated method stub	
		if (Gdx.input.justTouched()) {
			startflag=1;
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),0));
			if (OverlapTester.pointInRectangle(pauseBounds, touchPoint.x,touchPoint.y)) {
				Assets.playtouchsound(Assets.button);
				state = GAME_PAUSED;
				return;
			}
			if(OverlapTester.pointInRectangle(tool1Bounds, touchPoint.x,touchPoint.y)&&tool1>0){
				Assets.playtouchsound(Assets.tools3);
				Gdx.app.log("LLK", "ִ�е�һ�����ߣ�");
				tool1--;
				crakepic();
				return;
			}
			if(OverlapTester.pointInRectangle(tool2Bounds, touchPoint.x,touchPoint.y)&&tool2>0){
				Assets.playtouchsound(Assets.tools2);
				Gdx.app.log("LLK", "ִ�еڶ������ߣ�");
				tool2--;
				autoLink();
				return;
			}
			if(OverlapTester.pointInRectangle(tool3Bounds, touchPoint.x,touchPoint.y)&&tool3>0){
				Assets.playtouchsound(Assets.tools1);
				Gdx.app.log("LLK", "ִ�е��������ߣ�");
				tool3--;
				refreshMap();
				return;
			}
			if(OverlapTester.pointInRectangle(tool4Bounds, touchPoint.x,touchPoint.y)&&tool4>0){
				Assets.playtouchsound(Assets.tools4);
				tool4--;
				Gdx.app.log("LLK", "ִ�е��ĸ����ߣ�");
				return;
			}
			if((touchPoint.x>=48&&touchPoint.x<=420)&&(touchPoint.y>=(800-510)&&touchPoint.y<=(800-90))){		
				posx=(int) ((touchPoint.x-56)/45)+1;
				posy=(int) ((touchPoint.y-295)/45)+1;
				if(map[posx][posy]!=Assets.pt[0]){ //ѡ����Ч
					Assets.playtouchsound(Assets.click);
				}
//				Gdx.app.log("LLk", "��ǰͼƬ�������ǣ�  "+"x="+posx+"   y="+posy+"...map[0][0]"+map[0][0]+"...map[9][0]"+map[9][5]);
				if(picflag==1){
					if(posx>=0&&posx<xCount)
						pos2[0]=posx;
					if(posy>=0&&posy<yCount)
						pos2[1]=posy;
					picflag=0;
					if(link(pos1,pos2)){
						drawline(selected.toArray(new int[][]{}));
						killcount++;
						scorestr="score:"+score;
						if(killcount==1)
							Assets.playtouchsound(Assets.firstblood);
						if(killcount>1){
							switch(killcount){
								case 2:Assets.playtouchsound(Assets.doublekill);break;
								case 3:Assets.playtouchsound(Assets.dominating);break;
								case 4:Assets.playtouchsound(Assets.unstoppable);break;
								case 5:Assets.playtouchsound(Assets.megakill);break;
								case 6:Assets.playtouchsound(Assets.killingspree);break;
								case 7:Assets.playtouchsound(Assets.godlike);break;
								default:Assets.playtouchsound(Assets.holyshit);
								
							}
								
						}						
					};
				}
				
			}		
			
		}
		int tminutes = (int)(totalTime/60.0);
		int tseconds = (int)(totalTime-tminutes*60);
		leftTime-=deltaTime;
		if(leftTime>0){
			int minutes = (int)(leftTime/60.0);
			int seconds = (int)(leftTime-minutes*60);
			timestr=" "+minutes;
			usedtimestr=" "+(tminutes-minutes);
			if (seconds < 10) {
				timestr += ":0" + seconds;
				usedtimestr+=":0"+(tseconds-seconds);
			}
			else {
				timestr += ":" + seconds;
				usedtimestr+=":"+seconds;
			}
		}
		if(startflag==1&&(posx>=0&&posx<xCount&&posy>=0&&posy<yCount)&&map[posx][posy]!=Assets.pt[0]){	
			if(picflag==0){
				if(posx>=0&&posx<xCount)
					pos1[0]=posx;
				if(posy>=0&&posy<yCount)
					pos1[1]=posy;
			}
			picflag=1;
			batcher.begin();
				batcher.draw(Assets.selector,54 +(pos1[0]-1) * Assets.picwidth, 293 + (pos1[1]-1) * Assets.picheight, 50,50);
			batcher.end();
		}	
		if(leftTime<=0){
			state=GAME_OVER;
			Settings.level=level;
			lastscore=score;
			if (lastscore >= Settings.highscores[1])
				scorestr="New High Score :";
			else
				scorestr="Your Score is :";
			Settings.addScore(lastscore);
			Settings.save();
		}else{
			if(win()){
				level++;
				state=GAME_LEVEL_END;
				Settings.level=level;
				lastscore=score;
				if (lastscore >= Settings.highscores[1])
					scorestr="New High Score :";
				else
					scorestr="Your Score is :";
				Settings.addScore(lastscore);
				Settings.save();
			} 
			else if(die()){ //������ֲ������ӵ�ͼƬ�����Զ�ˢ�µ�ͼ
				ChangeMap();
			}
		}

	}
	
	private void updatePaused() {
		// TODO Auto-generated method stub
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),
					0));
			if (OverlapTester.pointInRectangle(resumeBounds, touchPoint.x,
					touchPoint.y)) {
				Assets.playtouchsound(Assets.button);
				state = GAME_RUNNING;
				return;
			}
			if (OverlapTester.pointInRectangle(quitBounds, touchPoint.x,
					touchPoint.y)) {
				Assets.playtouchsound(Assets.button);
				game.setScreen(new MainMenuScreen(game));
				return;
			}
			if(OverlapTester.pointInRectangle(bt_audio1, touchPoint.x,touchPoint.y)){
				Assets.playtouchsound(Assets.button);
				Settings.soundEnable=!Settings.soundEnable;
				if(Settings.soundEnable)
					Assets.bgmusic.play();
				else{
					Assets.bgmusic.pause();
				}
			}
			if(OverlapTester.pointInRectangle(bt_audio2, touchPoint.x,touchPoint.y)){
				Assets.playtouchsound(Assets.button);
				Settings.touchEnable=!Settings.touchEnable;
			}
			
		}

	}

	private void updateLevelEnd() {
		// TODO Auto-generated method stub
		if (Gdx.input.justTouched()) {
			Gdx.app.log("LLK", "Enter next level!");
			totalTime-=10;
			startPlay();
		}
	}

	private void updateGameOver() {
		// TODO Auto-generated method stub
		if (Gdx.input.justTouched()) {
			game.setScreen(new MainMenuScreen(game));
		}
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
//		Assets.playsound(Assets.click);
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
		if (state == GAME_RUNNING)
			state = GAME_PAUSED;
	}

	@Override
	public void render(float deltaTime) {
		// TODO Auto-generated method stub
		draw(deltaTime);
		update(deltaTime);
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

}
