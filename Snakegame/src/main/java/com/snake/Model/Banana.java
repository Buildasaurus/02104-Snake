package com.snake.Model;

// Made by Marinus Juhl

public class Banana extends Fruit {
    public Banana() {
        super(TileType.Banana);
    }

    public void doEffect(Snake snake) {
        snake.setSpeed(snake.getSpeed() + 1);
        snake.updateSnakePosition();
    }

    public void playSound() {
        Resources.playSoundByName("EatAppleSound");
    }
}
