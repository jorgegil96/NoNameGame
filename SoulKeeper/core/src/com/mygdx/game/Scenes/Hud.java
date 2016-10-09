/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import static com.mygdx.game.MyGdxGame.V_HEIGHT;
import static com.mygdx.game.MyGdxGame.V_WIDTH;
import org.w3c.dom.css.Rect;


/**
 *
 * @author Juan
 */
public class Hud implements Disposable{
    
    public Stage stage;
    private Viewport view;
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
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void update(float dt) {
        
    }
}

