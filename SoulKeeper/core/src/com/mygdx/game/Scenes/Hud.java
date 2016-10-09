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
    private Image  almaImg;
    private Image notAlmaImg;
    private Image lifeBgImg;
    private Image lifeImg;
    
    public Hud(final MyGdxGame game){
        int i=0;
        view = new FitViewport(game.width, game.height, new OrthographicCamera());
        stage = new Stage(view, game.batch);
        alma = new Texture(Gdx.files.internal("sprites/alma.png"));
        notAlma = new Texture(Gdx.files.internal("sprites/almaShadow.png"));
        life = new Texture(Gdx.files.internal("sprites/life.png"));
        lifeBg = new Texture(Gdx.files.internal("sprites/lifebg.png"));

        while(i<game.almas) {
            almaImg = new Image(alma);
            almaImg.setPosition(50+i*game.height*3/50, game.height * 9 / 10);
            stage.addActor(almaImg);
            i++;
        }
        while(7-i>0){
            notAlmaImg = new Image(notAlma);
            notAlmaImg.setPosition(50+i*game.height*3/50, game.height * 9 / 10);
            stage.addActor(notAlmaImg);
            i++;
        }
        lifeBgImg=new Image(lifeBg);
        lifeBgImg.setPosition(game.width*17/20,game.height*9/10);
        stage.addActor(lifeBgImg);

        lifeImg=new Image(life);
        lifeImg.setPosition(game.width*17/20+20,game.height*9/10+20);
        lifeImg.setScaleX(game.vida);
        stage.addActor(lifeImg);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void update(float dt) {
        
    }
}

