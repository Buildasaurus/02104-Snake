package com.snake.Model;

public class Cherry
{

    public Cherry(TileType Cherry)
    {
        super();
    }

    public void doEffect(Snake snake)
    {
        snake.setSpeed(2);
    }
}
