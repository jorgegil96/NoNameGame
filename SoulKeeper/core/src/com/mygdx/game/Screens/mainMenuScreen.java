package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;


/**
 * Created by ortiz on 8/10/16.
 */
public class mainMenuScreen implements Screen {
    final MyGdxGame game;
    OrthographicCamera camera;

    public mainMenuScreen(final MyGdxGame gam) {
        game=gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.width, game.height);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        int button1Y = game.height*7/12;
        int button2Y = game.height*5/12;
        int button3Y = game.height/4;
        int button4Y = game.height/12;
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.button(button1Y,"Continue", Color.NAVY);
        game.button(button2Y,"New Game", Color.NAVY);
        game.button(button3Y,"Credits", Color.NAVY);
        game.button(button4Y,"Quit", Color.NAVY);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        GlyphLayout layout1 = new GlyphLayout();
        layout1.setText(game.titleFont, String.valueOf("SOUL KEEPER"));
        game.titleFont.draw(game.batch, "SOUL KEEPER", game.width / 2 - layout1.width / 2, game.height * 9 / 10);
        game.batch.end();


        if(Gdx.input.isTouched()){
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if(touchPos.x > game.width/5 && touchPos.x < game.width*4/5 &&
                    touchPos.y > button4Y && touchPos.y < button4Y+game.height/8) {
                Gdx.app.exit();
                dispose();
            }
            if(touchPos.x > game.width/5 && touchPos.x < game.width*4/5 &&
                    touchPos.y > button3Y && touchPos.y < button3Y+game.height/8) {
                game.setScreen(new CreditsScreen(game));
                dispose();
            }
            if(touchPos.x > game.width/5 && touchPos.x < game.width*4/5 &&
                    touchPos.y > button2Y && touchPos.y < button2Y+game.height/8) {
                game.setScreen(new PlayScreen(game));
                dispose();
            }
            if(touchPos.x > game.width/5 && touchPos.x < game.width*4/5 &&
                    touchPos.y > button1Y && touchPos.y < button1Y+game.height/8) {
                game.setScreen(new PlayScreen(game));
                dispose();
            }
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
