package com.snake.Model;

import com.snake.Utils.Resources;

public class Apple extends Fruit
{
    public Apple()
    {
        super(TileType.Apple);
    }

    public void doEffect(Snake snake)
    {
        snake.grow();
    }

    public void playSound()
    {
        Resources.playSoundByName("EatAppleSound", 1);
    }
}
