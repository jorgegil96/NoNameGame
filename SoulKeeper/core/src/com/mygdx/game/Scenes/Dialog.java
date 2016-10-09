package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.SoulKeeper;

/**
 * Created by ortiz on 9/10/16.
 */
public class Dialog implements Disposable {
    private MyGdxGame game;
    public Stage stage;
    private Viewport view;
    String s2 = "";
    int i=0;
    int j=0;
    String[] lines={"","","","",""};
    GlyphLayout layout2=new GlyphLayout();
    long fontWaitTime;
    private Label label;
    String texto;

    public Dialog(final MyGdxGame gam, String text, long initTime){
        game=gam;
        texto=text;
        view = new FitViewport(game.width, game.height, new OrthographicCamera());
        stage = new Stage(view, game.batch);
        fontWaitTime=initTime;

    }

    public void dialog(String text){

        String[] words = text.split(" ");

        GlyphLayout layout1 = new GlyphLayout();
        layout1.setText(game.font, String.valueOf(text));
        Table table = new Table();
        table.left().padLeft(100);
        table.setHeight(game.height/3);
        stage.addActor(table);
        if(layout1.width<game.height-20){
            if (i<words.length && TimeUtils.nanoTime() - fontWaitTime > 100000000 && !(text.equals(s2))){
                s2 += words[i] + " ";

                fontWaitTime = TimeUtils.nanoTime();
                i++;
            }
            label = new Label(s2, new Label.LabelStyle(game.font, Color.WHITE));
            table.add(label).align(Align.left);
        }
        else{
            System.out.println(layout2.width);
            if(i<words.length && TimeUtils.nanoTime() - fontWaitTime > 100000000) {
                lines[j] += words[i] + " ";
                layout2 = new GlyphLayout();
                layout2.setText(game.font, String.valueOf(lines[j]));
                if (layout2.width < game.width - 320) {

                    fontWaitTime = TimeUtils.nanoTime();

                    i++;
                } else{
                    j++;
                    i++;
                    layout2.setText(game.font, String.valueOf(lines[j]));

                }
            }
            for(int k=0;k<5;k++){
                label = new Label(lines[k], new Label.LabelStyle(game.font, Color.WHITE));
                table.add(label).align(Align.left);
                table.row();
            }
        }

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void update(float dt) {
        dialog(texto);
    }
}
