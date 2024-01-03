package com.snake.Model;

import java.util.Random;

import com.snake.Settings;

public class GameModel
{
    public Tile[][] board;
    private Vector lastDirection;
    private Vector direction;
    private int rowCount;
    private int columnCount;
    private double speed;
    private Vector head;
    private Vector tail;
    private int snakeLength = 2;
    private Vector apple;
    private boolean gameover = false;
    private double acceleration = 0.1;


    public GameModel()
    {
        this.rowCount = Settings.rowCount;
        this.columnCount = Settings.columnCount;
        head = new Vector(rowCount / 2, columnCount / 2); // snake start position
        tail = new Vector(rowCount / 2 - 1, columnCount / 2);
        direction = new Vector(1, 0); // initializing direction as right
        board = new Tile[rowCount][columnCount];

        board[head.y][head.x] = new SnakeTile(TileType.Snakehead, direction, direction);
        board[tail.y][tail.x] = new SnakeTile(TileType.Snaketail, direction, direction);
        speed = 2;
        Random randint = new Random();
        apple = new Vector(randint.nextInt(columnCount), randint.nextInt(rowCount));
        lastDirection = new Vector(0, 0);
    }

    /**
     * if inside board limit
     *
     * @param position
     * @return
     */
    private boolean isInRange(Vector position)
    {
        return position.x >= 0 && position.x < rowCount && position.y >= 0
                && position.y < columnCount;
    }

    public void nextState()
    {
        speed += acceleration;
        Vector nextHeadPosition = head.add(direction);
        Vector tailDirection = ((SnakeTile) board[tail.y][tail.x]).targetDirection;
        Vector nextTailPosition = tail.add(tailDirection);

        nextHeadPosition = nextHeadPosition.modulo(Settings.columnCount, Settings.columnCount);
        nextTailPosition = nextTailPosition.modulo(Settings.columnCount, Settings.columnCount);

        Vector previousDirection = ((SnakeTile) board[head.y][head.x]).targetDirection;

        if (board[nextHeadPosition.y][nextHeadPosition.x] == null)
        {
            board[head.y][head.x] = new SnakeTile(TileType.Snakebody, previousDirection, direction);
            // opdatere hoved
            board[nextHeadPosition.y][nextHeadPosition.x] =
                    new SnakeTile(TileType.Snakehead, direction, direction);

            // opdatere hoved
            board[head.y][head.x] = new SnakeTile(TileType.Snakebody, previousDirection, direction);
            board[nextHeadPosition.y][nextHeadPosition.x] =
                    new SnakeTile(TileType.Snakehead, direction, direction);
            // opdatere hale
            board[nextTailPosition.y][nextTailPosition.x] = new SnakeTile(TileType.Snaketail,
                    ((SnakeTile) board[nextTailPosition.y][nextTailPosition.x]).enterDirection,
                    ((SnakeTile) board[nextTailPosition.y][nextTailPosition.x]).targetDirection);
            board[tail.y][tail.x] = null;
            tail = nextTailPosition;
            head = nextHeadPosition;
            lastDirection = direction;
        }
    }

    public void setDirection(Vector direction)
    {
        if (direction.x == -this.lastDirection.x || direction.y == -this.lastDirection.y)
        {
            return;
        }
        this.direction = direction;
    }

    public boolean gameOver()
    {
        return gameover;
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
        return snakeLength;
    }
}
