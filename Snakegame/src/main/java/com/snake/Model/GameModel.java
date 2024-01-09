package com.snake.Model;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import com.snake.Settings;

public class GameModel
{
    /**
     * The board of snake, of size rowcount and columncount stored in the {@link com.snake.Settings
     * Settings}.
     */
    public Tile[][] board;

    private int rowCount;
    private int columnCount;
    private Snake[] players;

    /**
     * Imports the current settings from {@link com.snake.Settings Settings} and initializes a board
     * with tiles, and initializes the players. Also spawns random {@link com.snake.Model.Fruit
     * fruits} on the level.
     */
    public GameModel()
    {
        this.rowCount = Settings.rowCount;
        this.columnCount = Settings.columnCount;
        Vector midpoint = new Vector(columnCount / 2, rowCount / 2);
        board = new Tile[rowCount][columnCount];
        players = new Snake[Settings.getGameSettings().getPlayerCount()];

        for (int i = 0; i < Settings.getGameSettings().getPlayerCount(); i++)
        {
            players[i] = new Snake(board, midpoint.add(0, i * 2), midpoint.add(-1, i * 2),
                    new Vector(1, 0), i);
        }

        // level generation
        LevelGenerator.generateLevel(board);
        Apple apple = new Apple();
        while (board[apple.getPosition().y][apple.getPosition().x] != null)
        {
            apple.setRandomPosition();
        }
        board[apple.getPosition().y][apple.getPosition().x] = apple;

        Cherry cherry = new Cherry();
        while (board[cherry.getPosition().y][cherry.getPosition().x] != null)
        {
            cherry.setRandomPosition();
        }
        board[cherry.getPosition().y][cherry.getPosition().x] = cherry;
    }

    public GameModel(GameState gameState)
    {
        this.rowCount = Settings.rowCount;
        this.columnCount = Settings.columnCount;

        board = gameState.getBoard();
        players = gameState.getPlayers();
    }

    /**
     * Updates the board to the next state. Only updates the snakes whose id is in the
     * playersToUpdate
     *
     * @param playersToUpdate
     */
    public void nextState(ArrayList<Integer> playersToUpdate)
    {
        // for syncronization, find any snakes colliding head on, and tell them they are colliding.
        // These snakes won't update.
        Snake[] snakesToUpdate = new Snake[playersToUpdate.size()];
        int j = 0;
        // Gather the correct pointers for later use
        for (int playerID : playersToUpdate)
        {
            snakesToUpdate[j] = players[playerID];
            j++;
        }
        ArrayList<Vector> headPositions = new ArrayList<>();
        HashMap<String, Integer> nextHeadPositions = new HashMap<>();
        for (int i = 0; i < playersToUpdate.size(); i++)
        {
            Vector nextPosition = snakesToUpdate[i].getNextHeadPosition();
            headPositions.add(nextPosition);
            if (nextHeadPositions.containsKey(nextPosition.toString()))
            {
                snakesToUpdate[nextHeadPositions.get(nextPosition.toString())].isColliding = true;
                snakesToUpdate[i].isColliding = true;
            }
            else
            {
                nextHeadPositions.put(nextPosition.toString(), i);
            }
        }
        // Now we check whether the next position will be clear, in the next frame.
        // This the snakes won't know, as there might be a snake tail that disappears, or that
        // doesn't
        // disappear, if the snake eats an apple, or dies, or is colliding.
        boolean[] willClear = new boolean[players.length];
        int i = 0;
        for (Vector vec : headPositions)
        {
            willClear[i] = true;
            // essentially looping through the playerss
            Tile tile = board[vec.y][vec.x];
            if (tile instanceof SnakeTile)
            {
                SnakeTile snakeTile = ((SnakeTile) tile);
                if (!(snakeTile.tileType == TileType.Snaketail)
                        || (players[snakeTile.assignedPlayer].isColliding
                                || players[snakeTile.assignedPlayer].willGrow(board))
                        || !playersToUpdate.contains(snakeTile.assignedPlayer))
                {
                    System.out.println(
                            snakeTile.tileType + " " + players[snakeTile.assignedPlayer].isColliding
                                    + "  " + players[snakeTile.assignedPlayer].willGrow(board));
                    willClear[i] = false;
                }
            }
            i++;
        }
        ArrayList<Fruit> fruitsToRespawn = new ArrayList<>();
        j = 0;
        for (Snake snake : snakesToUpdate)
        {
            if (snake.isAlive())
            {
                snake.calculateNextFrame(willClear[j]);
                if (snake.Fruiteaten() != null)
                    fruitsToRespawn.add(snake.Fruiteaten());
            }
            else
            {
                System.out.println(snake.playerNumber);
            }
            j++;
        }
        for (Fruit fruit : fruitsToRespawn)
        {
            // TODO - fix this, so that when someone wins the game, it isn't an infinite loop,
            // and when they are close, it doesn't take forever

            try
            { // cursed code from
              // https://stackoverflow.com/questions/5533702/instantiating-object-of-same-class-from-within-class-in-java
                Constructor constructor = fruit.getClass().getConstructor();
                Fruit piece = (Fruit) constructor.newInstance();
                while (board[piece.getPosition().y][piece.getPosition().x] != null)
                {
                    piece.setRandomPosition();
                }
                board[piece.getPosition().y][piece.getPosition().x] = piece;
            }
            catch (Exception e)
            {
                System.out.println(e);
                System.out.println("Your constructor stuff in gamemodel doesn't work...");
            }
        }
    }

    public void setDirection(Vector direction, int player)
    {
        player = player % Settings.getGameSettings().getPlayerCount();
        players[player].setDirection(direction);
    }

    public boolean gameOver()
    {
        // Single player
        if (players.length == 1)
        {
            return !players[0].isAlive();
        }
        // multiplayer
        else if (players.length > 1)
        {
            if (getAlivePlayerCount() < 2)
            {
                return true;
            }
            return false;
        }

        return true; // Neither multiplayer or singleplayer?? Should not happen
    }

    public Tile[][] getBoard()
    {
        return board;
    }

    public double getSpeed(int playerIndex)
    {
        return players[playerIndex].getSpeed();
    }

    public int getAlivePlayerCount()
    {
        int alivePlayerCount = players.length;
        for (Snake player : players)
        {
            if (!player.isAlive())
            {
                alivePlayerCount -= 1;
            }
        }

        return alivePlayerCount;
    }

    public int getSnakeLength(int player)
    {
        player = player % Settings.getGameSettings().getPlayerCount();
        return players[player].getSnakeLength();
    }

    public int getPlayerCount()
    {
        return players.length;
    }

}
