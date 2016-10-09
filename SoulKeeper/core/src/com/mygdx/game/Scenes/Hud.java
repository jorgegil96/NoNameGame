/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.SoulKeeper;



/**
 *
 * @author Juan
 */
public class Hud implements Disposable{



    private MyGdxGame game;
    public Stage stage;
    private Viewport view;
    private Texture alma;
    private Texture notAlma;
    private Texture lifeBg;
    private Texture life;
    private Texture pausa;
    private Image  almaImg;
    private Image notAlmaImg;
    private Image lifeBgImg;
    private Image lifeImg;
    private Image pausaImg;
    private PlayScreen screen1;
    public Hud(final MyGdxGame game, PlayScreen screen){
        int i=0;
                screen1 = screen;
        view = new FitViewport(game.width, game.height, new OrthographicCamera());
        stage = new Stage(view, game.batch);
        alma = new Texture(Gdx.files.internal("images/alma.png"));
        notAlma = new Texture(Gdx.files.internal("images/almaShadow.png"));
        life = new Texture(Gdx.files.internal("images/life.png"));
        lifeBg = new Texture(Gdx.files.internal("images/lifebg.png"));
        pausa = new Texture(Gdx.files.internal("pausa.png"));

        while(i<game.almas) {
            almaImg = new Image(alma);
            almaImg.setPosition(50+i*game.height*3/50, game.height - 100);
            stage.addActor(almaImg);
            i++;
        }
        while(7-i>0){
            notAlmaImg = new Image(notAlma);
            notAlmaImg.setPosition(50+i*game.height*3/50, game.height - 100);
            stage.addActor(notAlmaImg);
            i++;
        }
        lifeBgImg=new Image(lifeBg);
        lifeBgImg.setPosition(game.width - 300,game.height - 100);
        stage.addActor(lifeBgImg);

        lifeImg=new Image(life);
        lifeImg.setPosition(game.width - 280,game.height - 80);
        lifeImg.setScaleX(screen1.soulKeeper.getLife());
        stage.addActor(lifeImg);

        pausaImg=new Image(pausa);
        pausaImg.setPosition(game.width/2-50, game.height-100);
        stage.addActor(pausaImg);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void update(float dt) {
        lifeImg.setScaleX(screen1.soulKeeper.getLife());
        stage.addActor(lifeImg);
    }
}

