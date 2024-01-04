package com.snake;

import com.snake.Model.GameSettings;

public class Settings
{
    public static int windowHeight = 700;
    public static int windowWidth = 700;

    public static int rowCount = 20;
    public static int columnCount = 20;

    public static void setGameSettings(GameSettings settings)
    {
        gameSettings = settings;
    }

    public static GameSettings getGameSettings()
    {
        return gameSettings;
    }

    private static GameSettings gameSettings;
}
