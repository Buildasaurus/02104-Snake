package com.snake.Model;

import java.util.Random;

import com.snake.Settings;

public class GameModel
{
    public Tile[][] board;

    private int rowCount;
    private int columnCount;
    private double speed;
    private Vector apple;
    private double acceleration = 0.1;
    private Snake snake;


    public GameModel()
    {
        this.rowCount = Settings.rowCount;
        this.columnCount = Settings.columnCount;

        board = new Tile[rowCount][columnCount];
        snake = new Snake(board, new Vector(rowCount / 2, columnCount / 2), new Vector(rowCount / 2 - 1, columnCount / 2), new Vector(1,0));
        speed = 2;
        Random randint = new Random();
        apple = new Vector(randint.nextInt(columnCount), randint.nextInt(rowCount));
    }



    public void nextState()
    {
        speed += acceleration;
        snake.updatePosition(board);
    }

    public void setDirection(Vector direction)
    {
        // TODO You can later add an int player in the input
        snake.setDirection(direction);
    }

    public boolean gameOver()
    {
        return !snake.isAlive();
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
        return snake.getSnakeLength();
    }
}
