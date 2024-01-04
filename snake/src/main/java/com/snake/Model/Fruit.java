package com.snake.Model;

import java.net.URL;
import java.util.Random;
import com.snake.Settings;

import javafx.scene.image.Image;

public abstract class Fruit extends Tile
{
    protected Vector fruitposition;

    public Fruit(TileType type)
    {
        super(type);
        setRandomPosition();
    }

    public Vector getPosition()
    {
        return fruitposition;
    }

    public void setRandomPosition()
    {
        Random randint = new Random();
        fruitposition = new Vector(randint.nextInt(Settings.columnCount), randint.nextInt(Settings.rowCount));
    }

    public Image getImage()
    {
        if (sprite == null){
            String imageName = tileType.toString().toLowerCase();
            URL url = getClass().getResource("/com/snake/Graphics/" + imageName + ".png");
            sprite = new Image(url.toString());
        }
        return sprite;
    }

}
