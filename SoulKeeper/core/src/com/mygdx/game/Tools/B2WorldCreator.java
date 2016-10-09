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
    private Array<Trees> trees;
    private Array<Houses> houses;
    private Array<Door> door;
    private Array<Ground> ground;
    private Array<NPC> npc;
    private SoulKeeper player1;
    public B2WorldCreator(PlayScreen screen, SoulKeeper player){
        player1 = player;
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        
        trees = new Array<Trees>();
        for(MapObject object: map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            new Trees(screen, rect);
            trees.add(new Trees(screen, rect));
        }
        
        houses = new Array<Houses>();
        for(MapObject object: map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            new Houses(screen, rect);
            houses.add(new Houses(screen, rect));
        }
        
        door = new Array<Door>();
        for(MapObject object: map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            new Door(screen, rect);
            door.add(new Door(screen, rect));
        }
        
        ground = new Array<Ground>();
        for(MapObject object: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            new Ground(screen, rect);
            ground.add(new Ground(screen, rect));
        }
        
        npc = new Array<NPC>();
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            new NPC(screen, rect);
            npc.add(new NPC(screen, rect));
        }


        demons = new Array<Demons>();
        for(MapObject object: map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject)object).getRectangle();
            demons.add(new Demons(screen, rect.getX() / MyGdxGame.PPM, rect.getY() / MyGdxGame.PPM, player1));
        }
    }

    public Array<Demons> getDemons(){
        return demons;
    }
    
    public Array<Trees> getTrees(){
        return trees;
    }
    
    public Array<Houses> getHouses(){
        return houses;
    }
    
    public Array<Ground> getGround(){
        return ground;
    }
    
    public Array<NPC> getNPC(){
        return npc;
    }
}
