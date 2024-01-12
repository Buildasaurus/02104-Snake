package com.snake.Model;

import java.util.Random;
import com.snake.Settings;
import com.snake.Utils.Resources;
import javafx.scene.image.ImageView;

//Everyone has done things in here at some point.

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
        ImageView sprite = new ImageView(Resources.getImageByName(imageName));
        return sprite;
    }

}
