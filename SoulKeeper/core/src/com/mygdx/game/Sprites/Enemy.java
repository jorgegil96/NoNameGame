/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Sprites;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Screens.PlayScreen;

/**
 *
 * @author Juan
 */
public abstract class Enemy extends Sprite{
    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    public Vector2 velocity;
    
    public Enemy(PlayScreen screen, float x, float y)
    {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(-1,-2);
        b2body.setActive(false);
    }
    
    protected abstract void defineEnemy();
    public abstract void hitOnHead();
    public abstract void damaged();
    public abstract void update(float dt);
    public abstract int getLives();
    public void reverseVelocity(boolean x, boolean y)
    {
        if(x)
        {
            velocity.x = -velocity.x;
        }
        if(y)
        {
            velocity.y = -velocity.y;
        }
    }
}
