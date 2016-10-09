/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import static com.mygdx.game.MyGdxGame.BRICK_BIT;
import static com.mygdx.game.MyGdxGame.COIN_BIT;
import static com.mygdx.game.MyGdxGame.DEFAULT_BIT;
import static com.mygdx.game.MyGdxGame.ENEMY_BIT;
import static com.mygdx.game.MyGdxGame.OBJECT_BIT;
import static com.mygdx.game.MyGdxGame.PPM;
import static com.mygdx.game.MyGdxGame.SOULKEEPER_BIT;
import static com.mygdx.game.MyGdxGame.SWORD_BIT;
import com.mygdx.game.Screens.PlayScreen;

public class Sword extends Sprite {

  PlayScreen screen;
    World world;
    Array<TextureRegion> frames;
    Animation fireAnimation;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    boolean swordRight;
    boolean swordUp;
    private SoulKeeper player1;
    Body b2body;
    boolean attack;
    public Sword(PlayScreen screen, float x, float y, boolean swordRight, boolean swordUp, SoulKeeper player){
        this.swordRight = swordRight;
        this.swordUp = swordUp;
        this.screen = screen;
        this.world = screen.getWorld();
        player1 = player;
        frames = new Array<TextureRegion>();
        attack = false;
        for(int i = 0; i < 4; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("fireball"), i * 8, 0, 8, 8));
        }
        fireAnimation = new Animation(0.2f, frames);
        setRegion(fireAnimation.getKeyFrame(0));
        setBounds(x, y, 6 / PPM, 6 / PPM);
        defineSword();
    }

    public void defineSword(){
        if(!attack)
        {
        BodyDef bdef = new BodyDef();
        if(Gdx.input.isKeyPressed(Keys.UP))
        {
            if(!destroyed)
            {
                bdef.position.set(player1.getX(),player1.getY() + 16 /PPM);
            }
            else
            {
                b2body.applyLinearImpulse(new Vector2(0,1f), b2body.getWorldCenter(), false);
            }
        }
        else if(Gdx.input.isKeyPressed(Keys.DOWN))
        {
            if(!destroyed)
            {
            bdef.position.set(player1.getX(),player1.getY() - 16 /PPM);
            
            }
            else
            {
                b2body.applyLinearImpulse(new Vector2(0,-1f), b2body.getWorldCenter(), false);
            }
        }
        else if(Gdx.input.isKeyPressed(Keys.LEFT))
        {
            if(!destroyed)
            {
            bdef.position.set(player1.getX() - 12 /PPM, player1.getY());
            }
            else
            {
                b2body.applyLinearImpulse(new Vector2(-0.1f,0), b2body.getWorldCenter(), false);
            }
        }
    else if(Gdx.input.isKeyPressed(Keys.RIGHT))
    {
        if(!destroyed)
            {
                bdef.position.set(player1.getX() + 12 /PPM, player1.getY());
            }
        else
            {
                b2body.applyLinearImpulse(new Vector2(-0.1f,0), b2body.getWorldCenter(), false);
            }
        }
        else
    {
        if(!destroyed)
            {
                if(screen.right)
                {
                    bdef.position.set(player1.getX() + 12 /PPM, player1.getY());
                }
                else if(screen.left)
                {
                    bdef.position.set(player1.getX() - 12 /PPM, player1.getY());
                }
                else if(screen.up)
                {
                    bdef.position.set(player1.getX(),player1.getY() + 16 /PPM);
                }
                else if(screen.down)
                {
                    bdef.position.set(player1.getX(),player1.getY() - 16 /PPM);
                }
            }
         else
            {
                if(screen.right)
                {
                    b2body.applyLinearImpulse(new Vector2(0.1f,0), b2body.getWorldCenter(), false);
                }
                else if(screen.left)
                {
                    b2body.applyLinearImpulse(new Vector2(-0.1f,0), b2body.getWorldCenter(), false);
                }
                else if(screen.up)
                {
                    b2body.applyLinearImpulse(new Vector2(0,1f), b2body.getWorldCenter(), false);
                }
                else if(screen.down)
                {
                   b2body.applyLinearImpulse(new Vector2(0,-1f), b2body.getWorldCenter(), false);
                }
                
            }
    }
        bdef.type = BodyDef.BodyType.DynamicBody;
        if(!world.isLocked())
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(3 / PPM);
        fdef.filter.categoryBits = SWORD_BIT;
        fdef.filter.maskBits = DEFAULT_BIT |
                COIN_BIT |
                BRICK_BIT |
                ENEMY_BIT |
                OBJECT_BIT;

        fdef.shape = shape;
        fdef.restitution = 1;
        fdef.friction = 0;
        b2body.createFixture(fdef).setUserData(this);
        attack = true;
        }
    }

    public void update(float dt){
        
        stateTime += dt;
        
        setRegion(fireAnimation.getKeyFrame(stateTime, true));
        setPosition(player1.getX() - player1.getWidth() / 2, player1.getY() - player1.getHeight() / 2);
        if((stateTime > 0.5 || setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }
        if(attack)
        {
            setToDestroy();
        }
    }

    public void setToDestroy(){
        setToDestroy = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }

}
