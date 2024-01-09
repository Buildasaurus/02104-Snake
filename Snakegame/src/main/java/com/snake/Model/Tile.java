package com.snake.Model;

import javafx.scene.image.ImageView;

public abstract class Tile
{
    protected TileType tileType;
    protected transient ImageView sprite;

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
