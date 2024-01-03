package com.snake.Model;

public class GameModel
{
    private Tile[][] board;
    private Vector direction;
    private Vector snakePosition; // keep track of position
    private int rowCount;
    private int columnCount;
    private int speed;

    public GameModel(int rowCount, int columnCount)
    {

        this.rowCount = rowCount;
        this.columnCount = columnCount;
        snakePosition = new Vector(rowCount / 2, columnCount / 2); // snake start position
        direction = new Vector(1, 0); // initializing direction as right
        speed = 2;

        boolean startSquare = false;
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
        Vector nextPosition = snakePosition.add(direction);

        if (isInRange(nextPosition))
        {
            snakePosition = nextPosition;
            // Implement rest of next state logic, as we won't get out of bounds here.
        }
    }

    public void setDirection(Vector direction)
    {
        this.direction = direction;
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
