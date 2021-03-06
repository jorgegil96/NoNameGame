/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Sprites.Enemy;

import static com.mygdx.game.MyGdxGame.DEFAULT_BIT;
import static com.mygdx.game.MyGdxGame.DOOR_BIT;
import static com.mygdx.game.MyGdxGame.ENEMY_BIT;
import static com.mygdx.game.MyGdxGame.NPC_BIT;
import static com.mygdx.game.MyGdxGame.OBJECT_BIT;
import static com.mygdx.game.MyGdxGame.SHIELD_BIT;
import static com.mygdx.game.MyGdxGame.SOULKEEPER_BIT;
import static com.mygdx.game.MyGdxGame.SWORD_BIT;
import com.mygdx.game.Sprites.Demons;

import com.mygdx.game.Sprites.SoulKeeper;
import com.mygdx.game.Sprites.Sword;


public class WorldContactListener implements ContactListener {
    private static final String TAG = "WorldContactListener";
    private boolean gained = false;
    public static boolean almaCollision;

    @Override
    public void beginContact(Contact contact) {
        try{
        Fixture FixA = contact.getFixtureA();
        Fixture FixB = contact.getFixtureB();
        int cDef = FixA.getFilterData().categoryBits | FixB.getFilterData().categoryBits;

        switch(cDef)
        {
            case DEFAULT_BIT | SWORD_BIT:
                if(FixA.getFilterData().categoryBits == SWORD_BIT) {
                   // ((Sword)FixA.getUserData()).setToDestroy();
                }
                else {
                    //((Sword)FixB.getUserData()).setToDestroy();
                }
                break;
                case ENEMY_BIT | SOULKEEPER_BIT:
                if(FixA.getFilterData().categoryBits == SOULKEEPER_BIT) {
                    ((SoulKeeper)FixA.getUserData()).damaged();
                } else {
                    ((SoulKeeper)FixB.getUserData()).damaged();
                }
                break;
                case SOULKEEPER_BIT | DOOR_BIT:
                    if(FixA.getFilterData().categoryBits == SOULKEEPER_BIT) {
                    ((SoulKeeper)FixA.getUserData()).screen1.chageMap();
                } else {
                    ((SoulKeeper)FixB.getUserData()).screen1.chageMap();
                }
                    break;
                    
            case ENEMY_BIT | SWORD_BIT:
                if(FixA.getFilterData().categoryBits == ENEMY_BIT) {
                    ((Enemy)FixA.getUserData()).damaged();
                    if((((Enemy)FixA.getUserData()).getLives() % 2) == 0 && !gained)
                    {
                        gained = true;
                        ((Demons)FixA.getUserData()).gainLife();
                    }
                    else
                    {
                        gained = false;
                    }
                    if(FixB.getFilterData().categoryBits == SWORD_BIT)
                    {
                        ((Sword)FixB.getUserData()).setToDestroy();
                    }
                } 
                else 
                {
                    ((Enemy)FixB.getUserData()).damaged();
                    if((((Enemy)FixB.getUserData()).getLives() % 2) == 0 && !gained)
                    {
                        gained = true;
                        ((Demons)FixB.getUserData()).gainLife();
                    }
                    else
                    {
                        gained = false;
                    }
                    if(FixA.getFilterData().categoryBits == SWORD_BIT)
                    {
                        ((Sword)FixA.getUserData()).setToDestroy();
                    }
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
            case ENEMY_BIT | SHIELD_BIT:
                  if(FixA.getFilterData().categoryBits == SHIELD_BIT) {
                    ((SoulKeeper)FixA.getUserData()).defense();
                } else {
                    ((SoulKeeper)FixB.getUserData()).defense();
                }
                break;
            case DEFAULT_BIT | SOULKEEPER_BIT:
                almaCollision = true;
                break;
        }
        }catch(java.lang.ClassCastException e){};
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
