/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Gameplay.History;
import com.mygdx.game.MyGdxGame;
import static com.mygdx.game.MyGdxGame.PPM;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Scenes.Dialog;
import com.mygdx.game.Sprites.Enemy;
import com.mygdx.game.Sprites.SoulKeeper;
import com.mygdx.game.Tools.B2WorldCreator;
import com.mygdx.game.Tools.WorldContactListener;
import com.mygdx.game.Utilities.MyInputProcessor;

public class PlayScreen implements Screen{
    private History history;
    public MyGdxGame game;
    private OrthographicCamera camera;
    private Viewport view;
    private Hud hud;
    private Dialog Dialog;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private World world;
    private Box2DDebugRenderer b2dr;
    public SoulKeeper soulKeeper;
    private B2WorldCreator creator;
    private TextureAtlas atlas;
    private State state = State.RUN;
    public boolean up;
    public boolean down;
    public boolean right;
    public boolean left;
    public boolean isOutside;
    private long waitTime;
    private int dia=0;
    private long waitTime2;

    public enum State {
        PAUSE,
        RUN,
        RESUME,
    }

    public PlayScreen(MyGdxGame game){
        atlas = new TextureAtlas("Mario_and_Enemies.pack");
        this.game = game;
        camera = new OrthographicCamera();
        view = new FitViewport(game.width/ PPM, game.height / PPM,camera);
        isOutside = true;
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("mapa.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/ PPM);
        camera.position.set((view.getWorldWidth() / 2), (view.getWorldHeight() / 2),0);
        
        world = new World(new Vector2(0,-10), false);
        b2dr = new Box2DDebugRenderer();
        soulKeeper = new SoulKeeper(this);
        creator = new B2WorldCreator(this, soulKeeper);
        hud = new Hud(game, this);
        Dialog = new Dialog(game, "", TimeUtils.nanoTime());
        world.setContactListener(new WorldContactListener());
        world.setGravity(new Vector2(0,0));

        right = true;
        left = false;
        up = false;
        down = false;

        MyInputProcessor inputProcessor = new MyInputProcessor(this,soulKeeper);
        Gdx.input.setInputProcessor(inputProcessor);

    }
    
    public void chageMap()
    {
        
        
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
    
    public void update(float dt) {
        world.step(1/60f, 6, 2);
        soulKeeper.update(dt);
        game.setLife(soulKeeper.getLife());
        for(Enemy enemy: creator.getDemons())
        {
            enemy.update(dt);
            if(enemy.getX() < soulKeeper.getX() + 224 / PPM)
            {
                enemy.b2body.setActive(true);
            }
        }
        hud.update(dt);
        Dialog.update(dt);
        camera.position.x = soulKeeper.b2body.getPosition().x;
        camera.position.y = soulKeeper.b2body.getPosition().y;
        camera.update();
        renderer.setView(camera);
    }
    
    public TextureAtlas getAtlas()
    {
        return atlas;
    }

    public void setGameState(State s){
        this.state = s;
    }

    @Override
    public void show() {
       
    }

    @Override
    public void render(float delta) {
        switch (state) {
            case RUN:
                update(delta);
                Gdx.gl.glClearColor(0,0,0,1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                renderer.render();
                //b2dr.render(world, camera.combined);
                game.batch.setProjectionMatrix(camera.combined);
                game.batch.begin();
                soulKeeper.draw(game.batch);
                for(Enemy enemy: creator.getDemons()) {
                    enemy.draw(game.batch);
                }
                game.batch.end();
                game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
                hud.stage.draw();
                /*if(Gdx.input.isTouched()) {
                    Dialog = new Dialog(game, "The poor children had now nowhere to play. They tried to play on the road, but the" +
                            " road was very dusty and full of hard stones, and they did not like it.", TimeUtils.nanoTime());
                }*/
                if(WorldContactListener.almaCollision){
                    if(game.almas==0 && dia==0&& TimeUtils.nanoTime()-waitTime2>2000000000) {
                        Dialog = new Dialog(game, "Pickup the soul of the dead.", TimeUtils.nanoTime());
                        dia++;
                        waitTime2=TimeUtils.nanoTime();
                    }
                    if(game.almas==0 && dia==1&& TimeUtils.nanoTime()-waitTime2>2000000000) {
                        Dialog = new Dialog(game, "This is your mission: from now on, protect and collect the 7 souls of the people here.", TimeUtils.nanoTime());
                        game.almas++;
                        dia++;
                        waitTime2=TimeUtils.nanoTime();
                    }
                    if(game.almas==1 && dia==2&& TimeUtils.nanoTime()-waitTime2>2000000000) {
                        Dialog = new Dialog(game, "", TimeUtils.nanoTime());
                        dia=0;
                    }
                }
                game.batch.setProjectionMatrix(Dialog.stage.getCamera().combined);
                Dialog.stage.draw();
                if(Gdx.input.isTouched()){
                    Vector3 touchPos = new Vector3();
                    touchPos.set(Gdx.input.getX()*100, Gdx.input.getY(), 0);
                    camera.unproject(touchPos);
                    if(touchPos.x > game.width*2/5 && touchPos.x < game.width*3/5 &&
                            touchPos.y*100 > game.height-100 && touchPos.y*100 < game.height)
                    setGameState(State.PAUSE);
                    waitTime=TimeUtils.nanoTime();
                }
                break;
            case PAUSE:
                game.textCenter(game.height*4/5,"The game is on pause",game.font);
                int button2Y = game.height*2/5;
                int button1Y = game.height/5;
                game.button(button2Y,"Resume", Color.FIREBRICK);
                game.button(button1Y,"Main Menu", Color.FIREBRICK);
                if(Gdx.input.isTouched()&&TimeUtils.nanoTime()-waitTime>500000000){
                    Vector3 touchPos = new Vector3();
                    touchPos.set(Gdx.input.getX()*100, Gdx.input.getY(), 0);
                    camera.unproject(touchPos);
                    System.out.println(touchPos.x + " " + touchPos.y);
                    if(touchPos.x > game.width/5 && touchPos.x < game.width*4/5 &&
                            touchPos.y*100 > button2Y && touchPos.y*100 < button2Y+game.height/8) {
                        setGameState(State.RESUME);
                        waitTime=TimeUtils.nanoTime();
                    }
                    if(touchPos.x > game.width/5 && touchPos.x < game.width*4/5 &&
                            touchPos.y*100 > button1Y && touchPos.y*100 < button1Y+game.height/8) {
                        game.setScreen(new mainMenuScreen(game));
                        dispose();
                    }
                }
                break;
            case RESUME:
                update(delta);
                Gdx.gl.glClearColor(0,0,0,1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                renderer.render();
                //b2dr.render(world, camera.combined);
                game.batch.setProjectionMatrix(camera.combined);
                game.batch.begin();
                soulKeeper.draw(game.batch);
                for(Enemy enemy: creator.getDemons()) {
                    enemy.draw(game.batch);
                }
                game.batch.end();
                game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
                hud.stage.draw();
                game.batch.setProjectionMatrix(Dialog.stage.getCamera().combined);
                Dialog.stage.draw();
                if(Gdx.input.isTouched()){
                    Vector3 touchPos3 = new Vector3();
                    touchPos3.set(Gdx.input.getX()*100, Gdx.input.getY(), 0);
                    camera.unproject(touchPos3);
                    if(touchPos3.x > game.width*2/5 && touchPos3.x < game.width*3/5 &&
                            touchPos3.y*100 > game.height-100 && touchPos3.y*100 < game.height)
                        setGameState(State.PAUSE);
                    waitTime=TimeUtils.nanoTime();
                }
                break;
        }
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
        this.state = State.PAUSE;
    }

    @Override
    public void resume() {
        this.state = State.RESUME;
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
