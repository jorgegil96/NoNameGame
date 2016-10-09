package com.mygdx.game.Sprites;

import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Screens.PlayScreen;

import static com.mygdx.game.MyGdxGame.NPC_BIT;

public class NPC extends InteractiveTileObject {

    public NPC(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        setCategoryFilter(NPC_BIT);
    }

    @Override
    public void onHeadHit() {

    }
}
