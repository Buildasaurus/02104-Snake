package com.snake.Model;

import java.net.URL;
import java.util.HashMap;
import javafx.scene.image.Image;

public class LoadedImages
{
    // TODO, write static code to get an image, by a string
    private static HashMap<String, Image> map = new HashMap<String, Image>();

    public static Image getImageByName(String name)
    {
        if (!map.containsKey(name))
        {
            URL url = LoadedImages.class.getResource("/com/snake/Graphics/" + name + ".png");
            Image image = new Image(url.toString());
            map.put(name, image);
        }
        return map.get(name);
    }
}
