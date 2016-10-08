package com.goddard.nonamegame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.goddard.nonamegame.Characters.Player;

public class GameScreen implements Screen {
    OrthographicCamera camera;
    TiledMap map;
    TiledMapRenderer mapRenderer;
    SpriteBatch sb;
    Texture texture;
    Sprite sprite;
    Player player;

    public GameScreen() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        camera.update();

        map = new TmxMapLoader().load("shittymap.tmx");
        mapRenderer = new OrthoCachedTiledMapRenderer(map);

        sb = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("pik.png"));
        player = new Player(texture);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.moveX(-1 * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveX(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.moveY(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.moveY(-1 * delta);
        }

        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();

        //sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(player, player.getPosX(), player.getPosY());
        sb.end();
    }

    @Override
    public void resize(int width, int height) {

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
        sb.dispose();
    }
}
