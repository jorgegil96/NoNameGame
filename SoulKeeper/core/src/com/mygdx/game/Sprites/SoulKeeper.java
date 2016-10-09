/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
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

import static com.mygdx.game.MyGdxGame.*;

public class SoulKeeper extends Sprite{
    public enum State {UP, DOWN, LEFT, RIGHT, STANDING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    public Fixture fixture;
    private TextureRegion marioStand;
    private Animation marioJump;
    private Animation marioRun;
    private float stateTimer;
    private boolean runningRight, runningLeft, runningUp, runningDown;

    float speed = 100.0f;
    private Sword sword;
    private PlayScreen screen1;
    private Array<Sword> swords;
    private FixtureDef fdef_sword = new FixtureDef();
    private int life;
    public SoulKeeper(PlayScreen screen)
    {
        super(screen.getAtlas().findRegion("big_mario"));
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        screen1 = screen;
        runningLeft = false;
        runningRight = false;
        runningDown = false;
        runningUp = false;
        life = 100;
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

        CircleShape head = new CircleShape();
        head.setPosition(new Vector2(16 / PPM, 4 / PPM));
        head.setRadius(0/PPM);
        fdef_sword.shape = head;
        fdef_sword.filter.categoryBits = SWORD_BIT;
        fdef_sword.filter.maskBits = DEFAULT_BIT | COIN_BIT | BRICK_BIT | OBJECT_BIT | ENEMY_BIT;
        b2body.createFixture(fdef_sword).setUserData(this);
        marioStand = new TextureRegion(getTexture(), 0, 0, 16, 30);
        setBounds(0,0,16/PPM,30/PPM);
        setRegion(marioStand);
        swords = new Array<Sword>();
    }

    public void update(float dt)
    {
        Gdx.app.log("Life", String.valueOf(life));
        stateTimer += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
        for(Sword sword1 : swords) {
            sword1.update(dt);
            if(sword1.isDestroyed())
                swords.removeValue(sword1, true);
        }
    }

    public TextureRegion getFrame(float dt)
    {
        currentState = getState();
        TextureRegion region;
        switch(currentState)
        {
            case UP:
                region = marioRun.getKeyFrame(stateTimer, true /* loop */);
                break;
            case LEFT:
                region = marioRun.getKeyFrame(stateTimer, true /* loop */);;
                break;
            case RIGHT:
                region = marioRun.getKeyFrame(stateTimer, true /* loop */);;
                break;
            case DOWN:
                region = marioRun.getKeyFrame(stateTimer, true /* loop */);;
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
        else if(b2body.getLinearVelocity().x > 0)
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
        fdef.filter.maskBits = DEFAULT_BIT | COIN_BIT | BRICK_BIT | OBJECT_BIT | ENEMY_BIT | NPC_BIT;
        fdef.shape = shape;
        fixture = b2body.createFixture(fdef);
        fixture.setUserData(this);
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

    public void damaged(){
        life -= 10;
    }

    public float getX()
    {
        return b2body.getPosition().x;
    }

    public float getY()
    {
        return b2body.getPosition().y;
    }

    public Array<Sword> getSwords(){
        return swords;
    }

    public void hit(){
        swords.add(new Sword(screen1, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false, runningUp ? true : false, this));
    }
}
