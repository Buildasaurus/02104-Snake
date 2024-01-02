package com.snake.Model;

import java.io.IOException;
import javafx.fxml.FXML;
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

    public Color getImage()
    {
        return Color.BLACK;
    }

    public boolean equals()
    {

        return false;
    }

}
