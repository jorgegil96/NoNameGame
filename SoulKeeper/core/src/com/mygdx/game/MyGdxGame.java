package com.mygdx.game;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.game.Screens.mainMenuScreen;


public class MyGdxGame extends Game {
    
	public SpriteBatch batch;
	public BitmapFont font;
	public BitmapFont titleFont;
	public ShapeRenderer shapeRenderer;
	OrthographicCamera camera;
	public static final float PPM = 100;
	public static final short DEFAULT_BIT = 1;
	public static final short SOULKEEPER_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short ITEM_BIT = 128;
	public static final short NPC_BIT = 256;
	public static final short SWORD_BIT = 512;
	public int width;
	public int height;
	public int almas=3;
	public float vida=1f;
	private Preferences prefs;
	public Music acustica;


	public void button(int y, String text, Color fillColor){
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(1,1,1,1);
		shapeRenderer.rect(width/5,y,width*3/5,height/8);
		shapeRenderer.setColor(fillColor);
		shapeRenderer.rect(width/5+10,y+10,width*3/5-20,height/8-20);
		shapeRenderer.end();
		textCenter(y+height/10, text,font);
	}

	public void textCenter(int y, String text, BitmapFont font){
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		GlyphLayout layout1 = new GlyphLayout();
		layout1.setText(font, String.valueOf(text));
		font.draw(batch, text, width / 2 - layout1.width / 2, y);
		batch.end();
	}
        
	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("Colonial.TTF"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = height/5;
		titleFont = gen.generateFont(parameter);
		gen.dispose();
		font = new BitmapFont();
		FreeTypeFontGenerator gen2 = new FreeTypeFontGenerator(Gdx.files.internal("Colonial.TTF"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter2.size = height/12;
		font = gen2.generateFont(parameter2);
		gen2.dispose();
		prefs = Gdx.app.getPreferences("Saved game");
		almas = prefs.getInteger("Almas capturadas", 0);
		vida = prefs.getFloat("Vida", 1.0f);
		acustica = Gdx.audio.newMusic(Gdx.files.internal("audio/music/RomanceDeAmorAcustica.wav"));
		acustica.setLooping(true);
		acustica.play();
		setScreen(new mainMenuScreen(this));

	}
        
        public void setLife(float life)
        {
            vida = life;
        }

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		acustica.dispose();
	}
}
