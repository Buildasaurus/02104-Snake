package com.snake.Model;

import java.util.Random;
import com.snake.Settings;

import javafx.scene.image.Image;

public class Fruit extends Tile
{
    private static final Image Image = null;
    Vector fruitposition;
    
    public Fruit()
    { 
        super(TileType.Apple);
        randFruit();
    }

    public Vector getfruitPosition(){    
        return fruitposition;
    }

    public void randFruit()
    {
        Random randint = new Random();
        fruitposition.x = randint.nextInt(Settings.columnCount);
        fruitposition.y = randint.nextInt(Settings.rowCount);
    }
    public Image getImage(){
        
        return Image;
    }

}