package com.snake.Model;

import java.util.Timer;

public class GameModel {
    Tile[][] board;
    Vector direction;
    Timer time;
    Vector snakePosition; // keep track of position
    int rowCount;
    int columnCount;

    public GameModel(int rowCount, int columnCount) {

        this.rowCount = rowCount;
        this.columnCount = columnCount;
        snakePosition = new Vector(row/2, column/2); //snake start position
        direction = new Vector(rowCount, columnCount); //initializing direction

        boolean startSquare = false;
    }
    void updateDirection(Vector direction)
    {
        this.direction = direction;
    }
    /**
     * if inside board limit
     * @param position
     * @return
     */
    private boolean isValidMove(Vector position) 
    {
        return position.x >= 0 && position.x < rowCount &&
               position.y >= 0 && position.y < columnCount;
    }
    public Vector nextState()
    {
        Vector nextPosition = snakePosition.add(direction);

        if (isValidMove(nextPosition)) {
            return nextPosition();
        }

        if (isValidMove(nextPosition)) 
        {
            snakePosition = nextPosition;
        }
        else 
        {
            return null;
        }
    }
    
    public void setDirection(Vector direction)
    {
        this.direction = direction;
    }

}
