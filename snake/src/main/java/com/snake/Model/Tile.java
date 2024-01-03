package com.snake.Model;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import com.snake.App;
import javafx.scene.image.Image;

public class Tile
{
    TileType tileType;
    Image sprite;

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
        if (sprite == null)
        {
            try
            {
                String imageName = tileType.toString().toLowerCase();
                URL url = getClass().getResource("/com/snake/Graphics/" + imageName + ".png");
                sprite = new Image(url.toString());
            }
            catch (Exception e)
            {
                System.out.println("Image not found");
            }
        }
        return sprite;
    }



    public boolean equals()
    {

        return false;
    }

}
