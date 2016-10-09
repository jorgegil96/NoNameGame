package com.mygdx.game.Gameplay;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Scenes.Dialog;

/**
 * Created by ortiz on 9/10/16.
 */
public class History {
    private MyGdxGame game;
    int dia=0;
    public Dialog Dialog;
    public void History(final MyGdxGame game){
        this.game=game;
    }
    public void part1(){
        if(game.almas==0 && dia==0) {
            Dialog = new Dialog(game, "This door is not locked.", TimeUtils.nanoTime());
            game.almas++;
        }
        if(game.almas==0 && dia==1) {
            Dialog = new Dialog(game, "Pickup the soul of the dead.", TimeUtils.nanoTime());
            game.almas++;
        }
        if(game.almas==0 && dia==2) {
            Dialog = new Dialog(game, "This is your mission: from now on, protect and collect the 7 souls of the people here.", TimeUtils.nanoTime());
            game.almas++;
        }
        }

}
