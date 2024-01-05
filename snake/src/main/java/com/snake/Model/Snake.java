package com.snake.Model;

import com.snake.Settings;

public class Snake
{
    private Vector head;
    private Vector tail;
    private Vector lastDirection;
    private Vector tailDirection;
    private Vector direction;
    private int snakeLength = 2;
    private boolean snakeIsAlive = true;
    private Fruit fruit;
    public int playerNumber;
    public boolean isColliding = false;
    private double speed;

    public Snake(Tile[][] board, Vector startHeadPosition, Vector startTailPosition,
            Vector startDirection, int player)
    {
        head = startHeadPosition;
        tail = startTailPosition;
        direction = startDirection; // initializing direction as right
        this.playerNumber = player;
        board[head.y][head.x] = new SnakeTile(TileType.Snakehead, direction, direction, player);
        board[tail.y][tail.x] = new SnakeTile(TileType.Snaketail, direction, direction, player);
        lastDirection = startDirection;
        tailDirection = startDirection;
        speed = 2;
    }


    /**
     * Modifies the given board! Updates the position of the snake saved, and handles death. If the
     * snake dies it will store that, and for future
     */
    public void updatePosition(Tile[][] board, boolean willClear)
    {
        if (isColliding || !willClear)
        {
            snakeIsAlive = false;
            return;
        }
        Vector nextHeadPosition =
                head.add(direction).modulo(Settings.columnCount, Settings.columnCount);
        Vector nextTailPosition =
                tail.add(tailDirection).modulo(Settings.columnCount, Settings.columnCount);

        Tile tileAtHead = board[nextHeadPosition.y][nextHeadPosition.x];
        if (tileAtHead == null)
        {
            updateSnakePosition(board, nextHeadPosition, nextTailPosition);
        }
        else if (tileAtHead instanceof Fruit)
        {
            // update head
            board[head.y][head.x] =
                    new SnakeTile(TileType.Snakebody, lastDirection, direction, playerNumber);
            board[nextHeadPosition.y][nextHeadPosition.x] =
                    new SnakeTile(TileType.Snakehead, direction, direction, playerNumber);
            fruit = (Fruit) tileAtHead;

            snakeLength += 1;
            // Tail shouldn't be updated.

            // Save data
            head = nextHeadPosition;
            lastDirection = direction;
        }
        else if (tileAtHead instanceof SnakeTile)
        {
            // We can do this, because we assume that the "willClear" variable is correct
            // and if there is a snaketile on the next tile, we know it will disappear, and can just
            // overwrite it.
            updateSnakePosition(board, nextHeadPosition, nextTailPosition);
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
     * Assumes that the nextTailPosition is a snaketile.
     *
     * @param board
     * @param nextHeadPosition
     * @param nextTailPosition
     */
    void updateSnakePosition(Tile[][] board, Vector nextHeadPosition,
            Vector nextTailPosition)
    {
        // update old head
        board[head.y][head.x] =
                new SnakeTile(TileType.Snakebody, lastDirection, direction, playerNumber);


        // update tail
        board[nextTailPosition.y][nextTailPosition.x] = new SnakeTile(TileType.Snaketail,
                ((SnakeTile) board[nextTailPosition.y][nextTailPosition.x]).enterDirection,
                ((SnakeTile) board[nextTailPosition.y][nextTailPosition.x]).targetDirection,
                playerNumber);
        // only remove tail, if it actually is a tail.
        if (((SnakeTile) board[tail.y][tail.x]).tileType == TileType.Snaketail)
        {
            board[tail.y][tail.x] = null;
        }


        // update new head
        board[nextHeadPosition.y][nextHeadPosition.x] =
                new SnakeTile(TileType.Snakehead, direction, direction, playerNumber);

        // save data for next time
        tail = nextTailPosition;
        head = nextHeadPosition;
        lastDirection = direction;
        tailDirection = ((SnakeTile) board[nextTailPosition.y][nextTailPosition.x]).targetDirection;
        fruit = null;
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

    public Vector getNextHeadPosition()
    {
        if (snakeIsAlive)
        {
            return head.add(direction).modulo(Settings.columnCount, Settings.rowCount);
        }
        else
        {
            return head;
        }
    }

    public boolean isAlive()
    {
        return snakeIsAlive;
    }

    public int getSnakeLength()
    {
        return snakeLength;
    }

    /**
     * Returns if the snake thinks it will grow next frame. Takes into consideration, whether it
     * might collide, in which case it won't grow.
     *
     * @param board
     * @return
     */
    public boolean willGrow(Tile[][] board)
    {
        Vector nextHeadPosition = getNextHeadPosition();
        return board[nextHeadPosition.y][nextHeadPosition.x] instanceof Fruit && !isColliding;
    }

    public Fruit Fruiteaten()
    {
        return fruit;
    }

    public double getSpeed()
    {
        return speed;
    }

    public void setSpeed(double speed)
    {
        this.speed = speed;
    }
}
