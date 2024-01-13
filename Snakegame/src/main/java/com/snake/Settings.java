package com.snake;

import com.snake.Model.GameSettings;

public class Settings
{
    public static int windowHeight = (int) Math.round(1417 / 1.5);
    public static int windowWidth = (int) Math.round(1890 / 1.5);

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
