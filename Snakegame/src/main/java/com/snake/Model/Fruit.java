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
        position = new Vector(randint.nextInt(Settings.columnCount),
                randint.nextInt(Settings.rowCount));
    }

    public ImageView getImage()
    {
        if (sprite == null)
        {
            String imageName = tileType.toString().toLowerCase();
            URL url = getClass().getResource("/com/snake/Graphics/" + imageName + ".png");
            sprite = new ImageView(new Image(url.toString()));
        }
        return sprite;
    }

}
