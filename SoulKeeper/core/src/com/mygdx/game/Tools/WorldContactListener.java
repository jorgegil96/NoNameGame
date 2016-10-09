/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Sprites.Enemy;
import com.mygdx.game.Sprites.InteractiveTileObject;
import com.mygdx.game.Sprites.SoulKeeper;

import static com.mygdx.game.MyGdxGame.*;

/**
 *
 * @author Juan
 */
public class WorldContactListener implements ContactListener {
    private static final String TAG = "WorldContactListener";

    @Override
    public void beginContact(Contact contact) {
        Fixture FixA = contact.getFixtureA();
        Fixture FixB = contact.getFixtureB();

        int cDef = FixA.getFilterData().categoryBits | FixB.getFilterData().categoryBits;

        switch(cDef) {
            case ENEMY_BIT | SOULKEEPER_BIT:
                if(FixA.getFilterData().categoryBits == ENEMY_BIT) {
                    ((Enemy)FixA.getUserData()).hitOnHead();
                } else {
                    ((Enemy)FixB.getUserData()).hitOnHead();
                }
                break;
            case ENEMY_BIT | OBJECT_BIT:
                if(FixA.getFilterData().categoryBits == ENEMY_BIT) {
                    ((Enemy)FixA.getUserData()).reverseVelocity(true, false);
                } else {
                    ((Enemy)FixB.getUserData()).reverseVelocity(true, false);
                }
                break;
            case ENEMY_BIT | ENEMY_BIT:
                ((Enemy)FixA.getUserData()).reverseVelocity(true, false);
                ((Enemy)FixB.getUserData()).reverseVelocity(true, false);
            break;
                        
        }
    }

    @Override
    public void endContact(Contact contact) {
        
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
       
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        
    }
    
}
