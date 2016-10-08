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
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import static com.mygdx.game.MyGdxGame.PPM;
import static com.mygdx.game.MyGdxGame.V_HEIGHT;
import static com.mygdx.game.MyGdxGame.V_WIDTH;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Sprites.Enemy;
import com.mygdx.game.Sprites.Demons;
import com.mygdx.game.Sprites.SoulKeeper;
import com.mygdx.game.Tools.B2WorldCreator;
import com.mygdx.game.Tools.WorldContactListener;

/**
 *
 * @author Juan
 */
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
    private SoulKeeper player;
    private Demons goomba;
    private B2WorldCreator creator;
    private TextureAtlas atlas;
    
    public PlayScreen(MyGdxGame game){
        atlas = new TextureAtlas("Mario_and_Enemies.pack");
        this.game = game;
        camera = new OrthographicCamera();
        view = new FitViewport(V_WIDTH/ PPM, V_HEIGHT / PPM,camera);
        hud = new Hud(game.batch);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("SoulKeeper_Try.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/ PPM);
        camera.position.set((view.getWorldWidth() / 2), (view.getWorldHeight() / 2),0);
        world = new World(new Vector2(0,-10), false);
        b2dr = new Box2DDebugRenderer(); 
        creator = new B2WorldCreator(this);
        player = new SoulKeeper(this);
        world.setContactListener(new WorldContactListener());
        world.setGravity(new Vector2(0,0));
    }
    
    public void handleInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && player.b2body.getLinearVelocity().y <= 2)
        {
            player.b2body.applyLinearImpulse(new Vector2(0,1f), player.b2body.getWorldCenter(), false);   
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && player.b2body.getLinearVelocity().y >= -2)
        {
            player.b2body.applyLinearImpulse(new Vector2(0,-1f), player.b2body.getWorldCenter(), false);   
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
        {
           player.b2body.applyLinearImpulse(new Vector2(0.1f,0), player.b2body.getWorldCenter(), false);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
        {
           player.b2body.applyLinearImpulse(new Vector2(-0.1f,0), player.b2body.getWorldCenter(), false);
        }
    }
    
    public void update(float dt)
    {
        handleInput(dt);
        world.step(1/60f, 6, 2);
        player.update(dt);
        for(Enemy enemy: creator.getDemons())
        {
            enemy.update(dt);
            if(enemy.getX() < player.getX() + 224 / PPM)
            {
                enemy.b2body.setActive(true);
            }
        }
        hud.update(dt);
        camera.position.x = player.b2body.getPosition().x;
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
        player.draw(game.batch);
         for(Enemy enemy: creator.getDemons())
        {
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
