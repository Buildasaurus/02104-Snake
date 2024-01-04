package com.snake.Model;

public class Apple extends Fruit
{
    public Vector applePosition;

    public Apple(Vector applePosition)
    {
        super(TileType.Apple);
        this.applePosition = applePosition;
    }

    public void getapplePosition()
    {

    }

}
