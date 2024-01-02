package com.snake.Model;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

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
