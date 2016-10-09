/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import static com.mygdx.game.MyGdxGame.DEFAULT_BIT;
import static com.mygdx.game.MyGdxGame.ENEMY_BIT;
import static com.mygdx.game.MyGdxGame.OBJECT_BIT;
import static com.mygdx.game.MyGdxGame.PPM;
import static com.mygdx.game.MyGdxGame.SOULKEEPER_BIT;

import com.mygdx.game.Screens.PlayScreen;

import static com.mygdx.game.MyGdxGame.*;
import com.mygdx.game.Screens.mainMenuScreen;

public class SoulKeeper extends Sprite{
    public enum State {UP, DOWN, LEFT, RIGHT, STANDING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    public Fixture fixture;
    private TextureRegion marioStand;
    private TextureRegion marioHitDown, marioHitRight, marioHitLeft, marioHitUp;
    private Animation marioRunHorizontal;
    private Animation marioRunUp;
    private Animation marioRunDown;
    private float stateTimer;
    private boolean runningRight, runningLeft, runningUp, runningDown;
    private boolean hitting, defending;
    private float hitTimer;

    float speed = 100.0f;
    private Sword sword;
    private Shield shield;
    public PlayScreen screen1;
    private Array<Sword> swords;
    private Array<Shield> shields;
    private FixtureDef fdef_sword = new FixtureDef();
    private float life;
    public SoulKeeper(PlayScreen screen)
    {
        super(new Texture("protaNoche/soulkeeper.png"));
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        screen1 = screen;
        runningLeft = false;
        runningRight = false;
        runningDown = false;
        runningUp = false;
        life = 1;
        hitting = false;
        defending = false;
        hitTimer = 0;


        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(getTexture(), 658, 1, 41, 92));
        frames.add(new TextureRegion(getTexture(), 1, 1, 37, 94));
        frames.add(new TextureRegion(getTexture(), 615, 1, 41, 92));
        marioRunHorizontal = new Animation(0.1f, frames);
        frames.clear();

        frames.add(new TextureRegion(getTexture(), 437, 1, 41, 93));
        frames.add(new TextureRegion(getTexture(), 351, 1, 41, 93));
        frames.add(new TextureRegion(getTexture(), 394, 1, 41, 93));
        marioRunDown = new Animation(0.1f, frames);
        frames.clear();

        frames.add(new TextureRegion(getTexture(), 1098, 1, 41, 91));
        frames.add(new TextureRegion(getTexture(), 1012, 1, 41, 91));
        frames.add(new TextureRegion(getTexture(), 1055, 1, 41, 91));
        marioRunUp = new Animation(0.1f, frames);
        frames.clear();


        defineSoul();
        
        marioStand = new TextureRegion(getTexture(), 351, 1, 41, 93);
        marioHitDown = new TextureRegion(getTexture(), 295, 1, 54, 93);
        marioHitUp = new TextureRegion(getTexture(), 959, 1, 51, 91);
        marioHitLeft = new TextureRegion(getTexture(), 480, 1, 67, 92);
        marioHitRight = new TextureRegion(getTexture(), 549, 1, 64, 92);
        setBounds(0, 0, 41 / PPM, 93 / PPM);
        setRegion(marioStand);
        swords = new Array<Sword>();
        shields = new Array<Shield>();
    }

    public void update(float dt)
    {
        stateTimer += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
        for(Sword sword1 : swords) {
            sword1.update(dt);
            if(sword1.isDestroyed())
                swords.removeValue(sword1, true);
        }

        if (hitting) {
            hitTimer += dt;
            Gdx.app.log("SoulKeeper", "timer" + hitTimer);
            if (hitTimer > 1) {
                hitting = false;
                hitTimer = 0;
            }
        }
    }

    public TextureRegion getFrame(float dt)
    {
        currentState = getState();
        TextureRegion region;
        switch(currentState)
        {
            case UP:
                if (hitting) {
                    region = marioHitUp;
                } else {
                    region = marioRunUp.getKeyFrame(stateTimer, true /* loop */);
                }
                break;
            case LEFT:
                if (hitting) {
                    region = marioHitLeft;
                } else {
                    region = marioRunHorizontal.getKeyFrame(stateTimer, true /* loop */);
                }
                break;
            case RIGHT:
                if (hitting) {
                    region = marioHitRight;
                } else {
                    region = marioRunHorizontal.getKeyFrame(stateTimer, true /* loop */);
                }
                break;
            case DOWN:
                if (hitting) {
                    region = marioHitDown;
                } else {
                    region = marioRunDown.getKeyFrame(stateTimer, true /* loop */);
                }
                break;
            case STANDING:
            default:
                if (hitting) {
                    region = marioHitDown;
                } else {
                    region = marioStand;
                }
                break;
        }

        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX() && !hitting)
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
        if(isRunningUp())
        {
            return State.UP;
        }
        else if(isRunningLeft())
        {
            return State.LEFT;
        }
        else if(isRunningDown())
        {
            return State.DOWN;
        }
        else if(isRunningRight())
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
        bdef.position.set(1250/PPM,400/PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12/PPM);
        fdef.filter.categoryBits = SOULKEEPER_BIT;
        fdef.filter.maskBits = DEFAULT_BIT | DOOR_BIT | OBJECT_BIT | ENEMY_BIT | NPC_BIT;
        fdef.shape = shape;
        fixture = b2body.createFixture(fdef);
        fixture.setUserData(this);
    }
    
    public float getLife()
    {
        return life;
    }
    
    public void gainLife()
    {
        if(life < 1)
        {
            life += 0.1;
        }
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
        if(life > 0)
        {
            life -= 0.1;
        }
        else
        {
            screen1.game.setScreen(new mainMenuScreen(screen1.game));
        }
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
        hitting = true;
    }
    
    public void defense(){
        shields.add(new Shield(screen1, b2body.getPosition().x, b2body.getPosition().y, runningRight ? true : false, runningUp ? true : false, this));
        defending = true;
    }
}
