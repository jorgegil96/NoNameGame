package com.mygdx.game.Utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.SoulKeeper;

public class MyInputProcessor implements InputProcessor {
    private static final String TAG = "MyInputProcessor";

    private PlayScreen screen;
    private SoulKeeper soulKeeper;

    public MyInputProcessor(PlayScreen screen, SoulKeeper soulKeeper) {
        this.screen = screen;
        this.soulKeeper = soulKeeper;
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

        if (keycode == Input.Keys.L) {
            TiledMap map = screen.getMap();
            Rectangle trigger = ((RectangleMapObject)map.getLayers().get("Trigger").getObjects().get("Viejo")).getRectangle();
            float width = soulKeeper.getWidth() * 100;
            float height = soulKeeper.getHeight() * 100;
            float x = soulKeeper.getX() * MyGdxGame.PPM;
            float y = soulKeeper.getY() * MyGdxGame.PPM;
            Rectangle rectangle = new Rectangle(x, y, width, height);
            if (Intersector.overlaps(trigger, rectangle)) {
                Gdx.app.log(TAG, "Overlap con el viejo");
            }
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        Vector2 vector2 = null;
        float currentY = getCurrentYDirection();
        float currentX = getCurrentXDirection();

        if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
            screen.setUp();
            soulKeeper.setRunningUp(false);
            if (currentY != -1f) {
                vector2 = new Vector2(currentX, 0);
                
            }
        }
        if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
            screen.setDown();
            soulKeeper.setRunningDown(false);
            if (currentY != 1f) {
                vector2 = new Vector2(currentX, 0);
            }
        }
        if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
            screen.setLeft();
            soulKeeper.setRunningLeft(false);
            if (currentX != 1f) {
                vector2 = new Vector2(0, currentY);
            }
        }
        if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
            screen.setRight();
            soulKeeper.setRunningRight(false);
            if (currentX != -1f) {
                vector2 = new Vector2(0, currentY);
            }
        }
        if(keycode == Input.Keys.P || keycode == Input.Keys.X)
        {
            soulKeeper.hit();
            vector2 = new Vector2(0, 0);
        }
        if(keycode == Input.Keys.Z || keycode == Input.Keys.O)
        {
            soulKeeper.defense();
            vector2 = new Vector2(0, 0);
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
