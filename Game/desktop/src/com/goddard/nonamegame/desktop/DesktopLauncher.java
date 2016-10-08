package com.goddard.nonamegame.desktop;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.goddard.nonamegame.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Graphics.DisplayMode[] modes = LwjglApplicationConfiguration.getDisplayModes();
		Graphics.DisplayMode desktopMode = LwjglApplicationConfiguration.getDesktopDisplayMode();
		Graphics.DisplayMode displayMode = LwjglApplicationConfiguration.getDesktopDisplayMode();

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
config.setFromDisplayMode(displayMode);
		new LwjglApplication(new MainGame(), config);
	}
}
