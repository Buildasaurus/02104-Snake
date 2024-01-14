package com.snake.Model;

import javafx.scene.image.ImageView;

// Made by everyone

public abstract class Tile
{
    protected TileType tileType;
    protected Vector position;

    /**
     * Represents a tile, expects the TileType as input
     *
     * @param type The tileType
     */
    public Tile(TileType type)
    {
        tileType = type;
    }

    /**
     * @return An image representative of the current tile
     */
    public abstract ImageView getImage();

    public Vector getPosition()
    {
        return position;
    }

    public boolean equals()
    {
        return false;
    }


}
