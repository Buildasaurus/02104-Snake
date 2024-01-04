package com.snake.Model;

import com.snake.Settings;

public class Snake
{
    private Vector head;
    private Vector tail;
    private Vector lastDirection;
    private Vector direction;
    private int snakeLength = 2;
    private boolean snakeIsAlive = true;

    public Snake(Tile[][] board, Vector startHeadPosition, Vector startTailPosition,
            Vector startDirection)
    {
        head = startHeadPosition;
        tail = startTailPosition;
        direction = startDirection; // initializing direction as right

        board[head.y][head.x] = new SnakeTile(TileType.Snakehead, direction, direction);
        board[tail.y][tail.x] = new SnakeTile(TileType.Snaketail, direction, direction);
        lastDirection = new Vector(0, 0);
    }


    /**
     * Modifies the given board! Updates the position of the snake saved, and handles death. If the
     * snake dies it will store that, and for future
     */
    public void updatePosition(Tile[][] board)
    {
        Vector nextHeadPosition =
                head.add(direction).modulo(Settings.columnCount, Settings.columnCount);
        Vector tailDirection = ((SnakeTile) board[tail.y][tail.x]).targetDirection;
        Vector nextTailPosition =
                tail.add(tailDirection).modulo(Settings.columnCount, Settings.columnCount);

        Tile tileAtHead = board[nextHeadPosition.y][nextHeadPosition.x];
        if (tileAtHead == null)
        {
            // update head
            board[head.y][head.x] = new SnakeTile(TileType.Snakebody, lastDirection, direction);
            board[nextHeadPosition.y][nextHeadPosition.x] =
                    new SnakeTile(TileType.Snakehead, direction, direction);

            // update tail
            board[nextTailPosition.y][nextTailPosition.x] = new SnakeTile(TileType.Snaketail,
                    ((SnakeTile) board[nextTailPosition.y][nextTailPosition.x]).enterDirection,
                    ((SnakeTile) board[nextTailPosition.y][nextTailPosition.x]).targetDirection);
            board[tail.y][tail.x] = null;

            // save data for next time
            tail = nextTailPosition;
            head = nextHeadPosition;
            lastDirection = direction;
        }
        else if (tileAtHead instanceof Fruit)
        {
            // update head
            board[head.y][head.x] = new SnakeTile(TileType.Snakebody, lastDirection, direction);
            board[nextHeadPosition.y][nextHeadPosition.x] =
                    new SnakeTile(TileType.Snakehead, direction, direction);
            snakeLength += 1;
            // Tail shouldn't be updated.

            // Save data
            head = nextHeadPosition;
            lastDirection = direction;
        }
        else if (tileAtHead instanceof SnakeTile)
        {
            snakeIsAlive = false;
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

    /**
     * if inside board limit
     *
     * @param position
     * @return
     */
    private boolean isInRange(Vector position)
    {
        return position.x >= 0 && position.x < Settings.rowCount && position.y >= 0
                && position.y < Settings.columnCount;
    }

    public boolean isAlive()
    {
        return snakeIsAlive;
    }

    public int getSnakeLength()
    {
        return snakeLength;
    }
}
