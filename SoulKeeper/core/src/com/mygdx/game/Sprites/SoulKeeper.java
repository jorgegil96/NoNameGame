/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import static com.mygdx.game.MyGdxGame.BRICK_BIT;
import static com.mygdx.game.MyGdxGame.COIN_BIT;
import static com.mygdx.game.MyGdxGame.DEFAULT_BIT;
import static com.mygdx.game.MyGdxGame.ENEMY_BIT;
import static com.mygdx.game.MyGdxGame.OBJECT_BIT;
import static com.mygdx.game.MyGdxGame.PPM;
import static com.mygdx.game.MyGdxGame.SOULKEEPER_BIT;
import com.mygdx.game.Screens.PlayScreen;


/**
 *
 * @author Juan
 */
public class SoulKeeper extends Sprite{
    public enum State {UP, DOWN, LEFT, RIGHT, STANDING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion marioStand;
    private Animation marioJump;
    private Animation marioRun;
    private float stateTimer;
    private boolean runningRight, runningLeft, runningUp, runningDown;

    float speed = 100.0f;
    
    public SoulKeeper(PlayScreen screen)
    {   
        super(screen.getAtlas().findRegion("big_mario"));
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;

        runningLeft = false;
        runningRight = false;
        runningDown = false;
        runningUp = false;
        
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i < 4; i++)
        {
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 30));
            marioRun = new Animation(0.1f,frames);
            
        }
        frames.clear();
        for(int i = 4; i < 6; i++)
        {
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 30));
            marioJump = new Animation(0.1f,frames);
             frames.clear();
        }
       
        defineSoul();
        marioStand = new TextureRegion(getTexture(), 0, 0, 16, 30);
        setBounds(0,0,16/PPM,30/PPM);
        setRegion(marioStand);
    }
    
    public void update(float dt)
    {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }
    
    public TextureRegion getFrame(float dt)
    {
        currentState = getState();
        TextureRegion region;
        switch(currentState)
        {
            case UP:
                region = marioStand;
                break;
            case LEFT:
            region = marioStand;
            break;
            case RIGHT:
                 region = marioStand;
                break;
            case DOWN:
                 region = marioStand;
                break;
            case STANDING:
            default:
                region = marioStand;
                break;
        }
        
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX())
        {
            region.flip(true, false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX())
        {
            region.flip(true, false);
            runningRight = true;
        }
        
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }
    
    public State getState(){
        if(b2body.getLinearVelocity().y > 0)
        {
            return State.UP;
        }
        else if(b2body.getLinearVelocity().x < 0)
        {
            return State.LEFT;
        }
        else if(b2body.getLinearVelocity().y < 0)
        {
            return State.DOWN;
        }
        else if(b2body.getLinearVelocity().y > 0)
        {
            return State.RIGHT;
        }
        else
        {
            return State.STANDING;
        }
    }
    
    public void defineSoul()
    {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/PPM,32/PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12/PPM);
        fdef.filter.categoryBits = SOULKEEPER_BIT;
        fdef.filter.maskBits = DEFAULT_BIT | COIN_BIT | BRICK_BIT | OBJECT_BIT | ENEMY_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef);
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / PPM, 12 / PPM),new Vector2(2 / PPM, 12 / PPM));
        fdef.shape = head;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("head");
    }

    public void setRunningUp(boolean runningUp) {
        this.runningUp = runningUp;
    }

    public void setRunningDown(boolean runningDown) {
        this.runningDown = runningDown;
    }

    public void setRunningRight(boolean runningRight) {
        this.runningRight = runningRight;
    }

    public void setRunningLeft(boolean runningLeft) {
        this.runningLeft = runningLeft;
    }

    public boolean isRunningUp() {
        return runningUp;
    }

    public boolean isRunningDown() {
        return runningDown;
    }

    public boolean isRunningRight() {
        return runningRight;
    }

    public boolean isRunningLeft() {
        return runningLeft;
    }
}
