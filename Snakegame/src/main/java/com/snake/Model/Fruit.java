package com.snake.Model;

import java.net.URL;
import java.util.Random;
import com.snake.Settings;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Fruit extends Tile
{
    public Fruit(TileType type)
    {

        super(type);
        setRandomPosition();
    }

    public abstract void playSound();

    public abstract void doEffect(Snake snake);

    public void setRandomPosition()
    {
        Random randint = new Random();
        position = new Vector(randint.nextInt(Settings.getGameSettings().getColumnCount()),
                randint.nextInt(Settings.getGameSettings().getRowCount()));
    }

    public ImageView getImage()
    {
        String imageName = tileType.toString().toLowerCase();
        URL url = getClass().getResource("/com/snake/Graphics/" + imageName + ".png");
        ImageView sprite = new ImageView(new Image(url.toString()));
        return sprite;
    }

}
