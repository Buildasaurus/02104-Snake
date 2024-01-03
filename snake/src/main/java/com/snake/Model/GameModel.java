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
        Vector head = new Vector(rowCount/2, columnCount/2); //snake start position
        Vector tail = new Vector(rowCount/2, columnCount/2 + 1);
        direction = new Vector(1, 0); //initializing direction as right

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
    private boolean isInRange(Vector position)
    {
        return position.x >= 0 && position.x < rowCount &&
               position.y >= 0 && position.y < columnCount;
    }

    public void nextState(Vector head, Vector tail)
    {
        Vector nextPosition = head.add(direction);

        if (isInRange(nextPosition))
        {

            //Implement rest of next state logic, as we won't get out of bounds here.
            if (board[nextPosition.x][nextPosition.y] == null){
                board[head.x][head.y] = null;
                //opdatere hoved
                head = head.add(direction);
                board[head.x][head.y] = new Tile();
                //board[head.x][head.y].tileType = TileType.Snakehead;

                board[tail.x][tail.y] = null;
                //opdatere hale
                tail = tail.add(direction);
                board[tail.x][tail.y] = new Tile();
                //board[tail.x][tail.y].tileType = TileType.Snaketail;
            }
            else {
                gameOver();
            }

        }
        else {
            gameOver();
        }   
    }

    public void setDirection(Vector direction)
    {
        this.direction = direction;
    }

    public void gameOver(){
        //game over
    }
    
}
