package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/**
 * Classe que iniciará o jogo.
 */
public class DesktopLauncher {
    /**
     * Método que será chamado ao rodar o projeto.
     *
     * @param arg (String[])
     */
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
        config.setForegroundFPS(60);
        config.setTitle("HyperShapes");
        config.setWindowIcon("iconPatinho.png");
        new Lwjgl3Application(new HyperShapes(), config);
    }
}
