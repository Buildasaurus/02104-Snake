package com.snake.Model;

public class GameModel
{
    private Tile[][] board;
    private Vector direction;
    private int rowCount;
    private int columnCount;
    private int speed;
    Vector head;
    Vector tail;


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
    }

    void updateDirection(Vector direction)
    {
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
            // Implement rest of next state logic, as we won't get out of bounds here.
            if (board[nextHeadPosition.y][nextHeadPosition.x] == null)
            {
                board[head.y][head.x] = new Tile(TileType.Snakebody, previousDirection, direction);
                // opdatere hoved
                board[nextHeadPosition.y][nextHeadPosition.x] =
                        new Tile(TileType.Snakehead, direction, direction);

                // opdatere hale
                board[nextTailPosition.y][nextTailPosition.x] =
                        new Tile(TileType.Snaketail, board[tail.y][tail.x].enterDirection,
                                board[tail.y][tail.x].targetDirection);
                board[tail.y][tail.x] = null;
            }
            else
            { // loses if you hit an apple?
                gameOver();
            }

        }
        else
        {
            gameOver();
        }
        tail = nextTailPosition;
        head = nextHeadPosition;
    }

    public void setDirection(Vector direction)
    {
        this.direction = direction;
    }

    public void gameOver()
    {
        System.out.println("gameover");
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
}
