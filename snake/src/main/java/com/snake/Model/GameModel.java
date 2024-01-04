package com.snake.Model;

import com.snake.Settings;

public class GameModel
{
    public Tile[][] board;

    private int rowCount;
    private int columnCount;
    private double speed;
    private double acceleration = 0.1;
    private Snake snake;
    private Apple apple;
    private Fruit fruit;


    public GameModel()
    {
        this.rowCount = Settings.rowCount;
        this.columnCount = Settings.columnCount;

        board = new Tile[rowCount][columnCount];
        snake = new Snake(board, new Vector(rowCount / 2, columnCount / 2),
                new Vector(rowCount / 2 - 1, columnCount / 2), new Vector(1, 0));

        apple = new Apple();
        board[apple.getapplePosition().y][apple.getapplePosition().x] = apple;
    
        speed = 2;
    }

    public void nextState()
    {
        speed += acceleration;
        snake.updatePosition(board);
        fruit = snake.Fruiteaten();
        if (fruit != null){
            apple = new Apple();
            board[apple.getapplePosition().y][apple.getapplePosition().x] = apple;

        }
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
