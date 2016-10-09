/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import static com.mygdx.game.MyGdxGame.BRICK_BIT;
import static com.mygdx.game.MyGdxGame.DEFAULT_BIT;
import static com.mygdx.game.MyGdxGame.DOOR_BIT;
import static com.mygdx.game.MyGdxGame.ENEMY_BIT;
import static com.mygdx.game.MyGdxGame.OBJECT_BIT;
import static com.mygdx.game.MyGdxGame.PPM;
import static com.mygdx.game.MyGdxGame.SOULKEEPER_BIT;
import static com.mygdx.game.MyGdxGame.SWORD_BIT;
import com.mygdx.game.Screens.PlayScreen;

/**
 *
 * @author Juan
 */
public class Demons extends Enemy{
    private float stateTime;
    private Animation walk;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    int lives;
    private SoulKeeper player1;
    private PlayScreen screen1;
    public Demons(PlayScreen screen, float x, float y, SoulKeeper player) {
        super(screen, x, y);
        player1 = player;
        screen1 = screen;
        frames = new Array<TextureRegion>();
        setToDestroy = false;
        destroyed = false;
        lives = 3;
        for(int i = 0; i < 2; i++)
        {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("goomba"),0 + (i* 16),0, 16, 16));
            walk = new Animation(0.4f, frames);
            stateTime = 0;
            setBounds(getX(), getY(), 16 /PPM, 16 / PPM);
        }
    }

    @Override
    public void update(float dt)
    {
        stateTime += dt;
        if(setToDestroy && !destroyed)
        {
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("goomba"), 32, 0, 16, 16));
            stateTime = 0;
        }
        else if(!destroyed)
        {

            if(player1.getX() > b2body.getPosition().x && player1.getY() > b2body.getPosition().y)
            {
                b2body.setLinearVelocity(0.5f, 0.5f);
            }
            else if(player1.getX() < b2body.getPosition().x && player1.getY() < b2body.getPosition().y)
            {
                b2body.setLinearVelocity(-0.5f, -0.5f);
            }
            else if(player1.getX() < b2body.getPosition().x && player1.getY() >  b2body.getPosition().y)
            {
                b2body.setLinearVelocity(-0.5f, 0.5f);
            }
            else if(player1.getX() > b2body.getPosition().x && player1.getY() < b2body.getPosition().y)
            {
                b2body.setLinearVelocity(0.5f, -0.5f);
            }
            else if(player1.getX() < b2body.getPosition().x)
            {
                b2body.setLinearVelocity(-0.5f,0);
            }
            else if(player1.getX() > b2body.getPosition().x)
            {
                b2body.setLinearVelocity(0.5f,0);
            }
            else if(player1.getY() < b2body.getPosition().y)
            {
                b2body.setLinearVelocity(0,-0.5f);
            }
            else if(player1.getY() > b2body.getPosition().y)
            {
                b2body.setLinearVelocity(0,0.5f);
            }


            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walk.getKeyFrame(stateTime, true));
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/PPM);
        fdef.filter.categoryBits = ENEMY_BIT;
        fdef.filter.maskBits = DEFAULT_BIT | DOOR_BIT | BRICK_BIT | ENEMY_BIT | OBJECT_BIT | SOULKEEPER_BIT | SWORD_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

    }

    public void draw(Batch batch)
    {
        if(!destroyed || stateTime < 0.5)
        {
            super.draw(batch);
        }
    }
    
    public void g()
    {
    }
    
    @Override
    public int getLives(){
        return lives;
    }
    
    public void gainLife(){
        player1.gainLife();
    }

    @Override
    public void hitOnHead() {
        setToDestroy = true;
    }

    @Override
    public void damaged() {
        lives -= 1;
        if(lives == 0)
        {
            setToDestroy = true;
        }
    }
}
