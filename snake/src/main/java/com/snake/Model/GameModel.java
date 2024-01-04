package com.snake.Model;

import com.snake.Settings;

public class GameModel
{
    public Tile[][] board;

    private int rowCount;
    private int columnCount;
    private double speed;
    private double acceleration = 0.1;
    private Snake[] players;


    public GameModel()
    {
        this.rowCount = Settings.rowCount;
        this.columnCount = Settings.columnCount;
        Vector midpoint = new Vector(rowCount / 2, columnCount / 2);
        board = new Tile[rowCount][columnCount];
        players = new Snake[2];
        players[0] = new Snake(board, midpoint, midpoint.add(-1, 0), new Vector(1, 0));
        players[1] =
                new Snake(board, midpoint.add(2), midpoint.add(2).add(-1, 0), new Vector(1, 0));

        Apple apple = new Apple();
        board[apple.getPosition().y][apple.getPosition().x] = apple;

        speed = 2;
    }

    //TODO - seems to be buggy, when snake eats apple, both stop moving for a frame?
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
                break;
            }
            board[apple.getPosition().y][apple.getPosition().x] = apple;
        }
    }

    public void setDirection(Vector direction, int player)
    {
        // TODO You can later add an int player in the input
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
        // TODO You can later add an int player in the input
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
