package com.snake.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import com.snake.Settings;

public class GameModel
{
    public Tile[][] board;

    private int rowCount;
    private int columnCount;
    private double speed;
    private double acceleration = 0.01;
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


        speed = Settings.getGameSettings().getSpeed();
        acceleration = Settings.getGameSettings().getAcceleration();

    }

    // TODO - seems to be buggy, when snake eats apple, both stop moving for a frame?
    public void nextState()
    {
        if (speed < 10)
        {
            speed += acceleration;
        }
        // for syncronization, find any snakes colliding head on, and tell them they are colliding.
        // These snakes won't update.
        ArrayList<Vector> headPositions = new ArrayList<>();
        HashMap<String, Integer> nextHeadPositions = new HashMap<>();
        for (int i = 0; i < getPlayerCount(); i++)
        {
            Vector nextPosition = players[i].getNextHeadPosition();
            headPositions.add(nextPosition);
            if (nextHeadPositions.containsKey(nextPosition.toString()))
            {
                players[nextHeadPositions.get(nextPosition.toString())].isColliding = true;
                players[i].isColliding = true;
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

        Fruit fruit = null;
        for (Snake player : players)
        {
            player.updatePosition(board, willClear[player.playerNumber]);
            if (fruit == null)
                fruit = player.Fruiteaten();
        }
        if (fruit != null)
        {
            // TODO - fix this, so that when someone wins the game, it isn't an infinite loop, and
            // when they are close, it doesn't take forever
            Apple apple = new Apple();
            while (board[apple.getPosition().y][apple.getPosition().x] != null)
            {
                apple = new Apple();
            }
            board[apple.getPosition().y][apple.getPosition().x] = apple;
        }
    }

    public void setDirection(Vector direction, int player)
    {
        player = player % Settings.getGameSettings().getPlayerCount();
        if (player == 0)
        {
            players[0].setDirection(direction);
        }
        else if (player == 1)
        {
            players[1].setDirection(direction);
        }
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

    public double getSpeed()
    {
        return speed;
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
        if (player == 0)
        {
            return players[0].getSnakeLength();
        }
        else if (player == 1)
        {
            return players[1].getSnakeLength();
        }
        return -1;
    }

    public int getPlayerCount()
    {
        return players.length;
    }
    
}
