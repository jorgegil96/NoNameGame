package com.mygdx.game.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;

/**
 * Created by ortiz on 9/10/16.
 */
public class Dialog implements Disposable {
    private MyGdxGame game;
    public Stage stage;
    private Viewport view;
    private PlayScreen screen1;
    String s2 = "";
    int i=0;
    int j=0;
    String[] lines={"","","","",""};
    GlyphLayout layout2=new GlyphLayout();
    long fontWaitTime=0;
    public Dialog(final MyGdxGame game, PlayScreen screen){
        screen1 = screen;
        view = new FitViewport(game.width, game.height, new OrthographicCamera());
        stage = new Stage(view, game.batch);

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
                game.font.draw(game.batch, lines[k], 10, game.height / 3 - k*game.height/12);
            }
        }
        game.batch.end();

    }

    @Override
    public void dispose() {

    }

    public void update(float dt) {
    }
}
