package com.snake.Model;

import java.util.Random;
import com.snake.Settings;

public class LevelGenerator
{
    /**
     * Generates walls around the level. Will always have a clear 8x8 square in the middle
     *
     * @param board The board to generate the level on. Will modify this board.
     */

    static int width;
    static int height;

    public static void generateLevel(Tile[][] board)
    {
        // Early return if board is too small
        if (board.length < 1 || (board.length < 8 && board[0].length < 8))
        {
            return;
        }
        height = board.length;
        width = board[0].length;
        double fill = Settings.getGameSettings().getLevelFill();
        boolean[][] map = generateMap(fill);
        simplifyNoise(map);
        connectIslands(map);
        // illegal squares, that are to be ignored
        // TODO for now just assuming height 20, width 20. Do maths later.
        Vector illegalXVector = new Vector(7, 13);
        Vector illegalYVector = new Vector(7, 13);

        for (int rowCount = 0; rowCount < height; rowCount++)
        {
            for (int columnCount = 0; columnCount < width; columnCount++)
            {
                if (!(isInInterval(rowCount, illegalYVector)
                        && isInInterval(columnCount, illegalXVector)) && map[rowCount][columnCount])
                {
                    board[rowCount][columnCount] = new Wall();
                }
            }
        }
        // Generate noise, use this for walls.
    }

    /**
     *
     * @param fillValue a value between 0 and 1, where 1 is all are filled, 0 is none.
     * @return
     */
    private static boolean[][] generateMap(double fillValue)
    {
        boolean[][] randomMap = new boolean[width][height];
        Random rand = new Random();
        for (int rowCount = 0; rowCount < height; rowCount++)
        {
            for (int columnCount = 0; columnCount < width; columnCount++)
            {
                double randomNumber = rand.nextDouble();
                randomMap[rowCount][columnCount] = randomNumber < fillValue;
            }
        }


        return randomMap;
    }

    private static void simplifyNoise(boolean[][] map)
    {
        // TODO write this, to make map more smooth.
    }

    private static void connectIslands(boolean[][] map)
    {
        // TODO write this, to make map more smooth.
    }

    private static boolean isInInterval(int value, Vector interval)
    {
        return value > interval.x && value < interval.y;
    }
}
