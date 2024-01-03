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


    public GameModel(int rowCount, int columnCount) {

        this.rowCount = rowCount;
        this.columnCount = columnCount;
        head = new Vector(rowCount/2, columnCount/2); //snake start position
        tail = new Vector(rowCount/2, columnCount/2 + 1);
        direction = new Vector(1, 0); //initializing direction as right
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
        Vector nextPosition = head.add(direction);

        if (isInRange(nextPosition))
        {

            //Implement rest of next state logic, as we won't get out of bounds here.
            if (board[nextPosition.x][nextPosition.y] == null){
                board[head.x][head.y] = null;
                //opdatere hoved
                head = head.add(direction);
                board[head.x][head.y] = new Tile(TileType.Snakehead, head, nextPosition);

                board[tail.x][tail.y] = null;
                //opdatere hale
                tail = tail.add(direction);
                board[tail.x][tail.y] = new Tile(TileType.Snaketail,head, nextPosition);
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
    
    public Tile[][] getBoard()
    {
        return board;
    }

    public int getSpeed()
    {
        return speed;
    }
}
