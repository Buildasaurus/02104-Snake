package com.snake;

import com.snake.Model.GameSettings;

public class Settings
{
    public static int windowHeight = 480;
    public static int windowWidth = 640;

    public static int rowCount = 30;
    public static int columnCount = 30;

    public static void setGameSettings(GameSettings settings)
    {
        gameSettings = settings;
    }

    public static GameSettings getGameSettings()
    {
        return gameSettings;
    }

    private static GameSettings gameSettings = new GameSettings();
}
