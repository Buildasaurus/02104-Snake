package com.snake.Model;

import com.snake.Settings;
import com.snake.Utils.Resources;

// Made by Jonathan Sommerlund

public class Snake
{
    private Vector head;
    private Vector tail;
    private Vector lastDirection;
    private Vector tailDirection;
    private Vector direction;
    private int snakeLength = 2;
    private boolean snakeIsAlive = true;
    public transient Fruit fruit;
    public int playerNumber;
    public boolean isColliding = false;
    private double speed;
    public transient Tile[][] board;

    public Snake(Tile[][] board, Vector startHeadPosition, Vector startTailPosition,
            Vector startDirection, int player)
    {
        this.board = board;
        head = startHeadPosition;
        tail = startTailPosition;
        direction = startDirection; // initializing direction as right
        this.playerNumber = player;
        board[head.y][head.x] = new SnakeTile(TileType.Snakehead, direction, direction, player);
        board[tail.y][tail.x] = new SnakeTile(TileType.Snaketail, direction, direction, player);
        lastDirection = startDirection;
        tailDirection = startDirection;
        speed = Settings.getGameSettings().getSpeed();
    }


    /**
     * Modifies the given board! Updates the position of the snake saved, and handles death. If the
     * snake dies it will store that, and for future
     *
     * @param willClear If willClear is false, the snake will die, else if there is a snake on the
     *        next tile, it is assumed it will disappear
     */
    public void calculateNextFrame(boolean willClear)
    {
        fruit = null;
        if (!snakeIsAlive)
        {
            return;
        }
        if (isColliding || !willClear)
        {
            killSnake();
            return;
        }
        Vector nextHeadPosition = getNextHeadPosition();
        Tile tileAtHead = board[nextHeadPosition.y][nextHeadPosition.x];

        if (tileAtHead == null)
        {
            updateSnakePosition();
        }
        else if (tileAtHead instanceof Fruit)
        {
            ((Fruit) tileAtHead).doEffect(this);
            fruit = (Fruit) tileAtHead;
            ((Fruit) tileAtHead).playSound();
        }
        else if (tileAtHead instanceof SnakeTile)
        {
            // We can do this, because we assume that the "willClear" variable is correct
            // and if there is a snaketile on the next tile, we know it will disappear, and can just
            // overwrite it.
            updateSnakePosition();
        }
        else if (tileAtHead instanceof Wall)
        {
            killSnake();
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

    public Vector getDirection()
    {
        return direction;
    }

    /**
     * Kills the snake, and does relevant logic in this regard.
     */
    private void killSnake()
    {
        System.out.printf("Snake %d just died.", playerNumber);
        System.out.println("Is colliding: " + isColliding);
        Resources.playSoundByName("DeathSound", 1.0);
        snakeIsAlive = false;
    }

    /**
     * updates the position of the snake on the board, if the snake is alive
     */
    public void updateSnakePosition()
    {
        if (!snakeIsAlive)
        {
            return;
        }
        Vector nextHeadPosition =
                head.add(direction).modulo(Settings.getGameSettings().getColumnCount(),
                        Settings.getGameSettings().getRowCount());
        Vector nextTailPosition =
                tail.add(tailDirection).modulo(Settings.getGameSettings().getColumnCount(),
                        Settings.getGameSettings().getRowCount());
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
    }

    public Vector getNextHeadPosition()
    {
        if (snakeIsAlive)
        {
            Vector nextPosition =
                    head.add(direction).modulo(Settings.getGameSettings().getColumnCount(),
                            Settings.getGameSettings().getRowCount());
            if (board[nextPosition.y][nextPosition.x] instanceof Wall)
            {
                isColliding = true;
            }
            return nextPosition;

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

    /**
     * Will update the snake on the board stored, such that the head moves, but the tail stays, and
     * also add 1 to the snakeLength field stored
     */
    public void grow()
    {
        Vector nextHeadPosition = getNextHeadPosition();


        // update head
        board[head.y][head.x] =
                new SnakeTile(TileType.Snakebody, lastDirection, direction, playerNumber);
        board[nextHeadPosition.y][nextHeadPosition.x] =
                new SnakeTile(TileType.Snakehead, direction, direction, playerNumber);

        snakeLength += 1;
        // Tail shouldn't be updated.

        // Save data
        head = nextHeadPosition;
        lastDirection = direction;
    }

    public int getSnakeLength()
    {
        return snakeLength;
    }

    public void setSnakeLenght(int length)
    {
        this.snakeLength = length;
    }

    /**
     * Returns if the snake thinks it will grow next frame. Takes into consideration, whether it
     * might collide, in which case it won't grow.
     *
     * @return A boolean that is true if the snake grows
     */
    public boolean willGrow()
    {
        Vector nextHeadPosition = getNextHeadPosition();
        return board[nextHeadPosition.y][nextHeadPosition.x] instanceof Apple && !isColliding;
    }

    /**
     * @return If the snake has eaten a fruit the last frame this will be returned. Else null
     */
    public Fruit Fruiteaten()
    {
        return fruit;
    }

    public double getSpeed()
    {
        return speed;
    }

    /**
     * Will set the speed to the value given, if it is not lower than the minimum speed. Else it is
     * set to the minimum
     *
     * @param speed The new speed
     */
    public void setSpeed(double speed)
    {
        this.speed = speed < Settings.getGameSettings().getSpeed()
                ? Settings.getGameSettings().getSpeed()
                : speed;
    }

    public Vector getHeadPosition()
    {
        return head;
    }

    public void setHeadPosition(Vector head)
    {
        this.head = head;
    }

    public Vector getTailPosition()
    {
        return tail;
    }

    public Vector getNextTailPosition()
    {
        if (snakeIsAlive)
        {
            return tail.add(tailDirection).modulo(Settings.getGameSettings().getColumnCount(),
                    Settings.getGameSettings().getRowCount());
        }
        else
        {
            return tail;
        }
    }

    public void setTailDirectionPosition(Vector direction)
    {
        this.tailDirection = direction;
    }

    public Vector getTailDirection()
    {
        return tailDirection;
    }

    public void setTailDirection(Vector tailDirection)
    {
        this.tailDirection = tailDirection;
    }

    public Vector getLastDirection()
    {
        return lastDirection;
    }

    public void setLastDirection(Vector lastDirection)
    {
        this.lastDirection = lastDirection;
    }

    public void setBoard(Tile[][] board)
    {
        this.board = board;
    }
}
