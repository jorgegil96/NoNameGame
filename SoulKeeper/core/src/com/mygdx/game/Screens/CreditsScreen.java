package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.MyGdxGame;

/**
 * Created by ortiz on 8/10/16.
 */
public class CreditsScreen implements Screen {
    final MyGdxGame game;
    OrthographicCamera camera;
    long waitTime;
    String s2 = "";
    int i=0;
    int j=0;
    String[] lines={"","","","",""};
    GlyphLayout layout2=new GlyphLayout();
    long fontWaitTime=0;
    public CreditsScreen(final MyGdxGame gam) {
        game=gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.width, game.height);
        waitTime=  TimeUtils.nanoTime();

    }

    public void dialog(String text){
        String s = text;

        String[] words = s.split(" ");

        GlyphLayout layout1 = new GlyphLayout();
        layout1.setText(game.font, String.valueOf(s));
        game.batch.begin();
        if(layout1.width<game.height-20){
            if (i<words.length && TimeUtils.nanoTime() - fontWaitTime > 100000000 && !(s.equals(s2))){
                    s2 += words[i] + " ";

                    fontWaitTime = TimeUtils.nanoTime();
                    i++;
            }
            game.font.draw(game.batch, s2, 10, game.height / 3);
        }
        else{
            if (i<words.length && TimeUtils.nanoTime() - fontWaitTime > 100000000 && layout2.width<game.width-20){
                lines[j]+=words[i] + " ";
                GlyphLayout layout2 = new GlyphLayout();
                layout2.setText(game.font, String.valueOf(lines[j]));
                fontWaitTime = TimeUtils.nanoTime();
                System.out.println(layout2.width<game.width-20);
                i++;
            }
            else if(i<words.length && TimeUtils.nanoTime() - fontWaitTime > 100000000){
                j++;
                layout2.setText(game.font, String.valueOf(lines[j]));

            }
            for(int k=0;k<5;k++){
                game.font.draw(game.batch, lines[k], 10, game.height / 3 - k*game.height/12);
            }
        }
        game.batch.end();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        /*game.textCenter(game.height/10,"Carlos Ortiz",game.font);
        game.textCenter(game.height/5,"Juan Carlos Garza",game.font);
        game.textCenter(game.height*3/10,"Jorge Gil",game.font);
        game.textCenter(game.height*4/10,"Programmers:",game.font);
        game.textCenter(game.height*3/5,"Ana Garza",game.font);
        game.textCenter(game.height*7/10,"Dolores Macías",game.font);
        game.textCenter(game.height*4/5,"Designers:",game.font);
        game.textCenter(game.height+5,"Credits",game.titleFont);*/
        dialog("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec fermentum.");
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
