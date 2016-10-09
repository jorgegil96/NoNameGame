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
import static com.mygdx.game.MyGdxGame.DEFAULT_BIT;
import static com.mygdx.game.MyGdxGame.ENEMY_BIT;
import static com.mygdx.game.MyGdxGame.OBJECT_BIT;
import static com.mygdx.game.MyGdxGame.SOULKEEPER_BIT;
import static com.mygdx.game.MyGdxGame.SWORD_BIT;
import com.mygdx.game.Sprites.Enemy;
import com.mygdx.game.Sprites.InteractiveTileObject;
import com.mygdx.game.Sprites.SoulKeeper;
import com.mygdx.game.Sprites.Sword;

/**
 *
 * @author Juan
 */
public class WorldContactListener implements ContactListener{

    @Override
    public void beginContact(Contact contact) {
        Fixture FixA = contact.getFixtureA();
        Fixture FixB = contact.getFixtureB();
        Fixture head;
        Fixture object;
        int cDef = FixA.getFilterData().categoryBits | FixB.getFilterData().categoryBits;
        if(FixA.getUserData() == "head" || FixB.getUserData() == "head")
        {
            if(FixA.getUserData() == "head")
            {
                head = FixA;
                object = FixB;
            }
            else
            {
                head = FixB;
                object = FixA;
            }
            if(object.getUserData() != null)
            {
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }
        }
        switch(cDef)
        {
            case DEFAULT_BIT | SWORD_BIT:
                 if(FixA.getFilterData().categoryBits == SWORD_BIT)
                {
                   // ((Sword)FixA.getUserData()).setToDestroy();
                }
            else
                {
                    //((Sword)FixB.getUserData()).setToDestroy();
                }
                break;
            case ENEMY_BIT | SWORD_BIT:
                if(FixA.getFilterData().categoryBits == ENEMY_BIT)
                {
                    ((Enemy)FixA.getUserData()).damaged();
                    ((Sword)FixB.getUserData()).setToDestroy();
                }
            else
                {
                    ((Enemy)FixB.getUserData()).damaged();
                    ((Sword)FixA.getUserData()).setToDestroy();
                }
                break;
            case ENEMY_BIT | SOULKEEPER_BIT:
                if(FixA.getFilterData().categoryBits == SOULKEEPER_BIT)
                {
                    ((SoulKeeper)FixA.getUserData()).damaged();
                }
            else
                {
                    ((SoulKeeper)FixB.getUserData()).damaged();
                }
            break;
            case ENEMY_BIT | OBJECT_BIT:
                if(FixA.getFilterData().categoryBits == ENEMY_BIT)
                {
                    ((Enemy)FixA.getUserData()).reverseVelocity(true, false);
                }
            else
                {
                    ((Enemy)FixB.getUserData()).reverseVelocity(true, false);
                }
            break;
            case SOULKEEPER_BIT | OBJECT_BIT:
                if(FixA.getFilterData().categoryBits == OBJECT_BIT)
                {
                    
                }
            else
                {
                    
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
