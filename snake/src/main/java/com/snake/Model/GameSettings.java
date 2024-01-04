package com.snake.Model;

public class GameSettings
{

    private int maxPlayerCount = 2;
    private GameMode gameMode;
    private Level level;
    private int playerCount;

    public GameSettings()
    {
        playerCount = 1;
        gameMode = GameMode.Normal;
        level = Level.Empty;
    }

    /**
     * GameMode can be a variant of different things. What changes is e.g. the acceleration of the
     * snake.
     */
    private enum GameMode
    {
        Easy, Normal, Hard, Insane
    }

    /**
     * This can be extended with eg "block in the middle", "racetrack" or "maze"
     */
    private enum Level
    {
        Empty
    }

    /**
     * sets the player count if the given player count is valid, and can be handled.
     *
     * @return Returns if the update was succesful
     */
    public boolean setPlayerCount(int newPlayerCount)
    {
        if (playerCount > 0 && playerCount < 3)
        {
            playerCount = newPlayerCount;
            return true;
        }
        return false;
    }

    public int getPlayerCount()
    {
        return playerCount;
    }

    public int[] getAllPossiblePlayerCounts()
    {
        int[] playerCounts = new int[maxPlayerCount];
        for (int i = 1; i <= maxPlayerCount; i++)
        {
            playerCounts[i - 1] = i;
        }
        return playerCounts;
    }

    public Level getLevel()
    {
        return level;
    }

    public void setLevel(Level level)
    {
        this.level = level;
    }

    public GameMode getGameMode()
    {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode)
    {
        this.gameMode = gameMode;
    }


}
