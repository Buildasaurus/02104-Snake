package com.snake.Model;

import com.snake.Utils.Resources;

// Made by Kajsa Berlstedt

public class Cherry extends Fruit
{
    public Cherry()
    {
        super(TileType.Cherry);
    }

    public void doEffect(Snake snake)
    {
        snake.setSpeed(snake.getSpeed() + 3);
        snake.updateSnakePosition();
    }

    public void playSound()
    {
        Resources.playSoundByName("EatAppleSound", 1);
    }
}
