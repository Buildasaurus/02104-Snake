package com.snake.Model;

import com.snake.Settings;

public class GameModel
{
    public Tile[][] board;

    private int rowCount;
    private int columnCount;
    private double speed;
    private double acceleration = 0.1;
    private Snake player1;
    private Snake player2;
    private Apple apple;
    private Fruit fruit;


    public GameModel()
    {
        this.rowCount = Settings.rowCount;
        this.columnCount = Settings.columnCount;
        Vector midpoint = new Vector(rowCount / 2, columnCount / 2);
        board = new Tile[rowCount][columnCount];
        player1 = new Snake(board, midpoint, midpoint.add(-1, 0), new Vector(1, 0));
        player2 = new Snake(board, midpoint.add(2), midpoint.add(2).add(-1, 0), new Vector(1, 0));

        apple = new Apple();
        board[apple.getapplePosition().y][apple.getapplePosition().x] = apple;

        speed = 2;
    }

    public void nextState()
    {
        speed += acceleration;
        player1.updatePosition(board);
        player2.updatePosition(board);
        fruit = player1.Fruiteaten() == null ? player2.Fruiteaten() : player1.Fruiteaten();
        if (fruit != null)
        {
            apple = new Apple();
            board[apple.getapplePosition().y][apple.getapplePosition().x] = apple;

        }
    }

    public void setDirection(Vector direction, int player)
    {
        // TODO You can later add an int player in the input
        if (player == 1)
        {
            player1.setDirection(direction);
        }
        else if (player == 2)
        {
            player2.setDirection(direction);
        }
    }

    public boolean gameOver()
    {
        return !player1.isAlive();
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

    public int getSnakeLength()
    {
        return player1.getSnakeLength();
    }
}
