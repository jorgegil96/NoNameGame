/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import static com.mygdx.game.MyGdxGame.PPM;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Sprites.Enemy;
import com.mygdx.game.Sprites.SoulKeeper;
import com.mygdx.game.Tools.B2WorldCreator;
import com.mygdx.game.Tools.WorldContactListener;
import com.mygdx.game.Utilities.MyInputProcessor;

public class PlayScreen implements Screen{
    private MyGdxGame game;
    private OrthographicCamera camera;
    private Viewport view;
    private Hud hud;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private World world;
    private Box2DDebugRenderer b2dr;
    private SoulKeeper soulKeeper;
    private B2WorldCreator creator;
    private TextureAtlas atlas;
    public boolean up;
    public boolean down;
    public boolean right;
    public boolean left;
    public PlayScreen(MyGdxGame game){
        atlas = new TextureAtlas("Mario_and_Enemies.pack");
        this.game = game;
        camera = new OrthographicCamera();
        view = new FillViewport(400/ PPM, 208 / PPM,camera);
        hud = new Hud(game);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("SoulKeeper_Try4.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/ PPM);
        camera.position.set((view.getWorldWidth() / 2), (view.getWorldHeight() / 2),0);

        world = new World(new Vector2(0,-10), false);
        b2dr = new Box2DDebugRenderer();
        soulKeeper = new SoulKeeper(this);
        creator = new B2WorldCreator(this, soulKeeper);

        world.setContactListener(new WorldContactListener());
        world.setGravity(new Vector2(0,0));

        right = true;
        left = false;
        up = false;
        down = false;

        MyInputProcessor inputProcessor = new MyInputProcessor(this,soulKeeper);
        Gdx.input.setInputProcessor(inputProcessor);

    }
    
    public void setUp()
    {
        up = true;
            right = false;
            left = false;       
            down = false;   
    }
    
    public void setDown()
    {
        down = true;
            up = false;
            right = false;
            left = false;
    }
    
    public void setLeft()
    {
        up = false;
            right = false;
            left = true;      
           down = false;
    }
    
    public void setRight()
    {
        up = false;
            right = true;
            left = false;      
            down = false;
    }
    
    public void handleInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
        {
            up = true;
            right = false;
            left = false;       
            down = false;   
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
        {
            down = true;
            up = false;
            right = false;
            left = false;   
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            up = false;
            right = true;
            left = false;      
            down = false;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            up = false;
            right = false;
            left = true;      
           down = false;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.X))
        {
            soulKeeper.hit();
        }
    }
    
    public void update(float dt) {
        world.step(1/60f, 6, 2);
        soulKeeper.update(dt);
        for(Enemy enemy: creator.getDemons())
        {
            enemy.update(dt);
            if(enemy.getX() < soulKeeper.getX() + 224 / PPM)
            {
                enemy.b2body.setActive(true);
            }
        }
        hud.update(dt);
        camera.position.x = soulKeeper.b2body.getPosition().x;
        camera.update();
        renderer.setView(camera);
    }
    
    public TextureAtlas getAtlas()
    {
        return atlas;
    }
    
    @Override
    public void show() {
       
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        b2dr.render(world, camera.combined);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        soulKeeper.draw(game.batch);
        for(Enemy enemy: creator.getDemons()) {
            enemy.draw(game.batch);
        }
        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        view.update(width, height);
    }
    
    public TiledMap getMap(){
        return map;
    }
    
    public World getWorld(){
        return world;
    }
    
    @Override
    public void pause() {
        
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void hide() {
        
    }

    @Override
    public void dispose() {
       map.dispose();
       renderer.dispose();
       world.dispose();
       b2dr.dispose();
       hud.dispose();
    }
    
}
