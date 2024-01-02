package com.snake.Model;

import javafx.scene.image.Image;

public class Tile
{

    TileType tileType;

    public Tile(TileType type)
    {
        tileType = type;
    }

    public Tile()
    {
        tileType = TileType.Empty;
    }

    public Image getImage()
    {
        return null;
    }

    public boolean equals()
    {

        return false;
    }

}
