package com.snake.Model;

public class Cherry extends Fruit
{
    public Cherry()
    {
        super(TileType.Cherry);
    }

    public void doEffect(Snake snake)
    {
        snake.setSpeed(snake.getSpeed()*2);
        snake.updateSnakePosition();
    }
}
