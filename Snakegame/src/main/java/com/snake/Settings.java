package com.snake;

import com.snake.Model.GameSettings;

public class Settings
{
    public static int windowHeight = 480;
    public static int windowWidth = 640;

    public static int rowCount = 100;
    public static int columnCount = 100;

    public static void setGameSettings(GameSettings settings)
    {
        gameSettings = settings;
    }

    public static GameSettings getGameSettings()
    {
        return gameSettings;
    }

    /**
     * Calculates the ratio between the rows and columns. 30 rows and 20 columns would return 1.5 as ratio.
     * @return The calculated ratio.
     */
    public static double getRowColumnRatio()
    {
        return columnCount/rowCount;
    }

    private static GameSettings gameSettings = new GameSettings();
}
