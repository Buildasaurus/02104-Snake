package com.snake.Model;

import java.net.URL;
import javafx.scene.image.Image;

public abstract class Tile
{
    protected TileType tileType;
    protected Image sprite;

    public Tile(TileType type)
    {
        tileType = type;
    }

    public abstract Image getImage();

    public boolean equals()
    {
        return false;
    }


}
