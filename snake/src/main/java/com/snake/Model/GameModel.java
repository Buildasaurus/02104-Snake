package com.snake.Model;

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

        // TODO make code for dynamically iniatializing the players, in case there are n players.
        for (int i = 0; i < Settings.getGameSettings().getPlayerCount(); i++)
        {
            players[i] =
                    new Snake(board, midpoint.add(0, i), midpoint.add(-1, i), new Vector(1, 0), i);
        }

        Apple apple = new Apple();
        board[apple.getPosition().y][apple.getPosition().x] = apple;


        speed = Settings.getGameSettings().getSpeed();
        acceleration = Settings.getGameSettings().getAcceleration();

    }

    // TODO - seems to be buggy, when snake eats apple, both stop moving for a frame?
    public void nextState()
    {
        speed += acceleration;
        Fruit fruit = null;
        for (Snake player : players)
        {
            player.updatePosition(board);
            fruit = player.Fruiteaten();
            if (fruit != null)
                break;
        }
        if (fruit != null)
        {
            // TODO - fix this, so that when someone wins the game, it isn't an infinite looop, and
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
        boolean playerIsDead = false;
        for (Snake player : players)
        {
            if (!player.isAlive())
            {
                playerIsDead = true;
                break;
            }
        }

        return playerIsDead;
        // game over
    }

    public Tile[][] getBoard()
    {
        return board;
    }

    public double getSpeed()
    {
        return speed;
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
