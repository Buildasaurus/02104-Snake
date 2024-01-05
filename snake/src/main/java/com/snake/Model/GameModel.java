package com.snake.Model;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import com.snake.Settings;

public class GameModel
{
    public Tile[][] board;

    private int rowCount;
    private int columnCount;
    private Snake[] players;

    public GameModel()
    {
        this.rowCount = Settings.rowCount;
        this.columnCount = Settings.columnCount;
        Vector midpoint = new Vector(rowCount / 2, columnCount / 2);
        board = new Tile[rowCount][columnCount];
        players = new Snake[Settings.getGameSettings().getPlayerCount()];

        for (int i = 0; i < Settings.getGameSettings().getPlayerCount(); i++)
        {
            players[i] = new Snake(board, midpoint.add(0, i * 2), midpoint.add(-1, i * 2),
                    new Vector(1, 0), i);
        }

        Apple apple = new Apple();
        board[apple.getPosition().y][apple.getPosition().x] = apple;

        Cherry cherry = new Cherry();
        board[cherry.getPosition().y][cherry.getPosition().x] = cherry;
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
                                || players[snakeTile.assignedPlayer].willGrow(board)))
                {
                    willClear[i] = false;
                }
            }
            i++;
        }

        ArrayList<Fruit> frutesToRespawn = new ArrayList<>();
        for (Snake snake : snakesToUpdate)
        {
            if (snake.isAlive())
            {
                snake.calculateNextFrame(willClear[snake.playerNumber]);
                if (snake.Fruiteaten() != null)
                    frutesToRespawn.add(snake.Fruiteaten());
            }
        }
        for (Fruit fruit : frutesToRespawn)
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
