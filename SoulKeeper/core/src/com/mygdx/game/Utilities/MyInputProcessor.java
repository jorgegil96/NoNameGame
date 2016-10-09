package com.mygdx.game.Utilities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.SoulKeeper;

public class MyInputProcessor implements InputProcessor {
    private SoulKeeper soulKeeper;
    private PlayScreen screen1;
    public MyInputProcessor(PlayScreen screen, SoulKeeper soulKeeper) {
        this.soulKeeper = soulKeeper;
        screen1 = screen;
    }
    @Override
    public boolean keyDown(int keycode) {
        Vector2 vector2 = null;
        float currentY = getCurrentYDirection();
        float currentX = getCurrentXDirection();

        if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
            soulKeeper.setRunningUp(true);
            vector2 = new Vector2(currentX, 1f);
        }
        if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
            soulKeeper.setRunningDown(true);
            vector2 = new Vector2(currentX, -1f);
        }
        if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
            soulKeeper.setRunningLeft(true);
            vector2 = new Vector2(-1f, currentY);
        }
        if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
            soulKeeper.setRunningRight(true);
            vector2 = new Vector2(1f, currentY);
        }
        if (vector2 != null) {
            soulKeeper.b2body.setLinearVelocity(vector2);
        }
        
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        Vector2 vector2 = null;
        float currentY = getCurrentYDirection();
        float currentX = getCurrentXDirection();

        if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
            screen1.setUp();
            soulKeeper.setRunningUp(false);
            if (currentY != -1f) {
                vector2 = new Vector2(currentX, 0);
                
            }
        }
        if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
            screen1.setDown();
            soulKeeper.setRunningDown(false);
            if (currentY != 1f) {
                vector2 = new Vector2(currentX, 0);
            }
        }
        if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
            screen1.setLeft();
            soulKeeper.setRunningLeft(false);
            if (currentX != 1f) {
                vector2 = new Vector2(0, currentY);
            }
        }
        if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
            screen1.setRight();
            soulKeeper.setRunningRight(false);
            if (currentX != -1f) {
                vector2 = new Vector2(0, currentY);
            }
        }
        if(keycode == Input.Keys.X)
        {
            soulKeeper.hit();
        }
        if (vector2 != null) {
            soulKeeper.b2body.setLinearVelocity(vector2);
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public float getCurrentXDirection() {
        if (soulKeeper.isRunningLeft()) {
            return -1f;
        }
        if (soulKeeper.isRunningRight()) {
            return 1f;
        }
        return 0;
    }

    public float getCurrentYDirection() {
        if (soulKeeper.isRunningDown()) {
            return -1f;
        }
        if (soulKeeper.isRunningUp()) {
            return 1f;
        }
        return 0;
    }
}
