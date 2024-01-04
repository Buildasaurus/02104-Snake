package com.snake.Model;

import java.util.Random;
import com.snake.Settings;

import javafx.scene.image.Image;

public abstract class Fruit extends Tile
{
    private static final Image Image = null;
    Vector fruitposition;

    public Fruit(TileType type)
    {
        super(type);
        randFruit();
    }

    public Vector getfruitPosition()
    {
        return fruitposition;
    }

    public void randFruit()
    {
        Random randint = new Random();
        fruitposition = new Vector(randint.nextInt(Settings.columnCount), randint.nextInt(Settings.rowCount));
    }

    public Image getImage()
    {

        return Image;
    }

}
