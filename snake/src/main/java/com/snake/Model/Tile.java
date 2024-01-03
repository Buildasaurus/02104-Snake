package com.snake.Model;

import java.io.FileInputStream;
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
                URL url = App.class.getResource("/resources/" + imageName + ".png");

                FileInputStream fis = new FileInputStream(imageName + ".png");
                sprite = new Image(fis);
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
