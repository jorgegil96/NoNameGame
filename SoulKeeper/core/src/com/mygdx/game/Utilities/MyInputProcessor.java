package com.mygdx.game.Utilities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Sprites.SoulKeeper;

public class MyInputProcessor implements InputProcessor {
    private SoulKeeper soulKeeper;

    public MyInputProcessor(SoulKeeper soulKeeper) {
        this.soulKeeper = soulKeeper;
    }
    @Override
    public boolean keyDown(int keycode) {
        Vector2 vector2 = null;
        float currentY = getCurrentYDirection();
        float currentX = getCurrentXDirection();

        if (keycode == Input.Keys.W) {
            soulKeeper.setRunningUp(true);
            vector2 = new Vector2(currentX, 1f);
        }
        if (keycode == Input.Keys.S) {
            soulKeeper.setRunningDown(true);
            vector2 = new Vector2(currentX, -1f);
        }
        if (keycode == Input.Keys.A) {
            soulKeeper.setRunningLeft(true);
            vector2 = new Vector2(-1f, currentY);
        }
        if (keycode == Input.Keys.D) {
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

        if (keycode == Input.Keys.W) {
            soulKeeper.setRunningUp(false);
            if (currentY != -1f) {
                vector2 = new Vector2(currentX, 0);
            }
        }
        if (keycode == Input.Keys.S) {
            soulKeeper.setRunningDown(false);
            if (currentY != 1f) {
                vector2 = new Vector2(currentX, 0);
            }
        }
        if (keycode == Input.Keys.A) {
            soulKeeper.setRunningLeft(false);
            if (currentX != -1f) {
                vector2 = new Vector2(0, currentY);
            }
        }
        if (keycode == Input.Keys.D) {
            soulKeeper.setRunningRight(false);
            if (currentX != 1f) {
                vector2 = new Vector2(0, currentY);
            }
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
