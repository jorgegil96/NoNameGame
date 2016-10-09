package com.goddard.nonamegame.Characters;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends Sprite{
    float speed = 100.0f; // 10 pixels per second.
    float posX, posY;

    public Player(Texture texture) {
        super(texture);
        posX = 0;
        posY = 0;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public void moveX(float delta) {
        posX += delta * speed;
    }

    public void moveY(float delta) {
        posY += delta * speed;
    }
}
