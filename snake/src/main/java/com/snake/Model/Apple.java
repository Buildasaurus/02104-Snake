package com.snake.Model;

public class Apple extends Fruit
{

    public Apple()
    {
        super(TileType.Apple);
    }

    public void doEffect(Snake snake)
    {
        snake.setSpeed(2);
    }

}
