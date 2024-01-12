package com.snake.Model;

import com.snake.Utils.Resources;
import javafx.scene.image.ImageView;

public class Wall extends Tile
{
    public Wall()
    {
        super(TileType.Wall);
    }

    public ImageView getImage()
    {
        return new ImageView(Resources.getImageByName("wall"));
    }
}
