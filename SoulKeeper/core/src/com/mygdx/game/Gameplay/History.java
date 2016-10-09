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
    int story =0;
    public Dialog Dialog;
    public void History(final MyGdxGame game){
        this.game=game;
    }
    public void part1(){
        Dialog = new Dialog(game, "This house is not locked.", TimeUtils.nanoTime());
        Dialog = new Dialog(game, "Pickup the soul of the dead.", TimeUtils.nanoTime());
        Dialog = new Dialog(game, "This is your mission: from now on, protect and collect the soul of the ones who passed away.", TimeUtils.nanoTime());
    }

}
