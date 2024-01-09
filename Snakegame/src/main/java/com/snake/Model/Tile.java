package com.snake.Model;

import javafx.scene.image.ImageView;

public abstract class Tile
{
    protected TileType tileType;

    public Tile(TileType type)
    {
        tileType = type;
    }

    public abstract ImageView getImage();

    public boolean equals()
    {
        return false;
    }


}
