package com.snake.Model;

public class Apple extends Fruit
{
    private Vector applePosition;

    public Apple(Vector applePosition)
    {
        super(TileType.Apple);
        this.applePosition = applePosition;
    }

    public Vector getapplePosition()
    {
        return applePosition;
    }

}
