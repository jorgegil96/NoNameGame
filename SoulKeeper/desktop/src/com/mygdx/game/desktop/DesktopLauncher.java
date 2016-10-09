package com.mygdx.game.desktop;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Graphics.DisplayMode[] modes = LwjglApplicationConfiguration.getDisplayModes();
		Graphics.DisplayMode displayMode = LwjglApplicationConfiguration.getDesktopDisplayMode();

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.setFromDisplayMode(displayMode);
		config.title="Soul Keeper";
		new LwjglApplication(new MyGdxGame(), config);
	}
}
