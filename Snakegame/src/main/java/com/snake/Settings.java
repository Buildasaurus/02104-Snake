package com.snake;

import com.snake.Model.GameSettings;

public class Settings
{
    public static int windowHeight = (int)Math.round(480*1.5);
    public static int windowWidth = (int)Math.round(640*1.5);

    public static int rowCount = 40;
    public static int columnCount = 40;

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
