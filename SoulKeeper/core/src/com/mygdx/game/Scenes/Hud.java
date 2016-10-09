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
<<<<<<< HEAD
import com.badlogic.gdx.math.Rectangle;
=======
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
>>>>>>> 45d2238831550f3b59519dbc3b55c049ab007e04
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
<<<<<<< HEAD
import static com.mygdx.game.MyGdxGame.V_HEIGHT;
import static com.mygdx.game.MyGdxGame.V_WIDTH;
import org.w3c.dom.css.Rect;
=======
import com.mygdx.game.MyGdxGame;

>>>>>>> 45d2238831550f3b59519dbc3b55c049ab007e04


/**
 *
 * @author Juan
 */
public class Hud implements Disposable{


    private MyGdxGame game;
    public Stage stage;
    private Viewport view;
<<<<<<< HEAD
    private Integer timer;
    private float count;
    private Integer score;
    
    Label countLabel;
    Label scoreLabel;
    Label lifeLabel;
    Label levelLabel;
    Label worldLabel;
    Label marioLabel;
    Label soulsLabel;
    
    public Hud(SpriteBatch sb){
        timer = 300;
        count = 0;
        score = 0;
        view = new FitViewport(V_WIDTH, V_HEIGHT, new OrthographicCamera());
        stage = new Stage(view, sb);
        
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        
        countLabel = new Label(String.format("%03d", timer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lifeLabel = new Label("Life", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        soulsLabel = new Label("Souls", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        marioLabel = new Label("MARIO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(marioLabel).expandX().padTop(2);
        table.add(lifeLabel).expandX().padTop(2);
        table.add(soulsLabel).expandX().padTop(2);
        table.row();
        table.add(countLabel).expandX();
        table.add().expandX();
        table.add(countLabel).expandX();
        stage.addActor(table);
=======
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
>>>>>>> 45d2238831550f3b59519dbc3b55c049ab007e04
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void update(float dt) {
        
    }
}

