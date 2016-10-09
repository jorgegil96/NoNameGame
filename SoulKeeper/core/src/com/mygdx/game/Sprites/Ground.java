/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import static com.mygdx.game.MyGdxGame.DEFAULT_BIT;
import com.mygdx.game.Screens.PlayScreen;


/**
 *
 * @author Juan
 */
public class Ground extends InteractiveTileObject{
    public Ground(PlayScreen screen, Rectangle bounds){
        super(screen, bounds);
        setCategoryFilter(DEFAULT_BIT);
    }

    @Override
    public void onHeadHit() {
        
    }
}
