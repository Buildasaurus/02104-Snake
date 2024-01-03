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
                URL url = getClass().getResource("/com/snake/Graphics/" + imageName + ".png");
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

    /**
     * Returns one of eight values, straightright, straightup, straightdown, straightleft,
     * turnright, turnup, turnleft, turndown Here the turns, eg turn up, is coming from the right,
     * then going upwards, always 90 degrees. Assuming that the two directions aren't opposite of
     * each other.
     *
     * @param firstDirection
     * @param secondDirection
     * @return
     */
    private String getDirectionName(Vector firstDirection, Vector secondDirection)
    {
        int crossProduct = firstDirection.crossProduct(secondDirection);
        System.out.println("crossproduct between " + firstDirection + " and " + secondDirection + " is " + crossProduct);
        switch (crossProduct)
        {

            case 0:
                // They are parallel, so now we can figure out in which direction
                if (firstDirection.x == 1 && firstDirection.y == 0)
                    return "right";
                else if (firstDirection.x == -1 && firstDirection.y == 0)
                    return "left";
                else if (firstDirection.x == 0 && firstDirection.y == 1)
                    return "up";
                else if (firstDirection.x == 0 && firstDirection.y == -1)
                    return "down";
                else
                {
                    System.out.println("vectors are weird, or something is wrong.");
                    return "error";
                }
            case 1:
                System.out.println("left turn");
                Vector temp = firstDirection.multiply(-1);
                firstDirection = secondDirection.multiply(-1);
                secondDirection = temp;
            case -1:
                // They are parallel, so now we can figure out in which direction
                if (firstDirection.x == 1 && firstDirection.y == 0)
                    return "turn_down";
                else if (firstDirection.x == -1 && firstDirection.y == 0)
                    return "turn_up";
                else if (firstDirection.x == 0 && firstDirection.y == 1)
                    return "turn_right";
                else if (firstDirection.x == 0 && firstDirection.y == -1)
                    return "turn_left";
                else
                {
                    System.out.println("vectors are weird, or something is wrong.");
                    System.out.println(firstDirection.toString() + " " + secondDirection);
                    return "error";
                }

            default:
                System.out.println("vectors are weird, or something is wrong.");
                System.out.println(firstDirection.toString() + " " + secondDirection);

                return "";
        }
    }
}
