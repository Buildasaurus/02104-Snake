package com.snake.Model;

import java.util.Random;

public class GameModel
{
    private Tile[][] board;
    private Vector direction;
    private int rowCount;
    private int columnCount;
    private int speed;
    Vector head;
    Vector tail;
    Vector appleposition;
    boolean gameover = false;


    public GameModel(int rowCount, int columnCount)
    {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        head = new Vector(rowCount / 2, columnCount / 2); // snake start position
        tail = new Vector(rowCount / 2 - 1, columnCount / 2);
        direction = new Vector(1, 0); // initializing direction as right
        board = new Tile[rowCount][columnCount];

        board[head.y][head.x] = new Tile(TileType.Snakehead, direction, direction);
        board[tail.y][tail.x] = new Tile(TileType.Snaketail, direction, direction);
        speed = 2;
        appleposition = newAppleposition();
        
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
        Vector nextHeadPosition = head.add(direction);
        Vector tailDirection = board[tail.y][tail.x].targetDirection;
        Vector nextTailPosition = tail.add(tailDirection);

        Vector previousDirection = board[head.y][head.x].targetDirection;
        if (isInRange(nextHeadPosition))
        {
            // opdatere hoved
            board[head.y][head.x] = new Tile(TileType.Snakebody, previousDirection, direction);
            board[nextHeadPosition.y][nextHeadPosition.x] = new Tile(TileType.Snakehead, direction, direction);

            if (board[nextHeadPosition.y][nextHeadPosition.x] == null) {
                // opdatere hale
                board[nextTailPosition.y][nextTailPosition.x] = new Tile(TileType.Snaketail, board[nextTailPosition.y][nextTailPosition.x].enterDirection, board[nextTailPosition.y][nextTailPosition.x].targetDirection);
                board[nextTailPosition.y][nextTailPosition.x] = new Tile(TileType.Snaketail,
                        board[nextTailPosition.y][nextTailPosition.x].enterDirection,
                        board[nextTailPosition.y][nextTailPosition.x].targetDirection);
                board[tail.y][tail.x] = null;
                tail = nextTailPosition;
                head = nextHeadPosition;
            }
            if (board[nextHeadPosition.y][nextHeadPosition.x] != null && board[nextHeadPosition.y][nextHeadPosition.x].tileType != TileType.Apple) {
                gameOver();
            }
            if (board[nextHeadPosition.y][nextHeadPosition.x].tileType == TileType.Apple){
                appleposition = newAppleposition();
            }
            if (board[nextHeadPosition.y][nextHeadPosition.x] != null && board[nextHeadPosition.y][nextHeadPosition.x].tileType != TileType.Apple) {
                gameOver();
            }
            if (board[nextHeadPosition.y][nextHeadPosition.x].tileType == TileType.Apple){
                appleposition = newAppleposition();
            }
        }
        else
        {
            gameOver();
        }

    }

    public void setDirection(Vector direction)
    {
        if (direction.x == -this.direction.x || direction.y == -this.direction.y)
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

    public int getSpeed()
    {
        return speed;
    }
    public Vector newAppleposition()
    {  
        Vector newappleposition = new Vector();
        Random randint = new Random();
        newappleposition.x = randint.nextInt(columnCount);
        newappleposition.y = randint.nextInt(rowCount);
        new Tile(TileType.Apple, appleposition, appleposition);
        return newappleposition;
    }

}
