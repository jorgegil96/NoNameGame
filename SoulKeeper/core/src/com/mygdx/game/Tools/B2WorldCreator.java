/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Demons;
import com.mygdx.game.Sprites.Ground;
import com.mygdx.game.Sprites.SoulKeeper;
import com.mygdx.game.Sprites.Trees;
import com.mygdx.game.Sprites.*;
import com.mygdx.game.MyGdxGame.*;

/**
 *
 * @author Juan
 */
public class B2WorldCreator {
    private Array<Demons> demons;
    private SoulKeeper player1;
    public B2WorldCreator(PlayScreen screen, SoulKeeper player){
        player1 = player;
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for(MapObject object: map.getLayers().get("items").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            new Trees(screen, rect);
        }
        
        for(MapObject object: map.getLayers().get("Casas").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            new Houses(screen, rect);
        }

        /*
        for(MapObject object: map.getLayers().get("Door").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            new Door(screen, rect);
        }


        for(MapObject object : map.getLayers().get("npc").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            new NPC(screen, rect);
        }
        */

        demons = new Array<Demons>();
        for(MapObject object: map.getLayers().get("enemies").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            demons.add(new Demons(screen, rect.getX() / MyGdxGame.PPM, rect.getY() / MyGdxGame.PPM, player1));
        }
    }

    public Array<Demons> getDemons(){
        return demons;
    }
}
