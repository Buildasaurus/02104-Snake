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
    Vector enterDirection;
    Vector targetDirection;

    public Tile(TileType type, Vector enterDirection, Vector targetDirection)
    {
        tileType = type;
        this.enterDirection = enterDirection;
        this.targetDirection = targetDirection;
    }

    public Image getImage()
    {
        if (sprite == null)
        {
            try
            {
                String imageName = tileType.toString().toLowerCase() + "_"
                        + getDirectionName(enterDirection, targetDirection);
                System.out.println(imageName);
                URL url = getClass().getResource("/com/snake/Graphics/" + imageName + ".png");
                System.out.println(url);
                sprite = new Image(url.toString());
            }
            catch (Exception e)
            {
                System.out.println(e);
                System.out.println("Image not found");
            }
        }
        return sprite;
    }


    public boolean equals()
    {
        return false;
    }

    private String getDirectionName(Vector firstDirection, Vector secondDirection)
    {
        int crossProduct = firstDirection.crossProduct(secondDirection);
        switch (crossProduct)
        {
            case 1:
                return "right";
            case 0:
                return "up";
            case -1:
                return "left";
            default:
                System.out.println("vectors are weird, or something is wrong.");
                return "";
        }
    }
}
