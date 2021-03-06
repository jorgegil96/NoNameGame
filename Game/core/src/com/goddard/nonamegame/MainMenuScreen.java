package com.goddard.nonamegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by ortiz on 8/10/16.
 */
public class MainMenuScreen implements Screen {
    final MainGame game;
    OrthographicCamera camera;


    public MainMenuScreen(final MainGame gam) {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        game = gam;

        camera = new OrthographicCamera();

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

    }
}
