package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.MyGdxGame;

/**
 * Created by ortiz on 8/10/16.
 */
public class CreditsScreen implements Screen {
    final MyGdxGame game;
    long waitTime;
    public CreditsScreen(final MyGdxGame gam) {
        game=gam;
        waitTime=  TimeUtils.nanoTime();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.textCenter(game.height/10,"Carlos Ortiz",game.font);
        game.textCenter(game.height/5,"Juan Carlos Garza",game.font);
        game.textCenter(game.height*3/10,"Jorge Gil",game.font);
        game.textCenter(game.height*4/10,"Programmers:",game.font);
        game.textCenter(game.height*3/5,"Ana Garza",game.font);
        game.textCenter(game.height*7/10,"Dolores Macías",game.font);
        game.textCenter(game.height*4/5,"Designers:",game.font);
        game.textCenter(game.height+5,"Credits",game.titleFont);

        if(Gdx.input.isTouched() && TimeUtils.nanoTime()-waitTime>1000000000){
            game.setScreen(new mainMenuScreen(game));
            dispose();
        }
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
